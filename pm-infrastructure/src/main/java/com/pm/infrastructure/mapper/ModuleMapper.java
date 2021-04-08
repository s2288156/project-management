package com.pm.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pm.infrastructure.dataobject.ModuleDO;
import com.pm.infrastructure.dataobject.ModuleVersionDO;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

/**
 * @author wcy
 */
public interface ModuleMapper extends BaseMapper<ModuleDO> {

    Optional<ModuleDO> selectByName(String name);

    Page<ModuleDO> listProjectAndVersion(IPage<ModuleDO> page, @Param("pid") String pid);

    Page<ModuleDO> listDependModule(IPage<ModuleDO> page, @Param("mid") String mid, @Param("pid") String pid);
}
