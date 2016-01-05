package model.map.impls;

import model.map.hash.BKHashMap;

public class SynchronizedBKHashMap extends BKHashMap {
    public SynchronizedBKHashMap(int capacity){
        super(capacity);
    };
}
