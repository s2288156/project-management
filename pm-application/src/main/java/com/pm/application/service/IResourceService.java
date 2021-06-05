package com.pm.application.service;

import com.pm.application.dto.cmd.ResourceAddCmd;
import com.pm.application.dto.vo.ResourceVO;
import com.pm.infrastructure.entity.PageQuery;
import com.pm.infrastructure.entity.PageResponse;

/**
 * @author wcy
 */
public interface IResourceService {
    String addResource(ResourceAddCmd resourceAddCmd);

    PageResponse<ResourceVO> pageResource(PageQuery pageQuery);

    void deleteById(String id);
}
