package model;

import model.map.BKMap;
import model.map.impls.FineGrainedBKHashMap;
import model.map.impls.GlobalLockBKHashMap;
import model.map.impls.SynchronizedBKHashMap;
import model.map.libimpls.JavaHashMap;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Collections.synchronizedMap;

public class BKHashMapFactory {
    public static final int DEFAULT_CAPACITY = 16;

    private final int capacity;

    public BKHashMapFactory(int capacity){
        this.capacity = capacity;
    }

    public static BKHashMapFactory init() {
        return new BKHashMapFactory(DEFAULT_CAPACITY);
    }

    public BKMap synchronizeed() {
        return new SynchronizedBKHashMap<>(capacity);
    }

    public BKMap globalLock() {
        return new GlobalLockBKHashMap<>(capacity);
    }

    public BKMap fineGrained() {
        return new FineGrainedBKHashMap<>(capacity);
    }

    public BKMap javaConcurrent() {
        return new JavaHashMap<>(new ConcurrentHashMap<>(capacity));
    }

    public BKMap javaSynchronized() {
        return new JavaHashMap<>(synchronizedMap(new HashMap<>(capacity)));
    }
}
