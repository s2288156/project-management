package com.pm.application.service.impl;

import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pm.application.convertor.UserConvertor;
import com.pm.application.dto.cmd.UserSetRolesCmd;
import com.pm.application.execute.command.UserLoginCmdExe;
import com.pm.application.execute.command.UserRegisterCmdExe;
import com.pm.application.dto.cmd.UserLoginCmd;
import com.pm.application.dto.cmd.UserRegisterCmd;
import com.pm.application.dto.vo.LoginUserVO;
import com.pm.application.dto.vo.UserVO;
import com.pm.application.service.IUserService;
import com.pm.infrastructure.dataobject.UserDO;
import com.pm.infrastructure.dataobject.UserRoleDO;
import com.pm.infrastructure.entity.PageQuery;
import com.pm.infrastructure.entity.PageResponse;
import com.pm.infrastructure.mapper.UserMapper;
import com.pm.infrastructure.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wcy
 */
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserRegisterCmdExe userRegisterCmdExe;

    @Autowired
    private UserLoginCmdExe userLoginCmdExe;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public Response userRegister(UserRegisterCmd userRegisterCmd) {
        return userRegisterCmdExe.execute(userRegisterCmd);
    }

    @Override
    public SingleResponse<LoginUserVO> userLogin(UserLoginCmd loginCmd) {
        return userLoginCmdExe.execute(loginCmd);
    }

    @Override
    public PageResponse<UserVO> listUser(PageQuery pageQuery) {
        Page<UserDO> page = pageQuery.createPage();
        userMapper.selectPage(page, new LambdaQueryWrapper<>());
        List<UserVO> userVoList = page.getRecords()
                .stream()
                .map(UserConvertor.INSTANCE::userDo2UserVo)
                .collect(Collectors.toList());
        return PageResponse.of(userVoList, page.getTotal());
    }

    @Override
    public Response userSetRoles(UserSetRolesCmd userSetRolesCmd) {
        List<UserRoleDO> userRoleDOList = userSetRolesCmd.convert2UserRoleDo();
        userRoleMapper.insertBatch(userRoleDOList);
        return Response.buildSuccess();
    }
}
