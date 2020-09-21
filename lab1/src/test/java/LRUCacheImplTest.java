import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LRUCacheImplTest {
    private Cache<String, String> cache;
    private static final int TEST_CAPACITY = 5;

    @Before
    public void before() {
        cache = new LRUCacheImpl<>(TEST_CAPACITY);
    }

    @Test
    public void emptyTest() {
        assertEquals(cache.size(), 0);
        assertEquals(cache.capacity(), TEST_CAPACITY);
        assertNull(cache.get("1"));
    }

    @Test
    public void simplePutTest() {
        String key = "simple";
        String value = "test";
        String putValue = cache.put(key, value);
        String result = cache.get(key);

        assertEquals(1, cache.size());
        assertEquals(value, result);
        assertNull(putValue);
    }

    @Test
    public void multyPutTest() {
        cache.put("1", "11");
        cache.put("2", "22");
        cache.put("3", "33");

        assertEquals(3, cache.size());
        assertEquals("11", cache.get("1"));
        assertEquals("22", cache.get("2"));
        assertEquals("33", cache.get("3"));
    }

    @Test
    public void simpleDeleteTest() {
        cache.put("1", "11");
        cache.put("2", "22");
        String deleteValue = cache.delete("1");

        assertEquals(1, cache.size());
        assertEquals("11", deleteValue);
        assertNull(cache.get("1"));
        assertNotNull(cache.get("2"));

        deleteValue = cache.delete("3");
        assertEquals(1, cache.size());
        assertNull(deleteValue);
    }

    @Test
    public void simpleUpdateTest() {
        cache.put("1", "11");
        String putValue = cache.put("2", "22");
        cache.put("3", "33");

        assertNull(putValue);
        assertEquals("22", cache.get("2"));

        putValue = cache.put("2", "44");

        assertEquals(3, cache.size());
        assertEquals("22", putValue);
        assertEquals("44", cache.get("2"));
    }

    @Test
    public void simpleOverflowTest() {
        cache.put("1", "11");
        cache.put("2", "22");
        cache.put("3", "33");
        cache.put("4", "44");
        cache.put("5", "55");

        assertEquals(5, cache.size());
        cache.put("6", "66");
        assertEquals(5, cache.size());
        assertNull(cache.get("1"));
    }

    @Test
    public void overflowTest() {
        cache.put("1", "11");
        cache.put("2", "22");
        cache.put("3", "33");
        cache.put("4", "44");
        cache.put("5", "55");

        assertEquals(5, cache.size());
        assertEquals("11", cache.get("1"));
        assertEquals("44", cache.get("4"));

        String putValue = cache.put("6", "66");
        assertEquals(5, cache.size());
        assertNull(putValue);
        assertNull(cache.get("2"));

        putValue = cache.put("7", "77");
        assertEquals(5, cache.size());
        assertNull(putValue);
        assertNull(cache.get("3"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidCapacity() {
        new LRUCacheImpl(-1);
    }
}
