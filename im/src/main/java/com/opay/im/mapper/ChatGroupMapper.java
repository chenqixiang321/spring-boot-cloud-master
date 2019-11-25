package com.opay.im.mapper;

import com.opay.im.model.ChatGroupModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatGroupMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ChatGroupModel record);

    int insertSelective(ChatGroupModel record);

    ChatGroupModel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ChatGroupModel record);

    int updateByPrimaryKeySelectiveWithVersion(ChatGroupModel record);

    int updateByPrimaryKey(ChatGroupModel record);

    List<ChatGroupModel> selectGroupList(String opayId);
}