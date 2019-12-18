package com.opay.invite.backstage.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 当需要分页时继承使用
 * 
 * @author liuzhihang
 * @date 2019/12/17 14:38
 */
@Data
@ApiModel(value = "分页传入条件", description = "分页基础条件")
public class BasePageReqDto extends BaseReqDto {

    @ApiModelProperty(value = "当前页码， 默认值 1")
    private Integer pageNum;
    @ApiModelProperty(value = "每页显示条数， 默认20")
    private Integer pageSize;

    @ApiModelProperty(value = "开始时间 yyyy-MM-dd HH:mm:ss")
    private String startTime;

    @ApiModelProperty(value = "结束时间 yyyy-MM-dd HH:mm:ss")
    private String endTime;


}
