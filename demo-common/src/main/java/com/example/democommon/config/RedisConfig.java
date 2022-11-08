package com.example.democommon.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.lettuce.core.ReadFrom;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStaticMasterReplicaConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    /**
     * redisProperties
     */
    @Bean
    public RedisProperties redisProperties() { // redis관련 기본 정보??
        RedisProperties redisProperties = new RedisProperties();
        redisProperties.setHost("localhost");
        redisProperties.setPort(6379);
        redisProperties.setPassword("1q2w3e4r!");
        return redisProperties;
    }

    /**
     * redis connection 설정
     */
    @Bean
    @ConditionalOnBean(RedisProperties.class)
    public LettuceConnectionFactory lettuceConnectionFactory(RedisProperties redisProperties) {
        RedisStaticMasterReplicaConfiguration configuration = new RedisStaticMasterReplicaConfiguration(
                redisProperties.getHost(), redisProperties.getPort());

        LettuceClientConfiguration clientConfig =
                LettuceClientConfiguration.builder().readFrom(ReadFrom.REPLICA_PREFERRED).build();

        return new LettuceConnectionFactory(configuration, clientConfig);
    }

    /**
     * string용 redis 템플릿
     */
    @Bean
    public StringRedisTemplate stringRedisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        StringRedisTemplate redisTemplate = new StringRedisTemplate();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        return redisTemplate;
    }

    /**
     * 기본 object용 redis serializer
     */
    @Bean
    public RedisSerializer<Object> redisObjectSerializer() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.activateDefaultTyping(new LaissezFaireSubTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.registerModules(new JavaTimeModule());

        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
        serializer.setObjectMapper(mapper);

        return serializer;
    }

    /**
     * 기본 object용 redis 템플릿
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory,
                                                       RedisSerializer<Object> redisObjectSerializer) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(redisObjectSerializer);
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(redisObjectSerializer);

        return redisTemplate;
    }

    @Getter
    @Setter
    private class RedisProperties {
        private String host;
        private String password;
        private int port;
    }


}
