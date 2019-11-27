package com.opay.invite.mapper;

import com.opay.invite.model.OpayActiveCashback;
import com.opay.invite.model.OpayActiveTixian;
import com.opay.invite.model.OpayActiveTixianLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OpayCashbackTixianMapper {

    OpayActiveTixian getTixianAmount(@Param("opayId") String opayId,@Param("day") Integer day);

    void saveTixian(OpayActiveTixian saveTixian);

    void saveTixianLog(OpayActiveTixianLog saveTixianLog);

    void updateTixian(@Param("id") long id, @Param("opayId") String opayId,@Param("reference")  String reference,@Param("orderNo") String orderNo);
}
