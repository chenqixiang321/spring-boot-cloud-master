package com.opay.invite.backstage.service.impl;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.opay.invite.backstage.config.OrderType;
import com.opay.invite.backstage.config.TransferConfig;
import com.opay.invite.backstage.constant.DateTimeConstant;
import com.opay.invite.backstage.dao.entity.*;
import com.opay.invite.backstage.dao.mapper.*;
import com.opay.invite.backstage.exception.BackstageException;
import com.opay.invite.backstage.exception.BackstageExceptionEnum;
import com.opay.invite.backstage.service.OpayFeignApiService;
import com.opay.invite.backstage.service.RpcService;
import com.opay.invite.backstage.service.WithdrawService;
import com.opay.invite.backstage.service.dto.*;
import com.opay.invite.backstage.utils.AESUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

/**
 * 提现记录相关操作方法
 *
 * @author liuzhihang
 * @date 2019/12/18 15:28
 */
@Slf4j
@Service(value = "withdrawService")
public class WithdrawServiceImpl implements WithdrawService {

    @Resource
    private OpayActiveTixianMapper opayActiveTixianMapper;
    @Resource
    private OpayInviteCodeMapper opayInviteCodeMapper;
    @Resource
    private OpayActiveCashbackMapper opayActiveCashbackMapper;
    @Resource
    private OpayInviteRelationMapper opayInviteRelationMapper;
    @Resource
    private OpayMasterPupilAwardMapper opayMasterPupilAwardMapper;
    @Autowired
    private RpcService rpcService;
    @Autowired
    private TransferConfig transferConfig;
    @Resource
    private OpayActiveTixianLogMapper opayActiveTixianLogMapper;
    @Autowired
    private OpayFeignApiService opayFeignApiService;
    @Resource
    private InviteOperatorMapper inviteOperatorMapper;

    @Value("${config.opay.aesKey}")
    private String aesKey;
    @Value("${config.opay.iv}")
    private String iv;
    @Value("${config.opay.merchantId}")
    private String merchantId;

    @Override
    public WithdrawRecordRespDto withdrawRecord(WithdrawRecordReqDto reqDto) throws Exception {

        OpayActiveTixianExample example = new OpayActiveTixianExample();
        OpayActiveTixianExample.Criteria criteria = example.createCriteria();

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
            criteria.andCreateAtGreaterThanOrEqualTo(startTime);
        }
        if (StringUtils.isNotBlank(reqDto.getEndTime())) {
            LocalDateTime endTime = LocalDateTime.parse(reqDto.getEndTime(), DateTimeConstant.FORMAT_TIME);
            criteria.andCreateAtLessThanOrEqualTo(endTime);
        }

        if (reqDto.getStatus() != null) {
            if (reqDto.getStatus() == (byte) 0 || reqDto.getStatus() == (byte) 1
                    || reqDto.getStatus() == (byte) 2 || reqDto.getStatus() == (byte) 3 ||
                    reqDto.getStatus() == (byte) 4) {
                criteria.andStatusEqualTo(reqDto.getStatus());
            }
        }
        if (StringUtils.isNotBlank(reqDto.getOpayId())) {
            criteria.andOpayIdEqualTo(reqDto.getOpayId());
        } else if (StringUtils.isNotBlank(reqDto.getOpayPhone())) {
            OpayInviteCodeExample codeExample = new OpayInviteCodeExample();
            codeExample.createCriteria().andPhoneLike("%" + reqDto.getOpayPhone() + "%");
            List<OpayInviteCode> list = opayInviteCodeMapper.selectByExample(codeExample);
            if (CollectionUtils.isNotEmpty(list)) {
                criteria.andOpayIdEqualTo(list.get(0).getOpayId());
            }
        }

        Page<OpayActiveTixian> withdrawPage = PageHelper.startPage(reqDto.getPageNum(), reqDto.getPageSize())
                .doSelectPage(() -> {
                    PageHelper.orderBy("create_at DESC");
                    opayActiveTixianMapper.selectByExample(example);
                });


        List<OpayActiveTixian> withdrawList = withdrawPage.getResult();

