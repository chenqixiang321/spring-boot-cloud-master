package com.opay.im.mapper;

import com.opay.im.model.LuckyMoneyModel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LuckyMoneyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LuckyMoneyModel record);

    int insertSelective(LuckyMoneyModel record);

    LuckyMoneyModel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LuckyMoneyModel record);

    int updateByPrimaryKey(LuckyMoneyModel record);
}