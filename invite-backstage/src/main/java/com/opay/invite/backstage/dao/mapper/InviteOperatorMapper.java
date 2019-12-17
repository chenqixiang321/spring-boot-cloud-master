package com.opay.invite.backstage.dao.mapper;

import com.opay.invite.backstage.dao.entity.InviteOperator;
import com.opay.invite.backstage.dao.entity.InviteOperatorExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InviteOperatorMapper {
    int countByExample(InviteOperatorExample example);

    int deleteByExample(InviteOperatorExample example);

    int deleteByPrimaryKey(Long id);

    int insert(InviteOperator record);

    int insertSelective(InviteOperator record);

    List<InviteOperator> selectByExample(InviteOperatorExample example);

    InviteOperator selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") InviteOperator record, @Param("example") InviteOperatorExample example);

    int updateByExample(@Param("record") InviteOperator record, @Param("example") InviteOperatorExample example);

    int updateByPrimaryKeySelective(InviteOperator record);

    int updateByPrimaryKey(InviteOperator record);
}