package com.pm.application.service;

import com.alibaba.cola.dto.SingleResponse;
import com.pm.application.dto.cmd.ProjectAddCmd;

/**
 * @author wcy
 */
public interface IProjectService {

    SingleResponse<?> addOne(ProjectAddCmd addCmd);

}
