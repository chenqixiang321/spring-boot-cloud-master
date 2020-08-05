package com.opay.invite.backstage.dao.mapper;

import com.opay.invite.backstage.dao.entity.OpayInviteCode;
import com.opay.invite.backstage.dao.entity.OpayInviteCodeExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OpayInviteCodeMapper {
    int countByExample(OpayInviteCodeExample example);

    int deleteByExample(OpayInviteCodeExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpayInviteCode record);

    int insertSelective(OpayInviteCode record);

    List<OpayInviteCode> selectByExample(OpayInviteCodeExample example);

    OpayInviteCode selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpayInviteCode record, @Param("example") OpayInviteCodeExample example);

    int updateByExample(@Param("record") OpayInviteCode record, @Param("example") OpayInviteCodeExample example);

    int updateByPrimaryKeySelective(OpayInviteCode record);

    int updateByPrimaryKey(OpayInviteCode record);
}