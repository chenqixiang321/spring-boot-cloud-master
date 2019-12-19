local lucky_money_key_set = "luckyMoney:set:" .. KEYS[1] .. ":" .. KEYS[2] .. ":" .. KEYS[3] .. ":" .. KEYS[4]
local lucky_money_key_list = "luckyMoney:list:" .. KEYS[1] .. ":" .. KEYS[2] .. ":" .. KEYS[3] .. ":" .. KEYS[4]
local grab_user_id = ARGV[1]
local exists = redis.call("exists", lucky_money_key_set)
if exists == 0 then
    return "[\"com.opay.im.model.response.GrabLuckyMoneyResult\",{\"code\":1,\"message\":\"The lucky money does not exist or has expired\"}]"
end
if redis.call("hget", lucky_money_key_set, "payStatus") ~= 1 then
    return "[\"com.opay.im.model.response.GrabLuckyMoneyResult\",{\"code\":4,\"message\":\"The lucky money unpaid\"}]"
end
local grab_user = redis.call("hget", lucky_money_key_set, "grab_user:" .. grab_user_id)
if grab_user == nil or grab_user == false then
    local id = redis.call("lpop", lucky_money_key_list)
    if id == false or id == nil then
        return "[\"com.opay.im.model.response.GrabLuckyMoneyResult\",{\"code\":2,\"message\":\"The lucky money has been robbed\"}]"
    end
    redis.call("hset", lucky_money_key_set, "grab_user:" .. grab_user_id, id)
    local amount = redis.call("hget", lucky_money_key_set, id)
    return "[\"com.opay.im.model.response.GrabLuckyMoneyResult\",{\"code\":0,\"id\":" .. id .. ",\"amount\":" .. amount .. "}]"
else
    local amount = redis.call("hget", lucky_money_key_set, grab_user)
    return "[\"com.opay.im.model.response.GrabLuckyMoneyResult\",{\"code\":3,\"id\":" .. grab_user .. ",\"amount\":" .. amount .. ",\"message\":\"You have robbed this lucky money\"}]"
end


