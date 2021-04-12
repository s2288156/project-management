package com.pm.application.command;

import com.alibaba.cola.dto.SingleResponse;
import com.pm.application.consts.ErrorCodeEnum;
import com.pm.application.dto.cmd.UserLoginCmd;
import com.pm.infrastructure.dataobject.UserDO;
import com.pm.infrastructure.mapper.UserMapper;
import com.pm.infrastructure.tool.JsonUtils;
import com.pm.infrastructure.tool.JwtUtil;
import com.pm.infrastructure.tool.Payload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author wcy
 */
@Component
public class UserLoginCmdExe {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public SingleResponse execute(UserLoginCmd userLoginCmd) {
        Optional<UserDO> optional = userMapper.selectForUsername(userLoginCmd.getUsername());
        if (!optional.isPresent()) {
            return SingleResponse.buildFailure(ErrorCodeEnum.USERNAME_NOT_FOUND.getErrorCode(), ErrorCodeEnum.USERNAME_NOT_FOUND.getErrorMsg());
        }
        if (!validPwd(userLoginCmd.getPassword(), optional.get().getPassword())) {
            return SingleResponse.buildFailure(ErrorCodeEnum.PASSWORD_FAIL.getErrorCode(), ErrorCodeEnum.PASSWORD_FAIL.getErrorMsg());
        }
        Payload payload = new Payload();
        payload.setUid(optional.get().getId());

        return SingleResponse.of(signJwt(payload));
    }

    private boolean validPwd(String inputPwd, String dbPwd) {
        return passwordEncoder.matches(inputPwd, dbPwd);
    }

    private String signJwt(Payload payload) {
        return jwtUtil.sign(JsonUtils.toJson(payload));
    }
}
