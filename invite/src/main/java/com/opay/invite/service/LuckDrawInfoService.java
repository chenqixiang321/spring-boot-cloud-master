package com.opay.invite.service;

import com.opay.invite.model.LuckDrawInfoModel;
import com.opay.invite.model.response.LuckDrawInfoResponse;

import java.util.List;

public interface LuckDrawInfoService {


    int deleteByPrimaryKey(Long id);

    int insert(LuckDrawInfoModel record);

    int insertSelective(LuckDrawInfoModel record);

    LuckDrawInfoModel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LuckDrawInfoModel record);

    int updateByPrimaryKey(LuckDrawInfoModel record);

    List<LuckDrawInfoResponse> selectLuckDrawInfoList() throws Exception;

    LuckDrawInfoResponse getLuckDraw() throws Exception;
}

