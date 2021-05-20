package com.pm.application.dto.vo;

import com.nimbusds.jose.JWSObject;
import com.pm.infrastructure.tool.JsonUtils;
import com.pm.infrastructure.security.JwtPayload;
import com.zyzh.exception.SysException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author wcy
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserDetailVO extends UserVO {

    private Set<String> roles;

    public static UserDetailVO createForToken(String token) {
        JWSObject jwsObject;
        try {
            jwsObject = JWSObject.parse(token);
        } catch (ParseException e) {
            throw new SysException(e.getMessage());
        }
        String payloadStr = jwsObject.getPayload().toString();
        JwtPayload jwtPayload = JsonUtils.fromJson(payloadStr, JwtPayload.class);

        UserDetailVO userDetailVO = new UserDetailVO();
        BeanUtils.copyProperties(jwtPayload, userDetailVO);
        return userDetailVO;
    }
}
