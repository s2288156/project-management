package com.pm.application.service.impl;

import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pm.application.convertor.RoleConvertor;
import com.pm.application.dto.cmd.RoleAddCmd;
import com.pm.application.dto.cmd.RoleSetResourcesCmd;
import com.pm.application.dto.query.UserRolesQuery;
import com.pm.application.dto.vo.RoleVO;
import com.pm.application.execute.command.RoleAddCmdExe;
import com.pm.application.execute.command.RoleDeleteCmdExe;
import com.pm.application.execute.command.RoleSetResourcesCmdExe;
import com.pm.application.service.IRoleService;
import com.pm.infrastructure.dataobject.RoleDO;
import com.pm.infrastructure.entity.PageQuery;
import com.pm.infrastructure.entity.PageResponse;
import com.pm.infrastructure.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @author wcy
 */
@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleAddCmdExe roleAddCmdExe;

    @Autowired
    private RoleSetResourcesCmdExe roleSetResourcesCmdExe;

    @Autowired
    private RoleDeleteCmdExe roleDeleteCmdExe;

    @Override
    public PageResponse<RoleVO> pageRole(PageQuery pageQuery) {
        Page<RoleDO> page = pageQuery.createPage();
        roleMapper.selectPage(page, new LambdaQueryWrapper<>());

        List<RoleVO> roleVOList = RoleConvertor.INSTANCE.roleDo2RoleVo(page.getRecords());

        return PageResponse.of(roleVOList, page.getTotal());
    }

    @Override
    public SingleResponse<String> addRole(RoleAddCmd roleAddCmd) {
        return SingleResponse.of(roleAddCmdExe.execute(roleAddCmd));
    }

    @Override
    public Response deleteRole(String id) {
        roleDeleteCmdExe.execute(id);
        return Response.buildSuccess();
    }

    @Override
    public MultiResponse<RoleVO> listRoleByUid(UserRolesQuery userRolesQuery) {
        Set<RoleDO> roleDOs = roleMapper.listRoleDoByUid(userRolesQuery.getUid());
        List<RoleVO> roleVOList = RoleConvertor.INSTANCE.roleDo2RoleVo(roleDOs);
        return MultiResponse.of(roleVOList);
    }

    @Override
    public Response setResources(RoleSetResourcesCmd setResourcesCmd) {
        roleSetResourcesCmdExe.execute(setResourcesCmd);
        return Response.buildSuccess();
    }
}
