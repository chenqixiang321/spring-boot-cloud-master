package com.opay.im.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opay.im.common.SystemCode;
import com.opay.im.config.OpayConfig;
import com.opay.im.constant.LuckyMoneyStatus;
import com.opay.im.exception.ImException;
import com.opay.im.exception.LuckyMoneyException;
import com.opay.im.mapper.LuckyMoneyMapper;
import com.opay.im.mapper.LuckyMoneyRecordMapper;
import com.opay.im.model.ChatGroupMemberModel;
import com.opay.im.model.LuckyMoneyModel;
import com.opay.im.model.LuckyMoneyRecordModel;
import com.opay.im.model.request.GrabLuckyMoneyRequest;
import com.opay.im.model.request.LuckyMoneyRequest;
import com.opay.im.model.request.OpayAcceptLuckyMoneyRequest;
import com.opay.im.model.response.GrabLuckyMoneyResponse;
import com.opay.im.model.response.GrabLuckyMoneyResult;
import com.opay.im.model.response.LuckyMoneyInfoResponse;
import com.opay.im.model.response.LuckyMoneyRecordListResponse;
import com.opay.im.model.response.LuckyMoneyRecordResponse;
import com.opay.im.model.response.LuckyMoneyResponse;
import com.opay.im.model.response.OpayApiResultResponse;
import com.opay.im.model.response.opaycallback.OPayCallBackResponse;
import com.opay.im.model.response.opaycallback.PayloadResponse;
import com.opay.im.service.ChatGroupMemberService;
import com.opay.im.service.IncrKeyService;
import com.opay.im.service.LuckyMoneyService;
import com.opay.im.service.OpayApiService;
import com.opay.im.service.RongCloudService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

@Service
public class LuckyMoneyServiceImpl implements LuckyMoneyService {

