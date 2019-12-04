package com.opay.invite.mapper;

import com.opay.invite.model.OpayActiveCashback;
import com.opay.invite.model.OpayActiveTixian;
import com.opay.invite.model.OpayActiveTixianLog;
import com.opay.invite.model.request.WithdrawalListRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OpayCashbackTixianMapper {

    OpayActiveTixian getTixianAmount(@Param("opayId") String opayId,@Param("day") Integer day);

    void saveTixian(OpayActiveTixian saveTixian);

    void saveTixianLog(OpayActiveTixianLog saveTixianLog);

    void updateTixian(@Param("id") long id, @Param("opayId") String opayId,@Param("reference")  String reference,@Param("tradeNo") String tradeNo,@Param("status") int status);

    List<OpayActiveTixian> withdrawalList(WithdrawalListRequest withdrawalListRequest);

    OpayActiveTixian getTixianById(@Param("id") Long id);
}
