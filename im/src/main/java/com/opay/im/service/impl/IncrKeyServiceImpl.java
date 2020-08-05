package com.opay.im.service.impl;

import com.opay.im.service.IncrKeyService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import java.util.TimeZone;

@Service
public class IncrKeyServiceImpl implements IncrKeyService {

    @Resource(name = "incrKey")
    private DefaultRedisScript<Long> incrKey;
    @Autowired
    private RedisTemplate redisTemplate;
    @Value("${spring.jackson.time-zone}")
    private String timeZone;

    @Override
    public String getIncrKey() {
        return getIncrKey(null);
    }

    @Override
    public String getIncrKey(String keyPrefix) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dateYMD = sdf.format(date);
        String key;
        if (StringUtils.isNotBlank(keyPrefix)) {
            key = keyPrefix + ":" + dateYMD;
        } else {
            key = dateYMD;
        }
        List<String> keys = Arrays.asList(key);
        Long incr = (Long) redisTemplate.execute(incrKey, keys, getSecondsToMidnight(date));
        return dateYMD + incr;
    }

    private long getSecondsToMidnight(Date date) {
        LocalDateTime midnight = LocalDateTime.ofInstant(date.toInstant(),
                TimeZone.getTimeZone(timeZone).toZoneId()).plusDays(1).withHour(0).withMinute(0)
                .withSecond(0).withNano(0);
        LocalDateTime currentDateTime = LocalDateTime.ofInstant(date.toInstant(),
                TimeZone.getTimeZone(timeZone).toZoneId());
        long seconds = ChronoUnit.SECONDS.between(currentDateTime, midnight);
        return seconds;
    }
}
