package com.opay.im.service;

public interface RongCloudService {
    String register(String userId, String userName) throws Exception;

    void createGroup(String userId, String groupId, String groupName) throws Exception;

    void joinGroup(String userId, String groupId, String groupName) throws Exception;

    void quitGroup(String userId, String groupId) throws Exception;

    void muteGroup(String userId, String targetId) throws Exception;

    void blockMember(String userId, String groupId) throws Exception;

    void unblockMember(String userId, String groupId) throws Exception;
}
