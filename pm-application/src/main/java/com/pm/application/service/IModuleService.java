package com.pm.application.service;

import com.alibaba.cola.dto.SingleResponse;
import com.pm.application.dto.cmd.ModuleAddCmd;
import com.pm.application.dto.cmd.ModulePageQueryCmd;
import com.pm.application.dto.vo.ModuleVO;
import com.pm.infrastructure.entity.PageResponse;

/**
 * @author wcy
 */
public interface IModuleService {

    SingleResponse<ModuleVO> addOne(ModuleAddCmd moduleAddCmd);

    PageResponse<ModuleVO> list(ModulePageQueryCmd pageQueryCmd);
}
