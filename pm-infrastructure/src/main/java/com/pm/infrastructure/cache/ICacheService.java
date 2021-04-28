package com.pm.infrastructure.cache;

/**
 * @author wcy
 */
public interface ICacheService<K, V> {

    void set(K key, V value);

    V get(K key);

    void delete(K key);
}
