package com.opay.invite.config;

import com.opay.invite.model.response.PrizePoolResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

@Configuration
public class LuaConfiguration {
    @Bean(name = "inviteShareCountInc")
    public DefaultRedisScript<Boolean> inviteShareCountInc() {
        DefaultRedisScript<Boolean> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua_script/invite_share_count_inc.lua")));
        redisScript.setResultType(Boolean.class);
        return redisScript;
    }

    @Bean(name = "inviteShareCountDec")
    public DefaultRedisScript<PrizePoolResponse> inviteShareCountDec() {
        DefaultRedisScript<PrizePoolResponse> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua_script/invite_share_count_dec.lua")));
        redisScript.setResultType(PrizePoolResponse.class);
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
