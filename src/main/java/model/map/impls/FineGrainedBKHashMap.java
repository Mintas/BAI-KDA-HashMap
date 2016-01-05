package model.map.impls;

import model.map.hash.BKHashMap;

public class FineGrainedBKHashMap<K, V> extends BKHashMap<K, V> {
    public FineGrainedBKHashMap(int capacity) {
        super(capacity);
    }
}
