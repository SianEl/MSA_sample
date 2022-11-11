package com.demo.web.backoffice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaClient
@ComponentScan(basePackages = {"com.demo"})
@EnableFeignClients
public class DemoWebBackofficeApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoWebBackofficeApplication.class, args);
    }

}
