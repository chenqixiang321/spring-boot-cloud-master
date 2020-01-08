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

//    @Override
//    public BigDecimal sumCashBackAmount(String activeId) {
//        return opayActiveCashbackMapper.sumCashBackAmount(activeId);
//    }

    @Override
    public BigDecimal sumCashBackAmount(String activeId) {
        return null;
    }

    @Override
    public int lockActive(String activeId) {
        Active active = activeMapper.selectByActiveId(activeId);
        if(active == null){
            Active activeNew = new Active();
            activeNew.setActiveId(activeId);
            activeNew.setValid(1);
            return activeMapper.insert(activeNew);
        }else{
            active.setValid(1);
            return activeMapper.update(active);
        }
    }

    @Override
    public int unLockActive(String activeId) throws Exception {
        Active active = activeMapper.selectByActiveId(activeId);
        if(active == null){
            throw new Exception("error activeId");
        }
        active.setValid(0);
        return activeMapper.update(active);
    }

    @Override
    public int isLockedActive(String activeId) {
        Active active = activeMapper.selectByActiveId(activeId);
        if(active !=null && "1".equals(active.getValid())){
            return 1;
        }
        return 0;
    }
}
