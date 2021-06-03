package com.pm.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pm.infrastructure.dataobject.RoleDO;

import java.util.Optional;
import java.util.Set;

/**
 * @author wcy
 */
public interface RoleMapper extends BaseMapper<RoleDO> {

    Set<String> listRoleByUid(String uid);

    Set<RoleDO> listRoleDoByUid(String uid);

    Set<String> listRoleByUrl(String url);

    Optional<RoleDO> selectByRole(String role);
}
