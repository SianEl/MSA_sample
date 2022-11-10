package com.demo.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class BackOfficeFilter extends AbstractGatewayFilterFactory<BackOfficeFilter.Config> {

    public BackOfficeFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            WebClient webClient = WebClient.create();

            return chain.filter(exchange);
        });
    }

    public static class Config {

    }
}
