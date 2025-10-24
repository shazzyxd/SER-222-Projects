package edu.ser222.m03_04;

/**
 * A symbol table implemented using a hashtable with quadratic probing.
 * 
 * @author Mallick, Acuna
 */
public class CompletedQuadProbingHT<Key, Value> extends CompletedLinearProbingHT<Key, Value> {

    //any constructors must be made public
    public CompletedQuadProbingHT() {
        super(); // Call the base class constructor
    }

    @Override
    public int hash(Key key, int i) {
        return ((key.hashCode() & 0x7fffffff) + i * i) % getM();
    }

}