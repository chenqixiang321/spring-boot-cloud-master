package com.opay.invite.backstage.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.opay.invite.backstage.constant.DateTimeConstant;
import com.opay.invite.backstage.dao.entity.InviteOperator;
import com.opay.invite.backstage.dao.entity.InviteOperatorExample;
import com.opay.invite.backstage.dao.entity.LuckDrawInfo;
import com.opay.invite.backstage.dao.entity.LuckDrawInfoExample;
import com.opay.invite.backstage.dao.mapper.InviteOperatorMapper;
import com.opay.invite.backstage.dao.mapper.LuckDrawInfoMapper;
import com.opay.invite.backstage.exception.BackstageException;
import com.opay.invite.backstage.exception.BackstageExceptionEnum;
import com.opay.invite.backstage.service.LuckDrawInfoService;
import com.opay.invite.backstage.service.dto.DrawOperateReqDto;
import com.opay.invite.backstage.service.dto.DrawRecordDto;
import com.opay.invite.backstage.service.dto.DrawRecordReqDto;
import com.opay.invite.backstage.service.dto.DrawRecordRespDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
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
@Slf4j
@Service(value = "luckDrawInfoService")
public class LuckDrawInfoServiceImpl implements LuckDrawInfoService {

    @Resource
    private LuckDrawInfoMapper luckDrawInfoMapper;
    @Resource
    private InviteOperatorMapper inviteOperatorMapper;

    @Override
    public DrawRecordRespDto drawRecord(DrawRecordReqDto reqDto) {

        LuckDrawInfoExample example = new LuckDrawInfoExample();
        LuckDrawInfoExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(reqDto.getPrize())) {
            criteria.andPrizeEqualTo(reqDto.getPrize());
        }
        if (StringUtils.isNotBlank(reqDto.getOpayPhone())) {
            criteria.andOpayPhoneLike("%" + reqDto.getOpayPhone() + "%");
        }

        if (StringUtils.isNotBlank(reqDto.getOperateStartTime())) {
            LocalDateTime startTime = LocalDateTime.parse(reqDto.getOperateStartTime(), DateTimeConstant.FORMAT_TIME);
            criteria.andOperateTimeGreaterThanOrEqualTo(startTime);
        }
        if (StringUtils.isNotBlank(reqDto.getOperateEndTime())) {
            LocalDateTime endTime = LocalDateTime.parse(reqDto.getOperateEndTime(), DateTimeConstant.FORMAT_TIME);
            criteria.andOperateTimeLessThanOrEqualTo(endTime);
        }

        if (StringUtils.isNotBlank(reqDto.getStartTime())) {
            LocalDateTime startTime = LocalDateTime.parse(reqDto.getStartTime(), DateTimeConstant.FORMAT_TIME);
            criteria.andCreateTimeGreaterThanOrEqualTo(startTime);
        }
        if (StringUtils.isNotBlank(reqDto.getEndTime())) {
            LocalDateTime endTime = LocalDateTime.parse(reqDto.getEndTime(), DateTimeConstant.FORMAT_TIME);
            criteria.andCreateTimeLessThanOrEqualTo(endTime);
        }

        Byte redeemStatus = reqDto.getRedeemStatus();
        if (redeemStatus != null && (redeemStatus == 0 || redeemStatus == 1)) {
            criteria.andRedeemStatusEqualTo(redeemStatus);
        }

        if (StringUtils.isBlank(reqDto.getOpayId())) {
            criteria.andOpayIdLike("%" + reqDto.getOpayId() + "%");
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

    @Override
    public void drawOperate(DrawOperateReqDto reqDto) throws BackstageException {

        InviteOperatorExample operatorExample = new InviteOperatorExample();
        operatorExample.createCriteria().andOperatorIdEqualTo(reqDto.getOperatorId());

        List<InviteOperator> operatorList = inviteOperatorMapper.selectByExample(operatorExample);
        if (CollectionUtils.isEmpty(operatorList)) {
            throw new BackstageException(BackstageExceptionEnum.OPERATOR_NOT_EXIST);
        }

        LuckDrawInfoExample example = new LuckDrawInfoExample();
        example.createCriteria().andIdEqualTo(reqDto.getId()).andRedeemStatusEqualTo((byte) 0);

        LuckDrawInfo record = new LuckDrawInfo();
        record.setRedeemStatus(reqDto.getRedeemStatus());
        record.setOperateTime(LocalDateTime.now());
        record.setOperatorId(reqDto.getOperatorId());
        record.setOpayName(operatorList.get(0).getOperatorName());
        record.setMemo(reqDto.getMemo());

        int i = luckDrawInfoMapper.updateByExampleSelective(record, example);

        if (i != 1) {
            log.error("操作员审核失败，原因：数据库update失败，请求参数:{}，id:{}, oldRedeemStatus: 0",
                    JSON.toJSONString(record), reqDto.getId());
            throw new BackstageException(BackstageExceptionEnum.FAIL);
        }


    }
}
