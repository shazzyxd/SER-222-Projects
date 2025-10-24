package edu.ser222.m03_04;

/**
 * A symbol table implemented using a hashtable with chaining.
 * Does not support load balancing or resizing.
 * 
 * @author Mallick, Sedgewick and Wayne, Acuna
 */
import java.util.LinkedList;
import java.util.Queue;

public class CompletedTwoProbeChainHT<Key, Value> implements TwoProbeChainHT<Key, Value> {
    private static final int DEFAULT_CAPACITY = 997; // Default size of the hash table
    private LinkedList<Entry<Key, Value>>[] table;
    private int size; // Number of key-value pairs
    private final int M; // Size of the hash table

    //any constructors must be made public
    public CompletedTwoProbeChainHT() {
        this.M = DEFAULT_CAPACITY;
        this.table = (LinkedList<Entry<Key, Value>>[]) new LinkedList[M];
        for (int i = 0; i < M; i++) {
            table[i] = new LinkedList<>();
        }
        this.size = 0;
    }

    private static class Entry<Key, Value> {
        Key key;
        Value value;

        public Entry(Key key, Value value) {
            this.key = key;
            this.value = value;
        }
    }

    @Override
    public int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }

    @Override
    public int hash2(Key key) {
        int h2 = (((key.hashCode() & 0x7fffffff) % M) * 31) % M;

        // Ensure h2 does not result in the same index as hash()
        if (h2 == hash(key)) {
            h2 = (h2 + 1) % M; // Shift to the next index to avoid overlap
        }

        return h2;
    }

    @Override
    public void put(Key key, Value val) {
        int h1 = hash(key);
        int h2 = hash2(key);

        LinkedList<Entry<Key, Value>> chain1 = table[h1];
        LinkedList<Entry<Key, Value>> chain2 = table[h2];

        // Update the value if the key already exists in either chain
        for (Entry<Key, Value> entry : chain1) {
            if (entry.key.equals(key)) {
                entry.value = val;
                return;
            }
        }

        for (Entry<Key, Value> entry : chain2) {
            if (entry.key.equals(key)) {
                entry.value = val;
                return;
            }
        }

        // Add the key to the shorter chain
        if (chain1.size() <= chain2.size()) {
            chain1.add(new Entry<>(key, val));
        } else {
            chain2.add(new Entry<>(key, val));
        }
        size++;
    }

    @Override
    public Value get(Key key) {
        int h1 = hash(key);
        int h2 = hash2(key);

        for (Entry<Key, Value> entry : table[h1]) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
        }

        for (Entry<Key, Value> entry : table[h2]) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
        }

        return null; // Key not found
    }

    @Override
    public void delete(Key key) {
        int h1 = hash(key);
        int h2 = hash2(key);

        // Try to remove from chain 1
        for (Entry<Key, Value> entry : table[h1]) {
            if (entry.key.equals(key)) {
                table[h1].remove(entry);
                size--;
                return;
            }
        }

        // Try to remove from chain 2
        for (Entry<Key, Value> entry : table[h2]) {
            if (entry.key.equals(key)) {
                table[h2].remove(entry);
                size--;
                return;
            }
        }
    }

    @Override
    public boolean contains(Key key) {
        return get(key) != null;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterable<Key> keys() {
        LinkedList<Key> keys = new LinkedList<>();
        for (LinkedList<Entry<Key, Value>> chain : table) {
            for (Entry<Key, Value> entry : chain) {
                keys.add(entry.key);
            }
        }
        return keys;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    // THESE METHODS ARE ONLY FOR GRADING AND COME FROM THE TWOPROBECHAINHT INTERFACE.

    @Override
    public int getM() {
        return M;
    }

    @Override
    public int getChainSize(int i) {
        return table[i].size();

    }
}