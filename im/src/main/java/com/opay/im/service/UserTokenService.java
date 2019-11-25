package com.opay.im.service;

import com.opay.im.model.UserTokenModel;

public interface UserTokenService {


    int deleteByPrimaryKey(Long id);

    int insert(UserTokenModel record);

    int insertSelective(UserTokenModel record);

    UserTokenModel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserTokenModel record);

    int updateByPrimaryKey(UserTokenModel record);

    String getRyToken(String opayId, String phone) throws Exception;
}
