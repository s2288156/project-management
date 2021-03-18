package com.pm.demo;

import com.nimbusds.jose.JOSEObject;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.pm.NoneWebBaseTest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * @author wcy
 */
@Slf4j
public class JwtTest extends NoneWebBaseTest {
    @Autowired
    private RSAKey rsaKey;

    @SneakyThrows
    @Test
    void testJwt() {
        JWSHeader jwsHeader = new JWSHeader.Builder(JWSAlgorithm.RS256)
                .type(JOSEObjectType.JWT)
                .build();
        Payload payload = new Payload("haha");
        JWSSigner signer = new RSASSASigner(rsaKey);

        JWSObject jwsObject = new JWSObject(jwsHeader, payload);
        jwsObject.sign(signer);

        String jwt = jwsObject.serialize();
        log.info("jwt = {}", jwt);

        JWSObject jwsObject1 = JWSObject.parse(jwt);
        RSASSAVerifier verifier = new RSASSAVerifier(rsaKey);
        log.warn("verifier = {}", jwsObject1.verify(verifier));

        String payloadStr = jwsObject1.getPayload().toString();
        log.info("payloadStr = {}", payloadStr);
    }
}
