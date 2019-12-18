package com.opay.invite.backstage.constant;

import java.time.format.DateTimeFormatter;

/**
 * 时间相关常量
 *
 *
 * @author liuzhihang
 * @date 2019/12/17 19:07
 */
public class DateTimeConstant {

    private DateTimeConstant() {
    }

    /**
     * 时间格式 yyyy-MM-dd HH:mm:ss 常量
     */
    public static final DateTimeFormatter FORMAT_TIME = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

}
