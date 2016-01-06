package model.map.naive;

import model.map.BKMap;

import java.util.Arrays;

public class BKSimpleMap<K,V> implements BKMap<K,V> {
    private int size;
    private int capacity;
    private BKSimpleEntry<K, V>[] values;

    public BKSimpleMap(){
        this.capacity = 16;
        this.values = new BKSimpleEntry[capacity];
    }

    public BKSimpleMap(int capacity) {
        this.capacity = capacity;
        this.values = new BKSimpleEntry[capacity];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size < 1;
    }

    @Override
    public V put(K key, V value) {
        boolean insert = true;
        for (int i = 0; i < size; i++) {
            if (values[i].getKey().equals(key)) {
                values[i].setValue(value);
                insert = false;
            }
        }
        if (insert) {
            ensureCapacity();
            values[size++] = new BKSimpleEntry<>(key, value);
        }
        return value;
    }

    @Override
    public V get(K key) {
        for (int i = 0; i < size; i++) {
            if (values[i] != null) {
                if (values[i].getKey().equals(key)) {
                    return values[i].getValue();
                }
            }
        }
        return null;
    }

    @Override
    public V remove(K key) {
        V value = null;
        for (int i = 0; i < size; i++) {
            if (values[i]!=null && values[i].getKey().equals(key)) {
                value = values[i].getValue();
                values[i] = null;
                size--;
                condenseArray(i);
            }
        }
        return value;
    }

    private void ensureCapacity() {
        if (size == values.length) {
            int newSize = values.length * 2;
            values = Arrays.copyOf(values, newSize);
        }
    }

    private void condenseArray(int start) {
        for (int i = start; i < size; i++) {
            values[i] = values[i + 1];
        }
    }
}
