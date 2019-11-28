package com.opay.invite.service.impl;

import com.opay.invite.config.PrizePoolConfig;
import com.opay.invite.mapper.InviteCountMapper;
import com.opay.invite.model.InviteCountModel;
import com.opay.invite.service.InviteCountService;
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

@Service
public class InviteCountServiceImpl implements InviteCountService {

    @Resource
    private InviteCountMapper inviteCountMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Value("${upperLimit.invite:100}")
    private int inviteUpperLimit;
    @Value("${upperLimit.share:3}")
    private int shareUpperLimit;
    @Resource(name = "inviteShareCountInc")
    private DefaultRedisScript<Boolean> inviteShareCountInc;

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
        Date date = new Date();
        List<String> keys = Arrays.asList(opayId, "invite");
        Boolean execute = (Boolean) redisTemplate.execute(inviteShareCountInc, keys, inviteUpperLimit, getSecondsToMidnight(date));
        if (execute) {
            InviteCountModel inviteCountModel = inviteCountMapper.selectByOpayId(opayId);
            if (inviteCountModel == null) {
                inviteCountModel = new InviteCountModel();
                inviteCountModel.setOpayId(opayId);
                inviteCountModel.setInvite(5);
                inviteCountModel.setOpayName(opayName);
                inviteCountModel.setOpayPhone(opayPhone);
                inviteCountModel.setCreateTime(date);
                inviteCountMapper.insertSelective(inviteCountModel);
            } else {
                inviteCountModel.setInvite(inviteCountModel.getInvite() + 5);
                inviteCountMapper.updateByPrimaryKeySelective(inviteCountModel);
            }
        }
        return execute;
    }

    @Override
    public boolean updateShareCount(String opayId, String opayName, String opayPhone) throws Exception {
        Date date = new Date();
        List<String> keys = Arrays.asList(opayId, "share");
        Boolean execute = (Boolean) redisTemplate.execute(inviteShareCountInc, keys, shareUpperLimit, getSecondsToMidnight(date));
        if (execute) {
            InviteCountModel inviteCountModel = inviteCountMapper.selectByOpayId(opayId);
            if (inviteCountModel == null) {
                inviteCountModel = new InviteCountModel();
                inviteCountModel.setOpayId(opayId);
                inviteCountModel.setShare(1);
                inviteCountModel.setOpayName(opayName);
                inviteCountModel.setOpayPhone(opayPhone);
                inviteCountModel.setCreateTime(date);
                inviteCountMapper.insertSelective(inviteCountModel);
            } else {
                inviteCountModel.setShare(inviteCountModel.getShare() + 1);
                inviteCountMapper.updateByPrimaryKeySelective(inviteCountModel);
            }
        }
        return execute;
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

