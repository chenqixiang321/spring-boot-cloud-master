package com.opay.invite.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.opay.invite.config.PrizePoolConfig;
import com.opay.invite.exception.InviteException;
import com.opay.invite.mapper.LuckDrawInfoMapper;
import com.opay.invite.model.LuckDrawInfoModel;
import com.opay.invite.model.PrizeModel;
import com.opay.invite.model.response.*;
import com.opay.invite.service.*;
import com.opay.invite.stateconfig.BonusStatus;
import com.opay.invite.transferconfig.OrderType;
import com.opay.invite.transferconfig.PayChannel;
import com.opay.invite.transferconfig.TransferConfig;
import com.opay.invite.utils.CommonUtil;
import com.opay.invite.utils.DateFormatter;
import com.opay.invite.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class LuckDrawInfoServiceImpl implements LuckDrawInfoService {
    private Map<Integer, PrizeModel> prizeInfo = null;
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
    private TransferConfig opayConfig;
    @Autowired
    private OpayApiService opayApiService;
    @Autowired
    private IncrKeyService incrKeyService;
    @Autowired
    private RpcService rpcService;
    @Value("${opay.luckDraw.callBack}")
    private String bonusCallBack;

    @Value("${opay.luckDraw.merchantId}")
    private String merchantId;
    @Value("${opay.luckDraw.aesKey}")
    private String aesKey;
    @Value("${opay.luckDraw.iv}")
    private String iv;

    @Autowired
    private RedisUtil redisUtil;


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
            String[] names = luckDrawInfoResponse.getOpayName().split(" ");
            if (names.length > 2) {
                luckDrawInfoResponse.setOpayName(String.format("%s *** %s", names[0], names[names.length - 1]));
            } else {
                luckDrawInfoResponse.setOpayName(String.format("%s ***", names[0]));
            }
            luckDrawInfoResponse.setPrizeId(luckDrawInfoModel.getPrizeLevel());
            luckDrawInfoResponseList.add(luckDrawInfoResponse);
        }
        return luckDrawInfoResponseList;
    }

    @Override
    public List<LuckDrawListResponse> selectLuckDrawInfoList(String opayId, int pageNum, int pageSize) throws Exception {
        List<LuckDrawListResponse> luckDrawListResponseList = new ArrayList<>();
        PageHelper.startPage(pageNum, pageSize);
        List<LuckDrawInfoModel> luckDrawInfoModelList = luckDrawInfoMapper.selectLuckDrawInfoListByOpayId(opayId);
        LuckDrawListResponse luckDrawListResponse = null;
        for (LuckDrawInfoModel luckDrawInfoModel : luckDrawInfoModelList) {
            luckDrawListResponse = new LuckDrawListResponse();
            BeanUtils.copyProperties(luckDrawInfoModel, luckDrawListResponse);
            luckDrawListResponse.setPrizeId(luckDrawInfoModel.getPrizeLevel());
            luckDrawListResponseList.add(luckDrawListResponse);
        }
        return luckDrawListResponseList;
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
        log.info("getLuckDraw prizePoolResponse:{}", JSON.toJSONString(prizePoolResponse));
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
            PrizeModel pm = prizes.get(prizePoolResponse.getPrize());
            String prize = pm.getPrize();
            luckDrawInfoModel.setPrizeLevel(pm.getId());
            luckDrawInfoModel.setPrize(prize);
            luckDrawInfoModel.setPrizePool(prizePoolResponse.getPool());
            luckDrawInfoResponse.setPrize(luckDrawInfoModel.getPrize());
            luckDrawInfoResponse.setPrizeId(pm.getId());

            //判断中手机大奖的数量
            if ("phone".equals(prize)) {
                //判断redis库存
                Integer phoneNum = redisUtil.get("invite_active_", "phoneNum");
                if(phoneNum == null || phoneNum <= 0 ){
                    log.warn("大奖已被抽完 phoneNum:{}",phoneNum);
                    luckDrawInfoModel.setPrizeLevel(2);
                    luckDrawInfoModel.setPrize("100");
                    luckDrawInfoResponse.setPrize("100");
                    luckDrawInfoResponse.setPrizeId(2);
                }else{
                    log.warn("恭喜你，抽到大奖了 phoneNum:{}",phoneNum);
                    redisUtil.decr("invite_active_", "phoneNum", 1);
                }
            }

            Integer prizeInt = new Integer(0);
            if (CommonUtil.isInteger(prize)) {
                prizeInt = new Integer(prize);
            }
            // 奖励金超过奖金限制
            if (redisUtil.decr("invite_active_", "luckDrawTotalAmount", prizeInt) < 0 ) {
                log.warn("活动金额超限，活动结束");
                throw new InviteException("Event funds have been exhausted, thank you for your participation");
            }

            if (CommonUtil.isInteger(prize) && !"0".equals(prize)) {
                String requestId = incrKeyService.getIncrKey();
                String reference = incrKeyService.getIncrKey("LD");
                luckDrawInfoModel.setRequestId(requestId);
                luckDrawInfoModel.setReference(reference);
                Map<String, String> data = rpcService.getParamMap(merchantId, opayId, prize, null, null, reference, OrderType.bonusOffer.getOrderType(), bonusCallBack, PayChannel.BalancePayment.getPayChannel());
                log.info("request to createOrder {}", data);
                OpayApiResultResponse<OpayApiOrderResultResponse> opayApiResultResponse = opayApiService.createOrder(merchantId, requestId, data, aesKey, iv);
                log.info("response from createOrder {}", opayApiResultResponse.toString());
                if (!"00000".equals(opayApiResultResponse.getCode())) {
                    luckDrawInfoModel.setStatus(3);
                    throw new InviteException(opayApiResultResponse.getMessage());
                }
                String status = opayApiResultResponse.getData().getStatus();
                if ("SUCCESS".equals(status)) {
                    luckDrawInfoModel.setStatus(BonusStatus.SUCCESS);
                } else if ("FAIL".equals(status)) {
                    luckDrawInfoModel.setStatus(BonusStatus.FAIL);
                } else {
                    luckDrawInfoModel.setStatus(BonusStatus.PENDING);
                }
                luckDrawInfoModel.setOrderNo(status);
                luckDrawInfoMapper.insertSelective(luckDrawInfoModel);
            } else {
                luckDrawInfoMapper.insertSelective(luckDrawInfoModel);
            }
        } else {
            throw new InviteException(prizePoolResponse.getMessage());
        }
        luckDrawInfoResponse.setActivityCount(prizePoolResponse.getActivityCount());
        luckDrawInfoResponse.setLoginCount(prizePoolResponse.getLoginCount());
        luckDrawInfoResponse.setInviteCount(prizePoolResponse.getInviteCount());
        luckDrawInfoResponse.setShareCount(prizePoolResponse.getShareCount());
        return luckDrawInfoResponse;
    }

    @Override
    public Map<Integer, PrizeModel> getPrize() {
        if (prizeInfo == null) {
            prizeInfo = new ConcurrentHashMap();
            List<PrizeModel> firstPool = prizePoolConfig.getFirstPool();
            List<PrizeModel> secondPool = prizePoolConfig.getSecondPool();
            int firstPollSize = firstPool.size();
            int secondPollSize = secondPool.size();
            for (int i = 0; i < firstPollSize; i++) {
                PrizeModel prizeModel = firstPool.get(i);
                prizeInfo.put(prizeModel.getId(), prizeModel);
            }
            for (int i = 0; i < secondPollSize; i++) {
                PrizeModel prizeModel = secondPool.get(i);
                prizeInfo.put(prizeModel.getId(), prizeModel);
            }
        }
        return prizeInfo;
    }

    @Override
    public int updateBonusStatus(String reference, int status) {
        return luckDrawInfoMapper.updateBonusStatus(reference, status);
    }
}

