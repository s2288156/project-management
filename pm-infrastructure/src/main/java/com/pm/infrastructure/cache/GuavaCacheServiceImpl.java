package com.pm.infrastructure.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author wcy
 */
@Component
public class GuavaCacheServiceImpl<K, V> implements ICacheService<K, V> {

    private Cache<K, V> cache;

    {
        cache = CacheBuilder.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(100, TimeUnit.DAYS)
                .build();
    }

    private Cache<K, V> buildCache() {
        return CacheBuilder.newBuilder().build();
    }

    private Cache<K, V> buildExpCache(long timeout, TimeUnit unit) {
        return CacheBuilder.newBuilder().expireAfterWrite(timeout, unit).build();
    }

    @Override
    public void set(K key, V value) {


    }

    @Override
    public void set(K key, V value, long timeout, TimeUnit unit) {

    }

    @Override
    public V get(K key) {
        return null;
    }

    @Override
    public boolean delete(K key) {
        return false;
    }
}
