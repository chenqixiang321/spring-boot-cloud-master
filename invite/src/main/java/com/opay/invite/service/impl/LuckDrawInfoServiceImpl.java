package com.opay.invite.service.impl;

import com.github.pagehelper.PageHelper;
import com.opay.invite.config.PrizePoolConfig;
import com.opay.invite.exception.InviteException;
import com.opay.invite.mapper.LuckDrawInfoMapper;
import com.opay.invite.model.LuckDrawInfoModel;
import com.opay.invite.model.PrizeModel;
import com.opay.invite.model.response.LuckDrawInfoResponse;
import com.opay.invite.model.response.LuckDrawListResponse;
import com.opay.invite.model.response.PrizePoolResponse;
import com.opay.invite.service.AliasMethodService;
import com.opay.invite.service.LuckDrawInfoService;
import com.opay.invite.utils.DateFormatter;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class LuckDrawInfoServiceImpl implements LuckDrawInfoService {

    @Resource
    private LuckDrawInfoMapper luckDrawInfoMapper;
    @Autowired
    private AliasMethodService aliasMethodService;
    @Resource(name = "inviteShareCountDec")
    private DefaultRedisScript<PrizePoolResponse> inviteShareCountDec;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private PrizePoolConfig prizePoolConfig;
    @Value("${prize-pool.grandPrizeIndex}")
    private int grandPrizeIndex;
    @Value("${prize-pool.secondPoolRate}")
    private int secondPoolRate;

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
    @Cacheable(value = "luckDrawInfoList@600", unless = "#result == null")
    public List<LuckDrawListResponse> selectLuckDrawInfoList() throws Exception {
        List<LuckDrawListResponse> luckDrawInfoResponseList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        List<LuckDrawInfoModel> luckDrawInfoModelList = luckDrawInfoMapper.selectLuckDrawInfoList(DateFormatter.getStartTime(calendar), DateFormatter.getEndTime(calendar));
        LuckDrawListResponse luckDrawInfoResponse = null;
        for (LuckDrawInfoModel luckDrawInfoModel : luckDrawInfoModelList) {
            luckDrawInfoResponse = new LuckDrawListResponse();
            BeanUtils.copyProperties(luckDrawInfoModel, luckDrawInfoResponse);
            luckDrawInfoResponseList.add(luckDrawInfoResponse);
        }
        return luckDrawInfoResponseList;
    }

    @Override
    public List<LuckDrawListResponse> selectLuckDrawInfoList(String opayId, int pageNum, int pageSize) throws Exception {
        List<LuckDrawListResponse> luckDrawInfoResponseList = new ArrayList<>();
        PageHelper.startPage(pageNum, pageSize);
        List<LuckDrawInfoModel> luckDrawInfoModelList = luckDrawInfoMapper.selectLuckDrawInfoListByOpayId(opayId);
        LuckDrawListResponse luckDrawInfoResponse = null;
        for (LuckDrawInfoModel luckDrawInfoModel : luckDrawInfoModelList) {
            luckDrawInfoResponse = new LuckDrawListResponse();
            BeanUtils.copyProperties(luckDrawInfoModel, luckDrawInfoResponse);
            luckDrawInfoResponseList.add(luckDrawInfoResponse);
        }
        return luckDrawInfoResponseList;
    }

    @Override
    public LuckDrawInfoResponse getLuckDraw(String opayId, String opayName, String opayPhone) throws Exception {
        int firstPrizePoolIndex = aliasMethodService.firstPoolAliasMethod();
        int secondPrizePoolIndex = aliasMethodService.secondPoolAliasMethod();
        LuckDrawInfoResponse luckDrawInfoResponse = new LuckDrawInfoResponse();
        List<String> keys = Arrays.asList(opayId);
        boolean grandPrizeTimeUp = true;
        PrizePoolResponse prizePoolResponse = (PrizePoolResponse) redisTemplate.execute(inviteShareCountDec, keys, firstPrizePoolIndex, secondPrizePoolIndex, grandPrizeIndex, grandPrizeTimeUp, secondPoolRate);
        LuckDrawInfoModel luckDrawInfoModel = new LuckDrawInfoModel();
        Date date = new Date();
        if ("success".equals(prizePoolResponse.getMessage()) && prizePoolResponse.getPrize() != null) {
            luckDrawInfoModel.setCreateTime(date);
            luckDrawInfoModel.setOpayId(opayId);
            luckDrawInfoModel.setOpayName(opayName);
            luckDrawInfoModel.setOpayPhone(opayPhone);
            List<PrizeModel> prizes = prizePoolConfig.getFirstPool();
            if (prizePoolResponse.getPool() == 2) {
                prizes = prizePoolConfig.getSecondPool();
            }
            String prize = prizes.get(prizePoolResponse.getPrize()).getPrize();
            if (!"0".equals(prize)) {
                luckDrawInfoModel.setPrizeLevel(prizePoolResponse.getPrize());
                luckDrawInfoModel.setPrize(prize);
                luckDrawInfoModel.setPrizePool(prizePoolResponse.getPool());
                luckDrawInfoMapper.insertSelective(luckDrawInfoModel);
                luckDrawInfoResponse.setPrize(luckDrawInfoModel.getPrize());
            }
        } else {
            throw new InviteException(prizePoolResponse.getMessage());
        }
        luckDrawInfoResponse.setActivityCount(prizePoolResponse.getActivityCount());
        luckDrawInfoResponse.setUserCount(prizePoolResponse.getUserCount());
        return luckDrawInfoResponse;
    }

}

