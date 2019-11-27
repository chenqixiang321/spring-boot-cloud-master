local opay_id = "invite_share_count:" .. KEYS[1]
local max_key = KEYS[2] .. "_max"
local upper_limit = ARGV[1]
local expire = ARGV[2]
local max_value = redis.call("hget", opay_id, max_key)
local current_value = redis.call("hget", opay_id, "current")
local step = 1
if KEYS[2] == "invite" then
    step = 5
end
if max_value == false then
    redis.call("hset", opay_id, max_key, step)
    if current_value == false then
        redis.call("hset", opay_id, "current", step + 1)
    else
        redis.call("hset", opay_id, "current", current_value + step)
    end
    redis.call("expire", opay_id, expire)
    return true
else
    if tonumber(max_value) >= tonumber(upper_limit) then
        return false
    else
        redis.call("hset", opay_id, max_key, max_value + step)
        redis.call("hset", opay_id, "current", current_value + step)
        return true
    end
end


