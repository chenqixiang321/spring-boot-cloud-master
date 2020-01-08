package com.opay.invite.mapper;

import com.opay.invite.model.Active;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ActiveMapper {

    Integer insert(Active active);

    int update(Active active);

    Active selectByActiveId(@Param("activeId") String activeId);

    Active selectByPrimaryKey(Long id);
}
