package com.opay.im.service;

import com.opay.im.model.ChatGroupModel;
public interface ChatGroupService {


    int deleteByPrimaryKey(Long id);

    int insert(ChatGroupModel record) throws Exception;

    int insertSelective(ChatGroupModel record);

    ChatGroupModel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ChatGroupModel record);

    int updateByPrimaryKey(ChatGroupModel record);

}
