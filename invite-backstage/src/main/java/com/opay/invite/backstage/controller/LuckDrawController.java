package com.opay.invite.backstage.controller;

import com.alibaba.fastjson.JSON;
import com.opay.invite.backstage.exception.BackstageException;
import com.opay.invite.backstage.exception.BackstageExceptionEnum;
import com.opay.invite.backstage.service.LuckDrawInfoService;
import com.opay.invite.backstage.service.dto.BaseRespDto;
import com.opay.invite.backstage.service.dto.DrawOperateReqDto;
import com.opay.invite.backstage.service.dto.DrawRecordReqDto;
import com.opay.invite.backstage.service.dto.DrawRecordRespDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 运营后台操作人员查询使用
 *
 * @author liuzhihang
 * @date 2019/12/17 18:22
 */
@Slf4j
@Api(value = "中奖纪录操作API")
@RestController
@RequestMapping(value = "/luckDraw")
public class LuckDrawController {

    @Resource
    private LuckDrawInfoService luckDrawInfoService;


    @PostMapping(value = "/drawRecord")
    @ApiOperation(value = "中奖纪录查询", notes = "获取活动页内容")
    public DrawRecordRespDto drawRecord(@RequestBody DrawRecordReqDto drawRecordReqDto) {

        log.info("请求查询中奖记录， 请求参数：{}", JSON.toJSONString(drawRecordReqDto));
        DrawRecordRespDto drawRecordRespDto = new DrawRecordRespDto();

        try {
            checkDrawRecordParam(drawRecordReqDto);

            drawRecordRespDto = luckDrawInfoService.drawRecord(drawRecordReqDto);

            drawRecordRespDto.buildSuccess();
        } catch (BackstageException e) {
            log.error("请求查询中奖记录， 失败", e);
            drawRecordRespDto.buildError(e);
        } catch (Exception e) {
            log.error("请求查询中奖记录， 失败", e);
            drawRecordRespDto.buildFail();
        }

        return drawRecordRespDto;

    }

    @PostMapping(value = "/drawOperate")
    @ApiOperation(value = "审核记录", notes = "审核中奖纪录")
    public BaseRespDto drawOperate(@RequestBody DrawOperateReqDto drawOperateReqDto) {
        BaseRespDto baseRespDto = new BaseRespDto();
        try {
            if (drawOperateReqDto == null) {
                throw new BackstageException(BackstageExceptionEnum.PARAMETER_EMPTY);
            }
            if (StringUtils.isBlank(drawOperateReqDto.getOperatorId())) {
                throw new BackstageException(BackstageExceptionEnum.OPERATOR_ID_ERROR);
            }
            if (drawOperateReqDto.getId() == null) {
                throw new BackstageException(BackstageExceptionEnum.DRAW_RECORD_ID_EMPTY);
            }
            luckDrawInfoService.drawOperate(drawOperateReqDto);

            baseRespDto.buildSuccess();
        } catch (BackstageException e) {
            log.error("请求查询中奖记录， 失败", e);
            baseRespDto.buildError(e);
        } catch (Exception e) {
            log.error("请求查询中奖记录， 失败", e);
            baseRespDto.buildFail();
        }
        return baseRespDto;
    }


    /**
     * 手工校验
     * <p>
     * 以后可以考虑 hibernate-validator
     *
     * @param reqDto
     * @throws BackstageException
     */
    private void checkDrawRecordParam(DrawRecordReqDto reqDto) throws BackstageException {

        if (reqDto == null) {
            throw new BackstageException(BackstageExceptionEnum.PARAMETER_EMPTY);
        }
        if (reqDto.getPageNum() == null) {
            reqDto.setPageNum(1);
        }
        if (reqDto.getPageSize() == null) {
            reqDto.setPageSize(20);
        }
        if (StringUtils.isBlank(reqDto.getOperatorId())) {
            throw new BackstageException(BackstageExceptionEnum.OPERATOR_ID_ERROR);
        }

    }


}
