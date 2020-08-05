local key = "incr:" .. KEYS[1]
local expire = ARGV[1]

local incr = redis.call("incr", key)
redis.call("expire", key, expire)
return incr


