package com.opay.invite.mapper;

import com.opay.invite.model.OpayInviteCode;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

@Mapper
public interface OpayInviteCodeMapper {

    OpayInviteCode getInviteCode(@Param("opayId") String opayId);

    void saveInviteCode(@Param("opayId") String opayId,@Param("inviteCode") String code,@Param("phone") String phone,@Param("time") Date time);

    OpayInviteCode getOpayIdByInviteCode(@Param("inviteCode") String inviteCode);
}
