package com.pm.infrastructure.security;

import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.RSAKey;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wcy
 */
@Component
public class TokenService {
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

    private String getHeaderToken(HttpServletRequest request) {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        return null;
    }
}
