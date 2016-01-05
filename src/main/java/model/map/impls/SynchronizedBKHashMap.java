package model.map.impls;

import model.map.naive.BKSimpleMap;

public class SynchronizedBKHashMap extends BKSimpleMap {
    public SynchronizedBKHashMap(int capacity){
        super(capacity);
    };
}
