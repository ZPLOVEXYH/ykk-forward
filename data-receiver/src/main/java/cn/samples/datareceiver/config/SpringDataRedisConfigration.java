package cn.samples.datareceiver.config;

import cn.samples.datareceiver.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

@Slf4j
@Configuration
public class SpringDataRedisConfigration {

    @Value("${redis.host:}")
    private String host;
    @Value("${redis.port:5672}")
    private int port;

    @Value("${redis.maxRedirects:3}")
    private int maxRedirects;

    @Value("${redis.pass:}")
    private String password;

    // 读取超时
    @Value("${redis.timeout:5000}")
    private int timeout;

    @Value("${redis.pool.maxActive:32}")
    private int maxTotal;
    @Value("${redis.pool.maxIdle:10}")
    private int maxIdle;
    @Value("${redis.pool.maxWait:15000}")
    private long maxWaitMillis;

    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        // 最大活动连接数，如果赋值为-1，则表示不限制。
        // 注意：2.4.1之前的jedis版本为maxActive
        jedisPoolConfig.setMaxTotal(maxTotal);
        // 最大空闲连接数, 默认8个
        jedisPoolConfig.setMaxIdle(maxIdle);
        // 获取连接时的最大等待毫秒数，超过等待时间直接抛出JedisConnectionException
        // 注意：2.4.1之前的jedis版本为maxWait
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);

        return jedisPoolConfig;
    }

    @Bean(destroyMethod = "destroy")
    public JedisConnectionFactory jedisConnectionFactory() {
        if (StringUtils.isBlank(host)) {
            log.error("redis not configured!!!");
        }
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setPoolConfig(jedisPoolConfig());
        jedisConnectionFactory.setHostName(host);
        jedisConnectionFactory.setPort(port);
        jedisConnectionFactory.setTimeout(timeout); // redis读取超时时间，默认2秒
        jedisConnectionFactory.setPassword(password);

        return jedisConnectionFactory;
    }

    @Bean
    public RedisTemplate redisTemplate() {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());

        return redisTemplate;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate() {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(jedisConnectionFactory());
        return stringRedisTemplate;
    }
}
