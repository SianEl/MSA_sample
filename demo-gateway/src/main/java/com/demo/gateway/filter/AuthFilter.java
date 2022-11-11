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
                        .baseUrl("http://demo.shop.bo.com:8000")
                        .build();

                // 헤더 정보 획득
                HttpHeaders headers = exchange.getRequest().getHeaders();
                // 실제 관리자의 인증체크는 web-back 프로젝트에서 처리함. -> system으로 변경진행중임.
                webClient
                        .get()
                        .uri("/api/system/adminAuth?url=" + url)
                        .headers(httpHeaders -> {            // 헤더정보에서 필요한 정보만을 이관함. 쿠키,인증토큰
                            // 쿠키이관
                            if (!CollectionUtils.isEmpty(headers.get("Cookie"))) {
                                httpHeaders.addAll("Cookie", headers.get("Cookie"));
                            }
                        })
                        .retrieve()
                        .bodyToMono(Result.class)
                        .map(r -> {
                            // 정상응답이 아니면 exception 발생
                            if (r.getErrorCode() != ErrorCode.OK.getValue()) {
                                log.error(r.getErrorMessage());
                            }
                            return exchange;
                        })
                        .subscribe(r -> {
                            
                        });
                return chain.filter(exchange);
            } else {
                return chain.filter(exchange);
            }
        });
    }

    public static class Config {

    }
}
