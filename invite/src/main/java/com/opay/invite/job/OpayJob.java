package com.opay.invite.job;

import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.util.StringUtils;

import java.util.Date;

public abstract class OpayJob extends QuartzJobBean {
    static final String DATE_KEY = "date_key";
    public static final String DEFAULT_GROUP = "opay-invite";

    /**
     * 获取查询日期
     *
     * @param jobExecutionContext
     * @return
     */
    final DataQuery getDataQuery(JobExecutionContext jobExecutionContext) {
        String dateStr = jobExecutionContext.getMergedJobDataMap().getString(DATE_KEY);
        if (!StringUtils.isEmpty(dateStr)) {
            return new DataQuery(dateStr);
        }

        return new DataQuery(new Date());
    }

    /**
     * 格式化时间
     *
     * @param starTime
     * @return
     */
    final String formatTime(long starTime) {
        long seconds = (System.currentTimeMillis() - starTime) / 1000;
        if (seconds < 60) {
            return String.valueOf(seconds);
        }

        if (seconds < 3600) {
            return (seconds / 60) + " min " + (seconds % 60);
        }

        return (seconds / 3600) + " hours " + ((seconds % 3600) / 60) + " min " + (seconds % 3600 % 60);
    }
}
