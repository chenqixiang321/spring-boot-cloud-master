package com.opay.invite.backstage.service.dto;

import lombok.Data;

/**
 * 带分页的基础返回信息
 * 
 * @author liuzhihang
 * @date 2019/12/17 14:38
 */
@Data
public class BasePageRespDto extends BaseRespDto {

    /**
     * 页码，从1开始
     */
    private int pageNum;
    /**
     * 页面大小
     */
    private int pageSize;

    /**
     * 总数
     */
    private long total;
    /**
     * 总页数
     */
    private int pages;

}
