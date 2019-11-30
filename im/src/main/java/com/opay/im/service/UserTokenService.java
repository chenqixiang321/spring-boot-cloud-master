package com.opay.im.service;

import com.opay.im.model.UserTokenModel;
import com.opay.im.model.response.BlackListUserIdsResponse;

import java.util.List;

public interface UserTokenService {


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
}
