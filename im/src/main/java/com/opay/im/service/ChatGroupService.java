package com.opay.im.service;

import com.opay.im.model.ChatGroupModel;
import com.opay.im.model.request.BlockGroupMemberRequest;
import com.opay.im.model.request.CreateGroupRequest;
import com.opay.im.model.request.GroupMemberQuitRequest;
import com.opay.im.model.request.JoinGroupRequest;
import com.opay.im.model.request.MuteGroupRequest;
import com.opay.im.model.request.RemoveGroupMemberRequest;

import java.util.List;

public interface ChatGroupService {


    int deleteByPrimaryKey(Long id);

    int insert(CreateGroupRequest createGroupRequest) throws Exception;

    int insertSelective(ChatGroupModel record);

    ChatGroupModel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ChatGroupModel record);

    int updateByPrimaryKey(ChatGroupModel record);

    void joinGroup(JoinGroupRequest joinGroupRequest) throws Exception;

    void leaveGroup(GroupMemberQuitRequest groupMemberQuitRequest) throws Exception;

    void removeGroupMember(RemoveGroupMemberRequest removeGroupMemberRequest) throws Exception;

    void muteGroup(MuteGroupRequest muteGroupRequest) throws Exception;

    void blockGroupMember(BlockGroupMemberRequest blockGroupMemberRequest) throws Exception;

    List<ChatGroupModel> selectGroupList(String opayId) throws Exception;
}
