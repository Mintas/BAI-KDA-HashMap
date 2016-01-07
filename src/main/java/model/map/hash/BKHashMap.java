package model.map.hash;

import model.map.BKMap;

public class BKHashMap<K, V> implements BKMap<K, V> {
    private int size;
    private int capacity = 16;
    public BKHashNode<K, V>[] buckets;

    @SuppressWarnings("unchecked")
    public BKHashMap() {
        buckets = new BKHashNode[capacity];
    }

    @SuppressWarnings("unchecked")
    public BKHashMap(int capacity) {
        this.capacity = capacity;
        buckets = new BKHashNode[capacity];
    }

    @Override
    public int size() {
        int theSize = 0;
        for (int i = 0; i < buckets.length; i++) {
            for (BKHashNode node = buckets[i]; node != null; node = node.getNext()) theSize++;
        }
        return theSize;
        //return size;
    }

    @Override
    public boolean isEmpty() {
        return size < 1;
    }

    @Override
    public V put(K key, V value) {
        if (key == null) return null;    //don't store null; check exception?
        return putValue(key, value);
    }

    @Override
    public V get(K key) {
        int bucket = getBucketNumber(key);
        if (buckets[bucket] == null) return null;
        return getValue(key, bucket);
    }

    @Override
    public V remove(K key) {
        if (key == null) return null;
        return removeVal(key);
    }

    private int getBucketNumber(K key) {
        return getBucketNumber(hash(key));
    }

    private int getBucketNumber(int hash) {
        return hash & (capacity - 1);
    }

    static final int hash(Object key) {
        return (key == null) ? 0 : fromCode(key);
    }

    private static int fromCode(Object key) {
        int h = key.hashCode();
        return h ^ (h >>> 16); //pow(2,4); like div(16)
    }

    private V removeVal(K key) {
        int bucket = getBucketNumber(key);
        return removeFromBucket(key, bucket);
    }

    protected V removeFromBucket(K key, int bucket) {
        if (buckets[bucket] == null) {
            return null;
        } else {
            BKHashNode<K, V> previous = null;
            BKHashNode<K, V> current = buckets[bucket];

            while (current != null) { //we have reached last entry node of bucket.
                if (current.key.equals(key)) { //delete first entry node.
                    V value = current.value;
                    if (previous == null) buckets[bucket] = null;
                    else previous.next = current.next;
                    size--;
                    return value;
                }
                previous = current;
                current = current.next;
            }
            return null;
        }
    }

    private V putValue(K key, V value) {
        //calc hash; create new entry
        int hash = hash(key);
        BKHashNode<K, V> newEntry = new BKHashNode<>(hash, key, value, null);
        int bucket = getBucketNumber(hash);
        return putIntoBucket(key, newEntry, bucket);
    }

    //return oldValue or null if there wasn't one
    protected V putIntoBucket(K key, BKHashNode<K, V> newEntry, int bucket) {
        //if emptyList, store entry there.
        if (buckets[bucket] == null) {
            buckets[bucket] = newEntry;
            size++;
            return null;
        } else {
            BKHashNode<K, V> previous = null;
            BKHashNode<K, V> current = buckets[bucket];

            while (current != null) { //while not in the end of bucket.
                if (current.key.equals(key)) {
                    V val = current.value;
                    newEntry.next = current.next;
                    if (previous == null) buckets[bucket] = newEntry;
                    else previous.next = newEntry;
                    return val;
                }
                previous = current;
                current = current.next;
            }
            previous.next = newEntry;
            size++;
            return null;
        }
    }

    private V getValue(K key, int bucket) {
        BKHashNode<K, V> curr = buckets[bucket];
        while (curr != null) {
            if (curr.key.equals(key))
                return curr.value;
            curr = curr.next; //return value corresponding to key.
        }
        return null;   //returns null if key is not found.
    }
}
