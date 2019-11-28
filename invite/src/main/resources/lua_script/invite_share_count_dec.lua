local opay_id = KEYS[1]
local opay_id_key = "invite_share_count:" .. opay_id
local luck_draw_key = "luck_draw_count"
local first_prize_index = ARGV[1]
local second_prize_index = ARGV[2]
local grand_prize_index = ARGV[3]
local second_pool_rate = ARGV[4]
local first_prize_key = "prize_pool:first:" .. first_prize_index
local second_prize_key = "prize_pool:second:" .. second_prize_index
local current_value = redis.call("hget", opay_id_key, "current")
local prize_pool_count = redis.call("get", "prize_pool:count")
local pool = 1
local prize = first_prize_index
if prize_pool_count == false then
    return "[\"com.opay.invite.model.response.PrizePoolResponse\",{\"message\":\"Prize pool is empty\"}]"
else
    if tonumber(prize_pool_count) <= 0 then
        return "[\"com.opay.invite.model.response.PrizePoolResponse\",{\"message\":\"Prize pool is empty\"}]"
    else
        redis.call("decr", "prize_pool:count")
        if current_value == false then
            return "[\"com.opay.invite.model.response.PrizePoolResponse\",{\"message\":\"The number of draws has been exhausted\"}]"
        else
            if tonumber(current_value) <= 0 then
                return "[\"com.opay.invite.model.response.PrizePoolResponse\",{\"message\":\"The number of draws has been exhausted\"}]"
            else
                local prize_key = first_prize_key
                local luck_draw_count = redis.call("hget", opay_id_key, luck_draw_key)
                if luck_draw_count == false then
                    redis.call("hset", opay_id_key, luck_draw_key, 1)
                else
                    if math.fmod(luck_draw_count, tonumber(second_pool_rate)) == 0 then
                        prize_key = second_prize_key
                        pool = 2
                        prize = second_prize_index
                    end
                    redis.call("hset", opay_id_key, luck_draw_key, luck_draw_count + 1)
                end
                redis.call("hset", opay_id_key, "current", current_value - 1)
                local prize_count = redis.call("get", prize_key)
                if prize_count == false then
                    return "[\"com.opay.invite.model.response.PrizePoolResponse\",{\"message\":\"success\",\"pool\":" .. pool .. "}]"
                else
                    if tonumber(prize_count) > 0 then
                        if tonumber(grand_prize_index) > tonumber(prize_pool_count) and pool == 2 and tonumber(redis.call("get", "prize_pool:second:0")) > 0 then
                            redis.call("decr", "prize_pool:second:0")
                            return "[\"com.opay.invite.model.response.PrizePoolResponse\",{\"message\":\"success\",\"pool\":2,\"prize\":0}]"
                        else
                            redis.call("decr", prize_key)
                            return "[\"com.opay.invite.model.response.PrizePoolResponse\",{\"message\":\"success\",\"pool\":" .. pool .. ",\"prize\":" .. prize .. "}]"
                        end
                    else
                        return "[\"com.opay.invite.model.response.PrizePoolResponse\",{\"message\":\"success\",\"pool\":" .. pool .. "}]"
                    end
                end
            end
        end
    end
end