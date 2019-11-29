package com.opay.invite.job;


import com.opay.invite.service.RewardJobService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 */
@Slf4j
public class RewardJob extends OpayJob {
    private static final int PAGE_SIZE = 20000;

    @Autowired
    private RewardJobService rewardJobService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {

//        DataQuery dataQuery = getDataQuery(jobExecutionContext);
//        String preDay = dataQuery.getPreDay();
//        //获取所有需要执行用户数据
//        int start=0;
//        while (true) {
//            List<OpayUserOrder> list = rewardJobService.getOpayUserOrderList(Integer.valueOf(preDay),start, PAGE_SIZE);
//            if(list.isEmpty()){
//                log.warn("RewardJob data empty");
//                break;
//            }
//            //循环每一个用户，获取用户数据
//            List<OpayMasterPupilAward> mplist =rewardJobService.calMasterPupilAward(list);
//            //解析用户数据，计算金额奖励,//插入奖励
//            List<OpayActiveCashback> nlist = rewardJobService.getCashbackList(mplist);
//            rewardJobService.saveAwardAndCashAndOrderStatus(nlist,mplist,list);
//
//        }
    }


}
