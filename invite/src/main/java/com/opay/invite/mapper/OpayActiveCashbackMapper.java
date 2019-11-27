package com.opay.invite.mapper;

import com.opay.invite.model.OpayActiveCashback;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OpayActiveCashbackMapper {

    OpayActiveCashback getActivityCashbackByOpayId(@Param("opayId") String opayId);

    void saveCashback(@Param("opayId") String opayId,@Param("phone")  String phone);

    int updateCashback(OpayActiveCashback cashback);

    int deductionCashback(OpayActiveCashback cashback);
}
