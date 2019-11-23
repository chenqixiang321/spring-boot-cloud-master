package com.opay.im.service.impl;

import com.opay.im.service.RongCloudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import com.opay.im.model.UserTokenModel;
import com.opay.im.mapper.UserTokenMapper;
import com.opay.im.service.UserTokenService;

import java.util.Date;

@Service
public class UserTokenServiceImpl implements UserTokenService {

    @Resource
    private UserTokenMapper userTokenMapper;
    @Autowired
    private RongCloudService rongCloudService;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return userTokenMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(UserTokenModel record) {
        return userTokenMapper.insert(record);
    }

    @Override
    public int insertSelective(UserTokenModel record) {
        return userTokenMapper.insertSelective(record);
    }

    @Override
    public UserTokenModel selectByPrimaryKey(Long id) {
        return userTokenMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(UserTokenModel record) {
        return userTokenMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(UserTokenModel record) {
        return userTokenMapper.updateByPrimaryKey(record);
    }

    @Override
    @Cacheable(value = "ryToken", key = "#userId", unless = "#result == null")
    public String getRyToken(String userId, String phone) throws Exception{
        UserTokenModel userToken = userTokenMapper.selectByUserId(userId);
        if (userToken == null) {
            String token = rongCloudService.register(userId, phone);
            if (token != null) {
                userToken = new UserTokenModel();
                userToken.setOpayId(userId);
                userToken.setPhone(phone);
                userToken.setToken(token);
                userToken.setCreateTime(new Date());
                userTokenMapper.insert(userToken);
                return token;
            }
        } else {
            return userToken.getToken();
        }
        return null;
    }


}
