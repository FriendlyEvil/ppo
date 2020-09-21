public class LRUCacheImpl<K, V> extends LRUCache<K, V> {
    private static final int DEFAULT_CAPACITY = 10;

    public LRUCacheImpl() {
        this(DEFAULT_CAPACITY);
    }

    public LRUCacheImpl(int capacity) {
        super(capacity);
    }

    private void addToLinkedList(Node<K, V> node) {
        if (head == null) {
            head = node;
            tail = node;
        } else {
            head.setPrev(node);
            node.setNext(head);
            head = node;
        }
    }

    private void deleteFromLinkedList(Node<K, V> node) {
        Node<K, V> prev = node.getPrev();
        Node<K, V> next = node.getNext();

        if (prev != null) {
            prev.setNext(next);
        } else {
            assert node == head;
            head = next;
        }

        if (next != null) {
            next.setPrev(prev);
        } else {
            assert node == tail;
            tail = prev;
        }
    }

    private void moveToTop(Node<K, V> node) {
        deleteFromLinkedList(node);
        node.setPrev(null);
        node.setNext(null);
        addToLinkedList(node);
    }

    @Override
    protected V doGet(K key) {
        Node<K, V> node = cache.get(key);
        if (node != null) {
            moveToTop(node);
            return node.getValue();
        }

        return null;
    }

    @Override
    protected V doPut(K key, V value) {
        Node<K, V> node = cache.get(key);
        if (node == null) {
            node = new Node<>(key, value);
            if (size() == capacity) {
                cache.remove(tail.getKey());
                deleteFromLinkedList(tail);
            }
            addToLinkedList(node);
            cache.put(key, node);

            return null;
        } else {
            V oldValue = node.getValue();
            node.setValue(value);
            moveToTop(node);

            return oldValue;
        }
    }

    @Override
    protected V doDelete(K key) {
        Node<K, V> node = cache.get(key);
        if (node != null) {
            cache.remove(key);
            deleteFromLinkedList(node);
            return node.getValue();
        }

        return null;
    }

    @Override
    protected int getSize() {
        return cache.size();
    }
}