        List<WithdrawRecordDto> recordDtoList = new ArrayList<>();

        for (OpayActiveTixian opayActiveTixian : withdrawList) {

            WithdrawRecordDto recordDto = new WithdrawRecordDto();
            BeanUtils.copyProperties(opayActiveTixian, recordDto);
            recordDto.setCreateTime(opayActiveTixian.getCreateAt().format(DateTimeConstant.FORMAT_TIME));
            String operateTime = opayActiveTixian.getOperateTime() == null ? null : opayActiveTixian.getOperateTime().format(DateTimeConstant.FORMAT_TIME);
            recordDto.setOperator(opayActiveTixian.getOperator());
            recordDto.setOperateTime(operateTime);

            if (StringUtils.isNotBlank(opayActiveTixian.getOperator())) {
                InviteOperatorExample example1 = new InviteOperatorExample();
                example1.createCriteria().andOperatorIdEqualTo(opayActiveTixian.getOperator());
                List<InviteOperator> operatorList = inviteOperatorMapper.selectByExample(example1);

                if (CollectionUtils.isNotEmpty(operatorList)) {
                    recordDto.setOperatorName(operatorList.get(0).getOperatorName());
                }
            }

            recordDto.setAmount(opayActiveTixian.getAmount().toString());

            // 去查询账户信息
            BatchQueryUserRequest batchQueryUserRequest = new BatchQueryUserRequest();
            batchQueryUserRequest.setUserId(opayActiveTixian.getOpayId());
            log.info("请求账户查询用户信息, 请求:{}", JSON.toJSONString(batchQueryUserRequest));
            OpayApiResultResponse<String> opayApiResultResponse = opayFeignApiService.batchQueryUserByPhone(getOpayApiRequest(batchQueryUserRequest));
            log.info("请求账户查询用户信息, 返回:{}", JSON.toJSONString(opayApiResultResponse));
            String json = opayApiResultResponseHandler(opayApiResultResponse);
            ObjectMapper mapper = new ObjectMapper();
            OpayApiQueryUserByUserIdResponse queryUserByPhoneResponse = mapper.readValue(json, OpayApiQueryUserByUserIdResponse.class);
            List<OpayUserModel> users = queryUserByPhoneResponse.getUsers();
            OpayUserModel userModel = users.get(0);
            BeanUtils.copyProperties(userModel, recordDto);


            OpayInviteRelationExample relationExample = new OpayInviteRelationExample();
            relationExample.createCriteria().andMasterIdEqualTo(opayActiveTixian.getOpayId());


            List<OpayInviteRelation> relationList = opayInviteRelationMapper.selectByExample(relationExample);

            if (CollectionUtils.isNotEmpty(relationList)) {
                OpayInviteRelation relation = relationList.get(0);
                recordDto.setRegisterTime(relation.getCreateAt().format(DateTimeConstant.FORMAT_TIME));
                recordDto.setMarkType(relation.getMarkType());
            }


            recordDtoList.add(recordDto);
        }

        WithdrawRecordRespDto respDto = new WithdrawRecordRespDto();
        respDto.setWithdrawRecordDtoList(recordDtoList);
        respDto.setPageNum(withdrawPage.getPageNum());
        respDto.setPageSize(withdrawPage.getPageSize());
        respDto.setTotal(withdrawPage.getTotal());
        respDto.setPages(withdrawPage.getPages());

