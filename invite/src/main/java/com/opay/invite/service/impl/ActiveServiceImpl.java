package com.opay.invite.service.impl;

import com.opay.invite.mapper.ActiveMapper;
import com.opay.invite.mapper.OpayActiveCashbackMapper;
import com.opay.invite.model.Active;
import com.opay.invite.service.ActiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author yimin
 */
@Service
public class ActiveServiceImpl implements ActiveService {

    @Autowired
    private ActiveMapper activeMapper;

    @Autowired
    private OpayActiveCashbackMapper opayActiveCashbackMapper;

    @Override
    public BigDecimal sumCashBackAmount(String activeId) {
        return opayActiveCashbackMapper.sumCashBackAmount(activeId);
    }

    @Override
    public int lockActive(String activeId) {
        Active active = new Active();
        active.setActiveId(activeId);
        active.setValid(1);
        return activeMapper.insert(active);
    }

    @Override
    public int isLockedActive(String activeId) {
        Active active = activeMapper.selectByActiveId(activeId);
        return active == null ? 0 : 1;
    }
}
