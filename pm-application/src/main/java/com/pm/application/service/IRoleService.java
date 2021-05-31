package com.pm.application.service;

import com.pm.application.dto.vo.RoleVO;
import com.pm.infrastructure.entity.PageQuery;
import com.pm.infrastructure.entity.PageResponse;

/**
 * @author wcy
 */
public interface IRoleService {

    PageResponse<RoleVO> pageRole(PageQuery pageQuery);
}
