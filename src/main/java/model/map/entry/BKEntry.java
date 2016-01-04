package model.map.entry;

public interface BKEntry<K,V> {
    public K getKey();

    public V getValue();

    public void setValue(V value);
}
