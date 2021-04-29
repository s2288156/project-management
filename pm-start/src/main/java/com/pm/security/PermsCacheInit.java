package com.pm.security;

import com.pm.infrastructure.mapper.ResourceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author wcy
 */
@Component
public class PermsCacheInit implements CommandLineRunner {

    @Autowired
    private ResourceMapper resourceMapper;

    @Override
    public void run(String... args) throws Exception {

    }
}
