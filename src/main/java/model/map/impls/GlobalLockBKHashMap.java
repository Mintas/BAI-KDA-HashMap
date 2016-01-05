package model.map.impls;

import model.map.hash.BKHashMap;

import java.util.concurrent.locks.ReentrantLock;

public class GlobalLockBKHashMap<K, V> extends BKHashMap<K, V> {
    private ReentrantLock lock;

    public GlobalLockBKHashMap(int capacity) {
        super(capacity);
        lock = new ReentrantLock();
    }

    @Override
    public V put(K key, V value) {
        lock.lock();
        V val = super.put(key, value);
        lock.unlock();
        return val;
    }

    @Override
    public V remove(K key) {
        lock.lock();
        V val = super.remove(key);
        lock.unlock();
        return val;
    }
}
