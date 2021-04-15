package com.pm.infrastructure.mapper;

import com.alibaba.cola.dto.Response;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pm.infrastructure.dataobject.ModuleVersionDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author wcy
 */
public interface ModuleVersionMapper extends BaseMapper<ModuleVersionDO> {

    Optional<ModuleVersionDO> selectByMidAndVersion(@Param("mid") String mid, @Param("version") String version);

    Page<ModuleVersionDO> listModuleVersion(IPage<ModuleVersionDO> page, @Param("mid") String mid);

}
