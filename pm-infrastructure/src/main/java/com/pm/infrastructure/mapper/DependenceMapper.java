package com.pm.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pm.infrastructure.dataobject.DependenceDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author wcy
 */
public interface DependenceMapper extends BaseMapper<DependenceDO> {

    Optional<DependenceDO> selectByPidAndDependMid(@Param("pid") String pid, @Param("dependMid") String dependMid);

    List<DependenceDO> queryDependenceByProjectId(@Param("pid") String id);

    List<DependenceDO> selectByDependMid(@Param("dependMid") String dependMid);
}
