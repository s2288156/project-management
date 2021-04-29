package com.pm.application.service;

import com.alibaba.cola.dto.Response;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pm.NoneWebBaseTest;
import com.pm.application.consts.ErrorCodeEnum;
import com.pm.application.dto.cmd.ModuleDeleteCmd;
import com.pm.infrastructure.dataobject.*;
import com.pm.infrastructure.mapper.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author ytl
 */
@Slf4j
@Transactional
public class ModuleDeleteTest extends NoneWebBaseTest {
    @Autowired
    private ModuleMapper moduleMapper;
    @Autowired
    private GroupMapper groupMapper;
    @Autowired
    private ProjectMapper projectMapper;
    @Autowired
    private ModuleVersionMapper moduleVersionMapper;
    @Autowired
    private DependenceMapper dependenceMapper;
    @Autowired
    private IModuleService moduleService;


    // 该模块被项目引入，删除失败
    @Test
    void testFailDeleteModule() {
        ProjectDO projectDO = insertProject("222");
        ModuleDO moduleDO = insertModule("111");

        ModuleVersionDO moduleVersionDO = insertModuleVersion(moduleDO.getId());

        DependenceDO dependenceDO = insertDependence(moduleDO.getId(), projectDO.getId());

        List<ModuleVersionDO> moduleVersionDOListDeleteBefore = moduleVersionMapper.selectList(new LambdaQueryWrapper<ModuleVersionDO>()
                .eq(ModuleVersionDO::getMid, moduleDO.getId()));

        Response response = this.getResponseDeleteModule(moduleDO);
        Assertions.assertFalse(response.isSuccess());
        assertEquals(ErrorCodeEnum.MODULE_DEPENDENCE_ERROR.getErrorCode(), response.getErrCode());
        log.info(">>>>>>>>>>>>>>>errorMessage:{}<<<<<<<<<<<<<<<<<<", response.getErrMessage());

        ModuleVersionDO selectModuleVersionDO = moduleVersionMapper.selectById(moduleVersionDO.getId());
        boolean selectModuleVersionDOBoolean = Objects.isNull(selectModuleVersionDO);
        assertEquals(false, selectModuleVersionDOBoolean);

        List<ModuleVersionDO> moduleVersionDOListDeleteAfter = moduleVersionMapper.selectList(new LambdaQueryWrapper<ModuleVersionDO>()
                .eq(ModuleVersionDO::getMid, moduleDO.getId()));
        assertEquals(moduleVersionDOListDeleteBefore.size(), moduleVersionDOListDeleteAfter.size());

        DependenceDO dependence = dependenceMapper.selectById(dependenceDO.getId());
        Assertions.assertFalse(Objects.isNull(dependence));

        ModuleDO selectModuleDO = moduleMapper.selectById(moduleDO.getId());
        Assertions.assertFalse(Objects.isNull(selectModuleDO));

    }

    // 该模块未被引用删除成功  检验模块是否删除  模块版本是否删除
    @Test
    void testSuccessDeleteModule() {
        ModuleDO moduleDO = insertModule("111");
        ModuleVersionDO moduleVersionDO = insertModuleVersion(moduleDO.getId());
        ModuleVersionDO moduleVersionDOTwo = insertModuleVersionTwo(moduleDO.getId());

        List<ModuleVersionDO> moduleVersionDOListDeleteBefore = moduleVersionMapper.selectList(new LambdaQueryWrapper<ModuleVersionDO>()
                .eq(ModuleVersionDO::getMid, moduleDO.getId()));
        Assertions.assertFalse(CollectionUtils.isEmpty(moduleVersionDOListDeleteBefore));
        log.info(">>>>>>>>>>>>>>>>>>>moduleVersionDOList:{}<<<<<<<<<<<<<<<<<<<<<<<", moduleVersionDOListDeleteBefore.toString());

        Response response = this.getResponseDeleteModule(moduleDO);
        Assertions.assertTrue(response.isSuccess());

        ModuleDO module = moduleMapper.selectById(moduleDO.getId());
        Assertions.assertNull(module);

        List<ModuleVersionDO> moduleVersionDOList = moduleVersionMapper.selectList(new LambdaQueryWrapper<ModuleVersionDO>()
                .eq(ModuleVersionDO::getMid, moduleDO.getId()));
        Assertions.assertTrue(CollectionUtils.isEmpty(moduleVersionDOList));

    }

    private Response getResponseDeleteModule(ModuleDO moduleDO) {
        ModuleDeleteCmd moduleDeleteCmd = new ModuleDeleteCmd();
        moduleDeleteCmd.setId(moduleDO.getId());
        return moduleService.deleteModule(moduleDeleteCmd);
    }


    private DependenceDO insertDependence(String moduleId, String projectId) {
        DependenceDO dependenceDO = new DependenceDO();
        dependenceDO.setDependMid(moduleId);
        dependenceDO.setPid(projectId);
        dependenceMapper.insert(dependenceDO);
        return dependenceDO;
    }

    private ModuleVersionDO insertModuleVersion(String moduleId) {
        ModuleVersionDO moduleVersionDO = new ModuleVersionDO();
        moduleVersionDO.setMid(moduleId);
        moduleVersionDO.setVersion("1.0.1");
        moduleVersionMapper.insert(moduleVersionDO);
        return moduleVersionDO;
    }

    private ModuleVersionDO insertModuleVersionTwo(String moduleId) {
        ModuleVersionDO moduleVersionDO = new ModuleVersionDO();
        moduleVersionDO.setMid(moduleId);
        moduleVersionDO.setVersion("1.0.2");
        moduleVersionMapper.insert(moduleVersionDO);
        return moduleVersionDO;
    }

    private ModuleDO insertModule(String projectId) {
        ModuleDO moduleDO = new ModuleDO();
        moduleDO.setName("module");
        moduleDO.setPid(projectId);
        moduleMapper.insert(moduleDO);
        return moduleDO;
    }

    private ProjectDO insertProject(String groupId) {
        ProjectDO projectDO = new ProjectDO();
        projectDO.setGroupId(groupId);
        projectDO.setName("project");
        projectDO.setDescription("description");
        projectMapper.insert(projectDO);
        return projectDO;
    }

    private GroupDO insertGroup() {
        GroupDO groupDO = new GroupDO();
        groupDO.setName("longlong");
        groupDO.setId("111");
        groupMapper.insert(groupDO);
        return groupDO;
    }

}
