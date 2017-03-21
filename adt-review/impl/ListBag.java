package impl;

import java.util.Iterator;

import adt.Bag;
import adt.List;

/**
 * ListBag
 * 
 * An implementation of Bag using a List as the underlying
 * implementation.
 * 
 * Recall that our Bag interface differs from what Sedgewick 
 * calls a "bag" (but he's wrong). 
 * 
 * CSCI 345, Wheaton College
 * Spring 2016
 * @param <E> The base-type of the bag
 */
public class ListBag<E> implements Bag<E> {

    /**
     * The internal representation (can be any implementation of
     * List)
     */
    private List<E> internal;

    /**
     * Constructor that is given the internal representation.
     * From a software development perspective, that's a bad idea
     * (breaks encapsulation), but for the purpose of this project 
     * it allows us to parameterize this class by what implementation
     * of Map we use. (Maybe in a future version we'll use 
     * reflection instead).
     * @param internal An (empty) implementation of List.
     */
    public ListBag(List<E> internal) {
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
     * Add an item to the bag, increasing its count if it's
     * already there.
     * @param item The item to add
     */
    public void add(E item) {
        internal.add(item);
    }

    /**
     * How many times does this bag contain this item?
     * @param item The item to check
     * @return The number of occurences of this item in the bag
     */
    public int count(E item) {
        int count = 0;
        for(Iterator<E> it = internal.iterator(); it.hasNext();){
        	if(it.next().equals(item)){
        		count++;
        	}
        }
        return count;
    }

    /**
     * Remove (all occurrences of) an item from the bag, if it's there
     * (ignore otherwise).
     * @param item The item to remove
     */
    public void remove(E item) {
    	int i = 0;
    	while(i<internal.size()){
    		if(internal.get(i) == null) continue;
    		if(internal.get(i).equals(item)){
    			internal.remove(i);
    		}else{
    			i++;
    		}
    	}
    }

    /**
     * The number of items in the bag. This is the sum
     * of the counts, not the number of unique items.
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
     * Is the bag empty?
     * @return True if the bag is empty, false otherwise.
     */
    public boolean isEmpty() {
        return size() == 0;
    }

}
