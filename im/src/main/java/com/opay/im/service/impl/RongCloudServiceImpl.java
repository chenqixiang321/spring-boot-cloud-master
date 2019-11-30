package com.opay.im.service.impl;

import com.opay.im.service.RongCloudService;
import com.sun.deploy.util.BlackList;
import io.rong.RongCloud;
import io.rong.methods.conversation.Conversation;
import io.rong.methods.group.Group;
import io.rong.methods.group.mute.MuteMembers;
import io.rong.methods.user.User;
import io.rong.methods.user.blacklist.Blacklist;
import io.rong.models.Result;
import io.rong.models.conversation.ConversationModel;
import io.rong.models.group.GroupMember;
import io.rong.models.group.GroupModel;
import io.rong.models.response.BlackListResult;
import io.rong.models.response.ResponseResult;
import io.rong.models.response.TokenResult;
import io.rong.models.user.UserModel;
import io.rong.util.CodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class RongCloudServiceImpl implements RongCloudService {

    @Value("${rongyun.appKey}")
    private String appKey;
    @Value("${rongyun.appSecret}")
    private String appSecret;
    private final String groupIdPrefix = "GROUP_";
    private RongCloud rongCloud = null;

    private RongCloud getRongCloud() {
        if (rongCloud == null) {
            rongCloud = RongCloud.getInstance(appKey, appSecret);
        }
        return rongCloud;
    }

    @Override
    public String register(String userId, String userName) throws Exception {
        UserModel userModel = new UserModel()
                .setId(userId)
                .setName(userName)
                .setPortrait("default");
        User userApi = getRongCloud().user;
        TokenResult result = userApi.register(userModel);
        if (result.getCode() == 200) {
            return result.getToken();
        } else {
            log.error("get rong yun register user error code:{}", result.getCode());
        }
        return null;
    }

    @Override
    public void createGroup(String userId, String groupId, String groupName) throws Exception {
        Group groupApi = getRongCloud().group;
        GroupMember[] members = {new GroupMember().setId(userId)};
        GroupModel group = new GroupModel()
                .setId(groupIdPrefix + groupId)
                .setMembers(members)
                .setName(groupName);
        Result groupCreateResult = (Result) groupApi.create(group);
        if (groupCreateResult.getCode() != 200) {
            throw new Exception(groupCreateResult.errorMessage);
        }
    }

    @Override
    public void joinGroup(String userId, String groupId, String groupName) throws Exception {
        Group groupApi = getRongCloud().group;
        GroupMember[] members = {new GroupMember().setId(userId)};
        GroupModel group = new GroupModel()
                .setId(groupIdPrefix + groupId)
                .setMembers(members)
                .setName(groupName);
        Result joinGroupResult = (Result) groupApi.join(group);
        if (joinGroupResult.getCode() != 200) {
            throw new Exception(joinGroupResult.errorMessage);
        }
    }

    @Override
    public void quitGroup(String userId, String groupId) throws Exception {
        Group groupApi = getRongCloud().group;
        GroupMember[] members = {new GroupMember().setId(userId)};
        GroupModel group = new GroupModel()
                .setId(groupIdPrefix + groupId)
                .setMembers(members);
        Result joinGroupResult = (Result) groupApi.quit(group);
        if (joinGroupResult.getCode() != 200) {
            throw new Exception(joinGroupResult.errorMessage);
        }
    }

    @Override
    public void muteGroup(String userId, String targetId) throws Exception {
        Conversation conversationApi = getRongCloud().conversation;
        ConversationModel conversation = new ConversationModel()
                .setType(CodeUtil.ConversationType.PRIVATE.getName())
                .setUserId(userId)
                .setTargetId(groupIdPrefix + targetId);
        ResponseResult muteConversationResult = conversationApi.mute(conversation);
        if (muteConversationResult.getCode() != 200) {
            throw new Exception(muteConversationResult.errorMessage);
        }
    }

    @Override
    public void blockMember(String userId, String groupId) throws Exception {
        MuteMembers muteMembers = getRongCloud().group.muteMembers;
        GroupMember[] members = {new GroupMember().setId(userId)};
        GroupModel group = new GroupModel()
                .setId(groupIdPrefix + groupId)
                .setMembers(members)
                .setMinute(0);
        Result result = muteMembers.add(group);
        if (result.getCode() != 200) {
            throw new Exception(result.errorMessage);
        }
    }

    @Override
    public void unblockMember(String userId, String groupId) throws Exception {
        MuteMembers muteMembers = getRongCloud().group.muteMembers;
        GroupMember[] members = {new GroupMember().setId(userId)};
        GroupModel group = new GroupModel()
                .setId(groupIdPrefix + groupId)
                .setMembers(members);
        Result result = muteMembers.remove(group);
        if (result.getCode() != 200) {
            throw new Exception(result.errorMessage);
        }
    }

    @Override
    public void addBlackList(String userId, String blackUserId) throws Exception {
        operationBlackList(userId, blackUserId, 0);
    }

    @Override
    public void removeBlackList(String userId, String blackUserId) throws Exception {
        operationBlackList(userId, blackUserId, 1);
    }

    /**
     * @param userId
     * @param blackUserId
     * @param type        0添加 1删除
     * @throws Exception
     */
    private void operationBlackList(String userId, String blackUserId, int type) throws Exception {
        Blacklist blackListApi = getRongCloud().user.blackList;
        UserModel blackUser = new UserModel().setId(blackUserId);
        UserModel[] blacklist = {blackUser};
        UserModel user = new UserModel()
                .setId(userId)
                .setBlacklist(blacklist);
        if (type == 0) {
            Result blacklistResult = (Result) blackListApi.add(user);
            if (blacklistResult.getCode() != 200) {
                throw new Exception(blacklistResult.errorMessage);
            }
        } else {
            Result blacklistResult = (Result) blackListApi.remove(user);
            if (blacklistResult.getCode() != 200) {
                throw new Exception(blacklistResult.errorMessage);
            }
        }

    }

    @Override
    public List<String> getBlackList(String userId) throws Exception {
        Blacklist blackListApi = getRongCloud().user.blackList;
        UserModel user = new UserModel().setId(userId);
        BlackListResult result = blackListApi.getList(user);
        if (result.getCode() != 200) {
            throw new Exception(result.errorMessage);
        }
        List<String> userIds = new ArrayList<>();
        for (UserModel u : result.getUsers()) {
            userIds.add(u.getId());
        }
        return userIds;
    }
}
