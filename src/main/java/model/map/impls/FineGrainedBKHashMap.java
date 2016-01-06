package model.map.impls;

import model.map.hash.BKHashMap;
import model.map.hash.BKHashNode;

import java.util.concurrent.locks.ReentrantLock;

public class FineGrainedBKHashMap<K, V> extends BKHashMap<K, V> {
    private final ReentrantLock[] bucketLocks;

    public FineGrainedBKHashMap(int capacity) {
        super(capacity);
        this.bucketLocks = new ReentrantLock[capacity];
        for (int i = 0; i<capacity; i++) {
            bucketLocks[i] = new ReentrantLock();
        }
    }

    @Override
    protected V putIntoBucket(K key, V value, BKHashNode<K, V> newEntry, int bucket) {
        ReentrantLock lock = bucketLocks[bucket];
        lock.lock();
        try{
            return super.putIntoBucket(key, value, newEntry, bucket);
        } finally {
            lock.unlock();
        }
    }

    @Override
    protected V removeFromBucket(K key, int bucket) {
        ReentrantLock lock = bucketLocks[bucket];
        lock.lock();
        try{
            return super.removeFromBucket(key, bucket);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int size() {
        int theSize = 0;
        for (int i = 0; i<buckets.length; i++) {
            bucketLocks[i].lock();
            try {
                for (BKHashNode node = buckets[i]; node!=null; node = node.getNext()) theSize++;
            } finally {
                bucketLocks[i].unlock();
            }
        }
        return theSize;
    }
}
