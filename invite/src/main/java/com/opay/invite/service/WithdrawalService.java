package com.opay.invite.service;

import com.github.pagehelper.PageInfo;
import com.opay.invite.model.OpayActiveTixian;
import com.opay.invite.model.OpayActiveTixianLog;
import com.opay.invite.model.request.WithdrawalApproval;
import com.opay.invite.model.request.WithdrawalListRequest;

import java.util.List;

public interface WithdrawalService {

    OpayActiveTixian getTixianAmount(String opayId, Integer day);

    void saveTixian(OpayActiveTixian saveTixian);

    void saveTixianLog(OpayActiveTixianLog saveTixianLog);

    void updateTixian(long id,String opayId, String reference, String orderNo,int status);

    PageInfo<List<OpayActiveTixian>> withdrawalList(WithdrawalListRequest withdrawalListRequest);

    OpayActiveTixian getWithdrawal(WithdrawalApproval withdrawalApproval);
}
