package com.opay.invite.backstage.dao.mapper;

import com.opay.invite.backstage.dao.entity.InviteCount;
import com.opay.invite.backstage.dao.entity.InviteCountExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InviteCountMapper {
    int countByExample(InviteCountExample example);

    int deleteByExample(InviteCountExample example);

    int deleteByPrimaryKey(Long id);

    int insert(InviteCount record);

    int insertSelective(InviteCount record);

    List<InviteCount> selectByExample(InviteCountExample example);

    InviteCount selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") InviteCount record, @Param("example") InviteCountExample example);

    int updateByExample(@Param("record") InviteCount record, @Param("example") InviteCountExample example);

    int updateByPrimaryKeySelective(InviteCount record);

    int updateByPrimaryKey(InviteCount record);
}