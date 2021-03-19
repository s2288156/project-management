package com.pm.application.dto.vo;

import com.alibaba.cola.dto.DTO;
import com.alibaba.cola.exception.SysException;
import com.nimbusds.jose.JWSObject;
import com.pm.infrastructure.tool.Payload;
import com.zyzh.common.util.JsonUtils;
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
public class UserVO extends DTO {
    private String id;

    /**
     * 登录账户名
     **/
    private String username;

    /**
     * 头像
     **/
    private String avatar;

    /**
     * 邮箱
     **/
    private String email;

    /**
     * 姓名
     **/
    private String name;

    private Set<String> roles;

    public static UserVO createForToken(String token) {
        JWSObject jwsObject;
        try {
            jwsObject = JWSObject.parse(token);
        } catch (ParseException e) {
            throw new SysException("");
        }
        String userStr = jwsObject.getPayload().toString();
        Payload payload = JsonUtils.fromJson(userStr, Payload.class);

        Set<String> rs = new HashSet<>();
        rs.add("ADMIN");

        UserVO userVO = new UserVO();
        userVO.setRoles(rs);
        userVO.setName(payload.getUid());
        userVO.setAvatar("https://wcy-img.oss-cn-beijing.aliyuncs.com/images/avatar/default_avatar.jpg");
        return userVO;
    }
}
