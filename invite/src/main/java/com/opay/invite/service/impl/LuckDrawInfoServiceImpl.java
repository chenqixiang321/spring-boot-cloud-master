package com.opay.invite.service.impl;

import com.opay.invite.config.PrizePoolConfig;
import com.opay.invite.mapper.LuckDrawInfoMapper;
import com.opay.invite.model.LuckDrawInfoModel;
import com.opay.invite.model.PrizeModel;
import com.opay.invite.model.response.LuckDrawInfoResponse;
import com.opay.invite.model.response.PrizePoolResponse;
import com.opay.invite.service.AliasMethodService;
import com.opay.invite.service.LuckDrawInfoService;
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

    @Override
    public LuckDrawInfoResponse getLuckDraw(String opayId, String opayName, String opayPhone) throws Exception {
        String prizePool = "prize_pool:first:";
        int firstPrizePoolIndex = aliasMethodService.firstPoolAliasMethod();
        int secondPrizePoolIndex = aliasMethodService.secondPoolAliasMethod();
        List<String> keys = Arrays.asList(opayId);
        PrizePoolResponse prizePoolResponse = (PrizePoolResponse) redisTemplate.execute(inviteShareCountDec, keys, firstPrizePoolIndex, secondPrizePoolIndex, grandPrizeIndex, secondPoolRate);
        LuckDrawInfoModel luckDrawInfoModel = new LuckDrawInfoModel();
        Date date = new Date();
        LuckDrawInfoResponse luckDrawInfoResponse = new LuckDrawInfoResponse();
        if ("success".equals(prizePoolResponse.getMessage())) {
            luckDrawInfoModel.setCreateTime(date);
            luckDrawInfoModel.setOpayId(opayId);
            luckDrawInfoModel.setOpayName(opayName);
            luckDrawInfoModel.setOpayPhone(opayPhone);
            List<PrizeModel> prizes = prizePoolConfig.getFirstPool();
            if (prizePoolResponse.getPool() == 2) {
                prizes = prizePoolConfig.getSecondPool();
            }
            if (prizePoolResponse.getPrize() != null) {
                luckDrawInfoModel.setPrizeLevel(prizePoolResponse.getPrize());
                luckDrawInfoModel.setPrize(prizes.get(prizePoolResponse.getPrize()).getPrize());
            } else {
                int index;
                String prize;
                if (prizePoolResponse.getPool() == 1) {
                    index = prizePoolConfig.getFirstPool().size() - 1;
                    prize = prizePoolConfig.getFirstPool().get(index).getPrize();
                } else {
                    index = prizePoolConfig.getSecondPool().size() - 1;
                    prize = prizePoolConfig.getSecondPool().get(index).getPrize();
                }
                luckDrawInfoModel.setPrizeLevel(index);
                luckDrawInfoModel.setPrize(prize);
            }
            luckDrawInfoModel.setPrizePool(prizePoolResponse.getPool());
            luckDrawInfoMapper.insertSelective(luckDrawInfoModel);
            luckDrawInfoResponse.setPrize(luckDrawInfoModel.getPrize());
            luckDrawInfoResponse.setId(luckDrawInfoModel.getId());
            luckDrawInfoResponse.setOpayId(luckDrawInfoModel.getOpayId());
            luckDrawInfoResponse.setOpayName(luckDrawInfoModel.getOpayName());
        } else {
            throw new Exception(prizePoolResponse.getMessage());
        }
        return luckDrawInfoResponse;
    }

}

