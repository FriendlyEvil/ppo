interface Cache<K, V> {
    V get(K key);

    V put(K key, V value);

    V delete(K key);

    int size();

    int capacity();
}
