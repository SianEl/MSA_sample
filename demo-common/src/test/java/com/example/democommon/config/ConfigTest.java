package com.example.democommon.config;

import com.example.democommon.config.factory.YamlLoadFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix = "spring.datasource.hikari") //갖고 오는 내용의 앞부분 설정
@PropertySource(value = "application.yml", factory = YamlLoadFactory.class) // application.properties가 아닌 application.yml의 내용을 갖고오도록 하는 annotation
public class ConfigTest {

    private String username;
    private String password;

    @Test
    void dataSourceConfig() {

    }
}
