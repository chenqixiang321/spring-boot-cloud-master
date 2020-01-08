package com.opay.invite.job;


import com.opay.invite.model.OpayActiveCashback;
import com.opay.invite.model.OpayMasterPupilAward;
import com.opay.invite.model.OpayUserOrder;
import com.opay.invite.service.InviteOperateService;
import com.opay.invite.service.RewardJobService;
import com.opay.invite.stateconfig.RewardConfig;
import com.opay.invite.utils.CommonUtil;
import com.opay.invite.utils.DateFormatter;
import com.opay.invite.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 */
@Slf4j
public class RewardJob extends OpayJob {
    private static final int PAGE_SIZE = 500;

    @Autowired
    private RewardJobService rewardJobService;

    @Autowired
    private InviteOperateService inviteOperateService;

    @Autowired
    private RewardConfig rewardConfig;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {
        DataQuery dataQuery = getDataQuery(jobExecutionContext);
        String preDay = dataQuery.getDay();
        String startTime=dataQuery.getFormatDay()+" 00:00:00";
        Date nowTime = DateFormatter.parseDate(startTime);
        int isStart = inviteOperateService.checkActiveStatusTime(nowTime,rewardConfig.getStartTime(),rewardConfig.getEndTime());
        if(isStart!=1){
            log.warn("RewardJob 活动已结束 startTime:{}",startTime);
            return;
        }

        //判断活动开关
        Integer cashBackTotalAmount = redisUtil.get("invite_active_", "cashBackTotalAmount");
        if(cashBackTotalAmount == null || cashBackTotalAmount <= 0 ){
            log.warn("RewardJob 活动已结束 额度已完 cashBackTotalAmount:{}",cashBackTotalAmount);
            return;
        }


        //获取所有需要执行用户数据
        int start=0;
        while (true) {
            List<OpayUserOrder> list = rewardJobService.getOpayUserOrderList(Integer.valueOf(preDay),start, PAGE_SIZE);
            if(list.isEmpty()){
                log.warn("RewardJob data empty,task finish day:{}",preDay);
                break;
            }
            //循环每一个用户，获取用户数据
            List<OpayMasterPupilAward> mplist =rewardJobService.calMasterPupilAward(list);
            //解析用户数据，计算金额奖励,//插入奖励
            List<OpayActiveCashback> nlist = rewardJobService.getCashbackList(mplist);

            // 判断活动金额是否超限
            BigDecimal sumAmount= CommonUtil.calSumAmount(nlist);
            try {
                if (redisUtil.decr("invite_active_", "cashBackTotalAmount", Integer.valueOf(String.valueOf(sumAmount))) < 0 ) {
                    log.warn("活动金额超限，活动结束");
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.warn("判断活动金额是否超限，异常", e);
                return;
            }

            rewardJobService.saveAwardAndCashAndOrderStatus(nlist,mplist,list);



            if (list.size() < PAGE_SIZE) {
                log.warn("RewardJob data pageSize,task finish day:{}",preDay);
                break;
            }
        }
    }


}
