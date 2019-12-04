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
local lucky_money_key_set = "luckyMoney:set:" .. KEYS[1] .. ":" .. KEYS[2] .. ":" .. KEYS[3] .. ":" .. KEYS[4]
local lucky_money_key_list = "luckyMoney:list:" .. KEYS[1] .. ":" .. KEYS[2] .. ":" .. KEYS[3] .. ":" .. KEYS[4]
local amountIds = Split(string.sub(ARGV[1], 2, -2), ",")
local amounts = Split(string.sub(ARGV[2], 2, -2), ",")
local expire = ARGV[3]
for i = 1, #amounts do
    redis.call("hset", lucky_money_key_set, amountIds[i], amounts[i])
    redis.call("rpush", lucky_money_key_list, amountIds[i])
end
redis.call("expire", lucky_money_key_set, expire)
redis.call("expire", lucky_money_key_list, expire)
return true


