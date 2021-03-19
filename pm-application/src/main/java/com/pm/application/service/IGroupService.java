package com.pm.application.service;

import com.alibaba.cola.dto.SingleResponse;
import com.pm.application.dto.cmd.GroupAddCmd;

/**
 * @author wcy
 */
public interface IGroupService {

    SingleResponse<?> addGroup(GroupAddCmd addCmd);
}
