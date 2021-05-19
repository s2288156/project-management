package com.pm.application.convertor;

import com.pm.application.dto.cmd.ModuleVersionAddCmd;
import com.pm.application.dto.vo.ModuleVersionVO;
import com.pm.infrastructure.dataobject.ModuleVersionDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author wcy
 */
@Mapper
public interface ModuleVersionConvertor {
    ModuleVersionConvertor INSTANCE = Mappers.getMapper(ModuleVersionConvertor.class);

    ModuleVersionDO convert2Do(ModuleVersionAddCmd moduleVersionAddCmd);

    ModuleVersionVO convert2Vo(ModuleVersionDO moduleVersionDO);
}
