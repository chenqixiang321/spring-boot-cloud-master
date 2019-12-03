package com.opay.im.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

@Slf4j
public class MessagePreciseShardingAlgorithm implements PreciseShardingAlgorithm {
    @Override
    public String doSharding(Collection collection, PreciseShardingValue preciseShardingValue) {
        long time = Long.parseLong(String.valueOf(preciseShardingValue.getValue()));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(time));
        return preciseShardingValue.getLogicTableName() + "_" + calendar.get(Calendar.YEAR) + "_" + (calendar.get(Calendar.MONTH) + 1);
    }
}
