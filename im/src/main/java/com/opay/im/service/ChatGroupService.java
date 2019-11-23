package com.opay.im.service;

import com.opay.im.model.ChatGroupModel;
import com.opay.im.model.request.CreateGroupRequest;
import com.opay.im.model.request.JoinGroupRequest;

public interface ChatGroupService {


    int deleteByPrimaryKey(Long id);

    int insert(CreateGroupRequest createGroupRequest) throws Exception;

    int insertSelective(ChatGroupModel record);

    ChatGroupModel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ChatGroupModel record);

    int updateByPrimaryKey(ChatGroupModel record);

    void joinGroup(JoinGroupRequest joinGroupRequest) throws Exception;
}
