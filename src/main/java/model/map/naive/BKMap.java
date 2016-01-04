package model.map.naive;

public interface BKMap<K,V>{
    int size();

    boolean isEmpty();

    V get(K key);

    V put(K key, V value);

    V remove(K key);
}
