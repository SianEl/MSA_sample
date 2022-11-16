package com.demo.common.config.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Slf4j
@Configuration
@RequiredArgsConstructor
/**
 * 트랜잭션의 내용을 구성하는 class
 */
public class TransactionConfig {
    /**
     * PlatformTransactionManager 는 트랜잭션 경계를 지정하는데 사용한다. 트랜잭션이 어디서 시작하고 종료하는지, 종료할 때 정상 종료(커밋)인지 비정상 종료(롤백)인지를 결정하는 것이다.
     */
    private final PlatformTransactionManager txManager;
    private static final int TX_METHOD_TIMEOUT = 3;

    /**
     * 트랜잭션 설정을 구성??
     */
    @Bean
    public TransactionInterceptor txAdvice() {
        TransactionInterceptor txAdvice = new TransactionInterceptor();

        // 읽기용 속성
        DefaultTransactionAttribute readOnlyAttribute = new DefaultTransactionAttribute(TransactionDefinition.PROPAGATION_REQUIRED);
        readOnlyAttribute.setReadOnly(true); //
        readOnlyAttribute.setTimeout(TX_METHOD_TIMEOUT);
        String readOnlyAttributeDefinition = readOnlyAttribute.toString();

        // rollback용 속성
        List<RollbackRuleAttribute> rollbackRules = new ArrayList<>();
        rollbackRules.add(new RollbackRuleAttribute(Exception.class)); // Exception.class를 설정한 이유 -> 기본적으로는 rollback은 RunTimeException이 기준이지만 파일 업로드 시 발생하는 IOException 등도 포함시키기 위해?
        DefaultTransactionAttribute writeAttribute = new RuleBasedTransactionAttribute(TransactionDefinition.PROPAGATION_REQUIRED, rollbackRules);
        writeAttribute.setTimeout(TX_METHOD_TIMEOUT);
        String writeAttributeDefinition = writeAttribute.toString();

        // 위 readOnly 속성과 rollback 속성이 적용해야 하는 시점?
        Properties txAttributes = new Properties();
        txAttributes.setProperty("get*", readOnlyAttributeDefinition);
        txAttributes.setProperty("select*", readOnlyAttributeDefinition);
        txAttributes.setProperty("count*", readOnlyAttributeDefinition);
        txAttributes.setProperty("*", writeAttributeDefinition);

        txAdvice.setTransactionAttributes(txAttributes);
        txAdvice.setTransactionManager(txManager);

        return txAdvice;
    }

    /**
     * 트랜잭션 감지를 위한 AOP 등록?
     */
    @Bean
    public DefaultPointcutAdvisor txAdviceAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(com.demo.*Service.*(..))");

        return new DefaultPointcutAdvisor(pointcut, txAdvice());
    }
}
