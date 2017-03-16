package impl;
/**
 * Stack.java
 *
 * Class to implement a stack using a priority queue.
 * 
 * CS 345, Wheaton College
 * Originally for CSCI 245, Spring 2007
 * Revised Jan 4, 2016
 */

import java.util.HashMap;
import java.util.Comparator;
import java.util.NoSuchElementException;

import adt.FullContainerException;
import adt.Stack;

public class PQStack<E> implements Stack<E> {

    /**
     * The priority queue to use as an internal representation.
     */
    private HeapPriorityQueue<E> pq;

    /**
     * Place to store data associated with representative
     * values in the priority queue.
     */
    private HashMap<E, Integer> arrivalTimes;

    private int counter = 0;
 
    /**
     * Constructor.
     * @param maxSize The capacity of this stack.
     */
    public PQStack(int maxSize) {
    	arrivalTimes = new HashMap<E, Integer>();
        pq = new HeapPriorityQueue<E>(maxSize, new Comparator<E>(){
			public int compare(E o1, E o2) {
				return arrivalTimes.get(o1) - arrivalTimes.get(o2);
			}
        });
    }

    /**
     * Is this stack empty? It is if the pq is empty.
     * @return True if this is empty, false otherwise.
     */
    public boolean isEmpty() { return pq.isEmpty(); }

    /**
     * Is this stack full? It is if the pq is full.
     * @return True if this is full, false otherwise.
     */
    public boolean isFull() { return pq.isFull(); }

    /**
     * Retrieve (but do not remove) the top element of this stack.
     * @return The top element.
     */
    public E top() { 
    	if(isEmpty()) throw new NoSuchElementException();
    	return pq.internal[0];
    }

    /**
     * Retreive and remove the top element of this stack.
     * @return The top element.
     */
    public E pop() {
    	if(isEmpty()) throw new NoSuchElementException();
        return pq.extractMax();
    }

    /**
     * Add an element to this stack.
     * @param x The element to add.
     */
    public void push(E x) {
    	if(isFull()) throw new FullContainerException();
    	arrivalTimes.put(x, counter++);
        pq.insert(x);  
    }

    public String toString() { return pq.toString(); }
    
}


