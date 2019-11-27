package com.opay.invite.controller;

import com.opay.invite.model.LoginUser;
import com.opay.invite.model.OpayActiveCashback;
import com.opay.invite.model.OpayActiveTixian;
import com.opay.invite.model.OpayActiveTixianLog;
import com.opay.invite.model.request.WithdrawalRequest;
import com.opay.invite.resp.CodeMsg;
import com.opay.invite.resp.Result;
import com.opay.invite.service.InviteOperateService;
import com.opay.invite.service.InviteService;
import com.opay.invite.service.WithdrawalService;
import com.opay.invite.stateconfig.TixianLimitConfig;
import com.opay.invite.utils.DateFormatter;
import com.opay.invite.utils.InviteCode;
import com.opay.invite.utils.IpUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;

@RestController
@RequestMapping(value = "/cash")
@Api(value = "提现API")
public class TixianController {

    @Autowired
    private WithdrawalService withdrawalService;

    @Autowired
    private InviteOperateService inviteOperateService;

    @Autowired
    private InviteService inviteService;

    @Autowired
    private TixianLimitConfig tixianLimitConfig;

    @ApiOperation(value = "提现申请", notes = "提现申请")
    @PostMapping("/withdrawal")
    public Result getInviteCode(HttpServletRequest request, @RequestBody WithdrawalRequest withdrawalRequest) throws Exception {
        //更登录用户生成code
        LoginUser user = inviteOperateService.getOpayInfo(request);
        //判断申请用户是否是代理，如果是代理，每天最大提现额度3000,必须超过8000可提现,拉新人数24人
        OpayActiveCashback cashback = inviteService.getActivityCashbackByOpayId(user.getOpayId());
        Result rt = checkParam(withdrawalRequest,cashback);
        if(rt !=null) return rt;
        int relationCount = inviteService.getRelationCount(user.getOpayId());
        if(relationCount<tixianLimitConfig.getPerson()){
            return Result.error(CodeMsg.ILLEGAL_CODE_TIXIAN_LIMIT);
        }
        Integer day = Integer.valueOf(DateFormatter.formatShortYMDDate(new Date()));
        Integer month = Integer.valueOf(DateFormatter.formatShortYMDate(new Date()));
        OpayActiveTixian tixian = withdrawalService.getTixianAmount(user.getOpayId(),day);
        if(tixian!=null && tixian.getAmount().compareTo(tixianLimitConfig.getPerDayAmount())>=0){
            return Result.error(CodeMsg.ILLEGAL_CODE_TIXIAN_LIMIT);
        }
        if(tixian!=null && withdrawalRequest.getAmount().compareTo(tixianLimitConfig.getPerDayAmount().subtract(tixian.getAmount()))>0){
            return Result.error(CodeMsg.ILLEGAL_CODE_TIXIAN_LIMIT);
        }
        OpayActiveTixian saveTixian = new OpayActiveTixian();
        saveTixian.setAmount(withdrawalRequest.getAmount());
        saveTixian.setOpayId(user.getOpayId());
        saveTixian.setType(withdrawalRequest.getType());
        saveTixian.setCreateAt(new Date());
        saveTixian.setDeviceId("1111");
        saveTixian.setIp(IpUtil.getLocalIp(request));
        saveTixian.setMonth(month);
        saveTixian.setDay(day);
        saveTixian.setStatus(1);//没有审核功能，直接审核通过
        OpayActiveTixianLog saveTixianLog = new OpayActiveTixianLog();
        saveTixianLog.setAmount(withdrawalRequest.getAmount());
        saveTixianLog.setOpayId(user.getOpayId());
        saveTixianLog.setType(withdrawalRequest.getType());
        saveTixianLog.setCreateAt(new Date());
        saveTixianLog.setDeviceId("1111");
        saveTixianLog.setIp(IpUtil.getLocalIp(request));
        saveTixianLog.setMonth(month);
        saveTixianLog.setDay(day);
        inviteOperateService.saveTixianAndLog(saveTixian,saveTixianLog,cashback);
        return Result.success();
    }

    private Result checkParam(WithdrawalRequest withdrawalRequest, OpayActiveCashback cashback) {
        if(cashback==null){
            return Result.error(CodeMsg.ILLEGAL_PARAMETER);
        }
        if(withdrawalRequest.getType()<0 || withdrawalRequest.getType()>2){
            return Result.error(CodeMsg.ILLEGAL_PARAMETER);
        }
        if(withdrawalRequest.getAmount()==null || withdrawalRequest.getAmount().compareTo(BigDecimal.ZERO)<=0){
            return Result.error(CodeMsg.ILLEGAL_PARAMETER);
        }
        if(withdrawalRequest.getType()==2){//用户balance提现
           if(withdrawalRequest.getAmount().compareTo(tixianLimitConfig.getMinAmount())<0){
                return Result.error(CodeMsg.ILLEGAL_CODE_TIXIAN);
           }
        }
        if(cashback.getAmount().compareTo(withdrawalRequest.getAmount())<0){
            return Result.error(CodeMsg.ILLEGAL_CODE_TIXIAN);
        }
        return null;
    }
}
