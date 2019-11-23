package com.opay.im.mapper;

import com.opay.im.model.UserTokenModel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserTokenMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserTokenModel record);

    int insertSelective(UserTokenModel record);

    UserTokenModel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserTokenModel record);

    int updateByPrimaryKey(UserTokenModel record);

    UserTokenModel selectByUserId(String userId);
}