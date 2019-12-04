local lucky_money_key_set = "luckyMoney:set:" .. KEYS[1] .. ":" .. KEYS[2] .. ":" .. KEYS[3] .. ":" .. KEYS[4]
local lucky_money_key_list = "luckyMoney:list:" .. KEYS[1] .. ":" .. KEYS[2] .. ":" .. KEYS[3] .. ":" .. KEYS[4]
local grab_user_id = ARGV[1]
local grab_user = redis.call("hget", lucky_money_key_set, "grab_user:" .. grab_user_id)
if grab_user == nil or grab_user == false then
    redis.call("hset", lucky_money_key_set, "grab_user:" .. grab_user_id, grab_user_id)
    local id = redis.call("lpop", lucky_money_key_list)
    if id ~= nil and id ~= false then
        local amount = redis.call("hget", lucky_money_key_set, id)
        return "[\"com.opay.im.model.response.GrabLuckyMoneyResult\",{\"id\":" .. id .. ",\"amount\":" .. amount .. "}]"
    end
else
    return "[\"com.opay.im.model.response.GrabLuckyMoneyResult\",{\"id\":0,\"amount\":0,\"message\":\"You have robbed this lucky money\"}]"
end
return "[\"com.opay.im.model.response.GrabLuckyMoneyResult\",{\"id\":0,\"amount\":0}]"


