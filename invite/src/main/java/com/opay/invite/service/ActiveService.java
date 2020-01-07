package com.opay.invite.service;

import java.math.BigDecimal;

/**
 * 活动Service
 * @author yimin
 */
public interface ActiveService {

    BigDecimal sumCashBackAmount(String activeId);

    int lockActive(String activeId);

    int isLockedActive(String activeId);

}
