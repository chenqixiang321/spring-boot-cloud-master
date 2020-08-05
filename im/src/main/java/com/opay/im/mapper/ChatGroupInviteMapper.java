package com.opay.im.mapper;

import com.opay.im.model.ChatGroupInviteModel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChatGroupInviteMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ChatGroupInviteModel record);

    int insertSelective(ChatGroupInviteModel record);

    ChatGroupInviteModel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ChatGroupInviteModel record);

    int updateByPrimaryKey(ChatGroupInviteModel record);
}