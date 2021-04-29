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
import java.util.HashSet;
import java.util.Set;

/**
 * @author wcy
 */
@Slf4j
@Component
public class TokenService {
    public static final String JWT_TOKEN_PREFIX = "Bearer ";
    @Autowired
    private RSAKey rsaKey;

    @Autowired
    private ICacheService<String, Set<String>> guavaCacheService;

    public boolean canAccess(HttpServletRequest request, Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Set<String> urlPerms = guavaCacheService.get(getPermKey(request));
        return CollectionUtils.containsAny(urlPerms, authorities);
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
