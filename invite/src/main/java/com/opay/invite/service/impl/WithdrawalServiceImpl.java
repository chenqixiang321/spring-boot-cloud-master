package com.opay.invite.service.impl;

import com.opay.invite.mapper.OpayCashbackTixianMapper;
import com.opay.invite.model.OpayActiveTixian;
import com.opay.invite.model.OpayActiveTixianLog;
import com.opay.invite.service.WithdrawalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WithdrawalServiceImpl implements WithdrawalService {

    @Autowired
    private OpayCashbackTixianMapper opayCashbackTixianMapper;

    @Override
    public OpayActiveTixian getTixianAmount(String opayId, Integer day) {
        return opayCashbackTixianMapper.getTixianAmount(opayId,day);
    }

    @Override
    public void saveTixian(OpayActiveTixian saveTixian) {
        opayCashbackTixianMapper.saveTixian(saveTixian);
    }

    @Override
    public void saveTixianLog(OpayActiveTixianLog saveTixianLog) {
        opayCashbackTixianMapper.saveTixianLog(saveTixianLog);
    }

    @Override
    public void updateTixian(long id,String opayId, String reference, String orderNo,int status) {
        opayCashbackTixianMapper.updateTixian(id,opayId,reference,orderNo,status);
    }
}
