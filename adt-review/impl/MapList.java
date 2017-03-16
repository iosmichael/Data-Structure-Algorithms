package impl;

import java.util.Iterator;
import adt.Map;
import adt.List;

/**
 * MapList
 * 
 * An implementation of List that uses a Map as its
 * underlying implementation.
 * 
 * CSCI 345, Wheaton College
 * Spring 2016
 * @param <E> The base-type of the list
 */
public class MapList<E> implements List<E> {

    /**
     * The internal representation (can be any 
     * implementation of map).
     */
    private Map<Integer, E> internal;
    private int size = 0;
    /**
     * Constructor that is given the internal representation.
     * From a software development perspective, that's a bad idea
     * (breaks encapsulation), but for the purpose of this project 
     * it allows us to parameterize this class by what implementation
     * of Map we use. (Maybe in a future version we'll use 
     * reflection instead).
     * @param internal An (empty) implementation of Map.
     */
    public MapList(Map<Integer, E> internal) {
        this.internal = internal;
      
    }
    
    /**
     * Return an iterator over this collection (remove() is
     * unsupported, nor is concurrent modification checked).
     */
    public Iterator<E> iterator() {
        return new Iterator<E>(){
        	int i = 0;
			public boolean hasNext() {
				return i<size;
			}

			public E next() {
				int in = i;
				i++;
				return internal.get(in);
			}
        };
    }

    /**
     * Append the specified element to the end of this list.
     * This increases the size by one.
     * @param element The element to be appended
     */
    public void add(E element) {
        internal.put(size, element);
        size++;
    }

    /**
     * Replace the element at the specified position in this list
     * with the specified element. If the index is invalid, an 
     * IndexOutOfBoundsException is thrown.
     * @param index The index of the element to return
     * @param element The element at the specified position
     */
    public void set(int index, E element) {
    	if(index>=size|| index < 0) throw new IndexOutOfBoundsException();
        internal.put(index, element);
    }

    /**
     * Retrieve the element at the specified position in this list.
     * If the index is invalid, an IndexOutOfBoundsException is thrown.
     * @param index The index of the element to return
     * @return The element at the specified position
     */
    public E get(int index) {
    	if(index >= size || index < 0){
    		throw new IndexOutOfBoundsException();
    	}
		return internal.get(index);
    }


    /**
     * Insert a new item at the specified position, shifting the
     * item already at the position and everything after it over
     * one position. If the index is equal to the length of the list,
     * then this is equivalent to the add method. If the index is 
     * negative or is greater than the length, an IndexOutOfBoundsException 
     * is thrown.
     * @param index The index into which to insert the element
     * @param element The element which to insert
     */
    public void insert(int index, E element) {
    	if(index > size|| index < 0){
    		throw new IndexOutOfBoundsException();
    	}
    	if(size == 0){
    		internal.put(size, element);
    		size++;
    		return;
    	}
    	if(size == index){
    		internal.put(size, element);
    		size++;
    		return;
    	}
    	for(Iterator<Integer> it = internal.iterator(); it.hasNext();){
    		Integer in = it.next();
    		if(in.equals(index)){
    			E next = internal.get(in);
    			E item = next;
    			internal.put(in, element);
    			while(it.hasNext()){
    				in = it.next();
    				next = internal.get(in);
    				internal.put(in, item);
    				item = next;
    			}
    			internal.put(size, item);
    			size++;
    			return;
    		}
    	}
    }



    /**
     * Remove (and return) the element at the specified position.
     * This reduces the size of the list by one and, if necessary,
     * shifts other elements over. If the index is invalid, an 
     * IndexOutOfBoundsException is thrown.
     * @param index The index of the element to remove
     * @return The item removed
     */
   public E remove(int index) {
	   if(index >= size|| index < 0){
   		throw new IndexOutOfBoundsException();
	   }
	   
	   E toRemove = internal.get(index);
	   internal.remove(index);
	   size--;
	   for(int i = index; i< size+1; i++ ){
		   internal.put(i, internal.get(i+1));
	   }
	   return toRemove;
   }

   /**
    * Return the number of elements in this list.
    * @return The number of elements in this list.
    */
    public int size() {
        return size;
    }

}
