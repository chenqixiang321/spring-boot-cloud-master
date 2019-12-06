package com.opay.im.service;

import com.opay.im.model.UserTokenModel;
import com.opay.im.model.response.BlackListUserIdsResponse;

import java.util.Map;

public interface RongCloudService {
    int deleteByPrimaryKey(Long id);

    int insert(UserTokenModel record);

    int insertSelective(UserTokenModel record);

    UserTokenModel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserTokenModel record);

    int updateByPrimaryKey(UserTokenModel record);

    String getRyToken(String opayId, String phone) throws Exception;

    void addBlackList(String userId, String blackUserId) throws Exception;

    void removeBlackList(String userId, String blackUserId) throws Exception;

    BlackListUserIdsResponse getBlackList(String userId) throws Exception;

    Map<String, String> getBlackListMap(String userId) throws Exception;

    void createGroup(String userId, String groupId, String groupName) throws Exception;

    void joinGroup(String userId, String groupId, String groupName) throws Exception;

    void quitGroup(String userId, String groupId) throws Exception;

    void muteGroup(String userId, String targetId) throws Exception;

    void blockMember(String userId, String groupId) throws Exception;

    void unblockMember(String userId, String groupId) throws Exception;
}
