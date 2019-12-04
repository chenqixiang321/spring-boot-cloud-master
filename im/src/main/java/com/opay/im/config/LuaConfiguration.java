package com.opay.im.config;

import com.opay.im.model.response.GrabLuckyMoneyResponse;
import com.opay.im.model.response.GrabLuckyMoneyResult;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

@Configuration
public class LuaConfiguration {
    @Bean(name = "sendLuckyMoney")
    public DefaultRedisScript<Boolean> sendLuckyMoney() {
        DefaultRedisScript<Boolean> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua_script/send_lucky_money.lua")));
        redisScript.setResultType(Boolean.class);
        return redisScript;
    }

    @Bean(name = "grabLuckyMoney")
    public DefaultRedisScript<GrabLuckyMoneyResult> grabLuckyMoney() {
        DefaultRedisScript<GrabLuckyMoneyResult> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua_script/grab_lucky_money.lua")));
        redisScript.setResultType(GrabLuckyMoneyResult.class);
        return redisScript;
    }

    @Bean(name = "incrKey")
    public DefaultRedisScript<Long> incrKey() {
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua_script/increment_key.lua")));
        redisScript.setResultType(Long.class);
        return redisScript;
    }
}
