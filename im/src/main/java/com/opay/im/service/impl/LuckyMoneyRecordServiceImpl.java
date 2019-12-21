package com.opay.im.service.impl;

import com.opay.im.mapper.LuckyMoneyRecordMapper;
import com.opay.im.model.LuckyMoneyModel;
import com.opay.im.model.LuckyMoneyRecordModel;
import com.opay.im.model.response.opaycallback.OPayCallBackResponse;
import com.opay.im.model.response.opaycallback.PayloadResponse;
import com.opay.im.service.LuckyMoneyRecordService;
import com.opay.im.service.LuckyMoneyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class LuckyMoneyRecordServiceImpl implements LuckyMoneyRecordService {

    @Resource
    private LuckyMoneyRecordMapper luckyMoneyRecordMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private LuckyMoneyService luckyMoneyService;
    @Resource(name = "resetLuckyMoney")
    private DefaultRedisScript<Boolean> resetLuckyMoney;

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

    @Override
    public int updateGetStatus(Long luckyMoneyId, Long luckyMoneyRecordId, OPayCallBackResponse oPayCallBackResponse) throws Exception {
        LuckyMoneyRecordModel luckyMoneyRecordModel = new LuckyMoneyRecordModel();
        luckyMoneyRecordModel.setId(luckyMoneyRecordId);
        luckyMoneyRecordModel.setTransactionId(oPayCallBackResponse.getPayload().getTransactionId());
        PayloadResponse payload = oPayCallBackResponse.getPayload();
        if ("successful".equals(payload.getStatus())) {
            luckyMoneyRecordModel.setGetStatus(1);
        } else if ("failed".equals(payload.getStatus())) {
            LuckyMoneyModel luckyMoneyModelData = luckyMoneyService.selectLuckyMoneyByOpayId(luckyMoneyId);
            LuckyMoneyRecordModel lmr = luckyMoneyRecordMapper.selectByPrimaryKey(luckyMoneyRecordId);
            List<String> keys2 = Arrays.asList(String.valueOf(luckyMoneyModelData.getId()), String.valueOf(luckyMoneyModelData.getTargetType()), luckyMoneyModelData.getOpayId(), luckyMoneyModelData.getTargetId());
            redisTemplate.execute(resetLuckyMoney, keys2, luckyMoneyRecordId, lmr.getOpayId());
            luckyMoneyRecordModel.setGetStatus(2);
        } else {
            luckyMoneyRecordModel.setGetStatus(3);
        }
        return luckyMoneyRecordMapper.updateByPrimaryKeySelective(luckyMoneyRecordModel);
    }

    @Override
    public void updateRecordStatus(Long luckMoneyId, OPayCallBackResponse oPayCallBackResponse) throws Exception {

        List<LuckyMoneyRecordModel> recordModelList = luckyMoneyRecordMapper.selectLuckyMoneyRecord(luckMoneyId);

        if (CollectionUtils.isEmpty(recordModelList)) {
            log.info("luckMoneyId:{}在record表不存在记录", luckMoneyId);
            throw new Exception();
        }
        LuckyMoneyRecordModel model = recordModelList.get(0);

        PayloadResponse payload = oPayCallBackResponse.getPayload();

        if ("successful".equals(payload.getStatus())) {
            luckyMoneyRecordMapper.updateStatusAndRefundIdByLuckMoneyIdId((byte) 2, null, model.getLuckMoneyId(), (byte) 4, model.getVersion());
        } else if ("failed".equals(payload.getStatus())) {
            luckyMoneyRecordMapper.updateStatusAndRefundIdByLuckMoneyIdId((byte) 3, null, model.getLuckMoneyId(), (byte) 4, model.getVersion());
        } else {
            log.info("luckMoneyId:{}回调返回状态不正确, 当前回调状态:{}", luckMoneyId, payload.getStatus());
            throw new Exception();
        }


    }
}
