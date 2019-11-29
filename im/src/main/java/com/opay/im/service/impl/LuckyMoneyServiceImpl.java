package com.opay.im.service.impl;

import com.opay.im.mapper.LuckyMoneyRecordMapper;
import com.opay.im.model.LuckyMoneyRecordModel;
import com.opay.im.model.request.LuckyMoneyRequest;
import com.opay.im.model.response.LuckyMoneyInfoResponse;
import com.opay.im.model.response.LuckyMoneyResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import com.opay.im.model.LuckyMoneyModel;
import com.opay.im.mapper.LuckyMoneyMapper;
import com.opay.im.service.LuckyMoneyService;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class LuckyMoneyServiceImpl implements LuckyMoneyService {

    @Resource
    private LuckyMoneyMapper luckyMoneyMapper;
    @Resource
    private LuckyMoneyRecordMapper luckyMoneyRecordMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return luckyMoneyMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(LuckyMoneyModel record) {
        return luckyMoneyMapper.insert(record);
    }

    @Override
    public int insertSelective(LuckyMoneyModel record) {
        return luckyMoneyMapper.insertSelective(record);
    }

    @Override
    public LuckyMoneyModel selectByPrimaryKey(Long id) {
        return luckyMoneyMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(LuckyMoneyModel record) {
        return luckyMoneyMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(LuckyMoneyModel record) {
        return luckyMoneyMapper.updateByPrimaryKey(record);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public LuckyMoneyResponse sendLuckyMoney(LuckyMoneyRequest luckyMoneyRequest) throws Exception {
        Date date = new Date();
        LuckyMoneyModel luckyMoneyModel = new LuckyMoneyModel();
        BeanUtils.copyProperties(luckyMoneyRequest, luckyMoneyModel);
        luckyMoneyModel.setCreateTime(date);
        luckyMoneyMapper.insertSelective(luckyMoneyModel);
        BigDecimal minValue = new BigDecimal(String.valueOf(luckyMoneyRequest.getQuantity() * 0.01));
        if (luckyMoneyRequest.getAmount().compareTo(minValue) == -1) {
            throw new Exception("The amount is too small and must be larger than " + minValue);
        }
        LuckyMoneyRecordModel luckyMoneyRecordModel = null;
        RedPackage moneyPackage = new RedPackage();
        moneyPackage.remainMoney = luckyMoneyModel.getAmount();
        moneyPackage.remainSize = luckyMoneyModel.getQuantity();
        while (moneyPackage.remainSize != 0) {
            luckyMoneyRecordModel = new LuckyMoneyRecordModel();
            luckyMoneyRecordModel.setLuckMoneyId(luckyMoneyModel.getId());
            luckyMoneyRecordModel.setAmount(getRandomMoney(moneyPackage));
            luckyMoneyRecordMapper.insertSelective(luckyMoneyRecordModel);
        }
        LuckyMoneyResponse luckyMoneyResponse = new LuckyMoneyResponse();
        BeanUtils.copyProperties(luckyMoneyModel, luckyMoneyResponse);
        return luckyMoneyResponse;
    }

    @Override
    public LuckyMoneyInfoResponse selectLuckyMoneyEveryPerson(Long id) throws Exception {
        return null;
    }

    public BigDecimal getRandomMoney(RedPackage _redPackage) {
        // remainSize 剩余的红包数量
        // remainMoney 剩余的钱
        if (_redPackage.remainSize == 1) {
            _redPackage.remainSize--;
            return _redPackage.remainMoney.setScale(2, BigDecimal.ROUND_DOWN);
        }

        BigDecimal random = BigDecimal.valueOf(Math.random());
        BigDecimal min = BigDecimal.valueOf(0.01);

        BigDecimal halfRemainSize = BigDecimal.valueOf(_redPackage.remainSize).divide(new BigDecimal(2), BigDecimal.ROUND_UP);
        BigDecimal max1 = _redPackage.remainMoney.divide(halfRemainSize, BigDecimal.ROUND_DOWN);
        BigDecimal minRemainAmount = min.multiply(BigDecimal.valueOf(_redPackage.remainSize - 1)).setScale(2, BigDecimal.ROUND_DOWN);
        BigDecimal max2 = _redPackage.remainMoney.subtract(minRemainAmount);
        BigDecimal max = (max1.compareTo(max2) < 0) ? max1 : max2;

        BigDecimal money = random.multiply(max).setScale(2, BigDecimal.ROUND_DOWN);
        money = money.compareTo(min) < 0 ? min : money;

        _redPackage.remainSize--;
        _redPackage.remainMoney = _redPackage.remainMoney.subtract(money).setScale(2, BigDecimal.ROUND_DOWN);
        ;
        return money;
    }

    static class RedPackage {
        int remainSize;
        BigDecimal remainMoney;
    }
}
