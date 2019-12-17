package com.opay.invite.backstage.dao.mapper;

import com.opay.invite.backstage.dao.entity.OpayActiveCashback;
import com.opay.invite.backstage.dao.entity.OpayActiveCashbackExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OpayActiveCashbackMapper {
    int countByExample(OpayActiveCashbackExample example);

    int deleteByExample(OpayActiveCashbackExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpayActiveCashback record);

    int insertSelective(OpayActiveCashback record);

    List<OpayActiveCashback> selectByExample(OpayActiveCashbackExample example);

    OpayActiveCashback selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpayActiveCashback record, @Param("example") OpayActiveCashbackExample example);

    int updateByExample(@Param("record") OpayActiveCashback record, @Param("example") OpayActiveCashbackExample example);

    int updateByPrimaryKeySelective(OpayActiveCashback record);

    int updateByPrimaryKey(OpayActiveCashback record);
}