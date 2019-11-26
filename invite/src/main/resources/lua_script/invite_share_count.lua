local key = KEYS[1]
local upper_limit = ARGV[1]
local expire = ARGV[2]
local num = redis.call("hget", key, "max")
if num == false then
    redis.call("hset", key, "max", 1)
    redis.call("hset", key, "current", 1)
    redis.call("expire", key, expire)
    return true
else
    if tonumber(num) >= tonumber(upper_limit) then
        return false
    else
        redis.call("hset", key, "max", num + 1)
        redis.call("hset", key, "current", num + 1)
        redis.call("expire", key, expire)
        return true
    end
end