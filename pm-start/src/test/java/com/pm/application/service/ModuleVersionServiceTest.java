package com.pm.application.service;

import com.alibaba.cola.dto.Response;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pm.NoneWebBaseTest;
import com.pm.infrastructure.consts.ErrorCodeEnum;
import com.pm.application.dto.cmd.ModuleVersionDeleteCmd;
import com.pm.application.service.impl.ModuleServiceImpl;
import com.pm.infrastructure.dataobject.*;
import com.pm.infrastructure.mapper.*;
import com.pm.infrastructure.tool.JsonUtils;
import com.zyzh.exception.BizException;
import com.zyzh.pm.domain.project.DependModuleInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Administrator
 * @ClassName ModuleVersionServiceTest
 * @date 2021/4/27 0027 11:53
 **/

public class ModuleVersionServiceTest extends NoneWebBaseTest {

    @Autowired
    private ModuleServiceImpl moduleServiceImpl;

    @Autowired
    private GroupMapper groupMapper;

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private ModuleMapper moduleMapper;

    @Autowired
    private ModuleVersionMapper moduleVersionMapper;

    @Autowired
    private DependenceMapper dependenceMapper;

    @BeforeEach
    public void insertModuleVersion() {
        addGroup();
        addProject();
        addModule();
    }

    /**
     * 模块版本未被引用,但为最新版本
     * 最新版本为1.0.0
     */
    @Transactional
    @Test
    void deleteModelVersionOne() {
        //添加版本为1.0.0的模块
        ModuleVersionDO moduleVersionDO = addModuleVersion("1.0.0");
        //删除模块版本
        ModuleVersionDeleteCmd moduleVersionDeleteCmd = deployModuleVersionDeleteCmd(moduleVersionDO);
        BizException bizException = Assertions.assertThrows(BizException.class, () -> moduleServiceImpl.deleteModuleVersion(moduleVersionDeleteCmd));
        assertEquals(bizException.getErrCode(), ErrorCodeEnum.LATEST_MODULE_VERSION_NOT_ALLOW_DELETE.getCode());

        ModuleVersionDO mvd = moduleVersionMapper.selectById(moduleVersionDO.getId());
        Assertions.assertNotNull(mvd);
        assertEquals(mvd.getId(),moduleVersionDO.getId());
    }

    /**
     * 模块版本被引用,不是最新版本
     * 最新版本为1.0.0
     */
    @Transactional
    @Test
    void deleteModelVersionTwo() {
        //添加版本为0.0.1的模块
        ModuleVersionDO moduleVersionDO = addModuleVersion("0.0.1");
        //添加项目对模块版本的依赖
        DependenceDO dd = addproductDepend(moduleVersionDO);
        //删除版本为0.0.1的模块
        ModuleVersionDeleteCmd moduleVersionDeleteCmd = deployModuleVersionDeleteCmd(moduleVersionDO);
        BizException bizException = assertThrows(BizException.class, () -> moduleServiceImpl.deleteModuleVersion(moduleVersionDeleteCmd));
        assertEquals(bizException.getErrCode(), ErrorCodeEnum.MODULE_DEPEND_NOT_ALLOW_DEL.getCode());

        ModuleVersionDO mvd = moduleVersionMapper.selectById(moduleVersionDO.getId());
        Assertions.assertNotNull(mvd);
        ModuleDO moduleDO = moduleMapper.selectById(moduleVersionDO.getMid());
        Assertions.assertNotEquals(moduleVersionDO.getVersion(), moduleDO.getLatestVersion());

        DependenceDO dependenceDO = selectByPidAndDependMid(moduleVersionDeleteCmd);
        Assertions.assertNotNull(dependenceDO);
        assertEquals(dependenceDO.getId(), dd.getId());
    }

    /**
     * 模块版本被引用,最新版本
     * 最新版本为1.0.0
     */
    @Transactional
    @Test
    void deleteModelVersionThree() {
        //添加版本为1.0.0的模块
        ModuleVersionDO moduleVersionDO = addModuleVersion("1.0.0");
        //添加项目对模块版本的依赖
        DependenceDO dd = addproductDepend(moduleVersionDO);
        //删除版本为1.0.0的模块
        ModuleVersionDeleteCmd moduleVersionDeleteCmd = deployModuleVersionDeleteCmd(moduleVersionDO);
        BizException bizException = Assertions.assertThrows(BizException.class,()->moduleServiceImpl.deleteModuleVersion(moduleVersionDeleteCmd));
        assertEquals(bizException.getErrCode(),ErrorCodeEnum.LATEST_MODULE_VERSION_NOT_ALLOW_DELETE.getCode());

        ModuleVersionDO mvd = moduleVersionMapper.selectById(moduleVersionDO.getId());
        Assertions.assertNotNull(mvd);
        assertEquals(mvd.getId(),moduleVersionDO.getId());

        ModuleDO moduleDO = moduleMapper.selectById(moduleVersionDO.getMid());
        assertEquals(moduleVersionDO.getVersion(), moduleDO.getLatestVersion());

        DependenceDO dependenceDO = selectByPidAndDependMid(moduleVersionDeleteCmd);
        Assertions.assertNotNull(dependenceDO);
        assertEquals(dependenceDO.getId(),dd.getId());
    }

