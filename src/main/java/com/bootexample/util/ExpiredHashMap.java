package com.bootexample.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static java.util.Collections.unmodifiableCollection;
import static java.util.Collections.unmodifiableSet;

/**
 * Created by antosha4e on 20.05.16.
 */
public class ExpiredHashMap<K, V> implements Map<K, V> {
    private final HashMap<K, V> store = new HashMap<>();
    private final HashMap<K, Long> timestamps = new HashMap<>();
    private final long timeToLive;

    public ExpiredHashMap(TimeUnit ttlUnit, long ttlValue) {
        this.timeToLive = ttlUnit.toNanos(ttlValue);
    }

    @Override
    public V get(Object key) {
        V value = this.store.get(key);

        if (value != null && expired(key)) {
            store.remove(key);
            timestamps.remove(key);

            return null;
        } else {
            return value;
        }
    }

    private boolean expired(Object key) {
        return (System.nanoTime() - timestamps.get(key)) > this.timeToLive;
    }

    @Override
    public V put(K key, V value) {
        timestamps.put(key, System.nanoTime());
        return store.put(key, value);
    }

    @Override
    public int size() {
        return store.size();
    }

    @Override
    public boolean isEmpty() {
        return store.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return store.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return store.containsValue(value);
    }

    @Override
    public V remove(Object key) {
        timestamps.remove(key);
        return store.remove(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (Map.Entry<? extends K, ? extends V> e : m.entrySet()) {
            this.put(e.getKey(), e.getValue());
        }
    }

    @Override
    public void clear() {
        timestamps.clear();
        store.clear();
    }

    @Override
    public Set<K> keySet() {
        clearExpired();
        return unmodifiableSet(store.keySet());
    }

    @Override
    public Collection<V> values() {
        clearExpired();
        return unmodifiableCollection(store.values());
    }

    @Override
    public Set<java.util.Map.Entry<K, V>> entrySet() {
        clearExpired();
        return unmodifiableSet(store.entrySet());
    }

    private void clearExpired() {
        for (K k : store.keySet()) {
            this.get(k);
        }
    }
}