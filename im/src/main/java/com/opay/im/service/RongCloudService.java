package com.opay.im.service;

import java.util.List;

public interface RongCloudService {
    String register(String userId, String userName) throws Exception;

    void createGroup(String userId, String groupId, String groupName) throws Exception;

    void joinGroup(String userId, String groupId, String groupName) throws Exception;

    void quitGroup(String userId, String groupId) throws Exception;

    void muteGroup(String userId, String targetId) throws Exception;

    void blockMember(String userId, String groupId) throws Exception;

    void unblockMember(String userId, String groupId) throws Exception;

    void addBlackList(String userId, String blackUserId) throws Exception;

    void removeBlackList(String userId, String blackUserId) throws Exception;

    List<String> getBlackList(String userId) throws Exception;
}
