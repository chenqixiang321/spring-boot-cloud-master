package com.opay.invite.mapper;

import com.opay.invite.model.InviteCountModel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface InviteCountMapper {
    int deleteByPrimaryKey(Long id);

    int insert(InviteCountModel record);

    int insertSelective(InviteCountModel record);

    InviteCountModel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(InviteCountModel record);

    int updateByPrimaryKey(InviteCountModel record);

    InviteCountModel selectByOpayId(String opayId, String day);
}