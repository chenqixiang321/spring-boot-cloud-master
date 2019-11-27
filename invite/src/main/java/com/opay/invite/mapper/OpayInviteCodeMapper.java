package com.opay.invite.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OpayInviteCodeMapper {

    String getInviteCode(@Param("opayId") String opayId);

    void saveInviteCode(@Param("opayId") String opayId,@Param("inviteCode") String code);

    String getOpayIdByInviteCode(@Param("inviteCode") String inviteCode);
}
