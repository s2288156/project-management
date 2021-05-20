package com.pm.application.convertor;

import com.pm.application.dto.cmd.ProjectAddCmd;
import com.pm.infrastructure.dataobject.ProjectDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author wcy
 */
@Mapper
public interface ProjectConvertor {

    ProjectConvertor INSTANCE = Mappers.getMapper(ProjectConvertor.class);

    ProjectDO convert2Do(ProjectAddCmd addCmd);
}
