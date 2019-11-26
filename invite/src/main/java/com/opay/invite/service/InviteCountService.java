package com.opay.invite.service;


import com.opay.invite.model.InviteCountModel;

public interface InviteCountService {


    int deleteByPrimaryKey(Long id);

    int insert(InviteCountModel record);

    int insertSelective(InviteCountModel record);

    InviteCountModel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(InviteCountModel record);

    int updateByPrimaryKey(InviteCountModel record);

    int updateInviteCount(String opayId, String opayName, String opayPhone) throws Exception;

    int updateShareCount(String opayId, String opayName, String opayPhone) throws Exception;
}

