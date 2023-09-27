package de.comparus.opensource.longmap;

import java.lang.reflect.Array;

public class LongMapImpl<V> implements LongMap<V> {

    private static final int DEFAULT_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75f;

    private Entry<V>[] table;
    private int size = 0;

    public LongMapImpl() {
        table = new Entry[DEFAULT_CAPACITY];
    }

    public V put(long key, V value) {
        if (size >= table.length * LOAD_FACTOR) {
            resize();
        }

        int index = getIndex(key);
        Entry<V> entry = table[index];
        while (entry != null) {
            if (entry.key == key) {
                V oldValue = entry.value;
                entry.value = value;
                return oldValue;
            }
            entry = entry.next;
        }

        // Key not found, create a new entry
        Entry<V> newEntry = new Entry<>(key, value);
        newEntry.next = table[index];
        table[index] = newEntry;
        size++;
        return null;
    }

    public V get(long key) {
        int index = getIndex(key);
        Entry<V> entry = table[index];
        while (entry != null) {
            if (entry.key == key) {
                return entry.value;
            }
            entry = entry.next;
        }
        return null;
    }

    private int getIndex(long key) {
        return (int) (key % table.length);
    }

    private void resize() {
        int newCapacity = table.length * 2;
        Entry<V>[] newTable = new Entry[newCapacity];

        for (Entry<V> entry : table) {
            while (entry != null) {
                Entry<V> next = entry.next;
                int index = getIndex(entry.key);
                entry.next = newTable[index];
                newTable[index] = entry;
                entry = next;
            }
        }

        table = newTable;
    }

    public V remove(long key) {
        int index = getIndex(key);
        Entry<V> entry = table[index];
        Entry<V> prev = null;
        while (entry != null) {
            if (entry.key == key) {
                if (prev == null) {
                    table[index] = entry.next;
                } else {
                    prev.next = entry.next;
                }
                size--;
                return entry.value;
            }
            prev = entry;
            entry = entry.next;
        }
        return null;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean containsKey(long key) {
        int index = getIndex(key);
        Entry<V> entry = table[index];
        while (entry != null) {
            if (entry.key == key) {
                return true;
            }
            entry = entry.next;
        }
        return false;
    }

    public boolean containsValue(V value) {
        for (Entry<V> entry : table) {
            while (entry != null) {
                if (entry.value.equals(value)) {
                    return true;
                }
                entry = entry.next;
            }
        }
        return false;
    }

    public long[] keys() {
        long[] keys = new long[size];
        int i = 0;
        for (Entry<V> entry : table) {
            while (entry != null) {
                keys[i++] = entry.key;
                entry = entry.next;
            }
        }
        return keys;
    }

    public V[] values() {
        @SuppressWarnings("unchecked")
        V[] values = (V[]) new Object[size];
        int i = 0;
        for (Entry<V> entry : table) {
            while (entry != null) {
                values[i++] = entry.value;
                entry = entry.next;
            }
        }
        return values;
    }

    public long size() {
        return size;
    }

    public void clear() {
        Entry<V>[] tab;
        if ((tab = table) != null && size > 0) {
            size = 0;
            for (int i = 0; i < tab.length; ++i) {
                tab[i] = null;
            }
        }
    }

    private static class Entry<V> {
        final long key;
        V value;
        Entry<V> next;

        Entry(long key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
