package com.opay.im.service;

import com.opay.im.model.ChatGroupMemberModel;
public interface ChatGroupMemberService{


    int deleteByPrimaryKey(Long id);

    int insert(ChatGroupMemberModel record);

    int insertSelective(ChatGroupMemberModel record);

    ChatGroupMemberModel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ChatGroupMemberModel record);

    int updateByPrimaryKey(ChatGroupMemberModel record);

}
