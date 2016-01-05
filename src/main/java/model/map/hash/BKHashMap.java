package model.map.hash;

import model.map.naive.BKMap;

//todo : implement hashMap
public class BKHashMap<K,V> implements BKMap<K,V>{
    private int size;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size < 1;
    }

    @Override
    public V get(K key) {
        //getNode by its hashCode and key
        return null;
    }

    @Override
    public V put(K key, V value) {
        //into bucket by its hash to the end of list; with resizing
        return null;
    }

    @Override
    public V remove(K key) {
        //with resizing
        return null;
    }



    static final int hash(Object key) {
        return (key == null) ? 0 : fromCode(key);
    }

    private static int fromCode(Object key) {
        int h = key.hashCode();
        return h ^ (h >>> 16); //pow(2,4); like div(16)
    }
}
