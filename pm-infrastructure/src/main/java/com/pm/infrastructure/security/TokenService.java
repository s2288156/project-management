package com.pm.infrastructure.security;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.RSAKey;
import com.pm.infrastructure.cache.ICacheService;
import com.pm.infrastructure.consts.ErrorCodeEnum;
import com.pm.infrastructure.mapper.RoleMapper;
import com.pm.infrastructure.tool.JsonUtils;
import com.zyzh.exception.BizException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Collection;
import java.util.Set;

/**
 * @author wcy
 */
@Slf4j
@Component
public class TokenService {
    public static final String JWT_TOKEN_PREFIX = "Bearer ";
    // 管理员拥有全部权限
    public static final String SUPER_ADMIN = "ADMIN";
    @Autowired
    private RSAKey rsaKey;

    @Autowired
    private ICacheService<String, Set<String>> guavaCacheService;

    @Autowired
    private RoleMapper roleMapper;

    public boolean canAccess(HttpServletRequest request, Authentication authentication) {
        // 请求用户携带的授权信息role
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        Set<String> urlPerms = queryRequestPerms(request);

        return validAccess(authorities, urlPerms);
    }

    /**
     * 查询请求权限数据
     *
     * @param request HttpServletRequest
     */
    private Set<String> queryRequestPerms(HttpServletRequest request) {
        String permKey = getPermKey(request);
        Set<String> urlPerms = guavaCacheService.get(permKey);
        if (CollectionUtils.isEmpty(urlPerms)) {
            log.warn("permKey  = {}, 未命中cache", permKey);
            urlPerms = roleMapper.listRoleByUrl(permKey);
            guavaCacheService.set(permKey, urlPerms);
        }
        return urlPerms;
    }

    /**
     * 鉴权，判断用户是否拥有url的role权限。超级管理员ADMIN，拥有全部权限
     */
    private boolean validAccess(Collection<? extends GrantedAuthority> authorities, Set<String> urlPerms) {
        for (GrantedAuthority authority : authorities) {
            if (StringUtils.equals(authority.getAuthority(), SUPER_ADMIN) || urlPerms.contains(authority.getAuthority())) {
                return true;
            }
        }
        return false;
    }

    private String getPermKey(HttpServletRequest request) {
        return request.getMethod() + request.getRequestURI();
    }

    @SneakyThrows
    public String sign(JwtPayload jwtPayload) {
        String payloadJson = JsonUtils.toJson(jwtPayload);
        JWSHeader jwsHeader = new JWSHeader.Builder(JWSAlgorithm.RS256)
                .type(JOSEObjectType.JWT)
                .build();
        Payload payload = new Payload(StringUtils.defaultString(payloadJson, "{}"));
        JWSSigner signer = new RSASSASigner(rsaKey);
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);
        jwsObject.sign(signer);
        return jwsObject.serialize();
    }

    /**
     * 解析jwt，获取Payload内容
     */
    public JwtPayload verifierAndGetPayload(HttpServletRequest request) {
        String realToken = getHeaderToken(request);
        if (StringUtils.isBlank(realToken)) {
            return null;
        }
        JWSObject jwsObject = null;
        try {
            jwsObject = JWSObject.parse(realToken);
        } catch (ParseException e) {
            log.warn("jwt parse error:{}", e.getMessage());
            if (log.isErrorEnabled()) {
                log.error("jwt parse errorStack:", e);
            }
            throw new BizException(ErrorCodeEnum.JWT_PARSE_ERROR);
        }
        String payloadStr = jwsObject.getPayload().toString();
        verifierToken(jwsObject);
        return JsonUtils.fromJson(payloadStr, JwtPayload.class);
    }

    private void verifierToken(JWSObject jwsObject) {
        try {
            RSASSAVerifier verifier = new RSASSAVerifier(rsaKey);
            boolean verify = jwsObject.verify(verifier);
            if (!verify) {
                log.error("令牌验签不通过: {}", verifier);
                throw new BizException(ErrorCodeEnum.JWT_VERIFIER_ERROR);
            }
        } catch (JOSEException e) {
            log.warn("jwt verifier error:{}", e.getMessage());
            if (log.isErrorEnabled()) {
                log.error("jwt verifier errorStack:", e);
            }
            throw new BizException(ErrorCodeEnum.JWT_VERIFIER_ERROR);
        }
    }

    private String getHeaderToken(HttpServletRequest request) {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isNotBlank(authorization)) {
            return authorization.replace(JWT_TOKEN_PREFIX, "");
        }
        return authorization;
    }
}
