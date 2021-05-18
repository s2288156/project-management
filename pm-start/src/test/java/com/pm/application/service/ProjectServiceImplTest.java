package com.pm.application.service;

import com.alibaba.cola.dto.Response;
import com.pm.NoneWebBaseTest;
import com.pm.application.dto.PidQuery;
import com.pm.application.dto.cmd.ProjectDependAddCmd;
import com.pm.application.dto.vo.DependModuleVO;
import com.pm.application.service.impl.ProjectServiceImpl;
import com.pm.infrastructure.consts.ErrorCodeEnum;
import com.pm.infrastructure.dataobject.ModuleDO;
import com.pm.infrastructure.dataobject.ProjectDO;
import com.pm.infrastructure.entity.PageResponse;
import com.pm.infrastructure.mapper.ModuleMapper;
import com.pm.infrastructure.mapper.ProjectMapper;
import com.zyzh.exception.BizException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wcy
 */
class ProjectServiceImplTest extends NoneWebBaseTest {

    @Autowired
    private ProjectServiceImpl projectService;

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private ModuleMapper moduleMapper;

    private String pid;
    private String mid;

    @BeforeEach
    void setUp() {
        ProjectDO projectDO = new ProjectDO();
        projectDO.setName("gagaga");
        projectDO.setGroupId("123");
        projectMapper.insert(projectDO);
        pid = projectDO.getId();

        ModuleDO moduleDO = new ModuleDO();
        moduleDO.setPid(pid);
        moduleDO.setName("moduleGGG");
        moduleDO.setLatestVersion("1.2.3");
        moduleMapper.insert(moduleDO);
        mid = moduleDO.getId();
    }

    @Transactional
    @Test
    void testDependAdd() {
        ProjectDependAddCmd projectDependAddCmd = assembleDependAddCmd();

        Response response = projectService.dependAdd(projectDependAddCmd);
        Assertions.assertTrue(response.isSuccess());

        BizException bizException = Assertions.assertThrows(BizException.class, () -> projectService.dependAdd(projectDependAddCmd));
        Assertions.assertEquals(ErrorCodeEnum.RE_DEPEND_NOT_ALLOW.getErrorCode(), bizException.getErrCode());
    }

    private ProjectDependAddCmd assembleDependAddCmd() {
        ProjectDependAddCmd projectDependAddCmd = new ProjectDependAddCmd();
        projectDependAddCmd.setDependMid(mid);
        projectDependAddCmd.setPid(pid);
        projectDependAddCmd.setVersion("1.3.2");
        return projectDependAddCmd;
    }

    @Transactional
    @Test
    void testDependList() {
        ProjectDependAddCmd projectDependAddCmd = assembleDependAddCmd();
        projectService.dependAdd(projectDependAddCmd);

        PidQuery pidQuery = new PidQuery();
        pidQuery.setPid(pid);

        PageResponse<DependModuleVO> response = projectService.listDepend(pidQuery);
        Assertions.assertTrue(response.isSuccess());

    }
}