package com.mphj.freelancer.utils;

import org.redisson.api.LocalCachedMapOptions;
import org.redisson.api.RLocalCachedMap;

import java.util.concurrent.TimeUnit;

public class Cache {

    private static RLocalCachedMap map;

    public static void init(RLocalCachedMap<String, Object> map) {
        Cache.map = map;
    }

    public static void put(String key, Object value) {
        map.fastPut(key, value);
    }

    public static void putAsync(String key, Object value) {
        map.fastPutAsync(key, value);
    }


    public static boolean exists(String key) {
        return map.containsKey(key);
    }

    public static Object get(String key) {
        return map.get(key);
    }

    public static void remove(String key) {
        map.fastRemove(key);
    }


    public static LocalCachedMapOptions options() {
        return LocalCachedMapOptions.defaults()
                .evictionPolicy(LocalCachedMapOptions.EvictionPolicy.NONE)
                .cacheSize(1000)
                .reconnectionStrategy(LocalCachedMapOptions.ReconnectionStrategy.NONE)
                .syncStrategy(LocalCachedMapOptions.SyncStrategy.INVALIDATE)
                .timeToLive(5, TimeUnit.MINUTES)
                .maxIdle(10, TimeUnit.SECONDS);
    }

}
