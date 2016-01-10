package model.map;

/**
 * Created by SBT-Kovalev-DA on 10.01.2016.
 */
public class LatencyMeasuredHashMap<K,V> implements BKMap<K,V> {
    private final BKMap<K,V> map;

    public Long putLat = 0L;
    public Long remLat = 0L;

    public LatencyMeasuredHashMap(BKMap<K,V> map) {
        this.map = map;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public V put(K key, V value) {
        long startTime = System.nanoTime();

        V put = map.put(key, value);

        long entTime = System.nanoTime();
        long totalTime = (entTime - startTime) / 1000000L;
        putLat += totalTime;
        return put;
    }

    @Override
    public V get(K key) {
        return null;
    }

    @Override
    public V remove(K key) {
        long startTime = System.nanoTime();

        V remove = map.remove(key);

        long entTime = System.nanoTime();
        long totalTime = (entTime - startTime) / 1000000L;
        remLat += totalTime;
        return remove;
    }
}
