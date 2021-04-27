package com.pm.application.service;

import com.pm.NoneWebBaseTest;
import com.pm.application.service.impl.UserDetailsServiceImpl;
import com.pm.infrastructure.security.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
    void testSelectByUsername() {
        LoginUser admin = (LoginUser) userDetailsService.loadUserByUsername("admin");
        assertNotNull(admin);
        Set<String> roles = admin.getRoles();
        assertTrue(roles.contains("ADMIN"));
        assertAll(
                () -> assertNotNull(admin.getUsername()),
                () -> assertNotNull(admin.getPassword()),
                () -> assertTrue(roles.contains("ADMIN"))
        );
        log.info("{}", admin);
    }
}
