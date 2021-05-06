package com.pm.application.service;

import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import com.pm.NoneWebBaseTest;
import com.pm.infrastructure.consts.ErrorCodeEnum;
import com.pm.application.dto.cmd.ModuleAddCmd;
import com.pm.application.dto.cmd.ModuleDeleteCmd;
import com.pm.application.dto.cmd.ModuleVersionAddCmd;
import com.pm.application.dto.cmd.ModuleVersionUpdateCmd;
import com.pm.application.dto.vo.ModuleVO;
import com.pm.infrastructure.dataobject.ModuleDO;
import com.pm.infrastructure.dataobject.ModuleVersionDO;
import com.pm.infrastructure.dataobject.ProjectDO;
import com.pm.infrastructure.mapper.ModuleMapper;
import com.pm.infrastructure.mapper.ModuleVersionMapper;
import com.pm.infrastructure.mapper.ProjectMapper;
import com.zyzh.exception.BizException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author wcy
 */
public class ModuleServiceTest extends NoneWebBaseTest {

    @Autowired
    private IModuleService moduleService;

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private ModuleMapper moduleMapper;

    @Autowired
    private ModuleVersionMapper moduleVersionMapper;

    @Transactional
    @Test
    void addOneModuleTest() {
        ModuleAddCmd moduleAddCmd = new ModuleAddCmd();

        ProjectDO projectDO = new ProjectDO();
        projectDO.setName("testaaaaa");
        projectMapper.insert(projectDO);

        moduleAddCmd.setPid(projectDO.getId());
        moduleAddCmd.setName("testModule");
        moduleAddCmd.setVersion("1.0.0");

        // 首次插入version
        SingleResponse<ModuleVO> singleResponse = moduleService.addOne(moduleAddCmd);

        assertTrue(singleResponse.isSuccess());

        // 验证重复插入相同version
        try {
            moduleService.addOne(moduleAddCmd);
        } catch (BizException ex) {
            assertEquals(ErrorCodeEnum.MODULE_NAME_EXISTED.getErrorCode(), ex.getErrCode());
        }
    }

    @Transactional
    @Test
    void testAddModuleVersionMidNotExist() {
        String version = "2.1.1";
        String mid = "1231510238213912";

        ModuleVersionAddCmd versionAddCmd = new ModuleVersionAddCmd();
        versionAddCmd.setVersion(version);
        versionAddCmd.setMid(mid);

        // 如果mid不存在，应该抛出ErrorCodeEnum.MODULE_NOT_FOUND错误码
        Response response = moduleService.addVersion(versionAddCmd);
        assertEquals(ErrorCodeEnum.MODULE_NOT_FOUND.getErrorCode(), response.getErrCode());
    }

    @Transactional
    @Test
    void testUpdateVersion() {
        ModuleVersionDO moduleVersionDO = new ModuleVersionDO();
        moduleVersionDO.setVersion("1.2.3");
        moduleVersionDO.setMid("123");
        moduleVersionDO.setDescription("ddd");
        moduleVersionMapper.insert(moduleVersionDO);

        String desc = "hahaha";
        ModuleVersionUpdateCmd updateCmd = new ModuleVersionUpdateCmd();
        updateCmd.setId(moduleVersionDO.getId());
        updateCmd.setDescription(desc);
        Response response = moduleService.updateVersion(updateCmd);
        assertTrue(response.isSuccess());

        ModuleVersionDO verifyData = moduleVersionMapper.selectById(moduleVersionDO.getId());
        assertNotNull(verifyData.getVersion());
        assertNotNull(verifyData.getMid());
        assertEquals(desc, verifyData.getDescription());
    }

    @Transactional
    @Test
    void testAddModuleVersionSuccess() {
        ModuleDO moduleDO = insertModule();
        ModuleVersionAddCmd versionAddCmd = new ModuleVersionAddCmd();
        versionAddCmd.setVersion("2.3.1");
        versionAddCmd.setMid(moduleDO.getId());

        Response response = moduleService.addVersion(versionAddCmd);
        assertTrue(response.isSuccess());
    }

    private ModuleDO insertModule() {
        ModuleDO moduleDO = new ModuleDO();
        moduleDO.setName("hahaha");
        moduleDO.setPid("112233");
        moduleMapper.insert(moduleDO);
        return moduleDO;
    }
}
