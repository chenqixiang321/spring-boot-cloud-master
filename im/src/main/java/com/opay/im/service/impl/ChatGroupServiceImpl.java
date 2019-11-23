package com.opay.im.service.impl;

import com.opay.im.mapper.ChatGroupMapper;
import com.opay.im.mapper.ChatGroupMemberMapper;
import com.opay.im.model.ChatGroupMemberModel;
import com.opay.im.model.ChatGroupModel;
import com.opay.im.model.request.CreateGroupRequest;
import com.opay.im.model.request.JoinGroupRequest;
import com.opay.im.service.ChatGroupService;
import com.opay.im.service.RongCloudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.crypto.Data;
import java.util.Date;

@Service
public class ChatGroupServiceImpl implements ChatGroupService {

    @Autowired
    private ChatGroupMapper chatGroupMapper;
    @Autowired
    private ChatGroupMemberMapper chatGroupMemberMapper;
    @Autowired
    private RongCloudService rongCloudService;
    @Value("${im.group.maxMember}")
    private int groupMax;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return chatGroupMapper.deleteByPrimaryKey(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insert(CreateGroupRequest createGroupRequest) throws Exception {
        Date date = new Date();
        ChatGroupModel chatGroupModel = new ChatGroupModel();
        chatGroupModel.setName(createGroupRequest.getGroupName());
        chatGroupModel.setOpayId(createGroupRequest.getOpayId());
        chatGroupModel.setImg(createGroupRequest.getGroupImg());
        chatGroupModel.setCreateTime(date);
        chatGroupMapper.insert(chatGroupModel);
        ChatGroupMemberModel chatGroupMemberModel = new ChatGroupMemberModel();
        chatGroupMemberModel.setGroupId(chatGroupModel.getId());
        chatGroupMemberModel.setOpayId(createGroupRequest.getOpayId());
        chatGroupMemberModel.setImg(createGroupRequest.getHeadImg());
        chatGroupMemberModel.setName(createGroupRequest.getFounderName());
        chatGroupMemberModel.setJoinTime(date);
        chatGroupMemberMapper.insert(chatGroupMemberModel);
        rongCloudService.createGroup(createGroupRequest.getOpayId(), String.valueOf(chatGroupModel.getId()), createGroupRequest.getGroupName());
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void joinGroup(JoinGroupRequest joinGroupRequest) throws Exception {
        Date date = new Date();
        int count = chatGroupMemberMapper.selectGroupMemberCount(joinGroupRequest.getGroupId());
        if (groupMax <= count) {
            throw new Exception("Exceed members limitation");
        }
        ChatGroupModel chatGroupModel = chatGroupMapper.selectByPrimaryKey(joinGroupRequest.getGroupId());
        chatGroupModel.setNumber(count + 1);
        chatGroupModel.setVersion(chatGroupModel.getVersion() + 1);
        chatGroupModel.setOpayId(joinGroupRequest.getOwnerOpayId());
        chatGroupModel.setUpdateTime(date);
        int rows = chatGroupMapper.updateByPrimaryKeySelectiveWithVersion(chatGroupModel);
        if (rows == 0) {
            throw new Exception("Fail to join, please retry");
        }
        ChatGroupMemberModel chatGroupMemberModel = new ChatGroupMemberModel();
        chatGroupMemberModel.setName(joinGroupRequest.getName());
        chatGroupMemberModel.setImg(joinGroupRequest.getImg());
        chatGroupMemberModel.setJoinTime(date);
        chatGroupMemberModel.setOpayId(joinGroupRequest.getOpayId());
        chatGroupMemberModel.setGroupId(joinGroupRequest.getGroupId());
        chatGroupMemberMapper.insert(chatGroupMemberModel);
        rongCloudService.joinGroup(joinGroupRequest.getOpayId(),String.valueOf(joinGroupRequest.getGroupId()),chatGroupModel.getName());
    }

}
