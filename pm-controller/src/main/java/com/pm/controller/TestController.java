package com.pm.controller;

import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import com.pm.infrastructure.cache.ICacheService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author wcy
 */
@Slf4j
@RequestMapping("/test")
@RestController
public class TestController {

    @Autowired
    private ICacheService<String, Set<String>> guavaCacheService;

    @Autowired
    private WebApplicationContext applicationContext;

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

    @GetMapping("/controller")
    public String controller() {
        RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = mapping.getHandlerMethods();
        Set<String> urls = new HashSet<>();
        handlerMethods.forEach((k, v) -> {
            Set<RequestMethod> methods = k.getMethodsCondition().getMethods();
            PatternsRequestCondition patternsCondition = k.getPatternsCondition();
            if (CollectionUtils.isEmpty(methods)) {
                log.warn("requestMethod is empty: {}, patterns = {}", methods, patternsCondition);
                return;
            }
            String url = StringUtils.substringBetween(methods.toString(), "[", "]") + ":/pm" + StringUtils.substringBetween(patternsCondition.toString(), "[", "]");
            urls.add(url);
        });
        log.warn("{}", urls);
        return "ok";
    }

}
