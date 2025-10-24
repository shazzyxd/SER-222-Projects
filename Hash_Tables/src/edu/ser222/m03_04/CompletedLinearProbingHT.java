package edu.ser222.m03_04;

/**
 * A symbol table implemented using a hashtable with linear probing.
 * 
 * @author Mallick, Sedgewick and Wayne, Acuna
 */
import java.util.LinkedList;
import java.util.Queue;

public class CompletedLinearProbingHT<Key, Value> implements ProbingHT<Key, Value> {

    private static final int DEFAULT_CAPACITY = 997; // Default table size (prime number)
    private final int M; // Size of the hash table
    private Key[] keys; // Array of keys
    private Value[] values; // Array of corresponding values
    private int size; // Number of key-value pairs

    //any constructors must be made public
    public CompletedLinearProbingHT() {
        this.M = DEFAULT_CAPACITY;
        this.keys = (Key[]) new Object[M];
        this.values = (Value[]) new Object[M];
        this.size = 0;
    }

    @Override
    public int hash(Key key, int i) {
        return ((key.hashCode() & 0x7fffffff) + i) % M;
    }

    @Override
    public void put(Key key, Value val) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null.");

        int i = 0;
        int index;

        // Use linear probing to find a free spot or an existing key
        do {
            index = hash(key, i++);
            if (keys[index] == null) {
                keys[index] = key;
                values[index] = val;
                size++;
                return;
            } else if (keys[index].equals(key)) { // Update existing key
                values[index] = val;
                return;
            }
        } while (true);
    }

    @Override
    public Value get(Key key) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null.");

        int i = 0;
        int index;

        // Use linear probing to find the key
        do {
            index = hash(key, i++);
            if (keys[index] == null) return null; // Stop once we hit an empty spot
            if (keys[index].equals(key)) return values[index];
        } while (true);
    }

    @Override
    public void delete(Key key) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null.");
        if (!contains(key)) return; // Key doesn't exist, nothing to delete

        int i = 0;
        int index;

        // Use linear probing to find and delete the key
        do {
            index = hash(key, i++);
            if (keys[index] == null) return; // Stop once we hit an empty spot
            if (keys[index].equals(key)) {
                keys[index] = null;
                values[index] = null;
                size--;
                break;
            }
        } while (true);

        // Rehash all subsequent keys in the same probe sequence
        i = index + 1;
        while (keys[i % M] != null) {
            Key rehashKey = keys[i % M];
            Value rehashValue = values[i % M];
            keys[i % M] = null;
            values[i % M] = null;
            size--;
            put(rehashKey, rehashValue); // Reinsert using put()
            i++;
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
        Queue<Key> queue = new LinkedList<>();
        for (Key key : keys) {
            if (key != null) queue.add(key);
        }
        return queue;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    // THESE METHODS ARE ONLY FOR GRADING AND COME FROM THE PROBINGHT INTERFACE.

    @Override
    public int getM() {
        return M;
    }

    @Override
    public Object getTableEntry(int i) {
        return keys[i];
    }
}