package com.opay.invite.job;


import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;

/**
 *
 */
@Slf4j
public class RewardJob extends OpayJob {
    private static final int PAGE_SIZE = 20000;


    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {
        //System.out.println("===========>");
        //获取所有需要执行用户数据


        //循环每一个用户，获取用户数据

        //解析用户数据，计算金额奖励

        //插入奖励

    }


}
