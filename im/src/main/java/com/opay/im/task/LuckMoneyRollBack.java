package com.opay.im.task;


import com.alibaba.fastjson.JSON;
import com.opay.im.mapper.LuckyMoneyMapper;
import com.opay.im.mapper.LuckyMoneyRecordMapper;
import com.opay.im.model.LuckyMoneyModel;
import com.opay.im.model.LuckyMoneyRecordModel;
import com.opay.im.model.request.OpayRefundLuckMoneyRequest;
import com.opay.im.model.response.OpayApiResultResponse;
import com.opay.im.service.IncrKeyService;
import com.opay.im.service.OpayApiService;
import com.opay.im.utils.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 红包退回接口 定时
 *
 * @author liuzhihang
 * @date 2019/12/19 14:42
 */
@Slf4j
@Component
public class LuckMoneyRollBack {

    @Autowired
    private IncrKeyService incrKeyService;

    @Resource
    private LuckyMoneyRecordMapper luckyMoneyRecordMapper;
    @Autowired
    private OpayApiService opayApiService;
    @Resource
    private LuckyMoneyMapper luckyMoneyMapper;

    @Value("${config.opay.aesKey}")
    private String aesKey;
    @Value("${config.opay.iv}")
    private String iv;
    @Value("${config.opay.merchantId}")
    private String merchantId;

    /**
     * 可以执行这个机器的定时任务
     */
    @Value("${task.executeIp}")
    private String executeIp;

    @Value("${task.minusHour.start}")
    private int rollBackStartTime;

    @Value("${task.minusHour.end}")
    private int rollBackEndTime;

    private static String realIp;


    static {
        realIp = IpUtils.getLocalIp();
        log.info("当前系统的 address 为: {}", realIp);
    }


    private Date minusHour(Date date, int minusHour) {

        Calendar cal = Calendar.getInstance();

        cal.setTime(date);

        cal.add(Calendar.HOUR_OF_DAY, -minusHour);

        return cal.getTime();

    }


    /**
     * 红包退回三分钟一次定时任务
     */
    @Scheduled(cron = "0 0/3 * * * ?")
    public void LuckMoneyRollBackTask() {

        if (!executeIp.equals(realIp)) {
            return;
        }
        Date now = new Date();
        Date startDate = minusHour(now, rollBackStartTime);
        Date endDate = minusHour(now, rollBackEndTime);

        log.info("红包退回三分钟一次定时任务, 本次执行机器IP:{}, start:{}, end:{}", executeIp, startDate, endDate);

        List<LuckyMoneyRecordModel> recordList = luckyMoneyRecordMapper.selectListByCreateTime(startDate, endDate);

        if (CollectionUtils.isEmpty(recordList)) {
            return;
        }

        // 一条一条交易进行处理回退金额
        for (LuckyMoneyRecordModel model : recordList) {


            LuckyMoneyModel moneyModel = luckyMoneyMapper.selectByPrimaryKey(model.getLuckMoneyId());


            log.info("本次需要退回红包:{}, version:{}", model.getLuckMoneyId(), model.getVersion());

            try {

                String requestId = incrKeyService.getIncrKey();

                String merchartOrderNo = "REFUND:" + model.getLuckMoneyId() + ":" + requestId;

                OpayRefundLuckMoneyRequest request = new OpayRefundLuckMoneyRequest();
                request.setMerchartOrderNo(merchartOrderNo);
                request.setSendOrderNo(moneyModel.getTransactionId());
                request.setSenderId(model.getOpayId());
                OpayApiResultResponse<Map> response = opayApiService.refundRedPacket(merchantId, requestId, request, aesKey, iv);

                log.info("定时任务退红包返回信息:{}", JSON.toJSONString(response));

                if ("00000".equals(response.getCode())) {
                    Map<String, String> resultMap = response.getData();
                    if ("PENDING".equals(resultMap.get("orderStatus"))) {
                        // 退回成功 修改状态 0-初始化(待抢) 1-成功 2-退回成功 3-退回失败 4-处理中
                        // ++ 新增携带原始状态 必须由 0->2  0->3 0->4
                        // 防止一单多次跑批, 首次成功二次失败 被更新为失败的情况
                        // 退回失败 等待下次定时跑批
                        luckyMoneyRecordMapper.updateStatusAndRefundIdByLuckMoneyIdId((byte) 4, merchartOrderNo, model.getLuckMoneyId(), (byte) 0, model.getVersion());
                    } else if ("FAIL".equals(resultMap.get("orderStatus"))) {
                        luckyMoneyRecordMapper.updateStatusAndRefundIdByLuckMoneyIdId((byte) 3, merchartOrderNo, model.getLuckMoneyId(), (byte) 0, model.getVersion());
                    } else if ("SUCCESS".equals(resultMap.get("orderStatus"))) {
                        // 退回成功 修改状态 0-初始化(待抢) 1-成功 2-退回成功 3-退回失败
                        luckyMoneyRecordMapper.updateStatusAndRefundIdByLuckMoneyIdId((byte) 2, merchartOrderNo, model.getLuckMoneyId(), (byte) 0, model.getVersion());
                    }
                } else if ("04188".equals(response.getCode())) {
                    // 上次已经退回成功了
                    // 退回成功 修改状态 0-初始化(待抢) 1-成功 2-退回成功 3-退回失败
                    luckyMoneyRecordMapper.updateStatusAndRefundIdByLuckMoneyIdId((byte) 2, merchartOrderNo, model.getLuckMoneyId(), (byte) 0, model.getVersion());

                } else if ("04189".equals(response.getCode())) {
                    // 已经被领取成功了
                    // 领取成功 修改状态 0-初始化(待抢) 1-成功 2-退回成功 3-退回失败
                    luckyMoneyRecordMapper.updateStatusAndRefundIdByLuckMoneyIdId((byte) 1, merchartOrderNo, model.getLuckMoneyId(), (byte) 0, model.getVersion());

                }


            } catch (Exception e) {
                log.error("luck_money_id :{} 退回失败, 等待下次处理", model.getLuckMoneyId(), e);
            }

        }


    }


}
