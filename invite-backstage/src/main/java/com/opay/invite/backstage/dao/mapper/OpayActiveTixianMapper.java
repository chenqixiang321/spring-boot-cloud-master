package com.opay.invite.backstage.dao.mapper;

import com.opay.invite.backstage.dao.entity.OpayActiveTixian;
import com.opay.invite.backstage.dao.entity.OpayActiveTixianExample;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface OpayActiveTixianMapper {
    int countByExample(OpayActiveTixianExample example);

    int deleteByExample(OpayActiveTixianExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpayActiveTixian record);

    int insertSelective(OpayActiveTixian record);

    List<OpayActiveTixian> selectByExample(OpayActiveTixianExample example);

    OpayActiveTixian selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpayActiveTixian record, @Param("example") OpayActiveTixianExample example);

    int updateByExample(@Param("record") OpayActiveTixian record, @Param("example") OpayActiveTixianExample example);

    int updateByPrimaryKeySelective(OpayActiveTixian record);

    int updateByPrimaryKey(OpayActiveTixian record);

    BigDecimal sumAmountByTypeAndStatus(@Param("type") Byte type, @Param("status") Byte status, @Param("opayId") String opayId);

}