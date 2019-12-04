package com.opay.invite.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateFormatter {
    private static final ThreadLocal<SimpleDateFormat> DATE_TIME_FMT = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    private static final ThreadLocal<SimpleDateFormat> DATE_FMT = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd"));
    private static final ThreadLocal<SimpleDateFormat> DATE_SHORT_FMT = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyyMMdd"));

    private static final ThreadLocal<SimpleDateFormat> DATE_SHORT = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyyMM"));


    private DateFormatter() {
    }

    public static Date parseDatetime(String dateStr) {
        try {
            return DATE_TIME_FMT.get().parse(dateStr);
        } catch (ParseException e) {
            throw new IllegalStateException(e);
        }
    }

    public static String formatDatetime(Date date) {
        return DATE_TIME_FMT.get().format(date);
    }

    public static String formatDate(Date date) {
        return DATE_FMT.get().format(date);
    }

    public static String formatShortYMDDate(Date date) {
        return DATE_SHORT_FMT.get().format(date);
    }

    public static String formatShortYMDate(Date date) {
        return DATE_SHORT.get().format(date);
    }
    public static String formatShortYMDDateByZone(Date date,String zone) {
        SimpleDateFormat format= DATE_SHORT_FMT.get();
        format.setTimeZone(TimeZone.getTimeZone(zone));
        return format.format(date);
    }

    public static String formatShortYMDateByZone(Date date,String zone) {
        SimpleDateFormat format= DATE_SHORT.get();
        format.setTimeZone(TimeZone.getTimeZone(zone));
        return format.format(date);
    }


    public static Date parseDate(String dateStr) {
        try {
            return DATE_FMT.get().parse(dateStr);
        } catch (ParseException e) {
            throw new IllegalStateException(e);
        }
    }

    public static Date parseYMDHMSDate(String dateStr) {
        try {
            return DATE_TIME_FMT.get().parse(dateStr);
        } catch (ParseException e) {
            throw new IllegalStateException(e);
        }
    }

    public static String formatDatetimeByZone(Date date, String zone) {
        SimpleDateFormat format= DATE_TIME_FMT.get();
        format.setTimeZone(TimeZone.getTimeZone(zone));
        return format.format(date);
    }

    /**
     * 获得日期前几天
     *
     * @param date 日期
     * @param day  天数
     * @return Date
     */
    public static Date getDateBefore(Date date, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
        return now.getTime();
    }

    /**
     * 获得日期后几天
     *
     * @param date 日期
     * @param day  天数
     * @return Date
     */
    public static Date getDateAfter(Date date, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        return now.getTime();
    }

    /***
     *获得日期前几月
     * @param date
     * @param month
     * @return
     */
    public static Date getDateBeforeMonth(Date date ,int month){
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.MONTH,now.get(Calendar.MONTH) - month);
        return now.getTime();
    }
    /***
     *获得日期前几月
     * @param date
     * @param month
     * @return
     */
    public static Date getDateAfterMonth(Date date ,int month){
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.MONTH,now.get(Calendar.MONTH) + month);
        return now.getTime();
    }
}
