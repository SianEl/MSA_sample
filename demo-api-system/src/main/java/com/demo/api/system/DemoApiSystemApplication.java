package com.demo.api.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaClient
@ComponentScan(basePackages = {"com.demo"})
public class DemoApiSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApiSystemApplication.class, args);
    }

}
