package com.opay.invite.service.impl;

import com.opay.invite.model.response.LuckDrawInfoResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import com.opay.invite.mapper.LuckDrawInfoMapper;
import com.opay.invite.model.LuckDrawInfoModel;
import com.opay.invite.service.LuckDrawInfoService;

import java.util.ArrayList;
import java.util.List;

@Service
public class LuckDrawInfoServiceImpl implements LuckDrawInfoService {

    @Resource
    private LuckDrawInfoMapper luckDrawInfoMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return luckDrawInfoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(LuckDrawInfoModel record) {
        return luckDrawInfoMapper.insert(record);
    }

    @Override
    public int insertSelective(LuckDrawInfoModel record) {
        return luckDrawInfoMapper.insertSelective(record);
    }

    @Override
    public LuckDrawInfoModel selectByPrimaryKey(Long id) {
        return luckDrawInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(LuckDrawInfoModel record) {
        return luckDrawInfoMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(LuckDrawInfoModel record) {
        return luckDrawInfoMapper.updateByPrimaryKey(record);
    }

    @Override
    @Cacheable(value = "luckDrawInfoList", unless = "#result == null")
    public List<LuckDrawInfoResponse> selectLuckDrawInfoList() throws Exception {
        List<LuckDrawInfoResponse> luckDrawInfoResponseList = new ArrayList<>();
        List<LuckDrawInfoModel> luckDrawInfoModelList = luckDrawInfoMapper.selectLuckDrawInfoList();
        LuckDrawInfoResponse luckDrawInfoResponse = null;
        for (LuckDrawInfoModel luckDrawInfoModel : luckDrawInfoModelList) {
            luckDrawInfoResponse = new LuckDrawInfoResponse();
            BeanUtils.copyProperties(luckDrawInfoModel, luckDrawInfoResponse);
            luckDrawInfoResponseList.add(luckDrawInfoResponse);
        }
        return luckDrawInfoResponseList;
    }

}

