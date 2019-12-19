package com.opay.im.mapper;

import com.opay.im.model.LuckyMoneyModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LuckyMoneyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LuckyMoneyModel record);

    int insertSelective(LuckyMoneyModel record);

    LuckyMoneyModel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LuckyMoneyModel record);

    int updateByPrimaryKey(LuckyMoneyModel record);

    int updateByReferenceKeySelective(LuckyMoneyModel record);

    Integer selectPayStatus(@Param("opayId") String opayId, @Param("reference") String reference);

    LuckyMoneyModel selectLuckyMoneyById(@Param("id") Long id);
}