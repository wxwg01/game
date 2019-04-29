/**
 * 用一句话描述该文件做什么
 *
 * @title RedisCacheConfiguration.java
 * @package com.igen.command.receive.factory
 * @author lvjian
 * @create 2017-6-19 下午1:52:20
 * @version V1.0
 */
package com.game.service.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 这里用一句话描述这个类的作用
 * @create 2017-6-19 下午1:52:20
 * @version 1.0
 */
@Configuration
@EnableCaching
public class RedisCacheConfiguration extends CachingConfigurerSupport {
    Logger logger = LoggerFactory.getLogger(RedisCacheConfiguration.class);

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.timeout}")
    private int timeout;

    @Value("${spring.redis.pool.max-idle}")
    private int maxIdle;

    @Value("${spring.redis.pool.max-wait}")
    private long maxWaitMillis;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.pool.max-active}")
    private Integer maxActives;

    @Value("${spring.redis.testOnBorrow}")
    private boolean testOnBorrow;

    @Bean
    public JedisPool redisPoolFactory() {
        logger.info("JedisPool注入成功！！");
        logger.info("redis地址：" + host + ":" + port);
        JedisPool jedisPool = null;
        JedisPoolConfig config = new JedisPoolConfig();

        config.setMaxTotal(maxActives);
        config.setMaxIdle(maxIdle);
        config.setMaxWaitMillis(maxWaitMillis);
        config.setTestOnBorrow(testOnBorrow);
        if (password == null || password.equals("")) {
            jedisPool = new JedisPool(config, host, port, 100000);
        } else {
            jedisPool = new JedisPool(config, host, port, 100000, password);
        }

        return jedisPool;
    }

}
