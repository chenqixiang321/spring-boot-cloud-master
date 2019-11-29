package com.opay.im.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.opay.im.model.LuckyMoneyRecordModel;
import com.opay.im.mapper.LuckyMoneyRecordMapper;
import com.opay.im.service.LuckyMoneyRecordService;
@Service
public class LuckyMoneyRecordServiceImpl implements LuckyMoneyRecordService{

    @Resource
    private LuckyMoneyRecordMapper luckyMoneyRecordMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return luckyMoneyRecordMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(LuckyMoneyRecordModel record) {
        return luckyMoneyRecordMapper.insert(record);
    }

    @Override
    public int insertSelective(LuckyMoneyRecordModel record) {
        return luckyMoneyRecordMapper.insertSelective(record);
    }

    @Override
    public LuckyMoneyRecordModel selectByPrimaryKey(Long id) {
        return luckyMoneyRecordMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(LuckyMoneyRecordModel record) {
        return luckyMoneyRecordMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(LuckyMoneyRecordModel record) {
        return luckyMoneyRecordMapper.updateByPrimaryKey(record);
    }

}