    @Resource
    private LuckyMoneyMapper luckyMoneyMapper;
    @Resource
    private LuckyMoneyRecordMapper luckyMoneyRecordMapper;
    @Resource(name = "sendLuckyMoney")
    private DefaultRedisScript<Boolean> sendLuckyMoney;
    @Resource(name = "grabLuckyMoney")
    private DefaultRedisScript<GrabLuckyMoneyResult> grabLuckyMoney;
    @Resource(name = "resetLuckyMoney")
    private DefaultRedisScript<Boolean> resetLuckyMoney;
    @Value("${im.luckyMoney.singleMax}")
    private int singleMax;
    @Value("${im.luckyMoney.dayMax}")
    private int dayMax;
    @Value("${im.luckyMoney.expirationDays}")
    private int expirationDays;
    @Value("${spring.jackson.time-zone}")
    private String timeZone;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ChatGroupMemberService chatGroupMemberService;
    @Autowired
    private IncrKeyService incrKeyService;
    @Autowired
    private RongCloudService rongCloudService;
    @Autowired
    private OpayApiService opayApiService;
    @Autowired
    private OpayConfig opayConfig;

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
        String reference = incrKeyService.getIncrKey("LM");
        LuckyMoneyModel luckyMoneyModel = new LuckyMoneyModel();
        BeanUtils.copyProperties(luckyMoneyRequest, luckyMoneyModel);
        luckyMoneyModel.setCreateTime(date);
        luckyMoneyModel.setReference(reference);
        luckyMoneyMapper.insertSelective(luckyMoneyModel);
        BigDecimal minValue = new BigDecimal(String.valueOf(luckyMoneyRequest.getQuantity() * 0.01));
        BigDecimal maxValue = new BigDecimal(singleMax);
        if (luckyMoneyRequest.getAmount().compareTo(minValue) == -1) {
            throw new Exception("The amount is too small and must be larger than " + minValue);
        }
        if (luckyMoneyRequest.getAmount().compareTo(maxValue) == 1) {
            throw new Exception("The amount is too large and must be smaller than " + maxValue);
        }
        LuckyMoneyRecordModel luckyMoneyRecordModel = null;
        RedPackage moneyPackage = new RedPackage();
        moneyPackage.remainMoney = luckyMoneyModel.getAmount();
        moneyPackage.remainSize = luckyMoneyModel.getQuantity();
        List<String> amounts = new ArrayList<>();
        List<String> amountIds = new ArrayList<>();
        while (moneyPackage.remainSize != 0) {
            luckyMoneyRecordModel = new LuckyMoneyRecordModel();
            luckyMoneyRecordModel.setLuckMoneyId(luckyMoneyModel.getId());
            luckyMoneyRecordModel.setAmount(getRandomMoney(moneyPackage));
            luckyMoneyRecordModel.setCreateTime(date);
            luckyMoneyRecordMapper.insertSelective(luckyMoneyRecordModel);
            amounts.add(String.valueOf(luckyMoneyRecordModel.getAmount()));
            amountIds.add(String.valueOf(luckyMoneyRecordModel.getId()));
        }
        List<String> keys = Arrays.asList(String.valueOf(luckyMoneyModel.getId()), String.valueOf(luckyMoneyModel.getTargetType()), luckyMoneyModel.getOpayId(), luckyMoneyModel.getTargetId());
        Boolean sendLuckyMoneyResult = (Boolean) redisTemplate.execute(sendLuckyMoney, keys, luckyMoneyRequest.getAmount(), String.join(",", amountIds), String.join(",", amounts), dayMax, expirationDays * 24 * 3600, getEndTime());
        if (!sendLuckyMoneyResult) {
            throw new LuckyMoneyException(SystemCode.LUCKY_MONEY_LIMIT.getCode(), SystemCode.LUCKY_MONEY_LIMIT.getMessage());
        }
        LuckyMoneyResponse luckyMoneyResponse = new LuckyMoneyResponse();
        luckyMoneyResponse.setId(luckyMoneyModel.getId());
        luckyMoneyResponse.setReference("SLM:" + luckyMoneyModel.getId() + ":" + reference);
        return luckyMoneyResponse;
    }

    private Long getEndTime() {
        Calendar now = Calendar.getInstance(TimeZone.getTimeZone(timeZone));
        Calendar todayEnd = Calendar.getInstance(TimeZone.getTimeZone(timeZone));
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        return (todayEnd.getTimeInMillis() - now.getTimeInMillis()) / 1000;
    }

    @Override
    public GrabLuckyMoneyResponse grabLuckyMoney(GrabLuckyMoneyRequest grabLuckyMoneyRequest) throws Exception {
        if (grabLuckyMoneyRequest.getTargetType() == 1) {
            List<ChatGroupMemberModel> members = chatGroupMemberService.selectGroupMember(Long.parseLong(grabLuckyMoneyRequest.getTargetId()));
            boolean isInGroup = false;
            for (ChatGroupMemberModel member : members) {
                if (member.getOpayId().equals(grabLuckyMoneyRequest.getCurrentOpayId())) {
                    isInGroup = true;
                }
            }
            if (!isInGroup) {
                throw new LuckyMoneyException(SystemCode.IM_NOT_BELONG_GROUP.getCode(), SystemCode.IM_NOT_BELONG_GROUP.getMessage());
            }
        } else {
            grabLuckyMoneyRequest.setTargetId(grabLuckyMoneyRequest.getCurrentOpayId());
        }
        List<String> keys = Arrays.asList(String.valueOf(grabLuckyMoneyRequest.getId()), String.valueOf(grabLuckyMoneyRequest.getTargetType()), grabLuckyMoneyRequest.getSenderId(), grabLuckyMoneyRequest.getTargetId());
        GrabLuckyMoneyResult grabLuckyMoneyResult = (GrabLuckyMoneyResult) redisTemplate.execute(grabLuckyMoney, keys, grabLuckyMoneyRequest.getCurrentOpayId());
        LuckyMoneyModel luckyMoneyModelData = selectLuckyMoneyByOpayId(grabLuckyMoneyRequest.getId());
        if (luckyMoneyModelData == null) {
            throw new LuckyMoneyException(grabLuckyMoneyResult.getCode(), grabLuckyMoneyResult.getMessage());
        }
        GrabLuckyMoneyResponse grabLuckyMoneyResponse = new GrabLuckyMoneyResponse();
        grabLuckyMoneyResponse.setAmount(luckyMoneyModelData.getAmount());
        grabLuckyMoneyResponse.setId(grabLuckyMoneyRequest.getId());
        grabLuckyMoneyResponse.setOpayName(luckyMoneyModelData.getOpayName());
        grabLuckyMoneyResponse.setOpayId(luckyMoneyModelData.getOpayId());
        grabLuckyMoneyResponse.setGrabAmount(grabLuckyMoneyResult.getAmount());
        grabLuckyMoneyResponse.setShow(luckyMoneyModelData.getShow());
        grabLuckyMoneyResponse.setTheme(luckyMoneyModelData.getTheme());
        if (SystemCode.SYS_API_SUCCESS.getCode().equals(grabLuckyMoneyResult.getCode())) {//成功抢到红包
            String reference = incrKeyService.getIncrKey("LM");
            LuckyMoneyRecordModel luckyMoneyRecordModel = new LuckyMoneyRecordModel();
            luckyMoneyRecordModel.setId(grabLuckyMoneyResult.getId());
            luckyMoneyRecordModel.setOpayPhone(grabLuckyMoneyRequest.getCurrentPhone());
            luckyMoneyRecordModel.setOpayName(grabLuckyMoneyRequest.getCurrentOpayName());
            luckyMoneyRecordModel.setOpayId(grabLuckyMoneyRequest.getCurrentOpayId());
            luckyMoneyRecordModel.setGetTime(new Date());
            luckyMoneyRecordModel.setReference(reference);

            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> map = new HashMap<>();
            map.put("sendUserId", luckyMoneyModelData.getOpayId());
            map.put("sendNickName", luckyMoneyModelData.getOpayName());
            map.put("openUserId", grabLuckyMoneyRequest.getCurrentOpayId());
            map.put("openNickName", grabLuckyMoneyRequest.getCurrentOpayName());
            map.put("envelopeId", grabLuckyMoneyRequest.getId());
            map.put("targetId", grabLuckyMoneyRequest.getTargetId());
            map.put("status", "1");//0:未抢,1:已抢 ,2:过期
            String requestId = incrKeyService.getIncrKey();
            OpayAcceptLuckyMoneyRequest opayAcceptLuckyMoneyRequest = new OpayAcceptLuckyMoneyRequest();
            opayAcceptLuckyMoneyRequest.setAccepterId(grabLuckyMoneyRequest.getCurrentOpayId());
            opayAcceptLuckyMoneyRequest.setAmount((grabLuckyMoneyResult.getAmount().multiply(new BigDecimal(100))).longValue());
            opayAcceptLuckyMoneyRequest.setMessage(luckyMoneyModelData.getShow());
            opayAcceptLuckyMoneyRequest.setMerchartOrderNo(String.format("RLM:%s:%s:%s", grabLuckyMoneyRequest.getId(), grabLuckyMoneyResult.getId(), reference));
            opayAcceptLuckyMoneyRequest.setSendOrderNo(luckyMoneyModelData.getTransactionId());
            opayAcceptLuckyMoneyRequest.setClientSource("App");
            OpayApiResultResponse<Map> opayApiResultResponse = opayApiService.acceptRedPacket(opayConfig.getMerchantId(), requestId, opayAcceptLuckyMoneyRequest, opayConfig.getAesKey(), opayConfig.getIv());
            if ("00000".equals(opayApiResultResponse.getCode())) {
                Map<String, String> resultMap = opayApiResultResponse.getData();
                if ("PENDING".equals(resultMap.get("orderStatus"))) {
                    luckyMoneyRecordModel.setGetStatus(3);
                } else if ("FAIL".equals(resultMap.get("orderStatus"))) {
                    luckyMoneyRecordModel.setGetStatus(2);
                    List<String> keys2 = Arrays.asList(String.valueOf(grabLuckyMoneyRequest.getId()), String.valueOf(grabLuckyMoneyRequest.getTargetType()), grabLuckyMoneyRequest.getSenderId(), grabLuckyMoneyRequest.getTargetId());
                    redisTemplate.execute(resetLuckyMoney, keys2, grabLuckyMoneyResult.getId(), grabLuckyMoneyRequest.getCurrentOpayId());
                    throw new ImException(resultMap.get("errorCode"), resultMap.get("errorMsg"));
                } else {
                    luckyMoneyRecordModel.setGetStatus(1);
                }
            } else {
                List<String> keys2 = Arrays.asList(String.valueOf(grabLuckyMoneyRequest.getId()), String.valueOf(grabLuckyMoneyRequest.getTargetType()), grabLuckyMoneyRequest.getSenderId(), grabLuckyMoneyRequest.getTargetId());
                redisTemplate.execute(resetLuckyMoney, keys2, grabLuckyMoneyResult.getId(), grabLuckyMoneyRequest.getCurrentOpayId());
                throw new ImException(opayApiResultResponse.getCode(), opayApiResultResponse.getMessage());
            }
            luckyMoneyRecordMapper.updateByPrimaryKeySelective(luckyMoneyRecordModel);
            rongCloudService.sendMessage(grabLuckyMoneyRequest.getTargetId(), grabLuckyMoneyRequest.getSenderId(), mapper.writeValueAsString(map), "");
            rongCloudService.sendMessage(grabLuckyMoneyRequest.getSenderId(), grabLuckyMoneyRequest.getTargetId(), mapper.writeValueAsString(map), "");
            return grabLuckyMoneyResponse;
        } else if ("20003".equals(grabLuckyMoneyResult.getCode())) {
            return grabLuckyMoneyResponse;
        } else {
            throw new LuckyMoneyException(grabLuckyMoneyResult.getCode(), grabLuckyMoneyResult.getMessage());
        }
    }

    @Override
    @CacheEvict(value = "luckyMoneyInfo", key = "#id")
    public int updatePayStatus(Long id, OPayCallBackResponse oPayCallBackResponse) throws Exception {
        PayloadResponse payload = oPayCallBackResponse.getPayload();
        LuckyMoneyModel luckyMoneyModel = new LuckyMoneyModel();
        if ("successful".equals(payload.getStatus())) {
            luckyMoneyModel.setPayStatus(1);
        } else if ("failed".equals(payload.getStatus())) {
            luckyMoneyModel.setPayStatus(2);
        } else {
            luckyMoneyModel.setPayStatus(3);
        }
        String reference = payload.getReference().split(":")[2];
        String luckMoneyId = payload.getReference().split(":")[1];
        LuckyMoneyModel lm = selectLuckyMoneyByOpayId(Long.parseLong(luckMoneyId));
        redisTemplate.opsForHash().put(String.format("luckyMoney:set:%s:%s:%s:%s", lm.getId(), String.valueOf(lm.getTargetType()), lm.getOpayId(), lm.getTargetId()), "payStatus", 1);
        luckyMoneyModel.setReference(reference);
        luckyMoneyModel.setTransactionId(payload.getTransactionId());
        return luckyMoneyMapper.updateByReferenceKeySelective(luckyMoneyModel);
    }

    @Override
    public Integer selectPayStatus(String opayId, String reference) throws Exception {
        String referenceData = reference.split(":")[2];
        return luckyMoneyMapper.selectPayStatus(opayId, referenceData);
    }

    @Override
    @Cacheable(value = "luckyMoneyInfo", key = "#id", unless = "#result == null")
    public LuckyMoneyModel selectLuckyMoneyByOpayId(Long id) throws Exception {
        return luckyMoneyMapper.selectLuckyMoneyById(id);
    }

    @Override
    public LuckyMoneyInfoResponse selectLuckyMoneyDetailByOpayId(Long id, String senderOpayId, String receivedOpayId) throws Exception {
        LuckyMoneyInfoResponse luckyMoneyInfoResponse = new LuckyMoneyInfoResponse();
        Object luckyMoney = redisTemplate.opsForValue().get("luckyMoney:" + id);
        if (luckyMoney == null) {
            luckyMoneyInfoResponse.setStatus(LuckyMoneyStatus.EXPIRED.getCode());
            return luckyMoneyInfoResponse;
        }
        LuckyMoneyModel luckyMoneyModelData = selectLuckyMoneyByOpayId(id);
        if (luckyMoneyModelData == null) {
            throw new LuckyMoneyException(SystemCode.LUCKY_MONEY_DOES_NOT_EXIST.getCode(), SystemCode.LUCKY_MONEY_DOES_NOT_EXIST.getMessage());
        }
        BeanUtils.copyProperties(luckyMoneyModelData, luckyMoneyInfoResponse);
        if (receivedOpayId.equals(senderOpayId)) {
            List<LuckyMoneyRecordModel> luckyMoneyRecordModels = luckyMoneyRecordMapper.selectLuckyMoneyRecord(id);
            luckyMoneyInfoResponse.setStatus(LuckyMoneyStatus.NOT_GRABBED.getCode());
            for (LuckyMoneyRecordModel luckyMoneyRecordModel : luckyMoneyRecordModels) {
                if (luckyMoneyRecordModel.getGetTime() != null) {
                    if (luckyMoneyInfoResponse.getGrabAmount() == null) {
                        luckyMoneyInfoResponse.setGrabAmount(new BigDecimal(0));
                    }
                    luckyMoneyInfoResponse.setGrabAmount(luckyMoneyInfoResponse.getGrabAmount().add(luckyMoneyRecordModel.getAmount()));
                    luckyMoneyInfoResponse.setStatus(LuckyMoneyStatus.GRABBED.getCode());
                }
            }
        } else {
            LuckyMoneyRecordModel luckyMoneyRecordModel = luckyMoneyRecordMapper.selectLuckyMoneyRecordByOpayId(id, receivedOpayId);
            if (luckyMoneyRecordModel == null) {
                luckyMoneyInfoResponse.setStatus(LuckyMoneyStatus.NOT_GRABBED.getCode());
            } else {
                luckyMoneyInfoResponse.setGrabAmount(luckyMoneyRecordModel.getAmount());
                luckyMoneyInfoResponse.setStatus(LuckyMoneyStatus.GRABBED.getCode());
            }
        }
        return luckyMoneyInfoResponse;
    }

    @Override
    public LuckyMoneyRecordResponse selectLuckyMoneyView(Long id) throws Exception {
        LuckyMoneyRecordResponse luckyMoneyRecordResponse = new LuckyMoneyRecordResponse();
        List<LuckyMoneyRecordModel> luckyMoneyRecords = luckyMoneyRecordMapper.selectLuckyMoneyRecord(id);
        if (luckyMoneyRecords.isEmpty()) {
            return luckyMoneyRecordResponse;
        }
        List<LuckyMoneyRecordListResponse> luckyMoneyRecordListResponses = new ArrayList<>();
        LuckyMoneyRecordListResponse luckyMoneyRecordListResponse;
        int quantity = luckyMoneyRecords.size();
        int grabQuantity = 0;
        BigDecimal grabAmount = new BigDecimal(0);
        BigDecimal amount = new BigDecimal(0);
        Date startTime = luckyMoneyRecords.get(0).getCreateTime();
        Date endTime = null;
        Long LuckMoneyId = luckyMoneyRecords.get(0).getLuckMoneyId();
        for (LuckyMoneyRecordModel luckyMoneyRecordModel : luckyMoneyRecords) {
            if (luckyMoneyRecordModel.getGetTime() != null) {
                grabQuantity++;
                endTime = luckyMoneyRecordModel.getGetTime();
                grabAmount = grabAmount.add(luckyMoneyRecordModel.getAmount());
                luckyMoneyRecordListResponse = new LuckyMoneyRecordListResponse();
                BeanUtils.copyProperties(luckyMoneyRecordModel, luckyMoneyRecordListResponse);
                luckyMoneyRecordListResponses.add(luckyMoneyRecordListResponse);
            }
            amount = amount.add(luckyMoneyRecordModel.getAmount());
        }
        luckyMoneyRecordResponse.setLuckMoneyId(LuckMoneyId);
        luckyMoneyRecordResponse.setGrabQuantity(grabQuantity);
        luckyMoneyRecordResponse.setQuantity(quantity);
        luckyMoneyRecordResponse.setAmount(amount);
        luckyMoneyRecordResponse.setGrabAmount(grabAmount);
        if (endTime != null) {
            luckyMoneyRecordResponse.setSeconds((endTime.getTime() - startTime.getTime()) / 1000);
        }
        luckyMoneyRecordResponse.setLuckyMoneyRecordListResponse(luckyMoneyRecordListResponses);
        return luckyMoneyRecordResponse;
    }


    private static BigDecimal getRandomMoney(RedPackage _redPackage) {
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
        return money;
    }

    static class RedPackage {
        int remainSize;
        BigDecimal remainMoney;
    }
}
