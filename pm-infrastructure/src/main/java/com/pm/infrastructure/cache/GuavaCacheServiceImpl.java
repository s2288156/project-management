package com.pm.infrastructure.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Component;

/**
 * @author wcy
 */
@Component
public class GuavaCacheServiceImpl<K, V> implements ICacheService<K, V> {

    private static final Cache<Object, Object> CACHE = CacheBuilder.newBuilder().build();

    @Override
    public void set(K key, V value) {
        CACHE.put(key, value);
    }

    @Override
    public V get(K key) {
        return (V) CACHE.getIfPresent(key);
    }

    @Override
    public void delete(K key) {
        CACHE.invalidate(key);
    }
}
