package com.opay.invite.stateconfig;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "activity.reward")
public class RewardConfig {

    private BigDecimal registerReward;//徒弟奖励注册后得到奖励

    private BigDecimal masterReward;//师傅奖励，徒弟注册后师傅得到奖励

    private BigDecimal rechargeReward;//徒弟首次充值到，徒弟得到奖励

    private int agentOpen;//控制代理下徒弟的任务显示

    private List<Reward> rewardList;//钱包充值

    private List<AgentRoyaltyReward> royList;//代理提成

    private String startTime;//活动开始时间

    private String endTime;//活动结束时间

    private int effectiveDay;//新注册用户有效期天数

    private String turntableStart;//转盘活动开始时间

    private String turntableEnd;//转盘活动结束时间


}
