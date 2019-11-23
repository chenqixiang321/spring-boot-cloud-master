package com.opay.im.service.impl;

import com.opay.im.service.RongCloudService;
import io.rong.RongCloud;
import io.rong.methods.group.Group;
import io.rong.methods.user.User;
import io.rong.models.Result;
import io.rong.models.group.GroupMember;
import io.rong.models.group.GroupModel;
import io.rong.models.response.TokenResult;
import io.rong.models.user.UserModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RongCloudServiceImpl implements RongCloudService {

    @Value("${rongyun.appKey}")
    private String appKey;
    @Value("${rongyun.appSecret}")
    private String appSecret;
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
        User user = getRongCloud().user;
        TokenResult result = user.register(userModel);
        if (result.getCode() == 200) {
            return result.getToken();
        } else {
            log.error("get rong yun register user error code:{}", result.getCode());
        }
        return null;
    }

    @Override
    public void createGroup(String userId, String groupId, String groupName) throws Exception {
        Group Group = getRongCloud().group;
        GroupMember[] members = {new GroupMember().setId(userId)};
        GroupModel group = new GroupModel()
                .setId(groupId)
                .setMembers(members)
                .setName(groupName);
        Result groupCreateResult = (Result) Group.create(group);
        if (groupCreateResult.getCode() != 200) {
            throw new Exception(groupCreateResult.errorMessage);
        }
    }
}
