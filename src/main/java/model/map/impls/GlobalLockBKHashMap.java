package model.map.impls;

import model.map.hash.BKHashMap;

public class GlobalLockBKHashMap extends BKHashMap {
    public GlobalLockBKHashMap(int capacity){
        super(capacity);
    };
}
