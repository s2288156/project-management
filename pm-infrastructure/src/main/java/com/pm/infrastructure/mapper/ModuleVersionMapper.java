package com.pm.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pm.infrastructure.dataobject.ModuleVersionDO;

import java.util.Optional;

/**
 * @author wcy
 */
public interface ModuleVersionMapper extends BaseMapper<ModuleVersionDO> {

    Optional<ModuleVersionDO> selectByVersion(String version);

}
