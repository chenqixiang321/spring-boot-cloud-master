package com.opay.invite.jobconfig;

import com.opay.invite.job.OpayJob;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 初始化Job配置
 */
@EnableConfigurationProperties(JobProperties.class)
@Configuration
public class JobConfiguration {

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private JobProperties jobProperties;

    @PostConstruct
    public void init() throws Exception {
        Set<JobKey> jobKeys = scheduler.getJobKeys(GroupMatcher.groupEquals(OpayJob.DEFAULT_GROUP));

        Iterator<Map.Entry<String, JobProperties.JobItem>> iterator = jobProperties.getJobs().entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, JobProperties.JobItem> next = iterator.next();

            String group = OpayJob.DEFAULT_GROUP;
            String name = next.getKey();
            JobProperties.JobItem jobItem = next.getValue();

            if (!scheduler.checkExists(JobKey.jobKey(name, group))) {
                scheduler.scheduleJob(JobBuilder.newJob(getJobClass(jobItem.getJobClass())).withIdentity(name, group).build(),
                        TriggerBuilder.newTrigger()
                                .withIdentity(name, group)
                                .withSchedule(CronScheduleBuilder.cronSchedule(jobItem.getExpression()))
                                .build());
            } else {
                scheduler.rescheduleJob(TriggerKey.triggerKey(name, group),
                        TriggerBuilder.newTrigger().withIdentity(name, group).
                                withSchedule(CronScheduleBuilder.cronSchedule(jobItem.getExpression())).build());
            }

            jobKeys.remove(JobKey.jobKey(name, group));
        }

        for (JobKey item : jobKeys) {
            scheduler.unscheduleJob(TriggerKey.triggerKey(item.getName(), item.getGroup()));
        }
    }

    /**
     * 实例化Job类
     *
     * @param jobClass
     * @return
     * @throws Exception
     */
    private Class<? extends Job> getJobClass(String jobClass) throws Exception {
        return (Class<? extends Job>) (Class.forName(jobClass).newInstance()
                .getClass());
    }
}
