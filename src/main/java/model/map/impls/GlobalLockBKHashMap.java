package model.map.impls;

import model.map.naive.BKSimpleMap;

public class GlobalLockBKHashMap extends BKSimpleMap {
    public GlobalLockBKHashMap(int size, int capacity){
        super(size, capacity);
    };
}
