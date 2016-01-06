package model.map.libimpls;

import model.map.naive.BKMap;

import java.util.Map;

/**
 * use this class for default JDK hashMap implementations
 */
public class JavaHashMap<K,V> implements BKMap<K,V> {
    private Map<K,V> javaHashMap;

    public JavaHashMap(Map javaHashMap) {
        this.javaHashMap = javaHashMap;
    }

    @Override
    public int size() {
        return javaHashMap.size();
    }

    @Override
    public boolean isEmpty() {
        return javaHashMap.isEmpty();
    }

    @Override
    public V put(K key, V value) {
        return javaHashMap.put(key, value);
    }

    @Override
    public V get(K key) {
        return javaHashMap.get(key);
    }

    @Override
    public V remove(K key) {
        return javaHashMap.remove(key);
    }
}
