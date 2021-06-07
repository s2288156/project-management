package com.pm.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pm.infrastructure.dataobject.RoleResourceDO;

import java.util.List;

/**
 * @author wcy
 */
public interface RoleResourceMapper extends BaseMapper<RoleResourceDO> {

    int insertBatchResourceIds(List<RoleResourceDO> resourceList);

}
