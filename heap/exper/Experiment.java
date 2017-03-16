package exper;

import impl.HeapPriorityQueue;
import impl.NaivePriorityQueue;
import impl.SortedPriorityQueue;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

import adt.PriorityQueue;

/**
 * Experiment
 * 
 * Program to demonstrate the relative performance of a heap
 * implementation of a priority queue vs naive and sorted-list 
 * implementations.
 * 
 * CSCI 345, Wheaton College
 * Spring 2016
 */
public class Experiment {

    /**
     * Random number generator.
     */
    private static Random randy = new Random();
    
    /**
     * Make a random sequence of integers.
     * @param size The size of the sequence to make
     * @param range The range of values for the sequence
     * @return An array of the given size filled with 
     * random integers in the indicated range.
     */
    private static int[] randomSeq(int size, int range) {
        int[] toReturn = new int[size];
        for (int i = 0; i < toReturn.length; i++)
            toReturn[i] = randy.nextInt(range);
        return toReturn;
    }

    /**
     * A comparator for integers.
     */
    private static Comparator<Integer> compInt = new Comparator<Integer> () {
        public int compare(Integer o1, Integer o2) {
            return o2 - o1;
        }
    };

    /**
     * Make an iterable out of an array.
     * @param array The array to be iterable
     * @return An iterable wrapper for the array
     */
    private static Iterable<Integer> array2It(final int[] array){
        return new Iterable<Integer>() {
            public Iterator<Integer> iterator() {
                return new Iterator<Integer>() {
                    int i = 0;
                    public boolean hasNext() {
                        return i < array.length;
                    }
                    public Integer next() {
                        if (i < array.length)
                            return array[i++];
                        else
                            throw new NoSuchElementException();
                    }
                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                };
            }
        };
    }

    public static void main(String[] args) {
        // Results (runtime in nanoseconds) for naive, sorted-list,
        // and heap implementations
        long npqResults = 0,
                spqResults = 0,
                hpqResults = 0;
        long fore, aft;
        
        int size = 5000;
        int range = 10000;

        // Experiment 1: How does their performance compare
        // in the case where the entire sequence of numbers is
        // given initially?
        PriorityQueue<Integer> pq;
        for (int i = 0; i < 5; i++) {
            int[] vals = randomSeq(size, range);

            fore = System.nanoTime();
            pq = new NaivePriorityQueue<Integer>(array2It(vals), compInt);
            while (! pq.isEmpty())
                pq.extractMax();
            aft = System.nanoTime();
            npqResults += aft-fore;

            fore = System.nanoTime();
            pq = new SortedPriorityQueue<Integer>(array2It(vals), compInt);
            while (! pq.isEmpty())
                pq.extractMax();
            aft = System.nanoTime();
            spqResults += aft-fore;

            fore = System.nanoTime();
            pq = new HeapPriorityQueue<Integer>(array2It(vals), compInt);
            while (! pq.isEmpty())
                pq.extractMax();
            aft = System.nanoTime();
            hpqResults += aft-fore;
        }

        npqResults /= 5;
        spqResults /= 5;
        hpqResults /= 5;
        
        System.out.println("All given initially:");
        System.out.println("Naive: \t\t" + npqResults);
        System.out.println("Sorted: \t" + spqResults);
        System.out.println("Heap: \t\t" + hpqResults);

        
        // Experiment 2: How does performance compare if we mix
        // insertions and extractions?
        npqResults = 0;
        spqResults = 0;
        hpqResults = 0;

        for (int i = 0; i < 5; i++) {
            int[] vals = randomSeq(size, range);

            pq = new NaivePriorityQueue<Integer>(size, compInt);
            fore = System.nanoTime();
            for (int j = 0; j < size; j+=2) {
                pq.insert(vals[j]);
                pq.insert(vals[j+1]);
                pq.extractMax();
            }
            aft = System.nanoTime();
            npqResults += aft-fore;
            
            pq = new SortedPriorityQueue<Integer>(size, compInt);
            fore = System.nanoTime();
            for (int j = 0; j < size; j+=2) {
                pq.insert(vals[j]);
                pq.insert(vals[j+1]);
                pq.extractMax();
            }
            aft = System.nanoTime();
            spqResults += aft-fore;
           
            pq = new HeapPriorityQueue<Integer>(size, compInt);
            fore = System.nanoTime();
            for (int j = 0; j < size; j+=2) {
                pq.insert(vals[j]);
                pq.insert(vals[j+1]);
                pq.extractMax();
            }
            aft = System.nanoTime();
            hpqResults += aft-fore;
        }
        
        npqResults /= 5;
        spqResults /= 5;
        hpqResults /= 5;
        
        System.out.println("Mixed insert and extract:");
        System.out.println("Naive: \t\t" + npqResults);
        System.out.println("Sorted: \t" + spqResults);
        System.out.println("Heap: \t\t" + hpqResults);

    }

        
        
}
