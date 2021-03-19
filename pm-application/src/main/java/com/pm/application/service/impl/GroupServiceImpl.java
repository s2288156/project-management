package com.pm.application.service.impl;

import com.alibaba.cola.dto.SingleResponse;
import com.pm.application.consts.ErrorCodeEnum;
import com.pm.application.convertor.GroupConvertor;
import com.pm.application.dto.cmd.GroupAddCmd;
import com.pm.application.service.IGroupService;
import com.pm.infrastructure.dataobject.GroupDO;
import com.pm.infrastructure.mapper.GroupMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author wcy
 */
@Service
public class GroupServiceImpl implements IGroupService {

    @Autowired
    private GroupMapper groupMapper;

    @Override
    public SingleResponse<?> addGroup(GroupAddCmd addCmd) {
        Optional<GroupDO> optional = groupMapper.selectByName(addCmd.getName());
        if (optional.isPresent()) {
            return SingleResponse.buildFailure(ErrorCodeEnum.GROUP_NAME_EXISTED.getErrorCode(), ErrorCodeEnum.GROUP_NAME_EXISTED.getErrorMsg());
        }
        groupMapper.insert(GroupConvertor.convert2Do(addCmd));
        return SingleResponse.buildSuccess();
    }
}
