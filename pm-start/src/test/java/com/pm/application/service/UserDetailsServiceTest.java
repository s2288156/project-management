package com.pm.application.service;

import com.pm.NoneWebBaseTest;
import com.pm.infrastructure.security.UserDetailsServiceImpl;
import com.pm.infrastructure.security.SecurityUser;
import com.pm.infrastructure.tool.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author wcy
 */
@Slf4j
public class UserDetailsServiceTest extends NoneWebBaseTest {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Test
    void testSerialLoginUser() {
        SecurityUser admin = (SecurityUser) userDetailsService.loadUserByUsername("admin");
        String adminJson = JsonUtils.toJson(admin);
        log.info("json =========== {}", adminJson);
    }

    @Test
    void testSelectByUsername() {
        SecurityUser admin = (SecurityUser) userDetailsService.loadUserByUsername("admin");
        log.info("{}", admin);
        assertNotNull(admin);
        Set<SimpleGrantedAuthority> authorities = admin.getAuthorities();
        assertAll(
                () -> assertNotNull(admin.getUsername()),
                () -> assertNotNull(admin.getPassword()),
                () -> assertTrue(authorities.contains(new SimpleGrantedAuthority("ADMIN")))
        );
    }
}
