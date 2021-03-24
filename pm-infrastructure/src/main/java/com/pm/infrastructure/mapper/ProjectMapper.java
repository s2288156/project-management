package com.pm.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pm.infrastructure.dataobject.ProjectDO;

import java.util.Optional;

/**
 * @author wcy
 */
public interface ProjectMapper extends BaseMapper<ProjectDO> {
    Optional<ProjectDO> selectByName(String name);
}
