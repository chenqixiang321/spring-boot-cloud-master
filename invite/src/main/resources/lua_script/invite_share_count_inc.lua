local invite_share_count_key = "invite_share_count:" .. KEYS[1]
local max_key = KEYS[2] .. "_max"
local upper_limit = ARGV[1]
local expire = ARGV[2]
local max_value = redis.call("hget", invite_share_count_key, max_key)
local share_current_key = "share_current"
local share_current_value = redis.call("hget", invite_share_count_key, share_current_key)
if share_current_value == false then
    redis.call("hset", invite_share_count_key, share_current_key, 1)
end
if KEYS[2] == "invite" then
    local step = 5
    local invite_count_key = "invite_count:" .. KEYS[1]
    local current_value = redis.call("get", invite_count_key)
    if max_value == false then
        redis.call("hset", invite_share_count_key, max_key, step)
        redis.call("set", invite_count_key, step)
        redis.call("expire", invite_share_count_key, expire)
        return true
    else
        if tonumber(max_value) >= tonumber(upper_limit) then
            return false
        else
            redis.call("hset", invite_share_count_key, max_key, max_value + step)
            redis.call("set", invite_count_key, current_value + step)
            return true
        end
    end
elseif KEYS[2] == "share" then
    local step = 1
    local current_value = redis.call("hget", invite_share_count_key, share_current_key)
    if max_value == false then
        redis.call("hset", invite_share_count_key, max_key, step + current_value)
        redis.call("hset", invite_share_count_key, share_current_key, step + current_value)
        redis.call("expire", invite_share_count_key, expire)
        return true
    else
        if tonumber(max_value) >= tonumber(upper_limit) then
            return false
        else
            redis.call("hset", invite_share_count_key, max_key, max_value + step)
            redis.call("hset", invite_share_count_key, share_current_key, current_value + step)
            return true
        end
    end
end


