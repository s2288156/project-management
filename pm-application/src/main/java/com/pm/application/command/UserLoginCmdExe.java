package com.pm.application.command;

import com.alibaba.cola.dto.SingleResponse;
import com.pm.application.dto.cmd.UserLoginCmd;
import com.pm.application.dto.vo.LoginUserVO;
import com.pm.infrastructure.security.SecurityUser;
import com.pm.infrastructure.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * @author wcy
 */
@Component
public class UserLoginCmdExe {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public SingleResponse<LoginUserVO> execute(UserLoginCmd userLoginCmd) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginCmd.getUsername(), userLoginCmd.getPassword()));
        SecurityUser securityUser = (SecurityUser) authenticate.getPrincipal();

        LoginUserVO loginUserVO = new LoginUserVO();
        String jwt = tokenService.sign(securityUser.generalPayload());
        loginUserVO.setToken(jwt);

        return SingleResponse.of(loginUserVO);
    }

}
