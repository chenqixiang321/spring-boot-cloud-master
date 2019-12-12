package com.opay.im.service.impl;

import com.opay.im.model.request.InviteGroupRequest;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.opay.im.mapper.ChatGroupInviteMapper;
import com.opay.im.model.ChatGroupInviteModel;
import com.opay.im.service.ChatGroupInviteService;

@Service
public class ChatGroupInviteServiceImpl implements ChatGroupInviteService {

    @Resource
    private ChatGroupInviteMapper chatGroupInviteMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return chatGroupInviteMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(ChatGroupInviteModel record) {
        return chatGroupInviteMapper.insert(record);
    }

    @Override
    public int insertSelective(ChatGroupInviteModel record) {
        return chatGroupInviteMapper.insertSelective(record);
    }

    @Override
    public ChatGroupInviteModel selectByPrimaryKey(Long id) {
        return chatGroupInviteMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(ChatGroupInviteModel record) {
        return chatGroupInviteMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(ChatGroupInviteModel record) {
        return chatGroupInviteMapper.updateByPrimaryKey(record);
    }

    @Override
    public int inviteUser(InviteGroupRequest inviteGroupRequest) {
        return 0;
    }
}

