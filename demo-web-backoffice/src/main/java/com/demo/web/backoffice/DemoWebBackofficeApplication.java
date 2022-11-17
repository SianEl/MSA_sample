package com.demo.web.backoffice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
@EnableEurekaClient
@ComponentScan(basePackages = {"com.demo"},  excludeFilters = @ComponentScan.Filter(type = FilterType.ASPECTJ, pattern = "com.demo.common.config.api.*"))
@EnableFeignClients
// 패키지를 기반으로 ConfigurationProperties가 등록된 클래스들을 찾아 값들을 주입하고 빈으로 등록 - 스캔을 진행할 패키지를 지정하지 않을 시 어노테이션이 붙은 패키지와 그 하위 패키지에 대해 스캔을 진행
public class DemoWebBackofficeApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoWebBackofficeApplication.class, args);
    }

}
