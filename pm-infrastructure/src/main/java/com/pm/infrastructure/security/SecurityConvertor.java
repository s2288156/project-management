package com.pm.infrastructure.security;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author wcy
 */
@Mapper
public interface SecurityConvertor {
    SecurityConvertor INSTANCE = Mappers.getMapper(SecurityConvertor.class);

    @Mapping(source = "id", target = "uid")
    JwtPayload securityUser2JwtPayload(SecurityUser securityUser);

}
