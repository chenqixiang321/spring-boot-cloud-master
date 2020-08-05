local opay_id = KEYS[1]
local invite_share_count_key = "invite_share_count:" .. opay_id
local invite_count_key = "invite_count:" .. opay_id
local luck_draw_key = "luck_draw_count"
local first_prize_index = ARGV[1]
local second_prize_index = ARGV[2]
local grand_prize_index = ARGV[3]
local grand_prize_time_up = ARGV[4]
local second_pool_rate = ARGV[5]
local first_prize_key = "prize_pool:first:" .. first_prize_index
local second_prize_key = "prize_pool:second:" .. second_prize_index
local share_current_value = redis.call("hget", invite_share_count_key, "share_current")
local login_current_value = redis.call("hget", invite_share_count_key, "login_current")
local invite_current_value = redis.call("get", invite_count_key)
local prize_pool_count = redis.call("get", "prize_pool:count")
local pool = 1
local prize = first_prize_index
if share_current_value == false then
    share_current_value = 0
end
if login_current_value == false then
    redis.call("hset", invite_share_count_key, "login_current", 1)
    login_current_value = 1
end
if invite_current_value == false then
    invite_current_value = 0
end
if prize_pool_count == false then
    prize_pool_count = 0
end
if tonumber(prize_pool_count) <= 0 then
    return "[\"com.opay.invite.model.response.PrizePoolResponse\",{\"message\":\"Prize pool is empty\",\"activityCount\":0,\"loginCount\":" .. login_current_value .. ",\"shareCount\":" .. share_current_value .. ",\"inviteCount\":" .. invite_current_value .. "}]"
else
    if tonumber(share_current_value) <= 0 and tonumber(invite_current_value) <= 0 and tonumber(login_current_value) <= 0 then
        return "[\"com.opay.invite.model.response.PrizePoolResponse\",{\"message\":\"The number of draws has been exhausted\",\"activityCount\":" .. prize_pool_count .. ",\"loginCount\":" .. login_current_value .. ",\"shareCount\":" .. share_current_value .. ",\"inviteCount\":" .. invite_current_value .. "}]"
    else
        local prize_key = first_prize_key
        local luck_draw_count = redis.call("hget", invite_share_count_key, luck_draw_key)
        if luck_draw_count == false then
            redis.call("hset", invite_share_count_key, luck_draw_key, 1)
        else
            if math.fmod(luck_draw_count, tonumber(second_pool_rate)) == 0 then
                prize_key = second_prize_key
                pool = 2
                prize = second_prize_index
            end
            redis.call("hset", invite_share_count_key, luck_draw_key, luck_draw_count + 1)
        end
        if tonumber(login_current_value) > 0 then
            login_current_value = login_current_value - 1
            redis.call("hset", invite_share_count_key, "login_current", login_current_value)
        elseif tonumber(share_current_value) > 0 then
            share_current_value = share_current_value - 1
            redis.call("hset", invite_share_count_key, "share_current", share_current_value)
        else
            invite_current_value = invite_current_value - 1
            redis.call("set", invite_count_key, invite_current_value)
        end
        redis.call("decr", "prize_pool:count")
        if pool == 2 and prize == 0 and tonumber(grand_prize_index) < tonumber(prize_pool_count) and grand_prize_time_up == "false" then
            return "[\"com.opay.invite.model.response.PrizePoolResponse\",{\"message\":\"success\",\"prize\":-1,\"pool\":" .. pool .. ",\"activityCount\":" .. (prize_pool_count - 1) .. ",\"loginCount\":" .. login_current_value .. ",\"shareCount\":" .. share_current_value .. ",\"inviteCount\":" .. invite_current_value .. "}]"
        end
        if (tonumber(grand_prize_index) > tonumber(prize_pool_count) or grand_prize_time_up == "true") and tonumber(redis.call("get", "prize_pool:second:0")) > 0 then
            redis.call("decr", "prize_pool:second:0")
            return "[\"com.opay.invite.model.response.PrizePoolResponse\",{\"message\":\"success\",\"pool\":2,\"prize\":0,\"activityCount\":" .. (prize_pool_count - 1) .. ",\"loginCount\":" .. login_current_value .. ",\"shareCount\":" .. share_current_value .. ",\"inviteCount\":" .. invite_current_value .. "}]"
        end
        local prize_count = redis.call("get", prize_key)
        if prize_count == false then
            return "[\"com.opay.invite.model.response.PrizePoolResponse\",{\"message\":\"success\",\"prize\":-1,\"pool\":" .. pool .. ",\"activityCount\":" .. (prize_pool_count - 1) .. ",\"loginCount\":" .. login_current_value .. ",\"shareCount\":" .. share_current_value .. ",\"inviteCount\":" .. invite_current_value .. "}]"
        else
            if tonumber(prize_count) > 0 then
                redis.call("decr", prize_key)
                return "[\"com.opay.invite.model.response.PrizePoolResponse\",{\"message\":\"success\",\"pool\":" .. pool .. ",\"prize\":" .. prize .. ",\"activityCount\":" .. (prize_pool_count - 1) .. ",\"loginCount\":" .. login_current_value .. ",\"shareCount\":" .. share_current_value .. ",\"inviteCount\":" .. invite_current_value .. "}]"
            else
                return "[\"com.opay.invite.model.response.PrizePoolResponse\",{\"message\":\"success\",\"prize\":-1,\"pool\":" .. pool .. ",\"activityCount\":" .. (prize_pool_count - 1) .. ",\"loginCount\":" .. login_current_value .. ",\"shareCount\":" .. share_current_value .. ",\"inviteCount\":" .. invite_current_value .. "}]"
            end
        end
    end
end
