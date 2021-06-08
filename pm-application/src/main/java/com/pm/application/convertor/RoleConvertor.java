package com.pm.application.convertor;

import com.pm.application.dto.cmd.RoleAddCmd;
import com.pm.application.dto.vo.RoleVO;
import com.pm.infrastructure.dataobject.RoleDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author wcy
 */
@Mapper
public interface RoleConvertor {
    RoleConvertor INSTANCE = Mappers.getMapper(RoleConvertor.class);

    RoleVO roleDo2RoleVo(RoleDO roleDO);

    RoleDO roleAddCmd2RoleDo(RoleAddCmd roleAddCmd);
}
