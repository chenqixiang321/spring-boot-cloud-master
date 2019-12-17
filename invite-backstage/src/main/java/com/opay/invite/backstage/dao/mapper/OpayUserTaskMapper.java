package com.opay.invite.backstage.dao.mapper;

import com.opay.invite.backstage.dao.entity.OpayUserTask;
import com.opay.invite.backstage.dao.entity.OpayUserTaskExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OpayUserTaskMapper {
    int countByExample(OpayUserTaskExample example);

    int deleteByExample(OpayUserTaskExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpayUserTask record);

    int insertSelective(OpayUserTask record);

    List<OpayUserTask> selectByExample(OpayUserTaskExample example);

    OpayUserTask selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpayUserTask record, @Param("example") OpayUserTaskExample example);

    int updateByExample(@Param("record") OpayUserTask record, @Param("example") OpayUserTaskExample example);

    int updateByPrimaryKeySelective(OpayUserTask record);

    int updateByPrimaryKey(OpayUserTask record);
}