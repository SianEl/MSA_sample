package com.example.demogateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class FilterConfig {
    //@Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/demo-front/**")
                        .filters(f -> f.addRequestHeader("front-request", "front-request-header")
                                .addResponseHeader("front-response", "front-response-header"))
                        .uri("http://localhost:8081/"))
                .route(r -> r.path("/demo-backoffice/**")
                        .filters(f -> f.addRequestHeader("backoffice-request", "backoffice-request-header")
                                .addResponseHeader("backoffice-response", "backoffice-response-header"))
                        .uri("http://localhost:8082/"))
                .build();
    }
}
