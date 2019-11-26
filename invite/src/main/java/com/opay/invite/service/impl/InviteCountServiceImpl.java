package com.opay.invite.service.impl;

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
    @Value("${upperLimit.invite:5}")
    private int inviteUpperLimit;
    @Value("${upperLimit.share:5}")
    private int shareUpperLimit;
    @Resource
    private DefaultRedisScript<Boolean> inviteShareCount;

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
    public int updateInviteCount(String opayId, String opayName, String opayPhone) throws Exception {
        Date date = new Date();
        StringBuilder key = new StringBuilder();
        key.append("invite:");
        key.append(opayId);
        List<String> keys = Arrays.asList(key.toString());
        Boolean execute = (Boolean) redisTemplate.execute(inviteShareCount, keys, inviteUpperLimit, getSecondsToMidnight(date));
        if (execute) {
            InviteCountModel inviteCountModel = inviteCountMapper.selectByOpayId(opayId);
            if (inviteCountModel == null) {
                inviteCountModel = new InviteCountModel();
                inviteCountModel.setOpayId(opayId);
                inviteCountModel.setInvite(1);
                inviteCountModel.setOpayName(opayName);
                inviteCountModel.setOpayPhone(opayPhone);
                inviteCountModel.setCreateTime(date);
                inviteCountMapper.insertSelective(inviteCountModel);
            } else {
                inviteCountModel.setInvite(inviteCountModel.getInvite() + 1);
                inviteCountMapper.updateByPrimaryKeySelective(inviteCountModel);
            }
        }
        return 0;
    }

    @Override
    public int updateShareCount(String opayId, String opayName, String opayPhone) throws Exception {
        Date date = new Date();
        StringBuilder key = new StringBuilder();
        key.append("share:");
        key.append(opayId);
        List<String> keys = Arrays.asList(key.toString());
        Boolean execute = (Boolean) redisTemplate.execute(inviteShareCount, keys, inviteUpperLimit, getSecondsToMidnight(date));
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
        return 0;
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

