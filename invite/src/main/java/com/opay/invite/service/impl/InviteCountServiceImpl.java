package com.opay.invite.service.impl;

import com.opay.invite.config.PrizePoolConfig;
import com.opay.invite.exception.InviteException;
import com.opay.invite.mapper.InviteCountMapper;
import com.opay.invite.model.InviteCountModel;
import com.opay.invite.service.InviteCountService;
import com.opay.invite.utils.DateFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Service
public class InviteCountServiceImpl implements InviteCountService {

    @Resource
    private InviteCountMapper inviteCountMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Resource(name = "inviteShareCountInc")
    private DefaultRedisScript<Boolean> inviteShareCountInc;
    @Value("${spring.jackson.time-zone}")
    private String timeZone;
    @Autowired
    private PrizePoolConfig prizePoolConfig;
    @Value("${prize-pool.activityStart}")
    private String activityStart;
    @Value("${prize-pool.activityEnd}")
    private String activityEnd;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return inviteCountMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(InviteCountModel record) {
        return inviteCountMapper.insert(record);
    }

    @Override
    public int insertSelective(InviteCountModel record) {
        return inviteCountMapper.insertSelective(record);
    }

    @Override
    public InviteCountModel selectByPrimaryKey(Long id) {
        return inviteCountMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(InviteCountModel record) {
        return inviteCountMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(InviteCountModel record) {
        return inviteCountMapper.updateByPrimaryKey(record);
    }

    @Override
    public boolean updateInviteCount(String opayId, String opayName, String opayPhone) throws Exception {
        long now = DateFormatter.parseYMDHMSDate(DateFormatter.formatDatetimeByZone(new Date(), timeZone)).getTime();
        Date activityStartDate = DateFormatter.parseYMDHMSDate(activityStart);
        Date activityEndDate = DateFormatter.parseYMDHMSDate(activityEnd);
        if (now < activityStartDate.getTime() || now > activityEndDate.getTime()) {
            return false;
        }
        Date date = new Date();
        List<String> keys = Arrays.asList(opayId, "invite");
        Boolean execute = (Boolean) redisTemplate.execute(inviteShareCountInc, keys, prizePoolConfig.getInviteLimit(), getSecondsToMidnight(date));
        if (execute) {
            String day = DateFormatter.formatShortYMDDateByZone(date, timeZone);
            InviteCountModel inviteCountModel = inviteCountMapper.selectByOpayId(opayId, day);
            if (inviteCountModel == null) {
                inviteCountModel = new InviteCountModel();
                inviteCountModel.setOpayId(opayId);
                inviteCountModel.setInvite(5);
                inviteCountModel.setOpayName(opayName);
                inviteCountModel.setOpayPhone(opayPhone);
                inviteCountModel.setCreateTime(date);
                inviteCountModel.setDay(day);
                inviteCountMapper.insertSelective(inviteCountModel);
            } else {
                inviteCountModel.setOpayName(opayName);
                inviteCountModel.setOpayPhone(opayPhone);
                inviteCountModel.setInvite(inviteCountModel.getInvite() + 5);
                inviteCountMapper.updateByPrimaryKeySelective(inviteCountModel);
            }
        } else {
            throw new InviteException("Today's invite limit has been reached");
        }
        return execute;
    }

    @Override
    public boolean updateShareCount(String opayId, String opayName, String opayPhone) throws Exception {
        Date date = new Date();
        List<String> keys = Arrays.asList(opayId, "share");
        Boolean execute = (Boolean) redisTemplate.execute(inviteShareCountInc, keys, prizePoolConfig.getShareLimit(), getSecondsToMidnight(date));
        if (execute) {
            String day = DateFormatter.formatShortYMDDateByZone(date, timeZone);
            InviteCountModel inviteCountModel = inviteCountMapper.selectByOpayId(opayId, day);
            if (inviteCountModel == null) {
                inviteCountModel = new InviteCountModel();
                inviteCountModel.setOpayId(opayId);
                inviteCountModel.setShare(1);
                inviteCountModel.setOpayName(opayName);
                inviteCountModel.setOpayPhone(opayPhone);
                inviteCountModel.setDay(day);
                inviteCountModel.setCreateTime(date);
                inviteCountMapper.insertSelective(inviteCountModel);
            } else {
                inviteCountModel.setOpayName(opayName);
                inviteCountModel.setOpayPhone(opayPhone);
                inviteCountModel.setShare(inviteCountModel.getShare() + 1);
                inviteCountMapper.updateByPrimaryKeySelective(inviteCountModel);
            }
        } else {
            throw new InviteException("Today's share limit has been reached");
        }
        return execute;
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

