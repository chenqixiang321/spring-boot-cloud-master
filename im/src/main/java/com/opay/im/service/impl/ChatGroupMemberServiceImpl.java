package com.opay.im.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.opay.im.model.ChatGroupMemberModel;
import com.opay.im.mapper.ChatGroupMemberMapper;
import com.opay.im.service.ChatGroupMemberService;
@Service
public class ChatGroupMemberServiceImpl implements ChatGroupMemberService{

    @Resource
    private ChatGroupMemberMapper chatGroupMemberMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return chatGroupMemberMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(ChatGroupMemberModel record) {
        return chatGroupMemberMapper.insert(record);
    }

    @Override
    public int insertSelective(ChatGroupMemberModel record) {
        return chatGroupMemberMapper.insertSelective(record);
    }

    @Override
    public ChatGroupMemberModel selectByPrimaryKey(Long id) {
        return chatGroupMemberMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(ChatGroupMemberModel record) {
        return chatGroupMemberMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(ChatGroupMemberModel record) {
        return chatGroupMemberMapper.updateByPrimaryKey(record);
    }

}
