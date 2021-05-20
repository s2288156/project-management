package com.pm.application.convertor;

import com.pm.application.dto.cmd.ProjectDependAddCmd;
import com.pm.infrastructure.dataobject.DependenceDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author wcy
 */
@Mapper
public interface DependenceConvertor {

    DependenceConvertor INSTANCE = Mappers.getMapper(DependenceConvertor.class);

    DependenceDO convert2DO(ProjectDependAddCmd dependAddCmd);
}
