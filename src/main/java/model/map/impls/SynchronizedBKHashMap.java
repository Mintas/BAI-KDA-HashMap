package model.map.impls;

import model.map.hash.BKHashMap;

public class SynchronizedBKHashMap<K, V> extends BKHashMap<K, V> {
    public SynchronizedBKHashMap(int capacity) {
        super(capacity);
    }

    ;
}
