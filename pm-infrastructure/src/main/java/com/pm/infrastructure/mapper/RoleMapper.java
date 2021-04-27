package com.pm.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pm.infrastructure.dataobject.RoleDO;

import java.util.Set;

/**
 * @author wcy
 */
public interface RoleMapper extends BaseMapper<RoleDO> {

    Set<String> listRoleByUid(String uid);
}
