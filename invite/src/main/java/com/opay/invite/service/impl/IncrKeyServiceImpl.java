package com.opay.invite.service.impl;

import com.opay.invite.service.IncrKeyService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class IncrKeyServiceImpl implements IncrKeyService {

    @Resource(name = "incrKey")
    private DefaultRedisScript<Long> incrKey;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public String getIncrKey() {
        return getIncrKey(null);
    }

    @Override
    public String getIncrKey(String keyPrefix) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String key;
        if (StringUtils.isNotBlank(keyPrefix)) {
            key = keyPrefix + ":" + sdf.format(date);
        } else {
            key = sdf.format(date);
        }
        List<String> keys = Arrays.asList(key);
        Long incr = (Long) redisTemplate.execute(incrKey, keys, getSecondsToMidnight(date));
        return key + incr;
    }

    private long getSecondsToMidnight(Date date) {
        LocalDateTime midnight = LocalDateTime.ofInstant(date.toInstant(),
                ZoneId.systemDefault()).plusDays(1).withHour(0).withMinute(0)
                .withSecond(0).withNano(0);
        LocalDateTime currentDateTime = LocalDateTime.ofInstant(date.toInstant(),
                ZoneId.systemDefault());
        long seconds = ChronoUnit.SECONDS.between(currentDateTime, midnight);
        return seconds;
    }
}
