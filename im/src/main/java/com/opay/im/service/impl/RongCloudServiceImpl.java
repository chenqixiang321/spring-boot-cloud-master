package com.opay.im.service.impl;

import com.opay.im.exception.ImException;
import com.opay.im.mapper.UserTokenMapper;
import com.opay.im.model.RedEnvelopeMessage;
import com.opay.im.model.UserTokenModel;
import com.opay.im.model.response.BlackListUserIdsResponse;
import com.opay.im.service.RongCloudService;
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
import io.rong.models.message.PrivateMessage;
import io.rong.models.response.BlackListResult;
import io.rong.models.response.ResponseResult;
import io.rong.models.response.TokenResult;
import io.rong.models.user.UserModel;
import io.rong.util.CodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class RongCloudServiceImpl implements RongCloudService {

    @Value("${rongyun.appKey}")
    private String appKey;
    @Value("${rongyun.appSecret}")
    private String appSecret;
    @Value("${rongyun.api:}")
    private String api;
    private final String groupIdPrefix = "GROUP_";
    private RongCloud rongCloud = null;

    private RongCloud getRongCloud() {
        if (rongCloud == null) {
            if ("".equals(api)) {
                rongCloud = RongCloud.getInstance(appKey, appSecret);
            } else {
                rongCloud = RongCloud.getInstance(appKey, appSecret, api);
            }

        }
        return rongCloud;
    }

    @Resource
    private UserTokenMapper userTokenMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return userTokenMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(UserTokenModel record) {
        return userTokenMapper.insert(record);
    }

    @Override
    public int insertSelective(UserTokenModel record) {
        return userTokenMapper.insertSelective(record);
    }

    @Override
    public UserTokenModel selectByPrimaryKey(Long id) {
        return userTokenMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(UserTokenModel record) {
        return userTokenMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(UserTokenModel record) {
        return userTokenMapper.updateByPrimaryKey(record);
    }

    @Override
    @Cacheable(value = "ryToken", key = "#opayId", unless = "#result == null")
    public String getRyToken(String opayId, String phone) throws Exception {
        UserTokenModel userToken = userTokenMapper.selectByUserId(opayId);
        if (userToken == null) {
            String token = register(opayId, opayId);
            if (token != null) {
                userToken = new UserTokenModel();
                userToken.setOpayId(opayId);
                userToken.setPhone(phone);
                userToken.setToken(token);
                userToken.setCreateTime(new Date());
                userTokenMapper.insert(userToken);
                return token;
            }
        } else {
            return userToken.getToken();
        }
        return null;
    }

    @Override
    public BlackListUserIdsResponse getBlackList(String userId) throws Exception {
        return new BlackListUserIdsResponse(getRyBlackList(userId));
    }

    @Override
    public Map<String, String> getBlackListMap(String userId) throws Exception {
        List<String> userIds = getRyBlackList(userId);
        int capacity = (int) (userIds.size() / 0.75 + 1);
        Map<String, String> map = new HashMap<>(capacity);
        for (String uId : userIds) {
            map.put(uId, uId);
        }
        return map;
    }

    private String register(String userId, String userName) throws Exception {
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
        operationGroup(userId, groupId, groupName, 0);
    }

    @Override
    public void joinGroup(String userId, String groupId, String groupName) throws Exception {
        operationGroup(userId, groupId, groupName, 1);
    }

    /**
     * @param userId
     * @param groupId
     * @param groupName
     * @param type      0创建 1加入
     */
    private void operationGroup(String userId, String groupId, String groupName, int type) throws Exception {
        Group groupApi = getRongCloud().group;
        GroupMember[] members = {new GroupMember().setId(userId)};
        GroupModel group = new GroupModel()
                .setId(groupIdPrefix + groupId)
                .setMembers(members)
                .setName(groupName);
        Result groupResult = null;
        if (type == 0) {
            groupResult = (Result) groupApi.create(group);
        } else {
            groupResult = (Result) groupApi.join(group);
        }
        if (groupResult.getCode() != 200) {
            throw new ImException(groupResult.getCode(), groupResult.errorMessage);
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
            throw new ImException(joinGroupResult.getCode(), joinGroupResult.errorMessage);
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
            throw new ImException(muteConversationResult.getCode(), muteConversationResult.errorMessage);
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
            throw new ImException(result.getCode(), result.errorMessage);
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
            throw new ImException(result.getCode(), result.errorMessage);
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

    @Override
    public void sendMessage(String fromUserId, String toUserId, String content, String extra) throws Exception {
        String[] targetIds = {toUserId};
        RedEnvelopeMessage ms = new RedEnvelopeMessage(content);
        PrivateMessage privateMessage = new PrivateMessage()
                .setSenderId(fromUserId)
                .setTargetId(targetIds)
                .setContent(ms)
                .setObjectName("app:red-envelope-receipt");
        ResponseResult privateResult = getRongCloud().message.msgPrivate.send(privateMessage);
        if (privateResult.getCode() != 200) {
            throw new ImException(privateResult.getCode(), privateResult.errorMessage);
        }
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
                throw new ImException(blacklistResult.getCode(), blacklistResult.errorMessage);
            }
        } else {
            Result blacklistResult = (Result) blackListApi.remove(user);
            if (blacklistResult.getCode() != 200) {
                throw new ImException(blacklistResult.getCode(), blacklistResult.errorMessage);
            }
        }

    }


    private List<String> getRyBlackList(String userId) throws Exception {
        Blacklist blackListApi = getRongCloud().user.blackList;
        UserModel user = new UserModel().setId(userId);
        BlackListResult result = blackListApi.getList(user);
        if (result.getCode() != 200) {
            throw new ImException(result.getCode(), result.errorMessage);
        }
        List<String> userIds = new ArrayList<>();
        for (UserModel u : result.getUsers()) {
            userIds.add(u.getId());
        }
        return userIds;
    }
}
