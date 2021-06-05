package com.pm.application.convertor;

import com.pm.application.dto.vo.ResourceVO;
import com.pm.infrastructure.dataobject.ResourceDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author wcy
 */
@Mapper
public interface ResourceConvertor {
    ResourceConvertor INSTANCE = Mappers.getMapper(ResourceConvertor.class);

    ResourceVO do2Vo(ResourceDO resourceDO);
}
