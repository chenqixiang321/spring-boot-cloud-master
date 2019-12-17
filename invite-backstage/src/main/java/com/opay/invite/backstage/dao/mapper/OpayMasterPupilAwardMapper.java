package com.opay.invite.backstage.dao.mapper;

import com.opay.invite.backstage.dao.entity.OpayMasterPupilAward;
import com.opay.invite.backstage.dao.entity.OpayMasterPupilAwardExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OpayMasterPupilAwardMapper {
    int countByExample(OpayMasterPupilAwardExample example);

    int deleteByExample(OpayMasterPupilAwardExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpayMasterPupilAward record);

    int insertSelective(OpayMasterPupilAward record);

    List<OpayMasterPupilAward> selectByExample(OpayMasterPupilAwardExample example);

    OpayMasterPupilAward selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpayMasterPupilAward record, @Param("example") OpayMasterPupilAwardExample example);

    int updateByExample(@Param("record") OpayMasterPupilAward record, @Param("example") OpayMasterPupilAwardExample example);

    int updateByPrimaryKeySelective(OpayMasterPupilAward record);

    int updateByPrimaryKey(OpayMasterPupilAward record);
}