package com.pm.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pm.infrastructure.dataobject.ModuleVersionDO;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

/**
 * @author wcy
 */
public interface ModuleVersionMapper extends BaseMapper<ModuleVersionDO> {

    Optional<ModuleVersionDO> selectByMidAndVersion(@Param("mid") String mid, @Param("version") String version);

    Page<ModuleVersionDO> listModuleVersion(IPage<ModuleVersionDO> page, @Param("mid") String mid);
}
