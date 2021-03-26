package com.pm.application.service;

import com.alibaba.cola.dto.SingleResponse;
import com.pm.application.dto.GroupId;
import com.pm.application.dto.cmd.ProjectAddCmd;
import com.pm.application.dto.vo.ProjectVO;
import com.pm.infrastructure.entity.PageResponse;

/**
 * @author wcy
 */
public interface IProjectService {

    SingleResponse<?> addOne(ProjectAddCmd addCmd);

    PageResponse<ProjectVO> listProjects(GroupId groupId);
}
