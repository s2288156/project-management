package com.pm.application.dto.vo;

import com.nimbusds.jose.JWSObject;
import com.pm.infrastructure.tool.JsonUtils;
import com.pm.infrastructure.security.JwtPayload;
import com.zyzh.exception.SysException;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
        String userStr = jwsObject.getPayload().toString();
        JwtPayload jwtPayload = JsonUtils.fromJson(userStr, JwtPayload.class);

        Set<String> rs = new HashSet<>();
        // TODO: 2021/4/8 暂时写死
        rs.add("ADMIN");

        UserDetailVO userDetailVO = new UserDetailVO();
        userDetailVO.setRoles(rs);
        userDetailVO.setName(jwtPayload.getUid());
        userDetailVO.setAvatar("https://wcy-img.oss-cn-beijing.aliyuncs.com/images/avatar/default_avatar.jpg");
        return userDetailVO;
    }
}
