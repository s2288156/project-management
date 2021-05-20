package com.pm.controller;

import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import com.pm.infrastructure.cache.ICacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

/**
 * @author wcy
 */
@RequestMapping("/test")
@RestController
public class TestController {

    @Autowired
    private ICacheService<String, Set<String>> guavaCacheService;

    @GetMapping("/cache/get")
    public SingleResponse<?> get(String key) {
        return SingleResponse.of(guavaCacheService.get(key));
    }

    @GetMapping("/cache/set")
    public Response set(String key, String value) {
        Set<String> setValue = new HashSet<>();
        setValue.add(value);
        setValue.add(value + "-test");
        guavaCacheService.set(key, setValue);
        return Response.buildSuccess();
    }
}
