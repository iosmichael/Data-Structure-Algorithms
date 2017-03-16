package impl;

import java.util.Iterator;
import java.util.NoSuchElementException;

import adt.DisjointSet;

public abstract class AbstractDisjointSet  implements DisjointSet {

    protected int[] id;
    protected int count;

    /**
     * Make a universe with the given size. Initially
     * each item is in its own set with itself as 
     * leader.
     * @param size The number of elements in the universe.
     */
    public AbstractDisjointSet(int size) {
        id = new int[size];
        for (int i = 0; i < id.length; i++)
            id[i] = i;
        count = size;
    }

    protected void checkIndex(int p) {
        if (p < 0 || p >= id.length)
            throw new NoSuchElementException();
    }

    /**
     * Test to see if these two elements are in the same
     * set.
     * @return True if p and q are in the same set, false
     * otherwise.
     * @throws NoSuchElementException If either element is
     * beyond the bounds of this universe
     */
    public boolean connected(int p, int q) {
        checkIndex(p);
        checkIndex(q);
    
        //works independently of rest of implementation
        return find(p) == find(q);  
    
        // optimized for this implementation
        //return id[p] == id[q];
    }

    /**
     * Determine the number of sets in the partition.
     * @return The number of equivalent classes.
     */
    public int count() {
        return count;
    }

    /**
     * Iterate through the elements in a set identified by
     * an element in that set.
     * @param p An element in the universe.
     * @return An Iterable that will return an Iterator that 
     * will iterate over all elements that the given one
     * is connected to, including the given one itself.
     */
    public Iterable<Integer> findAll(int p) {
        checkIndex(p);
        final int leader = find(p);
        return new Iterable<Integer>() {
            public Iterator<Integer> iterator() {
                int i = 0;
                while (i < id.length && find(i) != leader) i++;
                final int finalI = i;
                
                return new Iterator<Integer>() {
    
                    int next = finalI;
                    
                    public boolean hasNext() {
                        return next < id.length;
                    }
    
                    public Integer next() {
                        int toReturn = next;
                        do next++;
                        while (next < id.length && find(next) != leader);
                        return toReturn;
                    }
    
                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                    
                };
            }
        };
    }

    /**
     * Iterate through all the leaders (and hence the sets)
     * of this universe.
     */
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {
    
            // invariant: nextIndex is the first
            // index into id that is part of a set
            // whose leader has not yet been returned,
            // or id.length if there are none
            int nextIndex = 0;
    
            // invariant: used[i] iff i has been returned
            // as the leader of a set
            boolean[] used = new boolean[id.length];
    
            public boolean hasNext() {
                return nextIndex < id.length;
            }
    
            public Integer next() {
                // do we have something to return?
                if (! hasNext())
                    throw new NoSuchElementException();
                // what we're going to return:
                // the leader of the next set
                int toReturn = find(nextIndex);
                // mark that one as having been used already
                used[toReturn] = true;
                // maintain the invariant on toReturn, preparing
                // for the next call to hasNext() or next()
                // by moving toReturn ahead until it's at the next
                // index that has an unused leader, or at the end
                // of the array.
                do nextIndex++;
                while(nextIndex < id.length && used[find(nextIndex)]);
                // return
                return toReturn;
            }
    
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

}