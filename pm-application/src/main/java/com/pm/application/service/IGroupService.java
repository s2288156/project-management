package com.pm.application.service;

import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import com.pm.application.dto.cmd.GroupAddCmd;
import com.pm.application.dto.vo.GroupVO;
import com.pm.infrastructure.entity.PageQuery;
import com.pm.infrastructure.entity.PageResponse;

/**
 * @author wcy
 */
public interface IGroupService {

    SingleResponse<String> addGroup(GroupAddCmd addCmd);

    PageResponse<GroupVO> listGroup(PageQuery pageQuery);

    Response deleteById(String id);
}
