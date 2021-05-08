package com.pm.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pm.infrastructure.dataobject.GroupDO;

import java.util.List;
import java.util.Optional;

/**
 * @author wcy
 */
public interface GroupMapper extends BaseMapper<GroupDO> {

    Optional<GroupDO> selectByName(String name);

    List<String> listAllPidByGroupId(String id);

}
