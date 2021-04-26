package com.pm.application.command;

import com.alibaba.cola.dto.SingleResponse;
import com.pm.application.consts.ErrorCodeEnum;
import com.pm.application.dto.cmd.UserLoginCmd;
import com.pm.infrastructure.dataobject.UserDO;
import com.pm.infrastructure.mapper.UserMapper;
import com.pm.infrastructure.security.TokenService;
import com.pm.infrastructure.tool.JsonUtils;
import com.pm.infrastructure.tool.JwtPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
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
    private TokenService tokenService;

    public SingleResponse execute(UserLoginCmd userLoginCmd) {
        Optional<UserDO> optional = userMapper.selectForUsername(userLoginCmd.getUsername());
        if (!optional.isPresent()) {
            return SingleResponse.buildFailure(ErrorCodeEnum.USERNAME_NOT_FOUND.getErrorCode(), ErrorCodeEnum.USERNAME_NOT_FOUND.getErrorMsg());
        }
        if (!validPwd(userLoginCmd.getPassword(), optional.get().getPassword())) {
            return SingleResponse.buildFailure(ErrorCodeEnum.PASSWORD_FAIL.getErrorCode(), ErrorCodeEnum.PASSWORD_FAIL.getErrorMsg());
        }
        JwtPayload jwtPayload = new JwtPayload();
        jwtPayload.setUid(optional.get().getId());
        // TODO: 2021/4/26 iss暂时写死，exp暂定默认30天
        jwtPayload.setIss("zyzh");
        LocalDateTime expDateTime = LocalDateTime.now().plusDays(30);
        jwtPayload.setExp(expDateTime.toEpochSecond(ZoneOffset.of("+8")));

        return SingleResponse.of(signJwt(jwtPayload));
    }

    private boolean validPwd(String inputPwd, String dbPwd) {
        return passwordEncoder.matches(inputPwd, dbPwd);
    }

    private String signJwt(JwtPayload jwtPayload) {
        return tokenService.sign(JsonUtils.toJson(jwtPayload));
    }
}
