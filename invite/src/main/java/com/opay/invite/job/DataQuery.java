package com.opay.invite.job;


import com.opay.invite.utils.DateFormatter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 数据查询对象
 *
 * @author liuqs
 * @date 2019/8/29 10:16
 */
public class DataQuery {
    /**
     * 查询起始时间
     */
    private Date start;
    /**
     * 查询结束时间
     */
    private Date end;

    /**
     * 构造方法
     *
     * @param now 当前执行时间
     */
    public DataQuery(Date now) {
        Calendar instance = Calendar.getInstance();

        instance.setTime(now);

        instance.set(Calendar.MILLISECOND, 0);
        instance.set(Calendar.SECOND, 0);
        instance.set(Calendar.MINUTE, 0);
        instance.set(Calendar.HOUR_OF_DAY, 0);

        end = instance.getTime();

        instance.set(Calendar.DAY_OF_MONTH, instance.get(Calendar.DAY_OF_MONTH) - 1);
        start = instance.getTime();
    }

    /**
     * 构造方法
     *
     * @param targetDay 目标天
     */
    public DataQuery(String targetDay) {
        Calendar instance = Calendar.getInstance();

        instance.setTime(DateFormatter.parseDate(targetDay));

        instance.set(Calendar.MILLISECOND, 0);
        instance.set(Calendar.SECOND, 0);
        instance.set(Calendar.MINUTE, 0);
        instance.set(Calendar.HOUR_OF_DAY, 0);

        start = instance.getTime();

        instance.set(Calendar.DAY_OF_MONTH, instance.get(Calendar.DAY_OF_MONTH) + 1);
        end = instance.getTime();
    }

    public String getDay() {
        return DateFormatter.formatShortYMDDate(start);
    }

    public String getPreDay() {
        Calendar instance = Calendar.getInstance();

        instance.setTime(start);
        instance.set(Calendar.DAY_OF_MONTH, instance.get(Calendar.DAY_OF_MONTH) - 1);

        return DateFormatter.formatShortYMDDate(instance.getTime());
    }

    public String getPostFmtDaytime() {
        Calendar instance = Calendar.getInstance();

        instance.setTime(start);
        instance.set(Calendar.DAY_OF_MONTH, instance.get(Calendar.DAY_OF_MONTH) + 1);

        return DateFormatter.formatDatetime(instance.getTime());
    }

    public String getFormatDaytime() {
        return DateFormatter.formatDatetime(start);
    }

    public String getFormatDay() {
        return DateFormatter.formatDate(start);
    }

    public String getMonth() {
        return new SimpleDateFormat("yyyyMM").format(start);
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    public String getStartStr() {
        return DateFormatter.formatDatetime(start);
    }

    public String getEndStr() {
        return DateFormatter.formatDatetime(end);
    }
}
