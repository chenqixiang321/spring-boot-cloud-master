package com.opay.invite.job;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JobManager {
    @Autowired
    private Scheduler scheduler;

    /**
     * 手动触发任务
     *
     * @param group
     * @param name
     * @param executeDate
     * @throws Exception
     */
    public void trigger(String group, String name, String executeDate) throws Exception {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(OpayJob.DATE_KEY, executeDate);
        scheduler.triggerJob(JobKey.jobKey(name, group), jobDataMap);
    }

    /**
     * 删除任务
     *
     * @param group
     * @param name
     * @throws Exception
     */
    public void remove(String group, String name) throws Exception {
        scheduler.unscheduleJob(new TriggerKey(name, group));
    }

    /**
     * @param group
     * @param name
     * @param expression
     */
    public void update(String group, String name, String expression) throws Exception {
        scheduler.rescheduleJob(TriggerKey.triggerKey(name, group),
                TriggerBuilder.newTrigger().withIdentity(name, group).withSchedule(CronScheduleBuilder.cronSchedule(expression)).build());
    }

    /**
     * 查询执行任务状态
     *
     * @return
     */
    public List<JobInfo> running() throws Exception {
        List<JobExecutionContext> jobs = scheduler.getCurrentlyExecutingJobs();
        List<JobInfo> ret = new ArrayList(jobs.size());
        for (JobExecutionContext job : jobs) {
            ret.add(new JobInfo(job));
        }

        return ret;
    }
}
