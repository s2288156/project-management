package com.pm.application.convertor;

import com.pm.application.dto.cmd.GroupAddCmd;
import com.pm.infrastructure.dataobject.GroupDO;

/**
 * @author wcy
 */
public class GroupConvertor {

    public static GroupDO convert2Do(GroupAddCmd addCmd) {
        GroupDO groupDO = new GroupDO();
        groupDO.setName(addCmd.getName());
        return groupDO;
    }
}
