package com.opay.invite.job;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;

import java.util.Date;

public class JobInfo {
    private JobKey key;
    private JobDataMap dataMap;
    private long runTime;
    private Date fireTime;

    public JobInfo() {
    }

    public JobInfo(JobExecutionContext job) {
        fireTime = job.getFireTime();
        runTime = job.getJobRunTime();
        dataMap = job.getMergedJobDataMap();
        key = job.getJobDetail().getKey();
    }

    public JobKey getKey() {
        return key;
    }

    public void setKey(JobKey key) {
        this.key = key;
    }

    public JobDataMap getDataMap() {
        return dataMap;
    }

    public void setDataMap(JobDataMap dataMap) {
        this.dataMap = dataMap;
    }

    public long getRunTime() {
        return runTime;
    }

    public void setRunTime(long runTime) {
        this.runTime = runTime;
    }

    public Date getFireTime() {
        return fireTime;
    }

    public void setFireTime(Date fireTime) {
        this.fireTime = fireTime;
    }
}
