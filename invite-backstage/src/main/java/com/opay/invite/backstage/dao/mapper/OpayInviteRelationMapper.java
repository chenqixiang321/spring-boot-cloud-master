package com.opay.invite.backstage.dao.mapper;

import com.opay.invite.backstage.dao.entity.OpayInviteRelation;
import com.opay.invite.backstage.dao.entity.OpayInviteRelationExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OpayInviteRelationMapper {
    int countByExample(OpayInviteRelationExample example);

    int deleteByExample(OpayInviteRelationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpayInviteRelation record);

    int insertSelective(OpayInviteRelation record);

    List<OpayInviteRelation> selectByExample(OpayInviteRelationExample example);

    OpayInviteRelation selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpayInviteRelation record, @Param("example") OpayInviteRelationExample example);

    int updateByExample(@Param("record") OpayInviteRelation record, @Param("example") OpayInviteRelationExample example);

    int updateByPrimaryKeySelective(OpayInviteRelation record);

    int updateByPrimaryKey(OpayInviteRelation record);
}