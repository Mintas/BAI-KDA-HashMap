package model.map.impls;

import model.map.naive.BKSimpleMap;

public class GlobalLockBKHashMap extends BKSimpleMap {
    public GlobalLockBKHashMap(int capacity){
        super(capacity);
    };
}
