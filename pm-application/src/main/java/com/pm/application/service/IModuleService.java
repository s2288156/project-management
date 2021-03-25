package com.pm.application.service;

import com.alibaba.cola.dto.SingleResponse;
import com.pm.application.dto.cmd.ModuleAddCmd;

/**
 * @author wcy
 */
public interface IModuleService {

    SingleResponse<?> addOne(ModuleAddCmd moduleAddCmd);
}
