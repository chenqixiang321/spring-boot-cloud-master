package com.opay.im.service.impl;

import com.opay.im.mapper.ChatGroupMapper;
import com.opay.im.model.ChatGroupModel;
import com.opay.im.service.ChatGroupService;
import com.opay.im.service.RongCloudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class ChatGroupServiceImpl implements ChatGroupService {

    @Resource
    private ChatGroupMapper chatGroupMapper;
    @Autowired
    private RongCloudService rongCloudService;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return chatGroupMapper.deleteByPrimaryKey(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insert(ChatGroupModel record) throws Exception {
        record.setNumber(1);
        record.setCreateTime(new Date());
        chatGroupMapper.insert(record);
        rongCloudService.createGroup(record.getOpayId(), String.valueOf(record.getId()), record.getName());
        return 1;
    }

    @Override
    public int insertSelective(ChatGroupModel record) {
        return chatGroupMapper.insertSelective(record);
    }

    @Override
    public ChatGroupModel selectByPrimaryKey(Long id) {
        return chatGroupMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(ChatGroupModel record) {
        return chatGroupMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(ChatGroupModel record) {
        return chatGroupMapper.updateByPrimaryKey(record);
    }

}
