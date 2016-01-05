package model.map.impls;

import model.map.hash.BKHashMap;

public class SynchronizedBKHashMap<K, V> extends BKHashMap<K, V> {
    public SynchronizedBKHashMap(int capacity) {
        super(capacity);
    }

    @Override
    public V put(K key, V value) {
        synchronized (this) {
            return super.put(key, value);
        }
    }

    @Override
    public V remove(K key) {
        synchronized (this) {
            return super.remove(key);
        }
    }
}
