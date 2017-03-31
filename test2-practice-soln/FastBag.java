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

    private String[] keys;
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
	    this.keys = new String[numKeys];
	    for (int i = 0; keys.hasNext(); i++)
	        this.keys[i] = keys.next();
	    counts = new int[numKeys];
	}

	private int findIndex(String item) {
	    int start = 0, 
                stop = keys.length, 
                mid = keys.length / 2,
                compare = item.compareTo(keys[mid]);
      while(stop - start > 1 && compare != 0) {
            if (compare < 0) stop = mid;
            else start = mid;
            mid = (start + stop) / 2;
            compare = item.compareTo(keys[mid]);
        }
        if (compare != 0) return -1;
        else return mid;
	}

	/**
	 * Add an item to the bag, increasing its count by 1.
	 * Behavior undefined if the given item is not one of the
	 * keys supplied to the constructor.
	 * @param item The item to add
	 */
	public void add(String item) {
	    int index = findIndex(item);
	    assert index != -1;
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
	    int index = findIndex(item);
	    if (index == -1) return 0;
	    else return counts[index];
	}

	/**
	 * Remove the given item, resetting its count to 0.
	 * Behavior is undefined for items not supplied to the
	 * constructor.
	 * @param The item to remove
	 */
	public void remove(String item) {
	    int index = findIndex(item);
	    if (index != -1) counts[index] = 0;
	}

	/**
	 * The number of items in the bag. This is the sum
	 * of the counts, not the number of unique items.
	 * @return The number of items.
	 */
	public int size() {
	    int size = 0;
	    for (int count : counts)
	        size += count;
	    return size;
	}

    /**
     * Is the set empty?
     * @return True if the set is empty, false otherwise.
     */
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Make an iterator over the items in this bag which will
	 * return each item the number times indicated by its count.
	 * @return An iterator over the bag
	 */
	public Iterator<String> iterator() {
        int i = 0;
        while (i < counts.length && counts[i] == 0)
            i++;
        final int finalI = i;
        return new Iterator<String>() {
            int index = finalI;
            int remaining = index == counts.length ? 0 : counts[index];
            public boolean hasNext() {
                return index < counts.length;
            }

            public String next() {
                String toReturn = keys[index];
                remaining--;
                if (remaining == 0) {
                    do index++;
                    while(index < counts.length && counts[index] == 0);
                    if (index != counts.length)
                        remaining = counts[index];
                }
                return toReturn;
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
            
        };
    }

}
