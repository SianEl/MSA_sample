package com.demo.common.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * yml 파일에서 application 하위 내용을 저장하는 Config Class
 * DB 연결을 위해 필요한 값을 변수로 적용할 수 있다.
 */

@ConfigurationProperties("application")
@Component
@Getter
@Setter
public class ApplicationConfig {
    private DataSourceMap datasource;

    public DataSourceMap getDatasource() {
        return datasource;
    }

    public void setDatasource(DataSourceMap datasource) {
        this.datasource = datasource;
    }

    @Getter
    @Setter
    public static class DataSourceMap {
        private DataSource primaryDatasource;
        private DataSource secondaryDatasource;
    }

    @Getter
    @Setter
    public static class DataSource {
        private String jdbcUrl;
        private String username;
        private String password;
        private String driverClassName;
        private Hikari hikari;
    }

    @Getter
    @Setter
    public static class Hikari {
        private boolean autoCommit;
        private long idleTimeout;
        private long maxLifetime;
        private int minimumIdle;
        private int maximumPoolSize;
        private boolean readOnly;
        private long connectionTimeout;
    }
}
