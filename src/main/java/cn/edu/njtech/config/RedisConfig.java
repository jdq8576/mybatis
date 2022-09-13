package cn.edu.njtech.config;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.io.Serializable;

/**
 * @author tim
 * @date 2022/9/13 1:05 下午
 */
@Configuration
public class RedisConfig {

    @Bean(name = "jedisPoolConfig")
    public JedisPoolConfig getJedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(300);
        jedisPoolConfig.setMaxWaitMillis(10000);
        jedisPoolConfig.setMaxIdle(100);
        return jedisPoolConfig;
    }

    @Bean(name = "jedisConnectionFactory")
    public JedisConnectionFactory getJedisConnectionFactory(JedisPoolConfig jedisPoolConfig) {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName("127.0.0.1");
        redisStandaloneConfiguration.setPort(6379);
        redisStandaloneConfiguration.setDatabase(0);
        JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jedisPoolConfigBuilder =
                (JedisClientConfiguration.JedisPoolingClientConfigurationBuilder) JedisClientConfiguration.builder();
        jedisPoolConfigBuilder.poolConfig(jedisPoolConfig);

        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisStandaloneConfiguration);
        return jedisConnectionFactory;
    }

    @Bean(name = "redisTemplate")
    public RedisTemplate<Serializable, Serializable> getRedisTemplate(
            JedisConnectionFactory jedisConnectionFactory) {
        RedisTemplate<Serializable, Serializable> template = new RedisTemplate<Serializable, Serializable>();
        FastJsonRedisSerializer fastJsonRedisSerializer = new FastJsonRedisSerializer(Object.class);

        // value值的序列化采用fastJsonRedisSerializer
        template.setValueSerializer(fastJsonRedisSerializer);
        template.setHashValueSerializer(fastJsonRedisSerializer);
        // key的序列化采用StringRedisSerializer
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());

        template.setConnectionFactory(jedisConnectionFactory);
        return template;
    }

    public StringRedisTemplate getStringRedisTemplate(
            RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

}
