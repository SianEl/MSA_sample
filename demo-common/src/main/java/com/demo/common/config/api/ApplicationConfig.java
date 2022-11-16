package com.demo.common.config.api;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * yml 파일에서 application 하위 내용을 저장하는 Config Class
 * DB 연결을 위해 필요한 값을 변수로 적용할 수 있다.
 */
@Configuration
@ConfigurationProperties("application")
public class ApplicationConfig {
    private DataSourceMap datasource;

    public DataSourceMap getDatasource() {
        return datasource;
    }

    public void setDatasource(DataSourceMap datasource) {
        this.datasource = datasource;
    }

    public static class DataSourceMap {
        private DataSource firstDatasource;
        private DataSource secondDatasource;

        public DataSource getFirstDatasource() {
            return firstDatasource;
        }

        public void setFirstDatasource(DataSource firstDatasource) {
            this.firstDatasource = firstDatasource;
        }

        public DataSource getSecondDatasource() {
            return secondDatasource;
        }

        public void setSecondDatasource(DataSource secondDatasource) {
            this.secondDatasource = secondDatasource;
        }
    }

    public static class DataSource {
        private String jdbcUrl;
        private String username;
        private String password;
        private String driverClassName;

        public String getJdbcUrl() {
            return jdbcUrl;
        }

        public void setJdbcUrl(String jdbcUrl) {
            this.jdbcUrl = jdbcUrl;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getDriverClassName() {
            return driverClassName;
        }

        public void setDriverClassName(String driverClassName) {
            this.driverClassName = driverClassName;
        }
    }
}
