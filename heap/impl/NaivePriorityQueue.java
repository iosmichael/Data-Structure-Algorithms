package impl;

import java.util.Comparator;
import java.util.NoSuchElementException;

import adt.FullContainerException;
import adt.List;
import adt.PriorityQueue;

/**
 * NaivePriorityQueue
 * 
 * A dumb, slow implementation of a priority queue
 * that uses an unsorted list as the underlying
 * implementation.
 * 
 * @author Thomas VanDrunen
 * CSCI 345, Wheaton College
 * June 3, 2015
 * @param <E> The base-type of the priority queue
 */

public class NaivePriorityQueue<E> implements PriorityQueue<E> {

    private List<E> internal;
    private int capacity;
    private Comparator<E> compy;
    
    public NaivePriorityQueue(int maxSize, Comparator<E> compy) {
        internal = new ArrayList<E>();
        capacity = maxSize;
        this.compy = compy;
    }

    public NaivePriorityQueue(Iterable<E> items, Comparator<E> compy) {
        internal = new ArrayList<E>();
        capacity = 0;
        for (E item : items) {
            internal.add(item);
            capacity++;
        }
        this.compy = compy;
    }
    
    /**
     * Is this pq empty?
     * @return True if this is empty, false otherwise.
     */
    public boolean isEmpty() {
        return internal.size() == 0;
    }

    /**
     * Is this pq full?
     * @return True if this is full, false otherwise.
     */
    public boolean isFull() {
        return internal.size() >= capacity;
    }

    /**
     * Insert a new key into this pq.
     * @param key The key to insert.  
     */
    public void insert(E key) {
        if (isFull())
            throw new FullContainerException();
        internal.add(key);
    }

    /**
     * Return (but do not remove) the maximum key.
     * According to the (max-) heap property, the maximum key
     * should be at position 0.
     * @return The maximum key.
     */
    public E max() {
        if (isEmpty())
            throw new NoSuchElementException();
        E max = internal.get(0);
        for (int i = 1; i < internal.size(); i++)
            if (compy.compare(internal.get(i), max) > 0) {
                max = internal.get(i);
            }
        return max;
    }

    /**
     * Return and remove the maximum key.
     * @return The maximum key.
     */
    public E extractMax() {
        if (isEmpty())
            throw new NoSuchElementException();
        int maxPos = 0;
        E max = internal.get(0);
        for (int i = 1; i < internal.size(); i++)
            if (compy.compare(internal.get(i), max) > 0) {
                maxPos = i;
                max = internal.get(i);
            }
        internal.remove(maxPos);
        return max;
    }

    /**
     * Determine whether this key is in the pq.
     * @param key The key to look for.
     * @return True if this key is in the pq, false otherwise.
     */
    public boolean contains(E key) {
        boolean foundIt = false;
        for (int i = 0; !foundIt && i < internal.size(); i++)
            foundIt |= internal.get(i).equals(key);
        return foundIt;
    }

    /**
     * Indicate that the priority of a key at a given key
     * has changed, which may affect the internal storage
     * of the pq.
     * @param key The key whose priority has changed.
     */
    public void increaseKey(E key) {
        // do nothing
    }

}
