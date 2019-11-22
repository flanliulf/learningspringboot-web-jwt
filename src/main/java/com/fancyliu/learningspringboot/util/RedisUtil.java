package com.fancyliu.learningspringboot.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * 类描述:
 * StringRedisTemplate的封装工具类
 *
 * @author : Liu Fan
 * @date : 2019/11/22 16:23
 */
@Component
@Slf4j
public class RedisUtil {

    public static RedisUtil redisUtil;
    @Autowired
    public StringRedisTemplate redisTemplate;

    /**
     * 此方法只在 Spring启动时 加载一次
     */
    @PostConstruct
    public void init() {
        log.info("redis initing...");
        redisUtil = this;
        redisUtil.redisTemplate = this.redisTemplate;
    }

    /**
     * redis存入数据和设置缓存时间
     *
     * @param key   键
     * @param value 值
     * @param time  秒
     */
    public void set(String key, String value, long time) {
        log.info("redis set value={} with key={}, expire time in {} seconds", value, key, time);
        redisUtil.redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
    }

    /**
     * redis存入数据
     *
     * @param key   键
     * @param value 值
     */
    public void set(String key, String value) {
        log.info("redis set value={} with key={}", value, key);
        redisUtil.redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 根据 key 获取过期时间
     *
     * @param key
     * @return
     */
    public Long getExpire(String key) {
        return redisUtil.redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断 key 是否存在
     *
     * @param key
     * @return
     */
    public Boolean hasKey(String key) {
        return redisUtil.redisTemplate.hasKey(key);
    }

    /**
     * 根据 key 设置过期时间
     *
     * @param key  key
     * @param time 秒
     * @return
     */
    public Boolean expire(String key, long time) {
        return redisUtil.redisTemplate.expire(key, time, TimeUnit.SECONDS);
    }
}
