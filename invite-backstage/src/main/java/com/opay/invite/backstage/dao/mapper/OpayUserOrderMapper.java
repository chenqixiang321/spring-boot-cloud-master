package com.opay.invite.backstage.dao.mapper;

import com.opay.invite.backstage.dao.entity.OpayUserOrder;
import com.opay.invite.backstage.dao.entity.OpayUserOrderExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OpayUserOrderMapper {
    int countByExample(OpayUserOrderExample example);

    int deleteByExample(OpayUserOrderExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OpayUserOrder record);

    int insertSelective(OpayUserOrder record);

    List<OpayUserOrder> selectByExample(OpayUserOrderExample example);

    OpayUserOrder selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OpayUserOrder record, @Param("example") OpayUserOrderExample example);

    int updateByExample(@Param("record") OpayUserOrder record, @Param("example") OpayUserOrderExample example);

    int updateByPrimaryKeySelective(OpayUserOrder record);

    int updateByPrimaryKey(OpayUserOrder record);
}