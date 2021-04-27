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
import com.pm.infrastructure.consts.ErrorCodeEnum;
import com.pm.infrastructure.tool.JsonUtils;
import com.zyzh.exception.BizException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;

/**
 * @author wcy
 */
@Slf4j
@Component
public class TokenService {
    public static final String JWT_TOKEN_PREFIX = "Bearer ";
    @Autowired
    private RSAKey rsaKey;

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
    public JwtPayload verifierAndGetPayload(HttpServletRequest request) throws ParseException {
        String realToken = getHeaderToken(request);
        if (StringUtils.isBlank(realToken)) {
            return null;
        }
        JWSObject jwsObject = JWSObject.parse(realToken);

        verifierToken(jwsObject);

        String payloadStr = jwsObject.getPayload().toString();
        return JsonUtils.fromJson(payloadStr, JwtPayload.class);
    }

    private void verifierToken(JWSObject jwsObject) {
        try {
            RSASSAVerifier verifier = new RSASSAVerifier(rsaKey);
            boolean verify = jwsObject.verify(verifier);
            if (!verify) {
                log.error("令牌延签不通过: {}", verifier);
                throw new BizException(ErrorCodeEnum.JWT_VERIFIER_ERROR);
            }
        } catch (JOSEException e) {
            log.error("jwt verifier error:", e);
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
