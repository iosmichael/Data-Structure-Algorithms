package impl;

import java.util.Comparator;


/**
 * HeapSorter.java
 *
 * Class to implement the heapsort algorithm.
 *
 * @author Thomas VanDrunen
 * CSCI 345, Wheaton College   
 * Originally for CSCI 245, Spring 2007
 * Revised June 2, 2015
 */

public class HeapSorter extends Heap<Integer> {


    /**
     * Constructor. Take an array an sets it up as a (max-) heap.
     * @param internal The array to be used for the internal representation.
     */
    private HeapSorter(int[] array) {
        internal = new Integer[array.length];
        for (int i = 0; i < array.length; i++)
            internal[i] = array[i];
        heapSize = array.length;
        
        // fix this; set compy to an appropriate comparator
        compy = new Comparator<Integer>() {
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        };
        // insert code for rearranging this as a heap
        for(int i = 0; i < array.length ; i++){
        	this.heapify(array.length-1-i);
        }
    }
    
    /**
     * Sort this array, in place.
     * @param array The array to sort.
     */
    public static void sort(int[] array) {
    
        HeapSorter heap = new HeapSorter(array);

        // insert code for completing the heap sort algorithm,
        // with post condition that heap.internal is sorted
        for(int i = 0; i < array.length-1; i++){
        	int val = heap.internal[0];
        	heap.internal[0] = heap.internal[array.length-1-i];
        	heap.internal[array.length-1-i] = val;
        	heap.heapSize--;
        	heap.heapify(0);
        }
        // copy elements from internal (now sorted) back to array
        for (int i = 0; i < array.length; i++)
            array[i] = heap.internal[i];
        
        
    }
    
    private static void swap(int[] array, int pos1, int pos2){
    	int val = array[pos1];
    	array[pos1] = array[pos2];
    	array[pos2] = val;
    }

}
