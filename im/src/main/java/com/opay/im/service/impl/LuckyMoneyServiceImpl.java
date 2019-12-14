package com.opay.im.service.impl;

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
import com.opay.im.model.response.LuckyMoneyInfoResponse;
import com.opay.im.model.response.LuckyMoneyRecordInfoResponse;
import com.opay.im.model.response.LuckyMoneyResponse;
import com.opay.im.model.response.opaycallback.OPayCallBackResponse;
import com.opay.im.model.response.opaycallback.PayloadResponse;
import com.opay.im.service.ChatGroupMemberService;
import com.opay.im.service.IncrKeyService;
import com.opay.im.service.LuckyMoneyService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import java.util.List;
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
        BeanUtils.copyProperties(luckyMoneyModel, luckyMoneyResponse);
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
                if (member.getOpayId().equals(grabLuckyMoneyRequest.getOpayId())) {
                    isInGroup = true;
                }
            }
            if (!isInGroup) {
                throw new ImException("You do not belong to this group");
            }
        }
        List<String> keys = Arrays.asList(String.valueOf(grabLuckyMoneyRequest.getId()), String.valueOf(grabLuckyMoneyRequest.getTargetType()), grabLuckyMoneyRequest.getOpayId(), grabLuckyMoneyRequest.getTargetId());
        GrabLuckyMoneyResult grabLuckyMoneyResult = (GrabLuckyMoneyResult) redisTemplate.execute(grabLuckyMoney, keys, grabLuckyMoneyRequest.getOpayId());
        if (grabLuckyMoneyResult.getCode() == 0) {
            LuckyMoneyRecordModel luckyMoneyRecordModel = new LuckyMoneyRecordModel();
            luckyMoneyRecordModel.setId(grabLuckyMoneyResult.getId());
            luckyMoneyRecordModel.setOpayPhone(grabLuckyMoneyRequest.getOpayPhone());
            luckyMoneyRecordModel.setOpayName(grabLuckyMoneyRequest.getOpayName());
            luckyMoneyRecordModel.setOpayId(grabLuckyMoneyRequest.getOpayId());
            luckyMoneyRecordModel.setGetTime(new Date());
            luckyMoneyRecordMapper.updateByPrimaryKeySelective(luckyMoneyRecordModel);
            GrabLuckyMoneyResponse grabLuckyMoneyResponse = new GrabLuckyMoneyResponse();
            grabLuckyMoneyResponse.setAmount(grabLuckyMoneyResult.getAmount());
            grabLuckyMoneyResponse.setId(grabLuckyMoneyResult.getId());
            return grabLuckyMoneyResponse;
        } else if (grabLuckyMoneyResult.getCode() == 1) {
            LuckyMoneyRecordModel luckyMoneyRecordModel = luckyMoneyRecordMapper.selectLuckyMoneyRecordByOpayId(grabLuckyMoneyRequest.getId(), grabLuckyMoneyRequest.getOpayId());
            if (luckyMoneyRecordModel == null) {
                throw new LuckMoneyExpiredException(grabLuckyMoneyResult.getMessage());
            } else {
                GrabLuckyMoneyResponse grabLuckyMoneyResponse = new GrabLuckyMoneyResponse();
                grabLuckyMoneyResponse.setAmount(luckyMoneyRecordModel.getAmount());
                grabLuckyMoneyResponse.setId(luckyMoneyRecordModel.getLuckMoneyId());
                return grabLuckyMoneyResponse;
            }
        } else if (grabLuckyMoneyResult.getCode() == 2) {
            throw new LuckMoneyGoneException(grabLuckyMoneyResult.getMessage());
        } else {
            GrabLuckyMoneyResponse grabLuckyMoneyResponse = new GrabLuckyMoneyResponse();
            grabLuckyMoneyResponse.setAmount(grabLuckyMoneyResult.getAmount());
            grabLuckyMoneyResponse.setId(grabLuckyMoneyResult.getId());
            return grabLuckyMoneyResponse;
        }
    }

    @Override
    public LuckyMoneyInfoResponse selectLuckyMoneyEveryPerson(String opayId, Long id) throws Exception {
        LuckyMoneyInfoResponse luckyMoneyInfoResponse = new LuckyMoneyInfoResponse();
        List<LuckyMoneyRecordInfoResponse> luckyMoneyRecordInfoResponses = new ArrayList<>();
        LuckyMoneyModel luckyMoneyModel = luckyMoneyMapper.selectByPrimaryKey(id);
        BeanUtils.copyProperties(luckyMoneyModel, luckyMoneyInfoResponse);
        List<LuckyMoneyRecordModel> LuckyMoneyRecords = luckyMoneyRecordMapper.selectLuckyMoneyRecord(id);
        LuckyMoneyRecordInfoResponse luckyMoneyRecordInfoResponse;
        for (LuckyMoneyRecordModel luckyMoneyRecordModel : LuckyMoneyRecords) {
            luckyMoneyRecordInfoResponse = new LuckyMoneyRecordInfoResponse();
            BeanUtils.copyProperties(luckyMoneyRecordModel, luckyMoneyRecordInfoResponse);
            luckyMoneyRecordInfoResponses.add(luckyMoneyRecordInfoResponse);
            if (opayId.equals(luckyMoneyRecordModel.getOpayId())) {
                luckyMoneyInfoResponse.setGrabAmount(luckyMoneyRecordModel.getAmount());
            }
        }
        luckyMoneyInfoResponse.setLuckyMoneyRecordInfoResponses(luckyMoneyRecordInfoResponses);
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
        return money;
    }

    static class RedPackage {
        int remainSize;
        BigDecimal remainMoney;
    }
}
