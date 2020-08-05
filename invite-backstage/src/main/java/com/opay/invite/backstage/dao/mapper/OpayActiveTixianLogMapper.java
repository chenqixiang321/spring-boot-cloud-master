package com.opay.invite.backstage.dao.mapper;

import com.opay.invite.backstage.dao.entity.OpayActiveTixianLog;
import com.opay.invite.backstage.dao.entity.OpayActiveTixianLogExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OpayActiveTixianLogMapper {
    int countByExample(OpayActiveTixianLogExample example);

    int deleteByExample(OpayActiveTixianLogExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpayActiveTixianLog record);

    int insertSelective(OpayActiveTixianLog record);

    List<OpayActiveTixianLog> selectByExample(OpayActiveTixianLogExample example);

    OpayActiveTixianLog selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpayActiveTixianLog record, @Param("example") OpayActiveTixianLogExample example);

    int updateByExample(@Param("record") OpayActiveTixianLog record, @Param("example") OpayActiveTixianLogExample example);

    int updateByPrimaryKeySelective(OpayActiveTixianLog record);

    int updateByPrimaryKey(OpayActiveTixianLog record);
}