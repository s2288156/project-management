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
import com.pm.application.service.IRoleService;
import com.pm.infrastructure.consts.ErrorCodeEnum;
import com.pm.infrastructure.dataobject.RoleDO;
import com.pm.infrastructure.dataobject.RoleResourceDO;
import com.pm.infrastructure.dataobject.UserRoleDO;
import com.pm.infrastructure.entity.PageQuery;
import com.pm.infrastructure.entity.PageResponse;
import com.pm.infrastructure.mapper.RoleMapper;
import com.pm.infrastructure.mapper.RoleResourceMapper;
import com.pm.infrastructure.mapper.UserRoleMapper;
import com.zyzh.exception.BizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author wcy
 */
@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RoleResourceMapper roleResourceMapper;

    @Autowired
    private RoleAddCmdExe roleAddCmdExe;

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
        return SingleResponse.of(roleAddCmdExe.execute(roleAddCmd));
    }

    @Override
    public Response deleteRole(String id) {
        Integer userRoleCount = userRoleMapper.selectCount(new LambdaQueryWrapper<UserRoleDO>().eq(UserRoleDO::getRoleId, id));
        Integer roleResourceCount = roleResourceMapper.selectCount(new LambdaQueryWrapper<RoleResourceDO>().eq(RoleResourceDO::getRoleId, id));
        if (userRoleCount > 0 || roleResourceCount > 0) {
            throw new BizException(ErrorCodeEnum.ROLE_HAS_USED_NOT_ALLOW_DELETE);
        }
        roleMapper.deleteById(id);
        return Response.buildSuccess();
    }

    @Override
    public MultiResponse<RoleVO> listRoleByUid(UserRolesQuery userRolesQuery) {
        Set<RoleDO> roleDOs = roleMapper.listRoleDoByUid(userRolesQuery.getUid());
        List<RoleVO> roleVOList = roleDOs.stream()
                .map(RoleConvertor.INSTANCE::roleDo2RoleVo)
                .collect(Collectors.toList());
        return MultiResponse.of(roleVOList);
    }

    @Override
    public Response setResources(RoleSetResourcesCmd setResourcesCmd) {

        return null;
    }
}
