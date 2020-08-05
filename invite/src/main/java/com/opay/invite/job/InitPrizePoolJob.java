package com.opay.invite.job;

import com.opay.invite.service.AliasMethodService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

public class InitPrizePoolJob extends OpayJob {

    @Autowired
    private AliasMethodService aliasMethodService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        aliasMethodService.init();
    }
}
