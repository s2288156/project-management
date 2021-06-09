package com.pm.application.convertor;

import com.pm.application.dto.vo.UserVO;
import com.pm.infrastructure.dataobject.UserDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author wcy
 */
@Mapper
public interface UserConvertor {
    UserConvertor INSTANCE = Mappers.getMapper(UserConvertor.class);

    @Mapping(source = "icon", target = "avatar")
    List<UserVO> userDo2UserVo(List<UserDO> userDOList);
}
