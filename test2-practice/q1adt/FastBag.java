package q1adt;

import java.util.Iterator;

/**
 * FastBag
 * 
 * An implementation of a Bag whose keys are known 
 * ahead of time and whose main operations (add, count,
 * and remove) operate in logarithmic time.
 * 
 * The keys are given to the constructor; they all initially
 * have count 0. Behavior is undefined if add or remove is
 * called using a key besides those given to the constructor.
 * 
 * CSCI 345
 * Test 2 Practice Problem 1.
 */

public class FastBag implements Bag<String> {

    
    private int size = 0;
    private String[] internal;
    private int[] counts;

    /**
     * Constructor that takes an iterator that gives
     * the keys in sorted order and the number of keys.
     * Behavior is undefined if the number of items returned
     * by the iterator differs from numKeys.
     * @param keys An iterator that gives the potential keys
     * in sorted order.
     * @param numKeys The number of keys
     */
	public FastBag(Iterator<String> keys, int numKeys) {
		internal = new String[numKeys];
		counts = new int[numKeys];
		for(;keys.hasNext();){
			internal[size++] = keys.next();
		}
	}


	/**
	 * Add an item to the bag, increasing its count by 1.
	 * Behavior undefined if the given item is not one of the
	 * keys supplied to the constructor.
	 * @param item The item to add
	 */
	public void add(String item) {
		int index = this.search(item, 0, size);
		if(index == -1){
			return;
		}
		counts[index]++;
	}

    /**
     * How many times does this bag contain this item?
     * Items supplied to the constructor but either have never been
     * added or have been delete have count 0.
     * Behavior is undefined for items not supplied to the
     * constructor.
     * @param item The item to check
     * @return The count for the given item.
     */
	public int count(String item) {
		int index = this.search(item, 0, size);
		if(index == -1){
			return 0;
		}
		return counts[index];
	}

	/**
	 * Remove the given item, resetting its count to 0.
	 * Behavior is undefined for items not supplied to the
	 * constructor.
	 * @param The item to remove
	 */
	public void remove(String item) {
		int index = this.search(item, 0, size);
		if(index == -1){
			return;
		}
		counts[index] = 0;
	}

	/**
	 * The number of items in the bag. This is the sum
	 * of the counts, not the number of unique items.
	 * @return The number of items.
	 */
	public int size() {
		int total = 0;
		for(int i = 0; i < counts.length; i++){
			total += counts[i];
		}
	    return total;
	}

    /**
     * Is the set empty?
     * @return True if the set is empty, false otherwise.
     */
	public boolean isEmpty() {
	    return size() == 0;
	}
	
	/**
	 * Recursive calls
	 * @param item
	 * @return
	 */
	public int search(String item, int begin, int end){
		if( end - begin == 1) {  //0 , 1 , end
			if(item.compareTo(internal[begin]) == 0){
				return begin;
			}
			return -1;
		}
		if(end == begin){
			return -1;
		}
		int mid = (end + begin) / 2;
		if(item.compareTo(internal[mid]) < 0){
			return search(item, begin, mid);
		}else if(item.compareTo(internal[mid]) == 0){
			return mid;
		}else{
			return search(item, mid+1, end);
		}
	}
	/**
	 * Make an iterator over the items in this bag which will
	 * return each item the number times indicated by its count.
	 * @return An iterator over the bag
	 */
	public Iterator<String> iterator() {
	    return new Iterator<String>(){
	    	int i = 0;
	    	int count = counts[0];
	    	int total = 0;
			public boolean hasNext() {				
				return total < size();
			}

			public String next() {
				total++;
				if(count == 0){
					while(counts[++i]==0);
					String toReturn = internal[i];
					count = counts[i]-1;
					return toReturn;
				}else{
					count--;
					return internal[i];
				}
			}
	    	
	    };
    }

}
