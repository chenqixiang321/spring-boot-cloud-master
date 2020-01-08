package com.opay.invite.utils;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * redis 操作工具类
 *
 * @author liuzhihang
 * @date 2020/1/8 13:08
 */
@Slf4j
@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 设置对应的参数值，要设置不同的数据类型，从而可以转换为对应的参数类型
     *
     * @param keyPre
     * @param key
     * @param t
     * @param <T>
     */
    public <T> void set(String keyPre, String key, T t) {
        String realKey = keyPre + key;
        redisTemplate.opsForValue().set(realKey, t);
    }

    /**
     * 设置对应的参数值，要设置不同的数据类型，从而可以转换为对应的参数类型
     *
     * @param keyPre
     * @param key
     * @param t
     * @param <T>
     */
    public <T> void set(String keyPre, String key, T t, long time) {
        if (time > 0) {
            String realKey = keyPre + key;
            redisTemplate.opsForValue().set(realKey, t, time, TimeUnit.SECONDS);
        } else {
            set(keyPre, key, t);
        }
    }

    /**
     * 原子递增
     *
     * @param keyPre
     *            key的前缀
     * @param delta
     *            要增加的步长
     * @return 返回增加之后的值
     */
    public Long incr(String keyPre, String key, long delta) throws Exception {
        if (delta < 0) {
            log.error("RedisUtil.incr|原子递增请求参数不能小于0");
            throw new Exception("redis 增加失败, 增加大小不能小于0");
        }
        String realKey = keyPre + key;
        return redisTemplate.opsForValue().increment(realKey, delta);
    }

    /**
     * 原子递减
     *
     * @param keyPre
     *            key的前缀
     * @param key
     *            key本身
     * @param delta
     *            要减少几个
     * @return
     */
    public Long decr(String keyPre, String key, long delta) throws Exception {
        if (delta < 0) {
            log.error("RedisUtil.decr|原子递减请求参数不能小于0");
            throw new Exception("redis 扣减失败, 扣减大小不能小于0");
        }
        String realKey = keyPre + key;
        return redisTemplate.opsForValue().increment(realKey, -delta);
    }

    /**
     * 设置对应的参数值，要设置不同的数据类型，从而可以转换为对应的参数类型
     * @param keyPre
     * @param key
     * @return
     */
    public Integer get(String keyPre, String key) {
        String realKey = keyPre + key;
        Object o = redisTemplate.opsForValue().get(realKey);
        if (o == null) {
            return 0;
        }
        return (Integer)o;
    }


}
