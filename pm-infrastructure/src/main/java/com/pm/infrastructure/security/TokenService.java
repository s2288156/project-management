package com.pm.infrastructure.security;

import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.RSAKey;
import com.pm.infrastructure.tool.JsonUtils;
import com.pm.infrastructure.tool.JwtPayload;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;

/**
 * @author wcy
 */
@Component
public class TokenService {
    public static final String JWT_TOKEN_PREFIX = "Bearer ";
    @Autowired
    private RSAKey rsaKey;

    @SneakyThrows
    public String sign(String payloadJson) {
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
    public JwtPayload parsePayload(HttpServletRequest request) throws ParseException {
        String realToken = getHeaderToken(request);
        JWSObject jwsObject = JWSObject.parse(realToken);
        String payloadStr = jwsObject.getPayload().toString();
        return JsonUtils.fromJson(payloadStr, JwtPayload.class);
    }

    private String getHeaderToken(HttpServletRequest request) {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        return authorization.replace(JWT_TOKEN_PREFIX, "");
    }
}
