package model.map;

public interface BKMap<K,V>{
    int size();

    boolean isEmpty();

    V put(K key, V value);

    V get(K key);

    V remove(K key);
}
