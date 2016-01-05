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
        try {
            V val = super.put(key, value);
            return val;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public V remove(K key) {
        lock.lock();
        try {
            V val = super.remove(key);
            return val;
        } finally {
            lock.unlock();
        }
    }
}
