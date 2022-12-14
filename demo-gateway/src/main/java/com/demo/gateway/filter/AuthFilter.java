package com.demo.gateway.filter;

import com.demo.core.model.ErrorCode;
import com.demo.core.model.response.Result;
import com.demo.gateway.exception.AdminAuthException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Objects;

@Component
@Slf4j
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {

    @Value("${application.web.back-office.access-key}")
    private String accessKey;

    @Value("${application.web.back-office.access-value}")
    private String accessValue;

    @Value("${application.auth.except-url-list}")
    private List<String> excepUrlList;

    @Autowired
    private ReactorLoadBalancerExchangeFilterFunction loadBalancerExchangeFilter;


    public AuthFilter() {
        super(Config.class);
    }

    @Data
    public static class Config {

    }

    @Override
    public GatewayFilter apply(Config config) {

        return ((exchange, chain) -> {
            String url = exchange.getRequest().getURI().getPath();

            boolean isAuthCheck = true;

            // 헤더 정보 획득
            HttpHeaders headers = exchange.getRequest().getHeaders();

            if(!CollectionUtils.isEmpty(excepUrlList)) {
                isAuthCheck = excepUrlList.stream().noneMatch(chkUrl -> StringUtils.equals(chkUrl, url));
            }

            if (isAuthCheck) {
                WebClient webClient = WebClient
                        .builder()
                        .filter(this.loadBalancerExchangeFilter)
                        .baseUrl("lb://DEMO-API-SYSTEM")
                        .build();

                String api = ""; //요청 api 획득
                try {
                    api = exchange.getRequest().getPath().value();
                } catch (Exception e) {
                    throw new AdminAuthException("요청 api 정보를 구성하지 못했습니다.");
                }

                return webClient
                        .get()
                        .uri("/system/adminAuth?api=" + api)
                        .headers(httpHeaders -> {
                            // 쿠키에 저장된 관리자용 sessionToken 으로 인증
                            if (!CollectionUtils.isEmpty(headers.get("Cookie"))) {
                                httpHeaders.addAll("Cookie", Objects.requireNonNull(headers.get("Cookie")));
                            }

                            // access 토큰 설정
                            httpHeaders.add(accessKey, accessValue);
                        })
                        .retrieve()
                        .bodyToMono(Result.class)
                        .map(r -> {
                            // 정상응답이 아니면 exception 발생
                            if (r.getErrorCode() == ErrorCode.INACCESSIBLE.getValue()) {
                                log.error(r.getErrorMessage());
                                throw new AdminAuthException("접근권한이 없습니다.");
                            }
                            else if (r.getErrorCode() != ErrorCode.OK.getValue()) {
                                log.error(r.getErrorMessage());
                                throw new AdminAuthException("로그인이 되어 있지 않습니다.");
                            }
                            return exchange;
                        })
                        .flatMap(chain::filter);
            } else {
                return chain.filter(exchange);
            }
        });
    }


}
