package com.opay.im.service.impl;

import com.opay.im.mapper.ChatGroupMapper;
import com.opay.im.mapper.ChatGroupMemberMapper;
import com.opay.im.model.ChatGroupMemberModel;
import com.opay.im.model.ChatGroupModel;
import com.opay.im.model.request.BlockGroupMemberRequest;
import com.opay.im.model.request.CreateGroupRequest;
import com.opay.im.model.request.GroupMemberQuitRequest;
import com.opay.im.model.request.JoinGroupRequest;
import com.opay.im.model.request.MuteGroupRequest;
import com.opay.im.model.request.RemoveGroupMemberRequest;
import com.opay.im.service.ChatGroupService;
import com.opay.im.service.RongCloudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

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
    @CacheEvict(value = "groupList", key = "#createGroupRequest.opayId")
    @Transactional(rollbackFor = Exception.class)
    public int insert(CreateGroupRequest createGroupRequest) throws Exception {
        Date date = new Date();
        ChatGroupModel chatGroupModel = new ChatGroupModel();
        chatGroupModel.setName(createGroupRequest.getGroupName());
        chatGroupModel.setOpayId(createGroupRequest.getOpayId());
        chatGroupModel.setImg(createGroupRequest.getGroupImg());
        chatGroupModel.setNumber(1);
        chatGroupModel.setCreateTime(date);
        chatGroupMapper.insertSelective(chatGroupModel);
        ChatGroupMemberModel chatGroupMemberModel = new ChatGroupMemberModel();
        chatGroupMemberModel.setGroupId(chatGroupModel.getId());
        chatGroupMemberModel.setOpayId(createGroupRequest.getOpayId());
        chatGroupMemberModel.setImg(createGroupRequest.getHeadImg());
        chatGroupMemberModel.setName(createGroupRequest.getFounderName());
        chatGroupMemberModel.setJoinTime(date);
        chatGroupMemberMapper.insertSelective(chatGroupMemberModel);
        rongCloudService.createGroup(createGroupRequest.getOpayId(), String.valueOf(chatGroupModel.getId()), createGroupRequest.getGroupName());
        return 1;
    }

    @Override
    public int insertSelective(ChatGroupModel record) {
        return chatGroupMapper.insertSelective(record);
    }

    @Override
    @Cacheable(value = "groupInfo", key = "#id", unless = "#result == null")
    public ChatGroupModel selectByPrimaryKey(Long id) {
        return chatGroupMapper.selectByPrimaryKey(id);
    }

    @Override
    @CacheEvict(value = "groupList", key = "#record.opayId")
    public int updateByPrimaryKeySelective(ChatGroupModel record) {
        return chatGroupMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(ChatGroupModel record) {
        return chatGroupMapper.updateByPrimaryKey(record);
    }

    @Override
    @CacheEvict(value = "groupInfo", key = "#joinGroupRequest.groupId")
    @Transactional(rollbackFor = Exception.class)
    public void joinGroup(JoinGroupRequest joinGroupRequest) throws Exception {
        Date date = new Date();
        int count = chatGroupMemberMapper.selectGroupMemberCount(joinGroupRequest.getGroupId());
        if (groupMax <= count) {
            throw new Exception("Exceed members limitation");
        }
        ChatGroupModel chatGroupModel = chatGroupMapper.selectByPrimaryKey(joinGroupRequest.getGroupId());
        if (chatGroupModel == null) {
            throw new Exception("Group not exist");
        }
        chatGroupModel.setNumber(count + 1);
        chatGroupModel.setVersion(chatGroupModel.getVersion());
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
        chatGroupMemberMapper.insertSelective(chatGroupMemberModel);
        rongCloudService.joinGroup(joinGroupRequest.getOpayId(), String.valueOf(joinGroupRequest.getGroupId()), chatGroupModel.getName());
    }

    @Override
    @CacheEvict(value = "groupInfo", key = "#groupMemberQuitRequest.groupId")
    @Transactional(rollbackFor = Exception.class)
    public void leaveGroup(GroupMemberQuitRequest groupMemberQuitRequest) throws Exception {
        Date date = new Date();
        int count = chatGroupMemberMapper.selectGroupMemberCount(groupMemberQuitRequest.getGroupId());
        ChatGroupModel chatGroupModel = chatGroupMapper.selectByPrimaryKey(groupMemberQuitRequest.getGroupId());
        if (chatGroupModel == null) {
            throw new Exception("Group not exist");
        }
        if (chatGroupModel.getOpayId().equals(groupMemberQuitRequest.getOpayId())) {
            ChatGroupMemberModel chatGroupMemberModel = new ChatGroupMemberModel();
            chatGroupMemberModel.setGroupId(groupMemberQuitRequest.getGroupId());
            chatGroupMemberModel.setOpayId(groupMemberQuitRequest.getOpayId());
            ChatGroupMemberModel firstJoinMember = chatGroupMemberMapper.selectFirstJoinGroupMember(chatGroupMemberModel);
            chatGroupModel.setOpayId(firstJoinMember.getOpayId());
        }
        chatGroupModel.setNumber(count - 1);
        chatGroupModel.setVersion(chatGroupModel.getVersion());
        chatGroupModel.setUpdateTime(date);
        int rows = chatGroupMapper.updateByPrimaryKeySelectiveWithVersion(chatGroupModel);
        if (rows == 0) {
            throw new Exception("Fail to leave, please retry");
        }
        ChatGroupMemberModel chatGroupMemberModel = new ChatGroupMemberModel();
        chatGroupMemberModel.setGroupId(groupMemberQuitRequest.getGroupId());
        chatGroupMemberModel.setOpayId(groupMemberQuitRequest.getOpayId());
        chatGroupMemberMapper.deleteByGroupIdAndOpayId(chatGroupMemberModel);
        rongCloudService.quitGroup(groupMemberQuitRequest.getOpayId(), String.valueOf(groupMemberQuitRequest.getGroupId()));
    }

    @Override
    @CacheEvict(value = "groupInfo", key = "#removeGroupMemberRequest.groupId")
    @Transactional(rollbackFor = Exception.class)
    public void removeGroupMember(RemoveGroupMemberRequest removeGroupMemberRequest) throws Exception {
        Date date = new Date();
        int count = chatGroupMemberMapper.selectGroupMemberCount(removeGroupMemberRequest.getGroupId());
        ChatGroupModel chatGroupModel = chatGroupMapper.selectByPrimaryKey(removeGroupMemberRequest.getGroupId());
        if (chatGroupModel == null) {
            throw new Exception("Group not exist");
        }
        if (!chatGroupModel.getOpayId().equals(removeGroupMemberRequest.getOwnerOpayId())) {
            throw new Exception("You don't have permission to do this");
        }
        chatGroupModel.setNumber(count - 1);
        chatGroupModel.setVersion(chatGroupModel.getVersion());
        chatGroupModel.setUpdateTime(date);
        int rows = chatGroupMapper.updateByPrimaryKeySelectiveWithVersion(chatGroupModel);
        if (rows == 0) {
            throw new Exception("Fail to remove, please retry");
        }
        ChatGroupMemberModel chatGroupMemberModel = new ChatGroupMemberModel();
        chatGroupMemberModel.setGroupId(removeGroupMemberRequest.getGroupId());
        chatGroupMemberModel.setOpayId(removeGroupMemberRequest.getOpayId());
        chatGroupMemberMapper.deleteByGroupIdAndOpayId(chatGroupMemberModel);
        rongCloudService.quitGroup(removeGroupMemberRequest.getOpayId(), String.valueOf(removeGroupMemberRequest.getGroupId()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void muteGroup(MuteGroupRequest muteGroupRequest) throws Exception {
        ChatGroupMemberModel chatGroupMemberModel = new ChatGroupMemberModel();
        chatGroupMemberModel.setGroupId(muteGroupRequest.getGroupId());
        chatGroupMemberModel.setOpayId(muteGroupRequest.getOpayId());
        chatGroupMemberModel.setIsMute(muteGroupRequest.isIsMute());
        int rows = chatGroupMemberMapper.updateByOpayIdAndGroupId(chatGroupMemberModel);
        if (rows == 0) {
            throw new Exception("Fail to mute, please retry");
        }
        rongCloudService.muteGroup(muteGroupRequest.getOpayId(), String.valueOf(muteGroupRequest.getGroupId()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void blockGroupMember(BlockGroupMemberRequest blockGroupMemberRequest) throws Exception {
        ChatGroupModel chatGroupModel = chatGroupMapper.selectByPrimaryKey(blockGroupMemberRequest.getGroupId());
        if (chatGroupModel == null) {
            throw new Exception("Group not exist");
        }
        if (!chatGroupModel.getOpayId().equals(blockGroupMemberRequest.getOwnerOpayId())) {
            throw new Exception("You don't have permission to do this");
        }
        ChatGroupMemberModel chatGroupMemberModel = new ChatGroupMemberModel();
        chatGroupMemberModel.setGroupId(blockGroupMemberRequest.getGroupId());
        chatGroupMemberModel.setOpayId(blockGroupMemberRequest.getOpayId());
        chatGroupMemberModel.setIsBlock(blockGroupMemberRequest.isIsBlock());
        int rows = chatGroupMemberMapper.updateByOpayIdAndGroupId(chatGroupMemberModel);
        if (rows == 0) {
            throw new Exception("Fail to block, please retry");
        }
        if (blockGroupMemberRequest.isIsBlock()) {
            rongCloudService.blockMember(blockGroupMemberRequest.getOpayId(), String.valueOf(blockGroupMemberRequest.getGroupId()));
        } else {
            rongCloudService.unblockMember(blockGroupMemberRequest.getOpayId(), String.valueOf(blockGroupMemberRequest.getGroupId()));
        }

    }

    @Override
    @Cacheable(value = "groupList", key = "#opayId", unless = "#result == null")
    public List<ChatGroupModel> selectGroupList(String opayId) throws Exception {
        return chatGroupMapper.selectGroupList(opayId);
    }


}
