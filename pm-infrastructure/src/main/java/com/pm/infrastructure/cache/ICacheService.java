package com.pm.infrastructure.cache;

import java.util.concurrent.TimeUnit;

/**
 * @author wcy
 */
public interface ICacheService<K, V> {

    void set(K key, V value);

    void set(K key, V value, long timeout, TimeUnit unit);

    V get(K key);

    void delete(K key);
}
