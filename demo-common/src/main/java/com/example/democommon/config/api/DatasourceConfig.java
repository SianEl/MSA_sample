package com.example.democommon.config.api;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DatasourceConfig {

    /*@Bean(name="mysqlDataSource")
    @Primary
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .url("jdbc:mysql://localhost:3306/demo")
                .username("demo")
                .password("9705dltka@L")
                .build();
    }

    @Bean(name="jpaProperties")
    @Primary
    public JpaProperties jpaProperties() {
        return new JpaProperties();
    }*/
}
