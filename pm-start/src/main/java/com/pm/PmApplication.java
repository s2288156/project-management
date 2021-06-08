package com.pm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wcy
 */
@MapperScan("com.pm.infrastructure.mapper")
@SpringBootApplication
public class PmApplication {
    public static void main(String[] args) {
        SpringApplication.run(PmApplication.class, args);
    }
}
