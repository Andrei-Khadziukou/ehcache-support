package com.epam.ehcache.support.cache;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

import java.util.concurrent.locks.ReadWriteLock;

/**
 * Date: 04/18/2016
 *
 * @author Andrei_Khadziukou
 */
public class CommonCache implements org.apache.ibatis.cache.Cache {

    private Cache<Object, Object> cache;

    private String id;

    public CommonCache(String id) {
        this.id = id;
        init();
    }

    private void init() {
        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                .withCache("preConfigured", CacheConfigurationBuilder.newCacheConfigurationBuilder(
                        Object.class, Object.class, ResourcePoolsBuilder.heap(10)))
                .build();
        cacheManager.init();
        cache = cacheManager.getCache("preConfigured", Object.class, Object.class);
    }

    @Override
    public String getId() {
        // TODO find the way how to identify caches
        return "";
    }

    @Override
    public void putObject(Object key, Object value) {
        cache.put(key, value);
    }

    @Override
    public Object getObject(Object key) {
        return cache.get(key);
    }

    @Override
    public Object removeObject(Object key) {
        Object value = cache.get(key);
        cache.remove(key);
        return value;
    }

    @Override
    public void clear() {
        // TODO find the way to implement
    }

    @Override
    public int getSize() {
        // TODO find the way how to get number of records in cache
        return 0;
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return null;
    }
}
