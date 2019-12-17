package com.opay.invite.backstage.dao.mapper;

import com.opay.invite.backstage.dao.entity.LuckDrawInfo;
import com.opay.invite.backstage.dao.entity.LuckDrawInfoExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LuckDrawInfoMapper {
    int countByExample(LuckDrawInfoExample example);

    int deleteByExample(LuckDrawInfoExample example);

    int deleteByPrimaryKey(Long id);

    int insert(LuckDrawInfo record);

    int insertSelective(LuckDrawInfo record);

    List<LuckDrawInfo> selectByExample(LuckDrawInfoExample example);

    LuckDrawInfo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") LuckDrawInfo record, @Param("example") LuckDrawInfoExample example);

    int updateByExample(@Param("record") LuckDrawInfo record, @Param("example") LuckDrawInfoExample example);

    int updateByPrimaryKeySelective(LuckDrawInfo record);

    int updateByPrimaryKey(LuckDrawInfo record);
}