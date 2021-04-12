package com.pm.infrastructure.gatewayimpl;

import com.pm.NoneWebBaseTest;
import com.pm.infrastructure.dataobject.GroupDO;
import com.pm.infrastructure.mapper.GroupMapper;
import com.zyzh.pm.domain.gateway.GroupGateway;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author wcy
 */
class GroupGatewayImplTest extends NoneWebBaseTest {

    @Autowired
    private GroupGateway groupGateway;

    @Autowired
    private GroupMapper groupMapper;

    @Transactional
    @Test
    void testExistById() {
        boolean none = groupGateway.existById("hhhhh");
        assertFalse(none);

        GroupDO groupDO = new GroupDO();
        groupDO.setName("agagaga");
        groupMapper.insert(groupDO);

        boolean yes = groupGateway.existById(groupDO.getId());
        assertTrue(yes);
    }
}