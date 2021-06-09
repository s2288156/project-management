package com.pm.application.convertor;

import com.pm.application.dto.cmd.ModuleAddCmd;
import com.pm.application.dto.cmd.ModuleUpdateLatestVersionCmd;
import com.pm.application.dto.vo.ModuleVO;
import com.pm.infrastructure.dataobject.ModuleDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author wcy
 */
@Mapper
public interface ModuleConvertor {
    ModuleConvertor INSTANCE = Mappers.getMapper(ModuleConvertor.class);

    @Mapping(source = "version", target = "latestVersion")
    ModuleDO convert2Do(ModuleAddCmd moduleAddCmd);

    ModuleDO convert2Do(ModuleUpdateLatestVersionCmd updateLatestVersionCmd);

    List<ModuleVO> convertDo2ModuleVo(List<ModuleDO> moduleDOList);
}
