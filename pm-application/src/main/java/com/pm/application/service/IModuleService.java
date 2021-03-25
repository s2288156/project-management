package com.pm.application.service;

import com.alibaba.cola.dto.SingleResponse;
import com.pm.application.dto.cmd.ModuleAddCmd;
import com.pm.application.dto.vo.ModuleVO;

/**
 * @author wcy
 */
public interface IModuleService {

    SingleResponse<ModuleVO> addOne(ModuleAddCmd moduleAddCmd);
}
