package de.comparus.opensource.longmap;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LongMapImplTest {

    private LongMap<String> map;

    @Before
    public void setUp() {
        map = new LongMapImpl<>();
    }

    @Test
    public void testPutAndGet() {
        map.put(1L, "One");
        map.put(2L, "Two");
        map.put(3L, "Three");

        assertEquals("One", map.get(1L));
        assertEquals("Two", map.get(2L));
        assertEquals("Three", map.get(3L));
    }

    @Test
    public void testPutAndRemove() {
        map.put(1L, "One");
        map.put(2L, "Two");
        map.put(3L, "Three");

        assertEquals("Two", map.remove(2L));
        assertNull(map.get(2L));
        assertEquals(2, map.size());
    }

    @Test
    public void testContainsKeyAndContainsValue() {
        map.put(1L, "One");
        map.put(2L, "Two");
        map.put(3L, "Three");

        assertTrue(map.containsKey(1L));
        assertFalse(map.containsKey(4L));
        assertTrue(map.containsValue("Three"));
        assertFalse(map.containsValue("Four"));
    }

    @Test
    public void testKeysAndValues() {
        map.put(1L, "One");
        map.put(2L, "Two");
        map.put(3L, "Three");

        long[] keys = map.keys();
        String[] values = map.values();

        assertArrayEquals(new long[]{1L, 2L, 3L}, keys);
        assertArrayEquals(new String[]{"One", "Two", "Three"}, values);
    }

    @Test
    public void testClearAndIsEmpty() {
        map.put(1L, "One");
        map.put(2L, "Two");

        assertFalse(map.isEmpty());
        assertEquals(2, map.size());

        map.clear();

        assertTrue(map.isEmpty());
        assertEquals(0, map.size());
    }

}
