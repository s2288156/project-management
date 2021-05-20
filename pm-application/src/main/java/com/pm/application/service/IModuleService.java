package com.pm.application.service;

import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import com.pm.application.dto.cmd.*;
import com.pm.application.dto.query.ModulePageQuery;
import com.pm.application.dto.query.ModuleVersionPageQuery;
import com.pm.application.dto.vo.ModuleVO;
import com.pm.application.dto.vo.ModuleVersionVO;
import com.pm.infrastructure.entity.PageResponse;

/**
 * @author wcy
 */
public interface IModuleService {

    SingleResponse<ModuleVO> addOne(ModuleAddCmd moduleAddCmd);

    PageResponse<ModuleVO> list(ModulePageQuery pageQueryCmd);

    Response addVersion(ModuleVersionAddCmd versionAddCmd);

    PageResponse<ModuleVersionVO> listVersion(ModuleVersionPageQuery versionPageQueryCmd);

    Response updateVersion(ModuleVersionUpdateCmd versionUpdateCmd);

    Response deleteModuleVersion(ModuleVersionDeleteCmd moduleVersionDeleteCmd);

    Response deleteModule(ModuleDeleteCmd moduleDeleteCmd);

    Response moduleUpdateLatestVersion(ModuleUpdateLatestVersionCmd cmd);
}
