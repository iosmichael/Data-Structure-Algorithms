package impl;

import java.util.Comparator;

/**
 * Heap.java
 * 
 * Abstract class to provide the basic functionality of a heap, to
 * be used, for example, in heapsort or in a priority queue.
 *
 * @author Thomas VanDrunen
 * CSCI 345, Wheaton College   
 * Originally for CSCI 245, Spring 2007
 * Revised June 2, 2016
 */

public abstract class Heap<E> {

    /**
     * The array containing the internal data of the heap.
     */
    protected E[] internal;

    /**
     * The portion of the array currently used to store the heap.
     */
    protected int heapSize;

    /**
     * Comparator to determine the priority of keys.
     */
    protected Comparator<E> compy;
    
    /**
     * Find the index of the parent of the node at a given index.
     * @param i The index whose parent we want.
     * @return The index of the parent.
     */
    protected int parent(int i) { return (i - 1) / 2; }

    /**
     * Find the index of the left child of the node at a given index.
     * @param i The index whose left child we want.
     * @return The index of the left child.
     */
    protected int left(int i ) { return 2 * i + 1; }

    /**
     * Find the index of the right child of the node at a given index.
     * @param i The index whose right child we want.
     * @return The index of the right child.
     */
    protected int right(int i) { return 2 * i + 2; }

    /**
     * Force the (max-) heap property on the subtree rooted at
     * index i.
     * @param i The index where we want to make a heap.
     * PRECONDITION: The subtrees rooted at the left and right
     * children of i are already heaps.
     * POSTCONDITION: The subtree rooted at i is a heap.
     */
    protected void heapify(int i) {
    	if(i < 0 || i >= heapSize) return;
    	if(left(i) >= heapSize && right(i) >= heapSize) return;
    	E parent, leftChild, rightChild;
    	parent = internal[i];
    	leftChild = left(i) >= heapSize ? parent : internal[left(i)];
    	rightChild = right(i) >= heapSize ? parent: internal[right(i)];
    	if(compy.compare(parent, leftChild) < 0 && compy.compare(leftChild, rightChild) > 0){
    		internal[left(i)] = parent;
    		internal[i] = leftChild;
    	}else if(compy.compare(parent, rightChild) < 0){
    		internal[right(i)] = parent;
    		internal[i] = rightChild;
    	}
    	if(left(i) < heapSize) heapify(left(i));
    	if(right(i) < heapSize) heapify(right(i));
    }

}
