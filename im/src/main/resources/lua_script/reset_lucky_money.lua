local lucky_money_key_set = "luckyMoney:set:" .. KEYS[1] .. ":" .. KEYS[2] .. ":" .. KEYS[3] .. ":" .. KEYS[4]
local lucky_money_key_list = "luckyMoney:list:" .. KEYS[1] .. ":" .. KEYS[2] .. ":" .. KEYS[3] .. ":" .. KEYS[4]
local lucky_money_record_id = ARGV[1]
local grab_user_id = ARGV[2]

redis.call("lrem", lucky_money_key_list, 0, lucky_money_record_id) --去重
redis.call("lpush", lucky_money_key_list, lucky_money_record_id)
redis.call("hdel", lucky_money_key_set, "grab_user:" .. grab_user_id)

