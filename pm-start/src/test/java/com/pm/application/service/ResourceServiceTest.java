package com.pm.application.service;

import com.pm.NoneWebBaseTest;
import com.pm.application.dto.cmd.ResourceAddCmd;
import com.pm.application.service.impl.ResourceServiceImpl;
import com.pm.infrastructure.dataobject.ResourceDO;
import com.pm.infrastructure.mapper.ResourceMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author wcy
 */
@Slf4j
public class ResourceServiceTest extends NoneWebBaseTest {

    @Autowired
    private ResourceServiceImpl resourceService;

    @Autowired
    private ResourceMapper resourceMapper;
    ResourceAddCmd addCmd;
    String requestMethod = "GET";
    String apiPath = "/go";

    @BeforeEach
    void setUp() {
        addCmd = new ResourceAddCmd();
        addCmd.setRequestMethod(requestMethod);
        addCmd.setApiPath(apiPath);
    }

    @Transactional
    @DisplayName("addResource成功添加")
    @Test
    void testAddResource() {
        String id = resourceService.addResource(addCmd);
        ResourceDO resourceDO = resourceMapper.selectById(id);
        assertNotNull(resourceDO);
        String url = resourceDO.getUrl();
        assertTrue(url.contains(":"));
        assertAll(
                () -> assertEquals(requestMethod, url.split(":")[0]),
                () -> assertEquals(apiPath, url.split(":")[1])
        );
    }

    @Transactional
    @DisplayName("addResource重复添加")
    @Test
    void testAddResourceRepetition() {
        resourceService.addResource(addCmd);
        assertThrows(DuplicateKeyException.class, () -> resourceService.addResource(addCmd));
    }
}
