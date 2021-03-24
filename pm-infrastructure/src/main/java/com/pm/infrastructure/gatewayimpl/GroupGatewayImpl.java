package com.pm.infrastructure.gatewayimpl;

import com.pm.infrastructure.dataobject.GroupDO;
import com.pm.infrastructure.mapper.GroupMapper;
import com.zyzh.pm.domain.gateway.GroupGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wcy
 */
@Component
public class GroupGatewayImpl implements GroupGateway {

    @Autowired
    private GroupMapper groupMapper;

    @Override
    public boolean existById(String id) {
        GroupDO groupDO = groupMapper.selectById(id);
        return groupDO != null;
    }
}
