package com.opay.invite.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

@Configuration
public class LuaConfiguration {
    @Bean
    public DefaultRedisScript<Boolean> inviteShareCount() {
        DefaultRedisScript<Boolean> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua_script/invite_share_count.lua")));
        redisScript.setResultType(Boolean.class);
        return redisScript;
    }
}
