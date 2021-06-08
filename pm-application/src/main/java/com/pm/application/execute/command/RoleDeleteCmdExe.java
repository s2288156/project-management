package com.pm.application.execute.command;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pm.infrastructure.consts.ErrorCodeEnum;
import com.pm.infrastructure.dataobject.RoleResourceDO;
import com.pm.infrastructure.dataobject.UserRoleDO;
import com.pm.infrastructure.mapper.RoleMapper;
import com.pm.infrastructure.mapper.RoleResourceMapper;
import com.pm.infrastructure.mapper.UserRoleMapper;
import com.zyzh.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wcy
 */
@Slf4j
@Component
public class RoleDeleteCmdExe {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RoleResourceMapper roleResourceMapper;

    public void execute(String id) {
        Integer userRoleCount = userRoleMapper.selectCount(new LambdaQueryWrapper<UserRoleDO>()
                .eq(UserRoleDO::getRoleId, id));
        Integer roleResourceCount = roleResourceMapper.selectCount(new LambdaQueryWrapper<RoleResourceDO>()
                .eq(RoleResourceDO::getRoleId, id));
        if (userRoleCount > 0 || roleResourceCount > 0) {
            throw new BizException(ErrorCodeEnum.ROLE_HAS_USED_NOT_ALLOW_DELETE);
        }
        roleMapper.deleteById(id);
    }

}
