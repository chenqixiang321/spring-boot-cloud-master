package com.opay.im.service;

import com.opay.im.model.RongCloudMessageModel;

public interface RongCloudMessageService {


    int deleteByPrimaryKey(String msgUid);

    int insert(RongCloudMessageModel record);

    int insertSelective(RongCloudMessageModel record);

    RongCloudMessageModel selectByPrimaryKey(String msgUid);

    int updateByPrimaryKeySelective(RongCloudMessageModel record);

    int updateByPrimaryKey(RongCloudMessageModel record);

}
