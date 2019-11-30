package com.opay.im.mapper;

import com.opay.im.model.RongCloudMessageModel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RongCloudMessageMapper {
    int deleteByPrimaryKey(String msgUid);

    int insert(RongCloudMessageModel record);

    int insertSelective(RongCloudMessageModel record);

    RongCloudMessageModel selectByPrimaryKey(String msgUid);

    int updateByPrimaryKeySelective(RongCloudMessageModel record);

    int updateByPrimaryKey(RongCloudMessageModel record);
}