package com.opay.im.service.impl;

import com.opay.im.mapper.RongCloudMessageMapper;
import com.opay.im.model.RongCloudMessageModel;
import com.opay.im.service.RongCloudMessageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RongCloudMessageServiceImpl implements RongCloudMessageService {

    @Resource
    private RongCloudMessageMapper rongCloudMessageMapper;

    @Override
    public int deleteByPrimaryKey(String msgUid) {
        return rongCloudMessageMapper.deleteByPrimaryKey(msgUid);
    }

    @Override
    public int insert(RongCloudMessageModel record) {
        return rongCloudMessageMapper.insert(record);
    }

    @Override
    public int insertSelective(RongCloudMessageModel record) {
        return rongCloudMessageMapper.insertSelective(record);
    }

    @Override
    public RongCloudMessageModel selectByPrimaryKey(String msgUid) {
        return rongCloudMessageMapper.selectByPrimaryKey(msgUid);
    }

    @Override
    public int updateByPrimaryKeySelective(RongCloudMessageModel record) {
        return rongCloudMessageMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(RongCloudMessageModel record) {
        return rongCloudMessageMapper.updateByPrimaryKey(record);
    }

}
