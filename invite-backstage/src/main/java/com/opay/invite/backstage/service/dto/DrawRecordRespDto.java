package com.opay.invite.backstage.service.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * 抽奖记录查询
 *
 * @author liuzhihang
 * @date 2019/12/17 18:24
 */
@Data
@ApiModel(value = "抽奖记录", description = "查询抽奖记录返回结果")
public class DrawRecordRespDto extends BasePageRespDto {

    private List<DrawRecordDto> drawRecordDtoList;

}
