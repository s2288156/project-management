package com.pm.infrastructure.tool;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author wcy
 * @date 2019/7/18 11:40
 */
@Slf4j
public class JsonUtils {
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String toJson(Object bean) {
        try {
            return objectMapper.writeValueAsString(bean);
        } catch (Exception e) {
            log.error("json对象转换异常, {}", bean, e);
            throw new IllegalStateException("json转换异常");
        }
    }

    public static <T> T fromJson(String json, Class<T> valueType) {
        try {
            return objectMapper.readValue(json, valueType);
        } catch (Exception e) {
            log.error("error json: {}", json, e);
            throw new IllegalStateException("json转换异常");
        }
    }

    public static <T> T fromJson(String json, TypeReference<T> typeReference) {
        try {
            return objectMapper.readValue(json, typeReference);
        } catch (Exception e) {
            log.error("error json: {}", json, e);
            throw new IllegalStateException("json转换异常");
        }
    }

    public static <T> List<T> jsonToList(String json, Class<T> beanType) {
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, beanType);
        try {
            return objectMapper.readValue(json, javaType);
        } catch (Exception e) {
            log.error("error json: {}", json, e);
            throw new IllegalStateException("json转换异常");
        }
    }

    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

}
