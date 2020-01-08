package com.opay.invite.mapper;

import com.opay.invite.model.OpayActiveCashback;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface OpayActiveCashbackMapper {

    OpayActiveCashback getActivityCashbackByOpayId(@Param("opayId") String opayId);

    void saveCashback(@Param("opayId") String opayId,@Param("phone")  String phone,@Param("time") Date time);

    int updateCashback(OpayActiveCashback cashback);

    int deductionCashback(OpayActiveCashback cashback);

    void updateBatchCashback(List<OpayActiveCashback> list);

    void updateRollbackCashback(OpayActiveCashback cashback);
}
