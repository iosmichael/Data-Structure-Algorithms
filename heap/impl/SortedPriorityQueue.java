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
public class SortedPriorityQueue<E> implements PriorityQueue<E> {

    private List<E> internal;
    private int capacity;
    private Comparator<E> compy;

    // Invariant: The items in internal are sorted
    // from least to greatest according to compy
    
    public SortedPriorityQueue(int maxSize, Comparator<E> compy) {
        internal = new ArrayList<E>();
        capacity = maxSize;
        this.compy = compy;
    }

    public SortedPriorityQueue(Iterable<E> items, Comparator<E> compy) {
        internal = new ArrayList<E>();
        capacity = 0;
        this.compy = compy;
        for (E item : items) {
            internal.add(item);
            fixupLower(capacity);
            capacity++;
        }
    }

    /**
     * Insure that the internal list from pos to the beginning
     * is sorted from lest to greatest according to compy.
     * That is, move the item at pos to its right place,
     * insertion-sort style.
     * PRECONDITION: The portion of the internal list [0, pos)
     * is sorted from greatest to least.
     * POSTCONDITION: The portion of the internal list [0, pos]
     * is sorted from greatest to least.
     * @param pos
     */
    private void fixupLower(int pos) {        
        for (int i = pos; 
                i > 0 && compy.compare(internal.get(i-1), internal.get(i)) > 0;
                i--) {
            E temp = internal.get(i);
            internal.set(i, internal.get(i-1));
            internal.set(i-1, temp);
        } 
              
    }
    
    /**
     * Insure that the internal list from pos to the beginning
     * is sorted from lest to greatest according to compy.
     * That is, move the item at pos to its right place,
     * insertion-sort style.
     * PRECONDITION: The portion of the internal list [0, pos)
     * is sorted from greatest to least.
     * POSTCONDITION: The portion of the internal list [0, pos]
     * is sorted from greatest to least.
     * @param pos
     */
    private void fixupHigher(int pos) {        
        for (int i = pos; 
                i < internal.size() - 1 && compy.compare(internal.get(i), internal.get(i+1)) > 0;
                i++) {
            E temp = internal.get(i);
            internal.set(i, internal.get(i+1));
            internal.set(i+1, temp);
        } 
              
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
        fixupLower(internal.size() - 1);
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
        return internal.get(internal.size() - 1);
    }

    /**
     * Return and remove the maximum key.
     * @return The maximum key.
     */
    public E extractMax() {
        if (isEmpty())
            throw new NoSuchElementException();
        return internal.remove(internal.size() - 1);
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
        int pos = 0;
        while (pos < internal.size() && ! key.equals(internal.get(pos)))
            pos++;
        if (pos < internal.size())
            fixupHigher(pos);
    }


}
