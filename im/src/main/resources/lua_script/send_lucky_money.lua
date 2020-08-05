local function Split(szFullString, szSeparator)
    local nFindStartIndex = 1
    local nSplitIndex = 1
    local nSplitArray = {}
    while true do
        local nFindLastIndex = string.find(szFullString, szSeparator, nFindStartIndex)
        if not nFindLastIndex then
            nSplitArray[nSplitIndex] = string.sub(szFullString, nFindStartIndex, string.len(szFullString))
            break
        end
        nSplitArray[nSplitIndex] = string.sub(szFullString, nFindStartIndex, nFindLastIndex - 1)
        nFindStartIndex = nFindLastIndex + string.len(szSeparator)
        nSplitIndex = nSplitIndex + 1
    end
    return nSplitArray
end
local lucky_money_max = "luckyMoney:max:" .. KEYS[3]
local lucky_money = "luckyMoney:" .. KEYS[1]
local lucky_money_key_set = "luckyMoney:set:" .. KEYS[1] .. ":" .. KEYS[2] .. ":" .. KEYS[3] .. ":" .. KEYS[4]
local lucky_money_key_list = "luckyMoney:list:" .. KEYS[1] .. ":" .. KEYS[2] .. ":" .. KEYS[3] .. ":" .. KEYS[4]
local amount = ARGV[1]
local amountIds = Split(string.sub(ARGV[2], 2, -2), ",")
local amounts = Split(string.sub(ARGV[3], 2, -2), ",")
local day_max = ARGV[4]
local expire = ARGV[5]
local limit_expire = ARGV[6]
local day_max_value = redis.call("get", lucky_money_max)
if day_max_value == false then
    day_max_value = 0
end
if tonumber(day_max_value) + tonumber(amount) > tonumber(day_max) then
    return false
end
for i = 1, #amounts do
    redis.call("hset", lucky_money_key_set, amountIds[i], amounts[i])
    redis.call("lpush", lucky_money_key_list, amountIds[i])
end
redis.call("hset", lucky_money_key_set, "payStatus", 0)
redis.call("set", lucky_money_max, day_max_value + amount)
redis.call("set", lucky_money, KEYS[1]) --用于判断是否过期
redis.call("expire", lucky_money_max, limit_expire)
redis.call("expire", lucky_money_key_set, expire)
redis.call("expire", lucky_money_key_list, expire)
redis.call("expire", lucky_money, expire)
return true


