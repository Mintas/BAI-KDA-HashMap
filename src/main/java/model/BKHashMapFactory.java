package model;

import model.map.impls.FineGrainedBKHashMap;
import model.map.impls.GlobalLockBKHashMap;
import model.map.impls.SynchronizedBKHashMap;

public class BKHashMapFactory {
    public static final int DEFAULT_SIZE = 16;
    public static final int DEFAULT_CAPACITY = 16;

    private final int size;
    private final int capacity;

    public BKHashMapFactory(int size, int capacity){
        this.size = size;
        this.capacity = capacity;
    }

    public static BKHashMapFactory init() {
        return new BKHashMapFactory(DEFAULT_SIZE, DEFAULT_CAPACITY);
    }

    public SynchronizedBKHashMap synchronizeed() {
        return new SynchronizedBKHashMap(size, capacity);
    }

    public GlobalLockBKHashMap globalLock() {
        return new GlobalLockBKHashMap(size, capacity);
    }

    public FineGrainedBKHashMap fineGrained() {
        return new FineGrainedBKHashMap(size, capacity);
    }
}