    /**
     * 模块版本未被引用,不是新版本
     * 最新版本1.0.0
     */
    @Transactional
    @Test
    void deleteModelVersioFour() {
        //添加版本为0.0.1的模块
        ModuleVersionDO moduleVersionDO = addModuleVersion("0.0.1");
        //删除版本为0.0.1的模块
        ModuleVersionDeleteCmd moduleVersionDeleteCmd = deployModuleVersionDeleteCmd(moduleVersionDO);
        Response response = moduleServiceImpl.deleteModuleVersion(moduleVersionDeleteCmd);
        Assertions.assertTrue(response.isSuccess());

        ModuleVersionDO mvd = moduleVersionMapper.selectById(moduleVersionDO.getId());
        Assertions.assertNull(mvd);

        ModuleDO moduleDO = moduleMapper.selectById(moduleVersionDO.getMid());
        Assertions.assertNotEquals(moduleVersionDO.getVersion(), moduleDO.getLatestVersion());
    }


    public GroupDO addGroup() {
        //添加组
        GroupDO groupDO = new GroupDO();
        groupDO.setName("组");
        groupMapper.insert(groupDO);
        return groupDO;
    }

    public ProjectDO addProject() {
        ProjectDO projectDO = new ProjectDO();
        projectDO.setGroupId(addGroup().getId());
        projectDO.setName("项目");
        projectDO.setDescription("项目描述");
        projectMapper.insert(projectDO);
        return projectDO;
    }

    public ModuleDO addModule() {
        ModuleDO moduleDO = new ModuleDO();
        moduleDO.setUpdateTime(LocalDateTime.now());
        moduleDO.setProjectName(addProject().getName());
        moduleDO.setLatestVersion("1.0.0");
        moduleDO.setOpeningUp(1);
        moduleDO.setDescription("就是这个模块");
        moduleDO.setCreateTime(LocalDateTime.now());
        moduleDO.setPid(addProject().getId());
        moduleDO.setName("模块总");
        moduleMapper.insert(moduleDO);
        return moduleDO;
    }

    public ModuleVersionDO addModuleVersion(String version) {
        //添加模块版本
        ModuleDO moduleDO = addModule();
        ModuleVersionDO moduleVersionDO = new ModuleVersionDO();
        moduleVersionDO.setUpdateTime(LocalDateTime.now());
        moduleVersionDO.setCreateTime(LocalDateTime.now());
        moduleVersionDO.setDescription("就是一个版本");
        moduleVersionDO.setVersion(version);
        moduleVersionDO.setMid(moduleDO.getId());
        moduleVersionMapper.insert(moduleVersionDO);
        return moduleVersionDO;
    }

    public DependenceDO addproductDepend(ModuleVersionDO moduleVersionDO) {
        ModuleDO moduleDO = moduleMapper.selectById(moduleVersionDO.getMid());
        DependenceDO dependenceDO = new DependenceDO();
        dependenceDO.setUpdateTime(LocalDateTime.now());
        dependenceDO.setPid(moduleDO.getPid());

        Optional<ProjectDO> projectDO = projectMapper.selectByMid(moduleDO.getId());
        DependModuleInfo dependModuleInfo = new DependModuleInfo();
        dependModuleInfo.setModuleName(moduleDO.getName());
        dependModuleInfo.setProjectName(projectDO.get().getName());
        dependModuleInfo.setVersion(moduleVersionDO.getVersion());

        dependenceDO.setDependModuleInfo(JsonUtils.toJson(dependModuleInfo));
        dependenceDO.setDependMid(moduleVersionDO.getMid());
        dependenceDO.setCreateTime(LocalDateTime.now());
        dependenceMapper.insert(dependenceDO);
        return dependenceDO;
    }

    public ModuleVersionDeleteCmd deployModuleVersionDeleteCmd(ModuleVersionDO moduleVersionDO) {
        ModuleVersionDeleteCmd moduleVersionDeleteCmd = new ModuleVersionDeleteCmd();
        moduleVersionDeleteCmd.setId(moduleVersionDO.getId());
        moduleVersionDeleteCmd.setMid(moduleVersionDO.getMid());
        moduleVersionDeleteCmd.setVersion(moduleVersionDO.getVersion());
        return moduleVersionDeleteCmd;
    }

    public DependenceDO selectByPidAndDependMid(ModuleVersionDeleteCmd moduleVersionDeleteCmd) {
        DependenceDO dependenceDO = new DependenceDO();
        List<DependenceDO> dependenceDOList = dependenceMapper.selectList(new LambdaQueryWrapper<DependenceDO>()
                .eq(DependenceDO::getDependMid, moduleVersionDeleteCmd.getMid()));
        if (CollectionUtils.isEmpty(dependenceDOList)) {
            return dependenceDO;
        }

        for (DependenceDO d : dependenceDOList) {
            DependModuleInfo dependModuleInfo = JsonUtils.fromJson(d.getDependModuleInfo(), DependModuleInfo.class);
            if (dependModuleInfo.getVersion().equals(moduleVersionDeleteCmd.getVersion())) {
                dependenceDO = d;
            }
        }
        return dependenceDO;
    }
}
