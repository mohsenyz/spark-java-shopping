package com.mphj.freelancer.mocks;

import org.redisson.api.RFuture;
import org.redisson.api.RLocalCachedMap;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.mapreduce.RMapReduce;
import org.redisson.client.codec.Codec;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class MockedRLocalCache implements RLocalCachedMap {
    @Override
    public void preloadCache() {

    }

    @Override
    public RFuture<Void> clearLocalCacheAsync() {
        return null;
    }

    @Override
    public void clearLocalCache() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void loadAll(boolean replaceExistingValues, int parallelism) {

    }

    @Override
    public void loadAll(Set keys, boolean replaceExistingValues, int parallelism) {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean containsKey(Object o) {
        return false;
    }

    @Override
    public boolean containsValue(Object o) {
        return false;
    }

    @Override
    public Object get(Object key) {
        return null;
    }

    @Override
    public Object put(Object key, Object value) {
        return null;
    }

    @Override
    public Object putIfAbsent(Object key, Object value) {
        return null;
    }

    @Override
    public RReadWriteLock getReadWriteLock(Object key) {
        return null;
    }

    @Override
    public RLock getLock(Object key) {
        return null;
    }

    @Override
    public int valueSize(Object key) {
        return 0;
    }

    @Override
    public Object addAndGet(Object key, Number delta) {
        return null;
    }

    @Override
    public Object remove(Object key) {
        return null;
    }

    @Override
    public Object replace(Object key, Object value) {
        return null;
    }

    @Override
    public boolean replace(Object key, Object oldValue, Object newValue) {
        return false;
    }

    @Override
    public boolean remove(Object key, Object value) {
        return false;
    }

    @Override
    public void putAll(Map map) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Map getAll(Set keys) {
        return null;
    }

    @Override
    public long fastRemove(Object[] keys) {
        return 0;
    }

    @Override
    public boolean fastPut(Object key, Object value) {
        return false;
    }

    @Override
    public boolean fastPutIfAbsent(Object key, Object value) {
        return false;
    }

    @Override
    public Set readAllKeySet() {
        return null;
    }

    @Override
    public Collection readAllValues() {
        return null;
    }

    @Override
    public Set<Entry> readAllEntrySet() {
        return null;
    }

    @Override
    public Map readAllMap() {
        return null;
    }

    @Override
    public Set keySet() {
        return null;
    }

    @Override
    public Set keySet(String pattern) {
        return null;
    }

    @Override
    public Collection values() {
        return null;
    }

    @Override
    public Collection values(String keyPattern) {
        return null;
    }

    @Override
    public Set<Entry> entrySet() {
        return null;
    }

    @Override
    public Set<Entry> entrySet(String keyPattern) {
        return null;
    }

    @Override
    public RMapReduce mapReduce() {
        return null;
    }

    @Override
    public boolean expire(long timeToLive, TimeUnit timeUnit) {
        return false;
    }

    @Override
    public boolean expireAt(long timestamp) {
        return false;
    }

    @Override
    public boolean expireAt(Date timestamp) {
        return false;
    }

    @Override
    public boolean clearExpire() {
        return false;
    }

    @Override
    public long remainTimeToLive() {
        return 0;
    }

    @Override
    public RFuture<Void> loadAllAsync(boolean replaceExistingValues, int parallelism) {
        return null;
    }

    @Override
    public RFuture<Void> loadAllAsync(Set keys, boolean replaceExistingValues, int parallelism) {
        return null;
    }

    @Override
    public RFuture<Integer> valueSizeAsync(Object key) {
        return null;
    }

    @Override
    public RFuture<Map> getAllAsync(Set keys) {
        return null;
    }

    @Override
    public RFuture<Void> putAllAsync(Map map) {
        return null;
    }

    @Override
    public RFuture addAndGetAsync(Object key, Number value) {
        return null;
    }

    @Override
    public RFuture<Boolean> containsValueAsync(Object value) {
        return null;
    }

    @Override
    public RFuture<Boolean> containsKeyAsync(Object key) {
        return null;
    }

    @Override
    public RFuture<Integer> sizeAsync() {
        return null;
    }

    @Override
    public RFuture<Long> fastRemoveAsync(Object[] keys) {
        return null;
    }

    @Override
    public RFuture<Boolean> fastPutAsync(Object key, Object value) {
        return null;
    }

    @Override
    public RFuture<Boolean> fastPutIfAbsentAsync(Object key, Object value) {
        return null;
    }

    @Override
    public RFuture<Set> readAllKeySetAsync() {
        return null;
    }

    @Override
    public RFuture<Collection> readAllValuesAsync() {
        return null;
    }

    @Override
    public RFuture<Set<Entry>> readAllEntrySetAsync() {
        return null;
    }

    @Override
    public RFuture<Map> readAllMapAsync() {
        return null;
    }

    @Override
    public RFuture getAsync(Object key) {
        return null;
    }

    @Override
    public RFuture putAsync(Object key, Object value) {
        return null;
    }

    @Override
    public RFuture removeAsync(Object key) {
        return null;
    }

    @Override
    public RFuture replaceAsync(Object key, Object value) {
        return null;
    }

    @Override
    public RFuture<Boolean> replaceAsync(Object key, Object oldValue, Object newValue) {
        return null;
    }

    @Override
    public RFuture<Boolean> removeAsync(Object key, Object value) {
        return null;
    }

    @Override
    public RFuture putIfAbsentAsync(Object key, Object value) {
        return null;
    }

    @Override
    public RFuture<Boolean> expireAsync(long timeToLive, TimeUnit timeUnit) {
        return null;
    }

    @Override
    public RFuture<Boolean> expireAtAsync(Date timestamp) {
        return null;
    }

    @Override
    public RFuture<Boolean> expireAtAsync(long timestamp) {
        return null;
    }

    @Override
    public RFuture<Boolean> clearExpireAsync() {
        return null;
    }

    @Override
    public RFuture<Long> remainTimeToLiveAsync() {
        return null;
    }

    @Override
    public boolean touch() {
        return false;
    }

    @Override
    public void migrate(String host, int port, int database) {

    }

    @Override
    public boolean move(int database) {
        return false;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean delete() {
        return false;
    }

    @Override
    public boolean unlink() {
        return false;
    }

    @Override
    public void rename(String newName) {

    }

    @Override
    public boolean renamenx(String newName) {
        return false;
    }

    @Override
    public boolean isExists() {
        return false;
    }

    @Override
    public Codec getCodec() {
        return null;
    }

    @Override
    public RFuture<Boolean> touchAsync() {
        return null;
    }

    @Override
    public RFuture<Void> migrateAsync(String host, int port, int database) {
        return null;
    }

    @Override
    public RFuture<Boolean> moveAsync(int database) {
        return null;
    }

    @Override
    public RFuture<Boolean> deleteAsync() {
        return null;
    }

    @Override
    public RFuture<Boolean> unlinkAsync() {
        return null;
    }

    @Override
    public RFuture<Void> renameAsync(String newName) {
        return null;
    }

    @Override
    public RFuture<Boolean> renamenxAsync(String newName) {
        return null;
    }

    @Override
    public RFuture<Boolean> isExistsAsync() {
        return null;
    }
}
