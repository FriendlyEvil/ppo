import lombok.Data;

import java.util.HashMap;
import java.util.Map;

public abstract class LRUCache<K, V> implements Cache<K, V> {
    protected final Map<K, Node<K, V>> cache = new HashMap<>();
    protected Node<K, V> head;
    protected Node<K, V> tail;
    protected final int capacity;

    protected LRUCache(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException();
        }
        this.capacity = capacity;
    }

    protected abstract V doGet(K key);

    protected abstract V doPut(K key, V value);

    protected abstract V doDelete(K key);

    protected abstract int getSize();

    private V getCacheValue(K key) {
        Node<K, V> node = cache.get(key);
        return node != null ? node.getValue() : null;
    }

    @Override
    public V get(K key) {
        int oldSize = size();

        V result = doGet(key);

        int newSize = size();

        assert newSize == oldSize;
        assert !cache.containsKey(key) || cache.get(key).getValue() == result;
        assert !cache.containsKey(key) || head.getKey() == key;
        assert !cache.containsKey(key) || head.getValue() == result;

        return result;
    }

    @Override
    public V put(K key, V value) {
        int oldSize = size();
        V cacheValue = getCacheValue(key);

        V result = doPut(key, value);

        int newSize = size();

        assert oldSize == newSize && cacheValue != null ||
                oldSize == newSize && oldSize == capacity ||
                oldSize + 1 == newSize && cacheValue == null && oldSize < capacity;
        assert cache.get(key).getValue() == value;
        assert head.getKey() == key;
        assert head.getValue() == value;
        assert cacheValue != null && cacheValue == result || cacheValue == null && result == null;

        return result;
    }

    @Override
    public V delete(K key) {
        int oldSize = size();
        V cacheValue = getCacheValue(key);

        V result = doDelete(key);

        int newSize = size();

        assert oldSize == newSize && cacheValue == null || cacheValue != null && oldSize == newSize + 1;
        assert !cache.containsKey(key);

        return result;
    }

    @Override
    public int size() {
        int result = getSize();

        assert result >= 0 && result <= capacity;

        return result;
    }

    @Override
    public int capacity() {
        return capacity;
    }

    @Data
    static class Node<K, V> {
        private final K key;
        private V value;
        private Node<K, V> next;
        private Node<K, V> prev;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
