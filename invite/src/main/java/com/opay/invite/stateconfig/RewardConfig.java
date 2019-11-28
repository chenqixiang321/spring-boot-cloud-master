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

    private int open;//开启、关闭 0:默认全局,1:关闭，使用区间

    private List<Reward> rewardList;//钱包充值

    private List<AgentRoyaltyReward> royList;//代理提成

}