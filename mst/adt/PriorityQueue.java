package adt;

/**
 * PriorityQueue
 * 
 * Interface to define the PriorityQueue ADT.
 * The operations here are defined assuming a 
 * max-priority queue (I was tempted to name
 * methods things like "extractExtreme()" but decided
 * that sounded awful). How priority is determined is 
 * left to the implementation---as examples, the base
 * type can be assumed comparable or a comparator can
 * be supplied.
 * 
 * @author Thomas VanDrunen
 * CSCI 345, Wheaton College
 * June 2, 2015
 * @param <E> The base-type of the priority queue
 */

public interface PriorityQueue<E> {

    /**
     * Is this pq empty?
     * @return True if this is empty, false otherwise.
     */
    boolean isEmpty();
    
    /**
     * Is this pq full?
     * @return True if this is full, false otherwise.
     */
    boolean isFull();

    /**
     * Insert a new key into this pq.
     * @param key The key to insert.  
     */
    void insert(E key);
    
    /**
     * Return (but do not remove) the maximum key.
     * @return The maximum key.
     */
    E max();
    
    /**
     * Return and remove the maximum key.
     * @return The maximum key.
     */
    E extractMax();

    /**
     * Determine whether this key is in the pq.
     * @param key The key to look for.
     * @return True if this key is in the pq, false otherwise.
     */
    boolean contains(E key);

    /**
     * Indicate that the priority of a key at a given key
     * has changed, which may affect the internal storage
     * of the pq.
     * @param key The key whose priority has changed.
     */
    void increaseKey(E key);

}
