package com.pm.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pm.infrastructure.dataobject.UserRoleDO;

import java.util.List;

/**
 * @author wcy
 */
public interface UserRoleMapper extends BaseMapper<UserRoleDO> {
    int insertBatch(List<UserRoleDO> userRoleDOList);
}
