package com.demo.common.config.api;
import com.demo.common.annotation.SecondaryMapper;
import com.demo.common.config.ApplicationConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.AllArgsConstructor;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Configuration
@AllArgsConstructor
@EnableTransactionManagement
@MapperScan( value = "com.demo.api", annotationClass = SecondaryMapper.class, sqlSessionFactoryRef = "secondarySqlSessionFactory")
public class SecondaryDatasourceConfig {

    /**
     * PlatformTransactionManager 는 트랜잭션 경계를 지정하는데 사용한다. 트랜잭션이 어디서 시작하고 종료하는지, 종료할 때 정상 종료(커밋)인지 비정상 종료(롤백)인지를 결정하는 것이다.
     */
    private final PlatformTransactionManager txManager;

    /**
     * 기본이 되는 DataSource 설정
     */
    @Bean("secondaryDatasource")
    public DataSource firstDataSource(ApplicationConfig applicationConfig) {
        HikariConfig config = new HikariConfig();

        // DB 커넥션 관련 설정 - DB 기본 내용
        config.setDriverClassName(applicationConfig.getDatasource().getPrimaryDatasource().getDriverClassName()); //driver-class-name 설정
        config.setJdbcUrl(applicationConfig.getDatasource().getPrimaryDatasource().getJdbcUrl()); // jdbc-url 설정
        config.setUsername(applicationConfig.getDatasource().getPrimaryDatasource().getUsername()); // DB username 설정
        config.setPassword(applicationConfig.getDatasource().getPrimaryDatasource().getPassword()); // DB password 설정

        // DB 커넥션 관련 설정 - DB 연결 설정
        config.setAutoCommit(applicationConfig.getDatasource().getPrimaryDatasource().getHikari().isAutoCommit()); // DB auto-commit 설정 기본 true
        config.setIdleTimeout(applicationConfig.getDatasource().getPrimaryDatasource().getHikari().getIdleTimeout()); // pool에 일을 안하는 커넥션을 유지하는 시간. ms단위, minimumIdle 옵션이 maximumPoolSize보다 작게 설정되어 있을 때만 설정, 최소값은 10000ms(기본 600000)
        config.setMaxLifetime(applicationConfig.getDatasource().getPrimaryDatasource().getHikari().getMaxLifetime()); // 커넥션 풀에서 살아있을 수 있는 커넥션의 최대 수명시간, 사용중이지 않은 커넥션만 제거, 풀 전체가 아닌 커넥션 별로 적용-(이유: 풀에서 대량으로 커넥션들이 제거되는 것을 방지)
        /*
            maxLifeTime 설정 시 주의점
            강력하게 설정해야 하는 값으로 DB나 인프라의 적용된 connection time limit보다 작아야 한다.
            0으로 설정하면 infinite lifetime이 적용(idle timeout설정 값에 따라 적용)
            기본값 : 18000000 = 30minutes
         */

        config.setMinimumIdle(applicationConfig.getDatasource().getPrimaryDatasource().getHikari().getMinimumIdle()); // 아무런 일을 하지 않아도 적어도 유지해야 하는 pool 수, 기본값 : maximumPoolSize와 동일 -> 최적의 성능과 응답성을 요구한다면 이 값은 설정하지 않는게 좋다
        config.setMaximumPoolSize(applicationConfig.getDatasource().getPrimaryDatasource().getHikari().getMaximumPoolSize()); // 최대 풀 사이즈
        config.setReadOnly(applicationConfig.getDatasource().getPrimaryDatasource().getHikari().isReadOnly()); // pool에서 커넥션 획득할 때 read-only모드로 가져온다. 몇몇 DB는 read-only 모드를 지원하지 않음음
        config.setConnectionTimeout(applicationConfig.getDatasource().getPrimaryDatasource().getHikari().getConnectionTimeout()); // pool에서 커넥션을 얻어오기전까지 기다리는 최대 시간, 허용가능한 wait time을 초과하면 SQLException을 던진다. ms단위, 최소 설정시간-250ms, 기본시간-30000ms=30s

        return new HikariDataSource(config);
    }


    /**
     * transaction manager 등록??
     */
    @Bean("secondaryTransactionManager")
    public DataSourceTransactionManager firstTransactionManager(@Qualifier("secondaryDatasource") DataSource dataSource) {
        // Qualifier 어노테이션 = Bean 등록 시 name으로 설정한 값을 사용하여 동일 유형의 Component 중 어느 것을 주입할 지 설정하는 기능??
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource);
        return transactionManager;
    }

    /*
        SqlSessionFactory
     */
    @Bean("secondarySqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("secondaryDatasource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean
                .setMapperLocations(new PathMatchingResourcePatternResolver().getResources("mapper/**/*.xml"));
        sqlSessionFactoryBean.setTypeAliasesPackage("com.demo.**.entity");
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

    @Bean("secondarySqlSessionTemplate")
    @Primary
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("secondarySqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    /**
     * 트랜잭션 설정을 구성??
     */
    @Bean("secondaryTransactionInterceptor")
    @Primary
    public TransactionInterceptor txAdvice() {
        TransactionInterceptor txAdvice = new TransactionInterceptor();

        // 읽기용 속성
        DefaultTransactionAttribute readOnlyAttribute = new DefaultTransactionAttribute(TransactionDefinition.PROPAGATION_REQUIRED);
        readOnlyAttribute.setReadOnly(true); //
        readOnlyAttribute.setTimeout(10);
        String readOnlyAttributeDefinition = readOnlyAttribute.toString();

        // rollback용 속성
        List<RollbackRuleAttribute> rollbackRules = new ArrayList<>();
        rollbackRules.add(new RollbackRuleAttribute(Exception.class)); // Exception.class를 설정한 이유 -> 기본적으로는 rollback은 RunTimeException이 기준이지만 파일 업로드 시 발생하는 IOException 등도 포함시키기 위해?
        DefaultTransactionAttribute writeAttribute = new RuleBasedTransactionAttribute(TransactionDefinition.PROPAGATION_REQUIRED, rollbackRules);
        writeAttribute.setTimeout(10);
        String writeAttributeDefinition = writeAttribute.toString();

        // 위 readOnly 속성과 rollback 속성이 적용해야 하는 시점?
        Properties txAttributes = new Properties();
        txAttributes.setProperty("get*", readOnlyAttributeDefinition);
        txAttributes.setProperty("select*", readOnlyAttributeDefinition);
        txAttributes.setProperty("count*", readOnlyAttributeDefinition);
        txAttributes.setProperty("read*", readOnlyAttributeDefinition);
        txAttributes.setProperty("*", writeAttributeDefinition);

        txAdvice.setTransactionAttributes(txAttributes);
        txAdvice.setTransactionManager(txManager);

        return txAdvice;
    }

    /**
     * 트랜잭션 감지를 위한 AOP 등록?
     */
    @Bean("secondaryPointcutAdvisor")
    @Primary
    public DefaultPointcutAdvisor txAdviceAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* com.demo.*Service.*(..))");

        return new DefaultPointcutAdvisor(pointcut, txAdvice());
    }

}
