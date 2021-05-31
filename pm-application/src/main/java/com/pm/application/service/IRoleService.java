package com.pm.application.service;

import com.alibaba.cola.dto.SingleResponse;
import com.pm.application.dto.cmd.RoleAddCmd;
import com.pm.application.dto.vo.RoleVO;
import com.pm.infrastructure.entity.PageQuery;
import com.pm.infrastructure.entity.PageResponse;

/**
 * @author wcy
 */
public interface IRoleService {

    PageResponse<RoleVO> pageRole(PageQuery pageQuery);

    SingleResponse<String> addRole(RoleAddCmd roleAddCmd);
}
