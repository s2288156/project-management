package com.pm.application.service;

import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
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
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * 1. group内有被引用的module，则返回错误码ErrorCodeEnum.HAVE_DEPEND_GROUP_NOT_ALLOW_DELETE
     * 2. group没有被引用的module，删除全部相关数据：t_dependence / t_module_version / t_module / t_project
     */
    @Test
    void testDeleteById() {
        log.info("新增Group: web , dubbo");
        GroupAddCmd web = new GroupAddCmd();
        web.setName("web");
        String webGroupId = groupService.addGroup(web).getData();

        GroupAddCmd dubbo = new GroupAddCmd();
        dubbo.setName("dubbo");
        String dubboGroupId = groupService.addGroup(dubbo).getData();

        initDeleteGroupTestData(webGroupId, dubboGroupId);

        testCaseDeleteGroupFail(dubboGroupId);
        testCaseDeleteGroupSuccess(webGroupId);
    }

    private void initDeleteGroupTestData(String webGroupId, String dubboGroupId) {
        log.info("Group新增project: web(Group) -> webApp(Project);  dubbo(Group) -> dubbo-rpc(Project)");
        ProjectAddCmd webApp = new ProjectAddCmd();
        webApp.setGroupId(webGroupId);
        webApp.setName("webApp");
        ProjectAddCmd rpc = new ProjectAddCmd();
        rpc.setGroupId(dubboGroupId);
        rpc.setName("dubbo-rpc");
        SingleResponse<?> webAppAdd = projectService.addOne(webApp);
        SingleResponse<?> rpcAdd = projectService.addOne(rpc);

        log.info("Project新增Module: dubbo-rpc(P) -> rpc-core(M)");
        ModuleAddCmd dubboModuleAddCmd = new ModuleAddCmd();
        dubboModuleAddCmd.setName("rpc-core");
        dubboModuleAddCmd.setVersion("1.0.0");
        dubboModuleAddCmd.setPid((String) rpcAdd.getData());
        ModuleVO dubboModule = moduleService.addOne(dubboModuleAddCmd).getData();

        log.info("webApp(P) 依赖 rpc-core(M)");
        ProjectDependAddCmd webAppProjectAdd = new ProjectDependAddCmd();
        webAppProjectAdd.setPid((String) webAppAdd.getData());
        webAppProjectAdd.setDependMid(dubboModule.getId());
        webAppProjectAdd.setVersion("1.0.0");
        projectService.dependAdd(webAppProjectAdd);
    }

    private void testCaseDeleteGroupSuccess(String webGroupId) {
        Response webDeleteResponse = groupService.deleteById(webGroupId);
        assertTrue(webDeleteResponse.isSuccess());
    }

    private void testCaseDeleteGroupFail(String dubboGroupId) {
        Response dubboDeleteResponse = groupService.deleteById(dubboGroupId);
        assertFalse(dubboDeleteResponse.isSuccess());
        assertEquals(ErrorCodeEnum.HAVE_DEPEND_GROUP_NOT_ALLOW_DELETE.getCode(), dubboDeleteResponse.getErrCode());
    }

}
