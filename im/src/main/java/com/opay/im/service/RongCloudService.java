package com.opay.im.service;

public interface RongCloudService {
    String register(String userId, String userName) throws Exception;

    void createGroup(String userId, String groupId, String groupName) throws Exception;
}
