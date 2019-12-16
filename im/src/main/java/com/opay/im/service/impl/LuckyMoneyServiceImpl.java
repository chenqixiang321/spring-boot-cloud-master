package com.opay.im.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opay.im.common.SystemCode;
import com.opay.im.exception.ImException;
import com.opay.im.exception.LuckMoneyExpiredException;
import com.opay.im.exception.LuckMoneyGoneException;
import com.opay.im.exception.LuckMoneyLimitException;
import com.opay.im.mapper.LuckyMoneyMapper;
import com.opay.im.mapper.LuckyMoneyRecordMapper;
import com.opay.im.model.ChatGroupMemberModel;
import com.opay.im.model.LuckyMoneyModel;
import com.opay.im.model.LuckyMoneyRecordModel;
import com.opay.im.model.request.GrabLuckyMoneyRequest;
import com.opay.im.model.request.LuckyMoneyRequest;
import com.opay.im.model.response.GrabLuckyMoneyResponse;
import com.opay.im.model.response.GrabLuckyMoneyResult;
import com.opay.im.model.response.LuckyMoneyDetailResponse;
import com.opay.im.model.response.LuckyMoneyInfoResponse;
import com.opay.im.model.response.LuckyMoneyRecordResponse;
import com.opay.im.model.response.LuckyMoneyResponse;
import com.opay.im.model.response.opaycallback.OPayCallBackResponse;
import com.opay.im.model.response.opaycallback.PayloadResponse;
import com.opay.im.service.ChatGroupMemberService;
import com.opay.im.service.IncrKeyService;
import com.opay.im.service.LuckyMoneyService;
import com.opay.im.service.RongCloudService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
            luckyMoneyRecordMapper.insertSelective(luckyMoneyRecordModel);
            amounts.add(String.valueOf(luckyMoneyRecordModel.getAmount()));
            amountIds.add(String.valueOf(luckyMoneyRecordModel.getId()));
        }
        List<String> keys = Arrays.asList(String.valueOf(luckyMoneyModel.getId()), String.valueOf(luckyMoneyModel.getTargetType()), luckyMoneyModel.getOpayId(), luckyMoneyModel.getTargetId());
        Boolean sendLuckyMoneyResult = (Boolean) redisTemplate.execute(sendLuckyMoney, keys, luckyMoneyRequest.getAmount(), String.join(",", amountIds), String.join(",", amounts), dayMax, expirationDays * 24 * 3600, getEndTime());
        if (!sendLuckyMoneyResult) {
            throw new LuckMoneyLimitException("Today's red envelope amount has reached the limit");
        }
        LuckyMoneyResponse luckyMoneyResponse = new LuckyMoneyResponse();
        luckyMoneyResponse.setId(luckyMoneyModel.getId());
        luckyMoneyResponse.setReference(reference);
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
                throw new ImException("You do not belong to this group");
            }
        }
        List<String> keys = Arrays.asList(String.valueOf(grabLuckyMoneyRequest.getId()), String.valueOf(grabLuckyMoneyRequest.getTargetType()), grabLuckyMoneyRequest.getOpayId(), grabLuckyMoneyRequest.getTargetId());
        GrabLuckyMoneyResult grabLuckyMoneyResult = (GrabLuckyMoneyResult) redisTemplate.execute(grabLuckyMoney, keys, grabLuckyMoneyRequest.getOpayId());
        LuckyMoneyModel luckyMoneyModelData = selectLuckyMoneyByOpayId(grabLuckyMoneyRequest.getId(), grabLuckyMoneyRequest.getOpayId());
        if (luckyMoneyModelData == null) {
            throw new LuckMoneyExpiredException(grabLuckyMoneyResult.getMessage());
        }
        GrabLuckyMoneyResponse grabLuckyMoneyResponse = new GrabLuckyMoneyResponse();
        grabLuckyMoneyResponse.setAmount(luckyMoneyModelData.getAmount());
        grabLuckyMoneyResponse.setId(grabLuckyMoneyResult.getId());
        grabLuckyMoneyResponse.setOpayName(luckyMoneyModelData.getOpayName());
        grabLuckyMoneyResponse.setOpayId(luckyMoneyModelData.getOpayId());
        grabLuckyMoneyResponse.setGrabAmount(grabLuckyMoneyResult.getAmount());
        grabLuckyMoneyResponse.setShow(luckyMoneyModelData.getShow());
        grabLuckyMoneyResponse.setTheme(luckyMoneyModelData.getTheme());
        if (grabLuckyMoneyResult.getCode() == 0) {
            LuckyMoneyRecordModel luckyMoneyRecordModel = new LuckyMoneyRecordModel();
            luckyMoneyRecordModel.setId(grabLuckyMoneyResult.getId());
            luckyMoneyRecordModel.setOpayPhone(grabLuckyMoneyRequest.getCurrentPhone());
            luckyMoneyRecordModel.setOpayName(grabLuckyMoneyRequest.getCurrentOpayName());
            luckyMoneyRecordModel.setOpayId(grabLuckyMoneyRequest.getCurrentOpayId());
            luckyMoneyRecordModel.setGetTime(new Date());
            luckyMoneyRecordMapper.updateByPrimaryKeySelective(luckyMoneyRecordModel);
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> map = new HashMap<>();
            map.put("sendUserId", luckyMoneyModelData.getOpayId());
            map.put("sendNickName", luckyMoneyModelData.getOpayName());
            map.put("openUserId", grabLuckyMoneyRequest.getCurrentOpayId());
            map.put("openNickName", grabLuckyMoneyRequest.getCurrentOpayName());
            map.put("envelopId", grabLuckyMoneyRequest.getId());
            map.put("targetId", grabLuckyMoneyRequest.getTargetId());
            map.put("status", "0");
            rongCloudService.sendMessage(grabLuckyMoneyRequest.getTargetId(), grabLuckyMoneyRequest.getOpayId(), mapper.writeValueAsString(map), "");
            rongCloudService.sendMessage(grabLuckyMoneyRequest.getOpayId(), grabLuckyMoneyRequest.getTargetId(), mapper.writeValueAsString(map), "");
            return grabLuckyMoneyResponse;
        } else if (grabLuckyMoneyResult.getCode() == 2) {
            throw new LuckMoneyGoneException(grabLuckyMoneyResult.getMessage());
        } else {
            return grabLuckyMoneyResponse;
        }
    }

    @Override
    public LuckyMoneyDetailResponse selectLuckyMoneyEveryPerson(String opayId, Long id) throws Exception {
        LuckyMoneyDetailResponse luckyMoneyInfoResponse = new LuckyMoneyDetailResponse();
        List<LuckyMoneyRecordResponse> luckyMoneyRecordResponses = new ArrayList<>();
        LuckyMoneyModel luckyMoneyModel = selectLuckyMoneyByOpayId(id, opayId);
        if (luckyMoneyModel == null) {
            throw new LuckMoneyExpiredException(SystemCode.LUCKY_MONEY_EXPIRED_ERROR.getMessage());
        }
        BeanUtils.copyProperties(luckyMoneyModel, luckyMoneyInfoResponse);
        List<LuckyMoneyRecordModel> LuckyMoneyRecords = luckyMoneyRecordMapper.selectLuckyMoneyRecord(id);
        LuckyMoneyRecordResponse luckyMoneyRecordResponse;
        for (LuckyMoneyRecordModel luckyMoneyRecordModel : LuckyMoneyRecords) {
            luckyMoneyRecordResponse = new LuckyMoneyRecordResponse();
            BeanUtils.copyProperties(luckyMoneyRecordModel, luckyMoneyRecordResponse);
            luckyMoneyRecordResponses.add(luckyMoneyRecordResponse);
            if (opayId.equals(luckyMoneyRecordModel.getOpayId())) {
                luckyMoneyInfoResponse.setGrabAmount(luckyMoneyRecordModel.getAmount());
            }
        }
        luckyMoneyInfoResponse.setOpayName(luckyMoneyModel.getOpayName());
        luckyMoneyInfoResponse.setLuckyMoneyRecordResponses(luckyMoneyRecordResponses);
        return luckyMoneyInfoResponse;
    }

    @Override
    public int updatePayStatus(OPayCallBackResponse oPayCallBackResponse) throws Exception {
        PayloadResponse payload = oPayCallBackResponse.getPayload();
        LuckyMoneyModel luckyMoneyModel = new LuckyMoneyModel();
        if ("successful".equals(payload.getStatus())) {
            luckyMoneyModel.setPayStatus(1);
        } else if ("failed".equals(payload.getStatus())) {
            luckyMoneyModel.setPayStatus(2);
        } else {
            luckyMoneyModel.setPayStatus(3);
        }
        luckyMoneyModel.setReference(payload.getReference());
        luckyMoneyModel.setTransactionId(payload.getTransactionId());
        return luckyMoneyMapper.updateByReferenceKeySelective(luckyMoneyModel);
    }

    @Override
    public int selectPayStatus(String opayId, String reference) throws Exception {
        return luckyMoneyMapper.selectPayStatus(opayId, reference);
    }

    private BigDecimal getRandomMoney(RedPackage _redPackage) {
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

    @Override
    @Cacheable(value = "luckyMoneyInfo", key = "#id", unless = "#result == null")
    public LuckyMoneyModel selectLuckyMoneyByOpayId(Long id, String opayId) throws Exception {
        LuckyMoneyModel luckyMoneyModelData = luckyMoneyMapper.selectLuckyMoneyByOpayId(id, opayId);
        return luckyMoneyModelData;
    }

    @Override
    public LuckyMoneyInfoResponse selectLuckyMoneyDetailByOpayId(Long id, String senderOpayId, String receivedOpayId) throws Exception {
        LuckyMoneyInfoResponse luckyMoneyInfoResponse = new LuckyMoneyInfoResponse();
        Object luckyMoney = redisTemplate.opsForValue().get("luckyMoney:" + senderOpayId);
        if (luckyMoney == null) {
            luckyMoneyInfoResponse.setStatus(2);
            return luckyMoneyInfoResponse;
        }
        LuckyMoneyModel luckyMoneyModelData = luckyMoneyMapper.selectByPrimaryKey(id);
        if (luckyMoneyModelData == null) {
            throw new LuckMoneyExpiredException("The lucky money does not exist");
        }
        BeanUtils.copyProperties(luckyMoneyModelData, luckyMoneyInfoResponse);
        LuckyMoneyRecordModel luckyMoneyRecordModel = luckyMoneyRecordMapper.selectLuckyMoneyRecordByOpayId(id, receivedOpayId);
        if (luckyMoneyRecordModel == null) {
            luckyMoneyInfoResponse.setStatus(0);
        } else {
            luckyMoneyInfoResponse.setGrabAmount(luckyMoneyRecordModel.getAmount());
            luckyMoneyInfoResponse.setStatus(1);
        }

        return luckyMoneyInfoResponse;
    }

    @Override
    public List<LuckyMoneyRecordResponse> selectLuckyMoneyView(Long id) throws Exception {
        List<LuckyMoneyRecordResponse> luckyMoneyRecordResponses = new ArrayList<>();
        List<LuckyMoneyRecordModel> LuckyMoneyRecords = luckyMoneyRecordMapper.selectLuckyMoneyRecord(id);
        LuckyMoneyRecordResponse luckyMoneyRecordResponse;
        for (LuckyMoneyRecordModel luckyMoneyRecordModel : LuckyMoneyRecords) {
            luckyMoneyRecordResponse = new LuckyMoneyRecordResponse();
            BeanUtils.copyProperties(luckyMoneyRecordModel, luckyMoneyRecordResponse);
            luckyMoneyRecordResponses.add(luckyMoneyRecordResponse);
        }
        return luckyMoneyRecordResponses;
    }
}
