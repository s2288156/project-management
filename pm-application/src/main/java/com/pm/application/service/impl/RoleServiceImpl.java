package com.pm.application.service.impl;

import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pm.application.convertor.RoleConvertor;
import com.pm.application.dto.cmd.RoleAddCmd;
import com.pm.application.dto.vo.RoleVO;
import com.pm.application.service.IRoleService;
import com.pm.infrastructure.dataobject.RoleDO;
import com.pm.infrastructure.entity.PageQuery;
import com.pm.infrastructure.entity.PageResponse;
import com.pm.infrastructure.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wcy
 */
@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public PageResponse<RoleVO> pageRole(PageQuery pageQuery) {
        Page<RoleDO> page = pageQuery.createPage();
        roleMapper.selectPage(page, new LambdaQueryWrapper<>());

        List<RoleVO> roleVOList = page.getRecords()
                .stream()
                .map(RoleConvertor.INSTANCE::roleDo2RoleVo)
                .collect(Collectors.toList());

        return PageResponse.of(roleVOList, page.getTotal());
    }

    @Override
    public SingleResponse<String> addRole(RoleAddCmd roleAddCmd) {
        RoleDO roleDO = RoleConvertor.INSTANCE.roleAddCmd2RoleDo(roleAddCmd);
        roleMapper.insert(roleDO);
        return SingleResponse.of(roleDO.getId());
    }
}
