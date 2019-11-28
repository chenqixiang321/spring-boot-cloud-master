package com.opay.invite.service;

import com.opay.invite.model.OpayActiveTixian;
import com.opay.invite.model.OpayActiveTixianLog;

public interface WithdrawalService {

    OpayActiveTixian getTixianAmount(String opayId, Integer day);

    void saveTixian(OpayActiveTixian saveTixian);

    void saveTixianLog(OpayActiveTixianLog saveTixianLog);

    void updateTixian(long id,String opayId, String reference, String orderNo,int status);
}
