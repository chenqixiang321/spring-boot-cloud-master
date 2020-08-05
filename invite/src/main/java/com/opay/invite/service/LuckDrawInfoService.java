package com.opay.invite.service;

import com.opay.invite.model.LuckDrawInfoModel;
import com.opay.invite.model.PrizeModel;
import com.opay.invite.model.response.LuckDrawInfoResponse;
import com.opay.invite.model.response.LuckDrawListResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface LuckDrawInfoService {


    int deleteByPrimaryKey(Long id);

    int insert(LuckDrawInfoModel record);

    int insertSelective(LuckDrawInfoModel record);

    LuckDrawInfoModel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LuckDrawInfoModel record);

    int updateByPrimaryKey(LuckDrawInfoModel record);

    List<LuckDrawListResponse> selectLuckDrawInfoList() throws Exception;

    List<LuckDrawListResponse> selectLuckDrawInfoList(String opayId, int pageNum, int pageSize) throws Exception;

    LuckDrawInfoResponse getLuckDraw(String opayId, String opayName, String opayPhone) throws Exception;

    Map<Integer, PrizeModel> getPrize();

    int updateBonusStatus(String reference, int status);
}

