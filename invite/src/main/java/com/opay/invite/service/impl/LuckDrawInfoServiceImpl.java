package com.opay.invite.service.impl;

import com.github.pagehelper.PageHelper;
import com.opay.invite.config.OpayConfig;
import com.opay.invite.config.PrizePoolConfig;
import com.opay.invite.exception.InviteException;
import com.opay.invite.mapper.LuckDrawInfoMapper;
import com.opay.invite.model.LuckDrawInfoModel;
import com.opay.invite.model.PrizeModel;
import com.opay.invite.model.response.LuckDrawInfoResponse;
import com.opay.invite.model.response.LuckDrawListResponse;
import com.opay.invite.model.response.OpayApiResultResponse;
import com.opay.invite.model.response.PrizePoolResponse;
import com.opay.invite.service.AliasMethodService;
import com.opay.invite.service.IncrKeyService;
import com.opay.invite.service.LuckDrawInfoService;
import com.opay.invite.service.OpayApiService;
import com.opay.invite.service.RpcService;
import com.opay.invite.transferconfig.OrderType;
import com.opay.invite.transferconfig.PayChannel;
import com.opay.invite.utils.CommonUtil;
import com.opay.invite.utils.DateFormatter;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class LuckDrawInfoServiceImpl implements LuckDrawInfoService {

    @Resource
    private LuckDrawInfoMapper luckDrawInfoMapper;
    @Autowired
    private AliasMethodService aliasMethodService;
    @Resource(name = "inviteShareCountDec")
    private DefaultRedisScript<PrizePoolResponse> inviteShareCountDec;
    @Value("${spring.jackson.time-zone}")
    private String timeZone;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private PrizePoolConfig prizePoolConfig;
    @Autowired
    private OpayConfig opayConfig;
    @Autowired
    private OpayApiService opayApiService;
    @Autowired
    private IncrKeyService incrKeyService;
    @Autowired
    private RpcService rpcService;
    @Value("${transfer.opay.transferNotify}")
    private String transferNotify;

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
    @Transactional(rollbackFor = Exception.class)
    public LuckDrawInfoResponse getLuckDraw(String opayId, String opayName, String opayPhone) throws Exception {
        int firstPrizePoolIndex = aliasMethodService.firstPoolAliasMethod();
        int secondPrizePoolIndex = aliasMethodService.secondPoolAliasMethod();
        LuckDrawInfoResponse luckDrawInfoResponse = new LuckDrawInfoResponse();
        List<String> keys = Arrays.asList(opayId);
        Date date = new Date();
        prizePoolConfig.getFirstGrandPrizeTimeUp();
        String ymd = DateFormatter.formatDateByZone(date, timeZone);
        long now = DateFormatter.parseYMDHMSDate(DateFormatter.formatDatetimeByZone(date, timeZone)).getTime();
        long secondLong = DateFormatter.parseYMDHMSDate(ymd + " " + prizePoolConfig.getSecondGrandPrizeTimeUp()).getTime();
        boolean grandPrizeTimeUp = false;
        if (now > secondLong) {
            grandPrizeTimeUp = true;
        } else {
            long firstLong = DateFormatter.parseYMDHMSDate(ymd + " " + prizePoolConfig.getFirstGrandPrizeTimeUp()).getTime();
            if (now > firstLong) {
                grandPrizeTimeUp = true;
            }
        }
        PrizePoolResponse prizePoolResponse = (PrizePoolResponse) redisTemplate.execute(inviteShareCountDec, keys, firstPrizePoolIndex, secondPrizePoolIndex, prizePoolConfig.getGrandPrizeIndex(), grandPrizeTimeUp, prizePoolConfig.getSecondPoolRate());
        LuckDrawInfoModel luckDrawInfoModel = new LuckDrawInfoModel();

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
                String requestId = incrKeyService.getIncrKey();
                String reference = incrKeyService.getIncrKey();
                luckDrawInfoModel.setPrizeLevel(prizePoolResponse.getPrize());
                luckDrawInfoModel.setPrize(prize);
                luckDrawInfoModel.setPrizePool(prizePoolResponse.getPool());
                luckDrawInfoModel.setRequestId(requestId);
                luckDrawInfoModel.setReference(reference);
                luckDrawInfoMapper.insertSelective(luckDrawInfoModel);
                luckDrawInfoResponse.setPrize(luckDrawInfoModel.getPrize());
                if (CommonUtil.isInteger(prize)) {
                    Map<String, String> data = rpcService.getParamMap(opayConfig.getMerchantId(), opayId, prize, null, null, reference, OrderType.bonusOffer.getOrderType(), transferNotify, PayChannel.BalancePayment.getPayChannel());
                    OpayApiResultResponse opayApiResultResponse = opayApiService.createOrder(opayConfig.getMerchantId(), requestId, data, opayConfig.getAesKey(), opayConfig.getIv());
                    if (!"00000".equals(opayApiResultResponse.getCode())) {
                        throw new InviteException(opayApiResultResponse.getMessage());
                    }
                }
            }
        } else {
            throw new InviteException(prizePoolResponse.getMessage());
        }
        luckDrawInfoResponse.setActivityCount(prizePoolResponse.getActivityCount());
        luckDrawInfoResponse.setUserCount(prizePoolResponse.getUserCount());
        return luckDrawInfoResponse;
    }

}