        return respDto;
    }

    private String opayApiResultResponseHandler(OpayApiResultResponse<String> opayApiResultResponse) throws Exception {
        if (!"00000".equals(opayApiResultResponse.getCode())) {
            throw new BackstageException(BackstageExceptionEnum.FAIL);
        }
        return AESUtil.decrypt(opayApiResultResponse.getData(), aesKey, iv);
    }

    private OpayApiRequest getOpayApiRequest(Object object) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        OpayApiRequest batchQuery = new OpayApiRequest();
        batchQuery.setMerchantId(merchantId);
        batchQuery.setRequestId(UUID.randomUUID().toString());
        batchQuery.setData(AESUtil.encrypt(mapper.writeValueAsString(object), aesKey, iv));
        return batchQuery;
    }


    @Override
    public UserDetailRespDto userDetail(UserDetailReqDto reqDto) throws Exception {

        OpayActiveTixianExample tixianExample = new OpayActiveTixianExample();
        tixianExample.createCriteria().andIdEqualTo(reqDto.getId());

        List<OpayActiveTixian> tixianList = opayActiveTixianMapper.selectByExample(tixianExample);

        if (CollectionUtils.isEmpty(tixianList)) {
            throw new BackstageException(BackstageExceptionEnum.WITHDRAW_RECORD_NOT_EXIST);
        }
        OpayActiveTixian opayActiveTixian = tixianList.get(0);

        OpayActiveCashbackExample cashbackExample = new OpayActiveCashbackExample();
        cashbackExample.createCriteria().andOpayIdEqualTo(reqDto.getOpayId());
        List<OpayActiveCashback> cashbackList = opayActiveCashbackMapper.selectByExample(cashbackExample);
        if (CollectionUtils.isEmpty(cashbackList)) {
            throw new BackstageException(BackstageExceptionEnum.WITHDRAW_RECORD_NOT_EXIST);
        }
        OpayActiveCashback opayActiveCashback = cashbackList.get(0);


        OpayInviteRelationExample relationExample = new OpayInviteRelationExample();
        relationExample.createCriteria().andMasterIdEqualTo(reqDto.getOpayId());

        int inviteNo = opayInviteRelationMapper.countByExample(relationExample);


        // 0:bonus 1:balance
        // status 3 成功
        BigDecimal bonusSum = opayActiveTixianMapper.sumAmountByTypeAndStatus((byte) 0, (byte) 3, reqDto.getOpayId());
        BigDecimal balanceSum = opayActiveTixianMapper.sumAmountByTypeAndStatus((byte) 1, (byte) 3, reqDto.getOpayId());

        UserDetailRespDto respDto = new UserDetailRespDto();
        respDto.setReference(opayActiveTixian.getReference());
        respDto.setOpayId(reqDto.getOpayId());
        respDto.setInviteNo(inviteNo);
        respDto.setTotalCashback(opayActiveCashback.getTotalAmount().toString());
        respDto.setRemainCashback(opayActiveCashback.getAmount().toString());
        respDto.setToBonus(bonusSum != null ? bonusSum.toString() : null);
        respDto.setToBalance(balanceSum != null ? balanceSum.toString() : null);
        respDto.setRegisterTime(opayActiveCashback.getCreateAt().format(DateTimeConstant.FORMAT_TIME));
        respDto.setWithdrawAmount(opayActiveTixian.getAmount().toString());
        respDto.setWithdrawAmountType(opayActiveTixian.getType());
        respDto.setStatus(opayActiveTixian.getStatus());
        respDto.setMemo(opayActiveTixian.getMemo());


        OpayMasterPupilAwardExample awardExample = new OpayMasterPupilAwardExample();
        awardExample.createCriteria().andOpayIdEqualTo(reqDto.getOpayId());


        Page<OpayMasterPupilAward> awardPage = PageHelper.startPage(reqDto.getPageNum(), reqDto.getPageSize())
                .doSelectPage(() -> {
                    PageHelper.orderBy("create_at DESC");
                    opayMasterPupilAwardMapper.selectByExample(awardExample);
                });


        List<OpayMasterPupilAward> awardList = awardPage.getResult();

        List<UserRewardDto> userRewardDtoList = new ArrayList<>();

        for (OpayMasterPupilAward opayMasterPupilAward : awardList) {
            UserRewardDto userRewardDto = new UserRewardDto();
            BeanUtils.copyProperties(opayMasterPupilAward, userRewardDto);
            userRewardDto.setAmount(opayMasterPupilAward.getAmount().toString());
            userRewardDto.setCreateTime(opayMasterPupilAward.getCreateAt().format(DateTimeConstant.FORMAT_TIME));
            // 查询徒弟名字和手机号
            userRewardDto.setReward(opayMasterPupilAward.getReward());


            BatchQueryUserRequest batchQueryUserRequest = new BatchQueryUserRequest();
            batchQueryUserRequest.setUserId(opayMasterPupilAward.getPupilId());
            log.info("请求账户查询用户信息, 请求:{}", JSON.toJSONString(batchQueryUserRequest));
            OpayApiResultResponse<String> opayApiResultResponse = opayFeignApiService.batchQueryUserByPhone(getOpayApiRequest(batchQueryUserRequest));
            log.info("请求账户查询用户信息, 返回:{}", JSON.toJSONString(opayApiResultResponse));
            String json = opayApiResultResponseHandler(opayApiResultResponse);
            ObjectMapper mapper = new ObjectMapper();
            OpayApiQueryUserByUserIdResponse queryUserByPhoneResponse = mapper.readValue(json, OpayApiQueryUserByUserIdResponse.class);
            List<OpayUserModel> users = queryUserByPhoneResponse.getUsers();
            OpayUserModel userModel = users.get(0);
            userRewardDto.setPupilFirstName(userModel.getFirstName());
            userRewardDto.setPupilMiddleName(userModel.getMiddleName());
            userRewardDto.setPupilSurname(userModel.getSurname());
            userRewardDto.setPupilPhone(userModel.getMobile());
            userRewardDtoList.add(userRewardDto);
        }

        respDto.setUserRewardDtoList(userRewardDtoList);
        respDto.setPageNum(awardPage.getPageNum());
        respDto.setPageSize(awardPage.getPageSize());
        respDto.setTotal(awardPage.getTotal());
        respDto.setPages(awardPage.getPages());


        return respDto;
    }

    @Override
    public void withdrawOperate(WithdrawOperateReqDto reqDto) throws Exception {


        OpayActiveTixian tixian = opayActiveTixianMapper.selectByPrimaryKey(reqDto.getId());
        if (tixian == null) {
            throw new BackstageException(BackstageExceptionEnum.WITHDRAW_RECORD_NOT_EXIST);
        }

        if (tixian.getStatus() != (byte) 0) {
            log.info("提现状态不正确, 只有申请中的可以审核");
            throw new BackstageException(BackstageExceptionEnum.WITHDRAW_OPERATE_STATUS_ERROR);
        }

        if (reqDto.getStatus() == (byte) 1) {
            // 先置为审核成功 再调用转账
            OpayActiveTixian record = new OpayActiveTixian();
            record.setStatus((byte) 1);
            record.setMemo(reqDto.getMemo());
            record.setOperator(reqDto.getOperatorId());
            record.setOperateTime(LocalDateTime.now());

            OpayActiveTixianExample example = new OpayActiveTixianExample();
            example.createCriteria().andIdEqualTo(tixian.getId()).andOpayIdEqualTo(tixian.getOpayId()).andStatusEqualTo((byte) 0);
            int i = opayActiveTixianMapper.updateByExampleSelective(record, example);

            if (i != 1) {
                log.info("id={}将审核状态更改为审核成功失败", tixian.getId());
                throw new BackstageException(BackstageExceptionEnum.WITHDRAW_OPERATE_STATUS_UPDATE_ERROR);
            }
        } else if (reqDto.getStatus() == (byte) 2) {
            // 审核拒绝
            OpayActiveTixian record = new OpayActiveTixian();
            record.setStatus((byte) 2);
            record.setMemo(reqDto.getMemo());
            record.setOperator(reqDto.getOperatorId());
            record.setOperateTime(LocalDateTime.now());

            OpayActiveTixianExample example = new OpayActiveTixianExample();
            example.createCriteria().andIdEqualTo(tixian.getId()).andOpayIdEqualTo(tixian.getOpayId()).andStatusEqualTo((byte) 0);
            int i = opayActiveTixianMapper.updateByExampleSelective(record, example);

            if (i != 1) {
                log.info("id={}将审核状态更改为审核成功失败", tixian.getId());
                throw new BackstageException(BackstageExceptionEnum.WITHDRAW_OPERATE_STATUS_UPDATE_ERROR);
            }
            rollbackTixian(tixian);
            return;
        }


        String orderType = OrderType.bonusOffer.getOrderType();
        if (tixian.getType() == 1) {
            orderType = OrderType.MUAATransfer.getOrderType();
        }
        String reference = transferConfig.getReference() + "" + String.format("%10d", tixian.getId()).replace(" ", "0");
        Map<String, String> map = rpcService.transfer(reference, tixian.getOpayId(), tixian.getAmount().toString(), reference, orderType, "BalancePayment");
        if (map == null || map.size() == 0) {
            log.error("提现错误, 请求参数: {}", JSON.toJSONString(tixian));
            throw new BackstageException(BackstageExceptionEnum.FAIL);
        }
        log.info("transfer::::{}", JSON.toJSONString(map));
        if (map.size() > 0) {
            if ("504".equals(map.get("code"))) {
                log.error("提现超时, 请求参数 timeout err {}", JSON.toJSONString(tixian));
            } else if ("00000".equals(map.get("code")) && ("SUCCESS".equals(map.get("status")) || "PENDING".equals(map.get("status")))) {
                // 转账成功
                OpayActiveTixian activeTixian = new OpayActiveTixian();
                activeTixian.setReference(reference);
                activeTixian.setTradeNo(map.get("orderNo"));
                activeTixian.setStatus((byte) 3);

                OpayActiveTixianExample tixianExample1 = new OpayActiveTixianExample();
                tixianExample1.createCriteria().andIdEqualTo(tixian.getId()).andOpayIdEqualTo(tixian.getOpayId()).andStatusEqualTo((byte) 1);
                opayActiveTixianMapper.updateByExampleSelective(activeTixian, tixianExample1);
            } else {
                // 转账失败
                OpayActiveTixian activeTixian = new OpayActiveTixian();
                activeTixian.setReference(reference);
                activeTixian.setTradeNo(map.get("orderNo"));
                activeTixian.setStatus((byte) 4);

                OpayActiveTixianExample tixianExample1 = new OpayActiveTixianExample();
                tixianExample1.createCriteria().andIdEqualTo(tixian.getId()).andOpayIdEqualTo(tixian.getOpayId()).andStatusEqualTo((byte) 1);
                opayActiveTixianMapper.updateByExampleSelective(activeTixian, tixianExample1);
                rollbackTixian(tixian);
            }
        }

    }

    @Override
    public SumWithdrawInfoRespDto sumWithdrawInfo(SumWithdrawInfoReqDto reqDto) {

        LocalDateTime startTime;
        LocalDateTime endTime;

        if (StringUtils.isNotBlank(reqDto.getStartTime()) && StringUtils.isNotBlank(reqDto.getEndTime())) {
            startTime = LocalDateTime.parse(reqDto.getStartTime(), DateTimeConstant.FORMAT_TIME);
            endTime = LocalDateTime.parse(reqDto.getEndTime(), DateTimeConstant.FORMAT_TIME);
        } else {
            // 凌晨和今天结束
            startTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
            endTime = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        }
        // bonus 申请中
        OpayActiveTixianExample tixianExample1 = new OpayActiveTixianExample();
        tixianExample1.createCriteria().andTypeEqualTo((byte) 0).andStatusEqualTo((byte) 0).andCreateAtBetween(startTime, endTime);
        List<OpayActiveTixian> bonusList = opayActiveTixianMapper.selectByExample(tixianExample1);
        // balance 申请中
        OpayActiveTixianExample tixianExample2 = new OpayActiveTixianExample();
        tixianExample2.createCriteria().andTypeEqualTo((byte) 1).andStatusEqualTo((byte) 0).andCreateAtBetween(startTime, endTime);
        List<OpayActiveTixian> balanceList = opayActiveTixianMapper.selectByExample(tixianExample2);
        // 审批拒绝的
        OpayActiveTixianExample tixianExample3 = new OpayActiveTixianExample();
        tixianExample3.createCriteria().andStatusEqualTo((byte) 2).andCreateAtBetween(startTime, endTime);
        List<OpayActiveTixian> rejectList = opayActiveTixianMapper.selectByExample(tixianExample3);


        int toBonusRecordSum = 0;
        BigDecimal toBonusSum = new BigDecimal(0);
        if (CollectionUtils.isNotEmpty(bonusList)) {
            toBonusRecordSum = bonusList.size();
            toBonusSum = bonusList.stream().map(OpayActiveTixian::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        }
        int toBalanceRecordSum = 0;
        BigDecimal toBalanceSum = new BigDecimal(0);
        if (CollectionUtils.isNotEmpty(balanceList)) {
            toBalanceRecordSum = balanceList.size();
            toBalanceSum = balanceList.stream().map(OpayActiveTixian::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        }
        int rejectRecordSum = 0;
        BigDecimal rejectSum = new BigDecimal(0);
        if (CollectionUtils.isNotEmpty(rejectList)) {
            rejectRecordSum = rejectList.size();
            rejectSum = rejectList.stream().map(OpayActiveTixian::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        }

        SumWithdrawInfoRespDto respDto = new SumWithdrawInfoRespDto();
        respDto.setToBonusRecordSum(toBonusRecordSum);
        respDto.setToBonusSum(toBonusSum.toString());
        respDto.setToBalanceRecordSum(toBalanceRecordSum);
        respDto.setToBalanceSum(toBalanceSum.toString());
        respDto.setTotalRecordSum(toBonusRecordSum + toBalanceRecordSum);
        respDto.setTotalSum(toBonusSum.add(toBalanceSum).toString());
        respDto.setRejectRecordSum(rejectRecordSum);
        respDto.setRejectSum(rejectSum.toString());

        return respDto;
    }

    public void rollbackTixian(OpayActiveTixian saveTixian) {
        try {

            OpayActiveCashbackExample cashbackExample = new OpayActiveCashbackExample();
            cashbackExample.createCriteria().andOpayIdEqualTo(saveTixian.getOpayId());
            List<OpayActiveCashback> cashbackList = opayActiveCashbackMapper.selectByExample(cashbackExample);
            if (CollectionUtils.isEmpty(cashbackList)) {
                throw new BackstageException(BackstageExceptionEnum.WITHDRAW_RECORD_NOT_EXIST);
            }
            OpayActiveCashback cashback2 = cashbackList.get(0);

            // 金额加回

            Integer version = cashback2.getVersion();
            cashback2.setAmount(saveTixian.getAmount().add(cashback2.getAmount()));
            cashback2.setUpdateAt(LocalDateTime.now());
            cashback2.setVersion(version + 1);
            OpayActiveCashbackExample updateExample = new OpayActiveCashbackExample();
            updateExample.createCriteria().andOpayIdEqualTo(saveTixian.getOpayId()).andVersionEqualTo(version);

            opayActiveCashbackMapper.updateByExampleSelective(cashback2, updateExample);
            log.info("rollbackTixian {},status:{},cashback2:{}", JSON.toJSONString(saveTixian), 2, JSON.toJSONString(cashback2));

            OpayActiveTixianLog saveTixianLog = new OpayActiveTixianLog();
            saveTixianLog.setTixianId(saveTixian.getId());
            saveTixianLog.setAmount(saveTixian.getAmount());
            saveTixianLog.setOpayId(saveTixian.getOpayId());
            saveTixianLog.setType(saveTixian.getType());
            saveTixianLog.setCreateAt(new Date());
            saveTixianLog.setDeviceId(saveTixian.getDeviceId());
            saveTixianLog.setMonth(saveTixian.getMonth());
            saveTixianLog.setDay(saveTixian.getDay());
            saveTixianLog.setMark((byte) 1);//异常,退回提现金额日志
            opayActiveTixianLogMapper.insertSelective(saveTixianLog);
            log.info("rollbackTixian {},status:{},cashback2:{}", JSON.toJSONString(saveTixian), 4, JSON.toJSONString(cashback2));
        } catch (Exception e) {
            log.warn("transter err {},status:{},err:{}", JSON.toJSONString(saveTixian), 4, e.getMessage());
        }
    }

}
