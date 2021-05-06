package com.pm.application.service;

import com.alibaba.cola.dto.Response;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pm.NoneWebBaseTest;
import com.pm.infrastructure.consts.ErrorCodeEnum;
import com.pm.application.dto.cmd.ProjectDeleteCmd;
import com.pm.infrastructure.dataobject.DependenceDO;
import com.pm.infrastructure.dataobject.ModuleDO;
import com.pm.infrastructure.dataobject.ModuleVersionDO;
import com.pm.infrastructure.dataobject.ProjectDO;
import com.pm.infrastructure.mapper.DependenceMapper;
import com.pm.infrastructure.mapper.ModuleMapper;
import com.pm.infrastructure.mapper.ModuleVersionMapper;
import com.pm.infrastructure.mapper.ProjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author ytl
 */
@Slf4j
@Transactional
public class ProjectDeleteTest extends NoneWebBaseTest {
    @Autowired
    private ModuleMapper moduleMapper;
    @Autowired
    private ProjectMapper projectMapper;
    @Autowired
    private ModuleVersionMapper moduleVersionMapper;
    @Autowired
    private DependenceMapper dependenceMapper;
    @Autowired
    private IProjectService projectService;


    // 项目模块被依赖
    // 项目删除失败 依赖删除失败  模块删除失败 模块版本删除失败
    @Test
    void testFailDeleteProject() {
        ProjectDO projectDO = insertProject("222");
        ModuleDO moduleDO = insertModule(projectDO.getId());
        ModuleVersionDO moduleVersionDO = insertModuleVersion(moduleDO.getId());

        DependenceDO dependenceDO = insertDependence(moduleDO.getId(), projectDO.getId());

        List<ModuleDO> moduleDOListDeleteBefore = moduleMapper.selectList(new LambdaQueryWrapper<ModuleDO>()
                .eq(ModuleDO::getPid, projectDO.getId()));

        List<ModuleVersionDO> moduleVersionDOListDeleteBefore = moduleVersionMapper.selectList(new LambdaQueryWrapper<ModuleVersionDO>()
                .eq(ModuleVersionDO::getMid, moduleDO.getId()));

        Response response = getResponseDeleteProject(projectDO);
        Assertions.assertFalse(response.isSuccess());
        assertEquals(ErrorCodeEnum.PROJECT_MODULE_DEPENDENCE_ERROR.getErrorCode(), response.getErrCode());
        log.info(">>>>>>>>>>>>>>>errorMessage:{}<<<<<<<<<<<<<<<<<<", response.getErrMessage());

        ProjectDO deleteProjectDO = projectMapper.selectById(projectDO.getId());
        Assertions.assertFalse(Objects.isNull(deleteProjectDO));

        List<DependenceDO> dependenceDOList = dependenceMapper.selectList(new LambdaQueryWrapper<DependenceDO>()
                .eq(DependenceDO::getPid, projectDO.getId()));
        assertFalse(CollectionUtils.isEmpty(dependenceDOList));

        List<ModuleDO> moduleDOListDeleteAfter = moduleMapper.selectList(new LambdaQueryWrapper<ModuleDO>()
                .eq(ModuleDO::getPid, projectDO.getId()));
        assertEquals(moduleDOListDeleteBefore.size(), moduleDOListDeleteAfter.size());

        List<ModuleVersionDO> moduleVersionDOListDeleteAfter = moduleVersionMapper.selectList(new LambdaQueryWrapper<ModuleVersionDO>()
                .eq(ModuleVersionDO::getMid, moduleDO.getId()));
        assertEquals(moduleVersionDOListDeleteBefore.size(), moduleVersionDOListDeleteAfter.size());
    }

    // 项目模块未被依赖
    // 项目删除成功 依赖删除成功 模块删除成功 模块版本删除成功
    @Test
    void testSuccessDeleteProject() {
        ProjectDO projectDO = insertProject("222");
        ModuleDO moduleDO = insertModule(projectDO.getId());
        ModuleVersionDO moduleVersionDO = insertModuleVersion(moduleDO.getId());

        Response response = getResponseDeleteProject(projectDO);
        Assertions.assertTrue(response.isSuccess());

        ProjectDO selectById = projectMapper.selectById(projectDO.getId());
        assertTrue(Objects.isNull(selectById));

        List<DependenceDO> dependenceDOList = dependenceMapper.selectList(new LambdaQueryWrapper<DependenceDO>()
                .eq(DependenceDO::getPid, projectDO.getId()));
        assertTrue(CollectionUtils.isEmpty(dependenceDOList));

        List<ModuleDO> moduleDOList = moduleMapper.selectList(new LambdaQueryWrapper<ModuleDO>()
                .eq(ModuleDO::getPid, projectDO.getId()));
        assertTrue(CollectionUtils.isEmpty(moduleDOList));

        List<ModuleVersionDO> moduleVersionDOList = moduleVersionMapper.selectList(new LambdaQueryWrapper<ModuleVersionDO>()
                .eq(ModuleVersionDO::getMid, moduleDO.getId()));
        assertTrue(CollectionUtils.isEmpty(moduleVersionDOList));
    }

    private Response getResponseDeleteProject(ProjectDO projectDO) {
        ProjectDeleteCmd projectDeleteCmd = new ProjectDeleteCmd();
        projectDeleteCmd.setId(projectDO.getId());
        return projectService.deleteProject(projectDeleteCmd);
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

}
