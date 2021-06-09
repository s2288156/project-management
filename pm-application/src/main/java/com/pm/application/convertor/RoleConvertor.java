package com.pm.application.convertor;

import com.pm.application.dto.cmd.RoleAddCmd;
import com.pm.application.dto.vo.RoleVO;
import com.pm.infrastructure.dataobject.RoleDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.List;

/**
 * @author wcy
 */
@Mapper
public interface RoleConvertor {
    RoleConvertor INSTANCE = Mappers.getMapper(RoleConvertor.class);

    List<RoleVO> roleDo2RoleVo(Collection<RoleDO> roleDOList);

    RoleDO roleAddCmd2RoleDo(RoleAddCmd roleAddCmd);
}
