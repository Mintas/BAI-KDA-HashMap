package model.map.hash;

import model.map.BKEntry;

/**
 * Created by SBT-Kovalev-DA on 05.01.2016.
 */
public class BKHashNode<K,V> implements BKEntry<K,V> {
    final int hash;
    final K key;
    V value;
    BKHashNode<K,V> next;

    public BKHashNode(int hash, K key, V value, BKHashNode<K, V> next) {
        this.hash = hash;
        this.key = key;
        this.value = value;
        this.next = next;
    }


    @Override
    public K getKey() {
        return key;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public V setValue(V value) {
        V old = this.value;
        this.value = value;
        return old;
    }

    public BKHashNode<K, V> getNext() {
        return next;
    }

    @Override
    public int hashCode() {
        int result = key.hashCode();
        result = 31 * result + value.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BKHashNode that = (BKHashNode) o;

        if (!key.equals(that.key)) return false;
        if (!value.equals(that.value)) return false;

        return true;
    }
}
