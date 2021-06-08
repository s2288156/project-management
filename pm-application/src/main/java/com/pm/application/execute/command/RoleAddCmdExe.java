package com.pm.application.execute.command;

import com.pm.application.convertor.RoleConvertor;
import com.pm.application.dto.cmd.RoleAddCmd;
import com.pm.infrastructure.consts.ErrorCodeEnum;
import com.pm.infrastructure.dataobject.RoleDO;
import com.pm.infrastructure.mapper.RoleMapper;
import com.zyzh.exception.BizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author wcy
 */
@Component
public class RoleAddCmdExe {

    @Autowired
    private RoleMapper roleMapper;

    public String execute(RoleAddCmd roleAddCmd) {
        checkRoleNotExisted(roleAddCmd.getRole());
        RoleDO roleDO = RoleConvertor.INSTANCE.roleAddCmd2RoleDo(roleAddCmd);
        roleMapper.insert(roleDO);
        return roleDO.getId();
    }

    private void checkRoleNotExisted(String role) {
        Optional<RoleDO> roleOptional = roleMapper.selectByRole(role);
        if (roleOptional.isPresent()) {
            throw new BizException(ErrorCodeEnum.ROLE_EXISTED);
        }
    }

}
