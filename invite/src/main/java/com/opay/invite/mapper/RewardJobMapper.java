package com.opay.invite.mapper;

import com.opay.invite.model.OpayUserOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RewardJobMapper {

    List<OpayUserOrder> getOpayUserOrderList(@Param("day") Integer day, @Param("start") int start, @Param("pageSize") int pageSize);

    void updateOpayUserOrder(@Param("list") List<OpayUserOrder> list);
}