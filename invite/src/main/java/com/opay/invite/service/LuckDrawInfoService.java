package com.opay.invite.service;

import com.opay.invite.model.LuckDrawInfoModel;
import com.opay.invite.model.response.LuckDrawInfoResponse;
import com.opay.invite.model.response.LuckDrawListResponse;

import java.util.List;

public interface LuckDrawInfoService {


    int deleteByPrimaryKey(Long id);

    int insert(LuckDrawInfoModel record);

    int insertSelective(LuckDrawInfoModel record);

    LuckDrawInfoModel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LuckDrawInfoModel record);

    int updateByPrimaryKey(LuckDrawInfoModel record);

    List<LuckDrawListResponse> selectLuckDrawInfoList() throws Exception;

    LuckDrawInfoResponse getLuckDraw(String opayId, String opayName, String opayPhone) throws Exception;
}

