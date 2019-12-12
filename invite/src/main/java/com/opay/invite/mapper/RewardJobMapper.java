package com.opay.invite.mapper;

import com.opay.invite.model.OpayInviteRelation;
import com.opay.invite.model.OpayUserOrder;
import com.opay.invite.model.OpayUserTask;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RewardJobMapper {

    List<OpayUserOrder> getOpayUserOrderList(@Param("day") Integer day, @Param("start") int start, @Param("pageSize") int pageSize);

    void updateOpayUserOrder(@Param("list") List<OpayUserOrder> list);

    void saveOpayUserOrder(List<OpayUserOrder> list);

    void saveUserTask(List<OpayUserTask> list);

    List<OpayInviteRelation> getTaskRelationList(@Param("start") int start, @Param("pageSize") int pageSize,@Param("day") Integer day,@Param("type") Integer type);
}
