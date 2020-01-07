package com.opay.invite.mapper;

import com.opay.invite.model.Active;
import com.opay.invite.model.OpayInviteRankVo;
import com.opay.invite.model.OpayInviteRelation;
import com.opay.invite.model.OpayInviteRelationVo;
import com.opay.invite.model.OpayMasterPupilAward;
import com.opay.invite.model.OpayMasterPupilAwardVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ActiveMapper {

    Integer insert(Active active);

    Integer update(Active active);

    Active selectByActiveId(@Param("activeId") String activeId);

    Active selectByPrimaryKey(Long id);
}
