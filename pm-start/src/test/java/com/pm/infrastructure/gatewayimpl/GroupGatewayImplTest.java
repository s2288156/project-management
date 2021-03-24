package com.pm.infrastructure.gatewayimpl;

import com.pm.NoneWebBaseTest;
import com.pm.infrastructure.mapper.GroupMapper;
import com.zyzh.pm.domain.gateway.GroupGateway;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author wcy
 */
class GroupGatewayImplTest extends NoneWebBaseTest {

    @Autowired
    private GroupGateway groupGateway;

    @Autowired
    private GroupMapper groupMapper;

    @Test
    void testExistById() {
        boolean none = groupGateway.existById("hhhhh");
        assertFalse(none);

        String id = "111";

        boolean yes = groupGateway.existById(id);
        assertTrue(yes);
    }
}