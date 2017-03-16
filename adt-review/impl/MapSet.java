package impl;

import java.util.Iterator;

import adt.Set;
import adt.Map;

/**
 * MapSet
 * 
 * An implementation of Set that uses a Map as its
 * underlying implementation
 * 
 * CSCI 345, Wheaton College
 * Spring 2016
 * @param <E> The base-type of the set
 */
public class MapSet<E> implements Set<E> {

    /**
     * The internal representation. Note this can be any 
     * map implementation. 
     */
    private Map<E, String> internal;
    
    /**
     * Constructor that is given the internal representation.
     * From a software development perspective, that's a bad idea
     * (breaks encapsulation), but for the purpose of this project 
     * it allows us to parameterize this class by what implementation
     * of Map we use. (Maybe in a future version we'll use 
     * reflection instead).
     * @param internal An (empty) implementation of Map.
     */
    public MapSet(Map<E, String> internal) {
        this.internal = internal;
    }
    
    /**
     * Return an iterator over this collection (remove() is
     * unsupported, nor is concurrent modification checked).
     */
    public Iterator<E> iterator() {
        return internal.iterator();
    }

    /**
     * Add an item to the set. (Do nothing if the item is 
     * already there.)
     * @param item The item to add
     */
    public void add(E item) {
       if(internal.containsKey(item)) return;
       internal.put(item,"");
    }

    /**
     * Does this set contain the item?
     * @param item The item to check
     * @return True if the item is in the set, false otherwise
     */
    public boolean contains(E item) {
        return internal.containsKey(item);
    }

    /**
     * Remove an item from the set, if it's there
     * (ignore otherwise).
     * @param item The item to remove
     */
   public void remove(E item) {
       internal.remove(item);
    }

   /**
    * The number of items in the set
    * @return The number of items.
    */
    public int size() {
    	int i = 0;
        for(Iterator<E> it = internal.iterator(); it.hasNext();){ 
        	it.next();
        	i++;
        }
        return i;
    }

    /**
     * Is the set empty?
     * @return True if the set is empty, false otherwise.
     */
    public boolean isEmpty() {
        return size() == 0;
    }

}
