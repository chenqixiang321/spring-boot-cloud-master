package com.opay.im.mapper;

import com.opay.im.model.ChatGroupMemberModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatGroupMemberMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ChatGroupMemberModel record);

    int insertSelective(ChatGroupMemberModel record);

    ChatGroupMemberModel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ChatGroupMemberModel record);

    int updateByPrimaryKey(ChatGroupMemberModel record);

    int selectGroupMemberCount(Long groupId);

    int deleteByGroupIdAndOpayId(ChatGroupMemberModel chatGroupMemberModel);

    ChatGroupMemberModel selectFirstJoinGroupMember(ChatGroupMemberModel chatGroupMemberModel);

    int updateByOpayIdAndGroupId(ChatGroupMemberModel chatGroupMemberModel);

    List<ChatGroupMemberModel> selectGroupMember(Long groupId);
}