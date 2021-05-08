package com.pm.application.service;

import com.alibaba.cola.dto.Response;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pm.NoneWebBaseTest;
import com.pm.application.dto.cmd.GroupAddCmd;
import com.pm.application.dto.cmd.ModuleAddCmd;
import com.pm.application.dto.cmd.ProjectAddCmd;
import com.pm.application.dto.cmd.ProjectDependAddCmd;
import com.pm.application.dto.vo.ModuleVO;
import com.pm.application.service.impl.GroupServiceImpl;
import com.pm.application.service.impl.ModuleServiceImpl;
import com.pm.application.service.impl.ProjectServiceImpl;
import com.pm.infrastructure.consts.ErrorCodeEnum;
import com.pm.infrastructure.dataobject.DependenceDO;
import com.pm.infrastructure.dataobject.ProjectDO;
import com.pm.infrastructure.mapper.DependenceMapper;
import com.pm.infrastructure.mapper.ModuleMapper;
import com.pm.infrastructure.mapper.ProjectMapper;
import com.zyzh.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author wcy
 */
@Transactional
@Slf4j
public class GroupServiceTest extends NoneWebBaseTest {
    @Autowired
    private GroupServiceImpl groupService;

    @Autowired
    private ProjectServiceImpl projectService;

    @Autowired
    private ModuleServiceImpl moduleService;

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private ModuleMapper moduleMapper;

    @Autowired
    private DependenceMapper dependenceMapper;

    /**
     * 1. group内有被引用的module，则返回错误码ErrorCodeEnum.HAVE_DEPEND_GROUP_NOT_ALLOW_DELETE
     * 2. group没有被引用的module，删除全部相关数据：t_dependence / t_module_version / t_module / t_project
     */
    @Test
    void testDeleteById() {
        log.warn("新增Group: web , dubbo");
        String webGroupId = addGroupTestData("web");
        String dubboGroupId = addGroupTestData("dubbo");

        log.warn("Group新增project: web(Group) -> webApp(Project);  dubbo(Group) -> dubbo-rpc(Project)");
        String webProjectId = addProjectTestData("webApp", webGroupId);
        String rpcProjectId = addProjectTestData("rpc", dubboGroupId);

        log.warn("Project新增Module: dubbo-rpc(P) -> rpc-core(M)");
        ModuleVO dubboModule = addModuleTestData(rpcProjectId, "rpc-core", "1.0.0");
        ModuleVO webModule = addModuleTestData(webProjectId, "web-dao", "2.0.0");

        log.warn("webApp(P) 依赖 rpc-core(M)");
        addDependencyTestData(webProjectId, dubboModule.getId(), "1.0.0");

        testCaseDeleteGroupFail(dubboGroupId);
        testCaseDeleteGroupSuccess(webGroupId, webProjectId, webModule.getId());
    }

    private String addGroupTestData(String groupName) {
        GroupAddCmd group = new GroupAddCmd();
        group.setName(groupName);
        return groupService.addGroup(group).getData();
    }

    private String addProjectTestData(String projectName, String groupId) {
        ProjectAddCmd projectAdd = new ProjectAddCmd();
        projectAdd.setGroupId(groupId);
        projectAdd.setName(projectName);
        return (String) projectService.addOne(projectAdd).getData();
    }

    private ModuleVO addModuleTestData(String pid, String name, String version) {
        ModuleAddCmd moduleAddCmd = new ModuleAddCmd();
        moduleAddCmd.setName(name);
        moduleAddCmd.setVersion(version);
        moduleAddCmd.setPid(pid);
        return moduleService.addOne(moduleAddCmd).getData();
    }

    private void addDependencyTestData(String pid, String dependMid, String version) {
        ProjectDependAddCmd webAppProjectAdd = new ProjectDependAddCmd();
        webAppProjectAdd.setPid(pid);
        webAppProjectAdd.setDependMid(dependMid);
        webAppProjectAdd.setVersion(version);
        projectService.dependAdd(webAppProjectAdd);
    }

    private void testCaseDeleteGroupSuccess(String webGroupId, String webPid, String webMid) {
        Response webDeleteResponse = groupService.deleteById(webGroupId);
        assertTrue(webDeleteResponse.isSuccess());
        assertNull(projectMapper.selectById(webPid));
        assertNull(moduleMapper.selectById(webMid));
        List<DependenceDO> dependenceMid = dependenceMapper.selectByDependMid(webMid);
        List<DependenceDO> dependencePid = dependenceMapper.selectList(new LambdaQueryWrapper<DependenceDO>().eq(DependenceDO::getPid, webPid));
        assertTrue(CollectionUtils.isEmpty(dependenceMid));
        assertTrue(CollectionUtils.isEmpty(dependencePid));
    }

    private void testCaseDeleteGroupFail(String dubboGroupId) {
        BizException bizException = assertThrows(BizException.class, () -> groupService.deleteById(dubboGroupId));
        assertEquals(ErrorCodeEnum.PROJECT_MODULE_DEPENDENCE_ERROR.getCode(), bizException.getErrCode());
    }

}
