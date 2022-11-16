package com.demo.common.config.api;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class DatasourceConfig {

    @Value("${alis.package}")
    private String alisPackage;

    /**
     * 기본이 되는 DataSource 설정
     */
    @Primary
    @Bean(name="firstDataSource")
    public DataSource firstDataSource(ApplicationConfig applicationConfig) {
        HikariConfig config = new HikariConfig();

        // DB 커넥션 관련 설정 - DB 기본 내용
        config.setDriverClassName(applicationConfig.getDatasource().getFirstDatasource().getDriverClassName()); //driver-class-name 설정
        config.setJdbcUrl(applicationConfig.getDatasource().getFirstDatasource().getJdbcUrl()); // jdbc-url 설정
        config.setUsername(applicationConfig.getDatasource().getFirstDatasource().getUsername()); // DB username 설정
        config.setPassword(applicationConfig.getDatasource().getFirstDatasource().getPassword()); // DB password 설정

        // DB 커넥션 관련 설정 - DB 연결 설정
        config.setAutoCommit(true); // DB auto-commit 설정 기본 true
        config.setIdleTimeout(30000); // pool에 일을 안하는 커넥션을 유지하는 시간. ms단위, minimumIdle 옵션이 maximumPoolSize보다 작게 설정되어 있을 때만 설정, 최소값은 10000ms(기본 600000)
        config.setMaxLifetime(1800000); // 커넥션 풀에서 살아있을 수 있는 커넥션의 최대 수명시간, 사용중이지 않은 커넥션만 제거, 풀 전체가 아닌 커넥션 별로 적용-(이유: 풀에서 대량으로 커넥션들이 제거되는 것을 방지)
        /*
            maxLifeTime 설정 시 주의점
            강력하게 설정해야 하는 값으로 DB나 인프라의 적용된 connection time limit보다 작아야 한다.
            0으로 설정하면 infinite lifetime이 적용(idle timeout설정 값에 따라 적용)
            기본값 : 18000000 = 30minutes
         */

        config.setMinimumIdle(5); // 아무런 일을 하지 않아도 적어도 유지해야 하는 pool 수, 기본값 : maximumPoolSize와 동일 -> 최적의 성능과 응답성을 요구한다면 이 값은 설정하지 않는게 좋다
        config.setMaximumPoolSize(10); // 최대 풀 사이즈
        config.setReadOnly(false); // pool에서 커넥션 획득할 때 read-only모드로 가져온다. 몇몇 DB는 read-only 모드를 지원하지 않음음
        config.setConnectionTimeout(30000); // pool에서 커넥션을 얻어오기전까지 기다리는 최대 시간, 허용가능한 wait time을 초과하면 SQLException을 던진다. ms단위, 최소 설정시간-250ms, 기본시간-30000ms=30s

        return new HikariDataSource(config);
    }

    /**
     * 기본이 되는 DataSource 이외 별도의 DataSource가 추가되어 설정하는 부분
     */
    @Bean(name="secondDataSource")
    public DataSource SecondDataSource(ApplicationConfig applicationConfig) {
        HikariConfig config = new HikariConfig();

        config.setDriverClassName(applicationConfig.getDatasource().getSecondDatasource().getDriverClassName()); //driver-class-name 설정
        config.setJdbcUrl(applicationConfig.getDatasource().getSecondDatasource().getJdbcUrl()); // jdbc-url 설정
        config.setUsername(applicationConfig.getDatasource().getSecondDatasource().getUsername()); // DB username 설정
        config.setPassword(applicationConfig.getDatasource().getSecondDatasource().getPassword()); // DB password 설정

        return new HikariDataSource(config);
    }

    @Bean("firstTransaction")
    @Primary
    public DataSourceTransactionManager transactionManager(@Qualifier("firstDataSource") DataSource dataSource) {
        // Qualifier 어노테이션 = Bean 등록 시 name으로 설정한 값을 사용하여 동일 유형의 Component 중 어느 것을 주입할 지 설정하는 기능??
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource);
        return transactionManager;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean
                .setMapperLocations(new PathMatchingResourcePatternResolver().getResources("mapper/**/*.xml"));
        sqlSessionFactoryBean.setTypeAliasesPackage(alisPackage+".entity");
        sqlSessionFactoryBean.setTypeHandlersPackage("com.demon.common.config.api");

        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setCacheEnabled(true);
        configuration.setLazyLoadingEnabled(false);
        configuration.setMultipleResultSetsEnabled(true);
        configuration.setUseColumnLabel(true);
        configuration.setUseGeneratedKeys(false);
        configuration.setDefaultExecutorType(ExecutorType.SIMPLE);
        configuration.setDefaultStatementTimeout(7000);
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        configuration.setLogPrefix("mybatis.mapper.");
        configuration.setMapUnderscoreToCamelCase(true);
        sqlSessionFactoryBean.setConfiguration(configuration);

        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
