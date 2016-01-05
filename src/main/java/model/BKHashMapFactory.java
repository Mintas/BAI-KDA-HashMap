package model;

import model.map.impls.FineGrainedBKHashMap;
import model.map.impls.GlobalLockBKHashMap;
import model.map.impls.SynchronizedBKHashMap;

public class BKHashMapFactory {
    public static final int DEFAULT_CAPACITY = 16;

    private final int capacity;

    public BKHashMapFactory(int capacity){
        this.capacity = capacity;
    }

    public static BKHashMapFactory init() {
        return new BKHashMapFactory(DEFAULT_CAPACITY);
    }

    public SynchronizedBKHashMap synchronizeed() {
        return new SynchronizedBKHashMap(capacity);
    }

    public GlobalLockBKHashMap globalLock() {
        return new GlobalLockBKHashMap(capacity);
    }

    public FineGrainedBKHashMap fineGrained() {
        return new FineGrainedBKHashMap(capacity);
    }
}
