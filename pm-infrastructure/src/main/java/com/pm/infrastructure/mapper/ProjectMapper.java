package com.pm.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pm.infrastructure.dataobject.ProjectDO;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

/**
 * @author wcy
 */
public interface ProjectMapper extends BaseMapper<ProjectDO> {
    Optional<ProjectDO> selectByName(String name);

    Page<ProjectDO> pageBy(IPage<ProjectDO> page, @Param("groupId") String groupId);

}
