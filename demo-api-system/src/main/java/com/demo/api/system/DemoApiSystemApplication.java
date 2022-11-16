package com.demo.api.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
@EnableEurekaClient
@ComponentScan(basePackages = {"com.demo"},  excludeFilters = @ComponentScan.Filter(type = FilterType.ASPECTJ, pattern = "com.demo.common.config.web.*"))
// com.demo 패키지 하위의 모든 Component를 로딩하는데 그 중 common.config.web 하위의 Component, Config는 로딩을 제외
@ConfigurationPropertiesScan({"com.demo.common.config.api", "com.demo.api"})
// 패키지를 기반으로 ConfigurationProperties가 등록된 클래스들을 찾아 값들을 주입하고 빈으로 등록 - 스캔을 진행할 패키지를 지정하지 않을 시 어노테이션이 붙은 패키지와 그 하위 패키지에 대해 스캔을 진행
public class DemoApiSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApiSystemApplication.class, args);
    }

}
