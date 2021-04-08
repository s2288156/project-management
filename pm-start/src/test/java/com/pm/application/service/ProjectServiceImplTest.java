package com.pm.application.service;

import com.alibaba.cola.dto.Response;
import com.pm.NoneWebBaseTest;
import com.pm.application.consts.ErrorCodeEnum;
import com.pm.application.dto.cmd.ProjectDependAddCmd;
import com.pm.application.service.impl.ProjectServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wcy
 */
class ProjectServiceImplTest extends NoneWebBaseTest {

    @Autowired
    private ProjectServiceImpl projectService;

    @Transactional
    @Test
    void testDependAdd() {
        ProjectDependAddCmd projectDependAddCmd = new ProjectDependAddCmd();
        projectDependAddCmd.setDependMid("1");
        projectDependAddCmd.setPid("22");
        projectDependAddCmd.setVersion("1.3.2");
        projectDependAddCmd.setDescription("nihao,hahaha!!");

        Response response = projectService.dependAdd(projectDependAddCmd);

        Assertions.assertTrue(response.isSuccess());

        response = projectService.dependAdd(projectDependAddCmd);
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals(ErrorCodeEnum.RE_DEPEND_NOT_ALLOW.getErrorCode(), response.getErrCode());
    }
}