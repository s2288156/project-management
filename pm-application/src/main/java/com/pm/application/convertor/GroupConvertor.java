package com.pm.application.convertor;

import com.pm.application.dto.cmd.GroupAddCmd;
import com.pm.application.dto.vo.GroupVO;
import com.pm.infrastructure.dataobject.GroupDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author wcy
 */
@Mapper
public interface GroupConvertor {
    GroupConvertor INSTANCE = Mappers.getMapper(GroupConvertor.class);

    GroupDO convert2Do(GroupAddCmd groupAddCmd);

    List<GroupVO> convertDo2Vo(List<GroupDO> groupDOList);

}
