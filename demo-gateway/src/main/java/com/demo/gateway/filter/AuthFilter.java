package com.demo.gateway.filter;

import com.demo.core.model.ErrorCode;
import com.demo.core.model.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
@Slf4j
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {

    @Value("${application.auth.except-url-list}")
    private List<String> excepUrlList;

    public AuthFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            String url = exchange.getRequest().getURI().getPath();

            boolean isAuthCheck = true;

            if(!CollectionUtils.isEmpty(excepUrlList)) {
                isAuthCheck = excepUrlList.stream().noneMatch(chkUrl -> StringUtils.equals(chkUrl, url));
            }

            if (isAuthCheck) {
                WebClient webClient = WebClient
                        .builder()
                        .baseUrl("http://demo.shop.bo.com:8091")
                        .build();

                // 헤더 정보 획득
                HttpHeaders headers = exchange.getRequest().getHeaders();

                return webClient
                        .get()
                        .uri("/system/adminAuth?url=" + url)
                        .headers(httpHeaders -> {
                            // 쿠키에 저장된 관리자용 sessionToken 으로 인증
                            if (!CollectionUtils.isEmpty(headers.get("Cookie"))) {
                                httpHeaders.addAll("Cookie", headers.get("Cookie"));
                            }
                        })
                        .retrieve()
                        .bodyToMono(Result.class)
                        .map(r -> {
                            // 정상응답이 아니면 exception 발생
                            if (r.getErrorCode() == ErrorCode.INACCESSIBLE.getValue()) {
                                log.error(r.getErrorMessage());
                                throw new RuntimeException("접근권한이 없습니다.");
                            }
                            else if (r.getErrorCode() != ErrorCode.OK.getValue()) {
                                log.error(r.getErrorMessage());
                                throw new RuntimeException("로그인이 되어 있지 않습니다.");
                            }
                            return exchange;
                        })
                        .flatMap(chain::filter);
            } else {
                return chain.filter(exchange);
            }
        });
    }

    public static class Config {

    }
}
