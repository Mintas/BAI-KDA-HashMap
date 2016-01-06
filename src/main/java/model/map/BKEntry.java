package model.map;

public interface BKEntry<K,V> {
    public K getKey();

    public V getValue();

    public V setValue(V value);
}
