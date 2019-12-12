package com.opay.invite.mapper;

import com.opay.invite.model.LuckDrawInfoModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface LuckDrawInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LuckDrawInfoModel record);

    int insertSelective(LuckDrawInfoModel record);

    LuckDrawInfoModel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LuckDrawInfoModel record);

    int updateByPrimaryKey(LuckDrawInfoModel record);

    List<LuckDrawInfoModel> selectLuckDrawInfoList(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);
}