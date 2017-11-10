package com.adatafun.airportshop.order.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Redis参数对象
 *
 * @Date: 2017/7/24 上午11:42
 * @Author: ironc
 * @Version: 1.0
 */
@Component
public class RedisConfig {

    public static String keyPrefix;

    public static String host;

    public static int port;

    public static String password;

    public static int maxIdle;

    public static int maxActive;

    public static long maxWait;

    public static int timeout;

    public static boolean testOnBorrow;

    @Value("${redis.pre.fix}")
    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

    @Value("${redis.address}")
    public void setHost(String host) {
        RedisConfig.host = host;
    }

    @Value("${redis.port}")
    public void setPort(String port) {
        RedisConfig.port = Integer.parseInt(port);
    }

    @Value("${redis.password}")
    public void setPassword(String password) {
        RedisConfig.password = password;
    }

    @Value("${redis.pool.max.idle}")
    public void setMaxIdle(String maxIdle) {
        this.maxIdle = Integer.parseInt(maxIdle);
    }

    @Value("${redis.pool.max.active}")
    public void setMaxActive(String maxActive) {
        this.maxActive = Integer.parseInt(maxActive);
    }

    @Value("${redis.pool.max.wait}")
    public void setMaxWait(String maxWait) {
        this.maxWait = Long.parseLong(maxWait);
    }

    @Value("${redis.pool.timeout")
    public static void setTimeout(String timeout) {
        RedisConfig.timeout = Integer.parseInt(timeout);
    }

    @Value("${redis.pool.test.on.borrow}")
    public void setTestOnBorrow(String testOnBorrow) {
        this.testOnBorrow = Boolean.parseBoolean(testOnBorrow);
    }

}
