package com.opay.invite.job;


import com.alibaba.fastjson.JSON;
import com.opay.invite.model.OpayApiUserOrder;
import com.opay.invite.model.OpayInviteRelation;
import com.opay.invite.model.OpayUserOrder;
import com.opay.invite.model.response.OpayApiResultResponse;
import com.opay.invite.model.response.OpayApiUserOrderResponse;
import com.opay.invite.resp.Result;
import com.opay.invite.service.ActiveService;
import com.opay.invite.service.InviteOperateService;
import com.opay.invite.service.OpayApiService;
import com.opay.invite.service.UserTradeService;
import com.opay.invite.stateconfig.RewardConfig;
import com.opay.invite.transferconfig.ServiceType;
import com.opay.invite.utils.DateFormatter;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 */
@Slf4j
public class UserTradeJob extends OpayJob {
    private static final int PAGE_SIZE = 30;

    private static final int SUB_PAGE_SIZE = 100;

    @Autowired
    private UserTradeService userTradeService;

    @Autowired
    private OpayApiService opayApiService;

    @Autowired
    private InviteOperateService inviteOperateService;

    @Autowired
    private RewardConfig rewardConfig;

    @Autowired
    private ActiveService activeService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {
        DataQuery dataQuery = getDataQuery(jobExecutionContext);
        String month = dataQuery.getMonth();
        String day = dataQuery.getDay();
        String startTime=dataQuery.getFormatDay()+" 00:00:00";
        String endTime=dataQuery.getFormatDay()+" 23:59:59";
        Date sTime = DateFormatter.parseDate(startTime);
        int isStart = inviteOperateService.checkActiveStatusTime(sTime,rewardConfig.getStartTime(),rewardConfig.getEndTime());
        if(isStart!=1){
             log.warn("UserTradeJob 活动已结束 startTime:{}",startTime);
            return;
        }

        // 活动号
        String activeId = rewardConfig.getActiveId();
        // 如果金额超限不参与奖励
        int lockedActive = activeService.isLockedActive(activeId);
        if (lockedActive > 0) {
            log.warn("活动奖励金额超限");
            return;
        }

        int start=0;
        while (true) {
            List<OpayInviteRelation> list = userTradeService.getTaskRelationList(start, PAGE_SIZE,Integer.valueOf(day),3);
            if (list.isEmpty()) {
                log.warn("UserTradeJob data empty,task finish day:{}",day);
                break;
            }
            try {
                List<OpayApiUserOrder> userOrderList = new ArrayList<>();
                int subStart = 1;
                while (true) {
                    OpayApiResultResponse resultResponse = userTradeService.getUserTradeList(list, subStart, SUB_PAGE_SIZE,endTime,startTime, ServiceType.Airtime.getServiceType());
                    OpayApiUserOrderResponse opayApiUserOrderResponse = (OpayApiUserOrderResponse) resultResponse.getData();
                    if(opayApiUserOrderResponse.getRecords().isEmpty()){
                        log.warn("UserTradeJob resultResponse data empty,task finish day:{}",day);
                        break;
                    }
                    userOrderList.addAll(opayApiUserOrderResponse.getRecords());
                    if (opayApiUserOrderResponse.getRecords().size() < SUB_PAGE_SIZE) {
                        log.warn("UserTradeJob resultResponse data pageSize,task finish day:{}",day);
                        break;
                    }
                    subStart += 1;
                }
                List<OpayUserOrder> userOrders = new ArrayList<>();
                Date nowTime= new Date();
                for(OpayApiUserOrder order: userOrderList){
                    OpayUserOrder opayUserOrder = new OpayUserOrder();
                    opayUserOrder.setActualAmount(new BigDecimal(order.getActualPayAmount()));
                    opayUserOrder.setAmount(new BigDecimal(order.getOrderAmount()));
                    opayUserOrder.setOpayId(order.getUserId());
                    opayUserOrder.setOrderId(order.getOrderNo());
                    opayUserOrder.setOrderTime(order.getPayTime());
                    opayUserOrder.setCreateAt(nowTime);
                    opayUserOrder.setStatus(0);
                    opayUserOrder.setType(3);
                    opayUserOrder.setMonth(Integer.valueOf(month));
                    opayUserOrder.setDay(Integer.valueOf(day));
                    userOrders.add(opayUserOrder);
                }
                userTradeService.saveUserOrder(userOrders,list,Integer.valueOf(day),3);
            } catch(Exception e){
                log.error("UserTradeJob error :{}",e.getMessage());
            }
            if (list.size() < PAGE_SIZE) {
                log.warn("UserTradeJob data pageSize,task finish day:{}",day);
                break;
            }
        }

    }


}
