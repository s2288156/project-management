package com.pm.application.service;

import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import com.pm.application.dto.PidQuery;
import com.pm.application.dto.cmd.ProjectAddCmd;
import com.pm.application.dto.cmd.ProjectDeleteCmd;
import com.pm.application.dto.cmd.ProjectDependAddCmd;
import com.pm.application.dto.query.ProjectPageQuery;
import com.pm.application.dto.vo.DependModuleVO;
import com.pm.application.dto.vo.ProjectVO;
import com.pm.infrastructure.entity.PageResponse;

/**
 * @author wcy
 */
public interface IProjectService {

    SingleResponse<?> addOne(ProjectAddCmd addCmd);

    PageResponse<ProjectVO> listProjects(ProjectPageQuery projectPageQuery);

    Response dependAdd(ProjectDependAddCmd dependAddCmd);

    PageResponse<DependModuleVO> listDepend(PidQuery pid);

    Response deleteDepend(String id);

    Response deleteProject(ProjectDeleteCmd cmd);

}
