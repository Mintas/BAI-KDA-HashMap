package model.map.naive;

import model.map.BKEntry;

public class BKSimpleEntry<K,V> implements BKEntry<K,V> {
    private final K key;
    private V value;

    public BKSimpleEntry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public V setValue(V value) {
        V old = this.value;
        this.value = value;
        return old;
    }
}
