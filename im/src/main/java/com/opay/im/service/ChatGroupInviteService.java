package com.opay.im.service;

import com.opay.im.model.ChatGroupInviteModel;
import com.opay.im.model.request.InviteGroupRequest;

public interface ChatGroupInviteService {


    int deleteByPrimaryKey(Long id);

    int insert(ChatGroupInviteModel record);

    int insertSelective(ChatGroupInviteModel record);

    ChatGroupInviteModel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ChatGroupInviteModel record);

    int updateByPrimaryKey(ChatGroupInviteModel record);

    int inviteUser(InviteGroupRequest inviteGroupRequest);
}

