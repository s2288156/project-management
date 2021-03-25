package com.pm.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pm.infrastructure.dataobject.ModuleDO;

import java.util.Optional;

/**
 * @author wcy
 */
public interface ModuleMapper extends BaseMapper<ModuleDO> {

    Optional<ModuleDO> selectByName(String name);

}
