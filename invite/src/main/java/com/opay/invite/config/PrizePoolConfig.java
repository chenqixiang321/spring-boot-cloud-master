package com.opay.invite.config;

import com.opay.invite.model.PrizeModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ConfigurationProperties("prize-pool")
@Setter
@Getter
public class PrizePoolConfig {
    private int grandPrizeIndex;
    private int secondPoolRate;
    private int inviteLimit;
    private int shareLimit;
    private String firstGrandPrizeTimeUp;
    private String secondGrandPrizeTimeUp;
    private List<PrizeModel> firstPool = new ArrayList<>();
    private List<PrizeModel> secondPool = new ArrayList<>();
}
