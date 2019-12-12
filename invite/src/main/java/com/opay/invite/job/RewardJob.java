package com.opay.invite.job;


import com.opay.invite.model.OpayActiveCashback;
import com.opay.invite.model.OpayMasterPupilAward;
import com.opay.invite.model.OpayUserOrder;
import com.opay.invite.service.InviteOperateService;
import com.opay.invite.service.RewardJobService;
import com.opay.invite.stateconfig.RewardConfig;
import com.opay.invite.utils.DateFormatter;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 *
 */
@Slf4j
public class RewardJob extends OpayJob {
    private static final int PAGE_SIZE = 1000;

    @Autowired
    private RewardJobService rewardJobService;

    @Autowired
    private InviteOperateService inviteOperateService;

    @Autowired
    private RewardConfig rewardConfig;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {
        DataQuery dataQuery = getDataQuery(jobExecutionContext);
        String preDay = dataQuery.getDay();
        String startTime=dataQuery.getFormatDay()+" 00:00:00";
        Date nowTime = DateFormatter.parseDate(startTime);
        int isStart = inviteOperateService.checkActiveStatusTime(nowTime,rewardConfig.getStartTime(),rewardConfig.getEndTime());
        if(isStart!=1){
            log.warn("活动已结束 startTime:{}",startTime);
            return;
        }
        //获取所有需要执行用户数据
        int start=0;
        while (true) {
            List<OpayUserOrder> list = rewardJobService.getOpayUserOrderList(Integer.valueOf(preDay),start, PAGE_SIZE);
            if(list.isEmpty()){
                log.warn("RewardJob data empty,task finish");
                break;
            }
            //循环每一个用户，获取用户数据
            List<OpayMasterPupilAward> mplist =rewardJobService.calMasterPupilAward(list);
            //解析用户数据，计算金额奖励,//插入奖励
            List<OpayActiveCashback> nlist = rewardJobService.getCashbackList(mplist);
            rewardJobService.saveAwardAndCashAndOrderStatus(nlist,mplist,list);

            if (list.size() < PAGE_SIZE) {
                log.warn("RewardJob data empty,task finish");
                break;
            }
            start += PAGE_SIZE;
        }
    }


}
