package com.opay.invite.backstage.service.impl;

import com.opay.invite.backstage.dao.entity.InviteOperator;
import com.opay.invite.backstage.dao.entity.InviteOperatorExample;
import com.opay.invite.backstage.dao.mapper.InviteOperatorMapper;
import com.opay.invite.backstage.exception.BackstageException;
import com.opay.invite.backstage.exception.BackstageExceptionEnum;
import com.opay.invite.backstage.service.OperatorService;
import com.opay.invite.backstage.service.dto.LoginReqDto;
import com.opay.invite.backstage.service.dto.LoginRespDto;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author liuzhihang
 * @date 2019/12/17 14:58
 */
@Service(value = "operatorService")
public class OperatorServiceImpl implements OperatorService {

    @Resource
    private InviteOperatorMapper inviteOperatorMapper;


    @Override
    public LoginRespDto login(LoginReqDto reqDto) throws BackstageException {


        InviteOperatorExample example = new InviteOperatorExample();
        example.createCriteria().andOperatorIdEqualTo(reqDto.getOperatorId());

        List<InviteOperator> operatorList = inviteOperatorMapper.selectByExample(example);

        if (CollectionUtils.isEmpty(operatorList)) {
            throw new BackstageException(BackstageExceptionEnum.OPERATOR_NOT_EXIST);
        }

        InviteOperator inviteOperator = operatorList.get(0);
        if (!inviteOperator.getLoginPwd().equals(reqDto.getLoginPwd())) {
            throw new BackstageException(BackstageExceptionEnum.PWD_ERROR);
        }

        // 预留返回
        return null;
    }
}
