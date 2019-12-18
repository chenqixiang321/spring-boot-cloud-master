package com.opay.invite.backstage.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.opay.invite.backstage.constant.DateTimeConstant;
import com.opay.invite.backstage.dao.entity.LuckDrawInfo;
import com.opay.invite.backstage.dao.entity.LuckDrawInfoExample;
import com.opay.invite.backstage.dao.mapper.LuckDrawInfoMapper;
import com.opay.invite.backstage.service.LuckDrawInfoService;
import com.opay.invite.backstage.service.dto.DrawRecordDto;
import com.opay.invite.backstage.service.dto.DrawRecordReqDto;
import com.opay.invite.backstage.service.dto.DrawRecordRespDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 查询抽奖记录相关实现功能
 *
 * @author liuzhihang
 * @date 2019/12/17 19:05
 */
@Service(value = "luckDrawInfoService")
public class LuckDrawInfoServiceImpl implements LuckDrawInfoService {

    @Resource
    private LuckDrawInfoMapper luckDrawInfoMapper;

    @Override
    public DrawRecordRespDto drawRecord(DrawRecordReqDto reqDto) {

        LuckDrawInfoExample example = new LuckDrawInfoExample();
        LuckDrawInfoExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(reqDto.getPrize())) {
            criteria.andPrizeEqualTo(reqDto.getPrize());
        }
        if (StringUtils.isNotBlank(reqDto.getOpayPhone())) {
            criteria.andOpayPhoneEqualTo(reqDto.getOpayPhone());
        }
        if (StringUtils.isNotBlank(reqDto.getOperateStartTime()) && StringUtils.isNotBlank(reqDto.getOperateEndTime())) {
            LocalDateTime startTime = LocalDateTime.parse(reqDto.getOperateStartTime(), DateTimeConstant.FORMAT_TIME);
            LocalDateTime endTime = LocalDateTime.parse(reqDto.getOperateEndTime(), DateTimeConstant.FORMAT_TIME);
            criteria.andOperateTimeBetween(startTime, endTime);
        }
        if (StringUtils.isNotBlank(reqDto.getStartTime()) && StringUtils.isNotBlank(reqDto.getEndTime())) {
            LocalDateTime startTime = LocalDateTime.parse(reqDto.getStartTime(), DateTimeConstant.FORMAT_TIME);
            LocalDateTime endTime = LocalDateTime.parse(reqDto.getEndTime(), DateTimeConstant.FORMAT_TIME);
            criteria.andCreateTimeBetween(startTime, endTime);
        }
        Byte redeemStatus = reqDto.getRedeemStatus();
        if (redeemStatus != null && (redeemStatus == 0 || redeemStatus == 1)) {
            criteria.andRedeemStatusEqualTo(redeemStatus);
        }

        Page<LuckDrawInfo> luckDrawInfoPage = PageHelper.startPage(reqDto.getPageNum(), reqDto.getPageSize())
                .doSelectPage(() -> {
                    PageHelper.orderBy("create_time DESC");
                    luckDrawInfoMapper.selectByExample(example);
                });


        List<LuckDrawInfo> infoList = luckDrawInfoPage.getResult();

        List<DrawRecordDto> dtoList = infoList.stream().map(luckDrawInfo -> new DrawRecordDto().convertFrom(luckDrawInfo)).collect(Collectors.toList());

        DrawRecordRespDto respDto = new DrawRecordRespDto();
        respDto.setDrawRecordDtoList(dtoList);
        respDto.setPageNum(luckDrawInfoPage.getPageNum());
        respDto.setPageSize(luckDrawInfoPage.getPageSize());
        respDto.setTotal(luckDrawInfoPage.getTotal());
        respDto.setPages(luckDrawInfoPage.getPages());

        return respDto;
    }
}
