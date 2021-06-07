package com.pm.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pm.infrastructure.dataobject.ResourceDO;
import org.apache.ibatis.annotations.Param;

/**
 * @author wcy
 */
public interface ResourceMapper extends BaseMapper<ResourceDO> {
    Page<ResourceDO> selectAllByRoleId(IPage<ResourceDO> page, @Param("roleId") String roleId);
}
