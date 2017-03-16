package test;

import static org.junit.Assert.*;
import impl.Heap;

import java.util.Comparator;

import org.junit.Test;

public class HeapifyTest { 

    protected Comparator<Integer> comps = new Comparator<Integer>() {
        public int compare(Integer o1, Integer o2) {
            return o1 - o2;
        }
    };

    private void heapify(final Integer[] array, final int i, final int size) {
        new Heap<Integer>() {
            void testHeapify() {
                internal = array;
                heapSize = size;
                compy = comps;
                heapify(i);
            }
        }.testHeapify();
    }
    
    @Test
    public void testTrivial() {
        Integer[] array = { 5 };
        heapify(array, 0, 1);
        assertEquals(array[0].intValue(), 5);
    }

    
    
    @Test
    public void testSmallAlreadyHeap() {
        Integer[] array = {13, 12, 5};
        heapify(array, 0, 3);
        assertEquals(array[0].intValue(), 13);
        assertEquals(array[1].intValue(), 12);
        assertEquals(array[2].intValue(), 5);
    }
   
    
    @Test
    public void testSmallHeapBothViolate() {
        Integer[] array = {5, 12, 13};
        heapify(array, 0, 3);
        assertEquals(array[0].intValue(), 13);
        assertEquals(array[1].intValue(), 12);
        assertEquals(array[2].intValue(), 5);
    }

    @Test
    public void testSmallHeapLeftViolates() {
        Integer[] array = {5, 12, 3};
        heapify(array, 0, 3);
        assertEquals(array[0].intValue(), 12);
        assertEquals(array[1].intValue(), 5);
        assertEquals(array[2].intValue(), 3);
    }

    @Test
    public void testSmallHeapRightViolates() {
        Integer[] array = {5, 2, 13};
        heapify(array, 0, 3);
        assertEquals(array[0].intValue(), 13);
        assertEquals(array[1].intValue(), 2);
        assertEquals(array[2].intValue(), 5);
    }

    @Test
    public void testMedRightViolatesThenLeft() {
        Integer[] array = {4, 3, 6, 2, 1, 5};
        heapify(array, 0, 6);
        assertEquals(array[0].intValue(), 6);
        assertEquals(array[1].intValue(), 3);
        assertEquals(array[2].intValue(), 5);
        assertEquals(array[3].intValue(), 2);
        assertEquals(array[4].intValue(), 1);
        assertEquals(array[5].intValue(), 4);
        
    }
    
    @Test
    public void testLargeFullAlreadyHeap() {
        Integer[] array = { 8, 4, 7, 3, 1, 6, 2};
        heapify(array, 0, 7);
        assertEquals(array[0].intValue(), 8);
        assertEquals(array[1].intValue(), 4);
        assertEquals(array[2].intValue(), 7);
        assertEquals(array[3].intValue(), 3);
        assertEquals(array[4].intValue(), 1);
        assertEquals(array[5].intValue(), 6);
        assertEquals(array[6].intValue(), 2);
    }

    @Test
    public void testLargeNonFullAlreadyHeap() {
        Integer[] array = { 8, 4, 7, 3, 1, 6, 2, 2};
        heapify(array, 0, 7);
        assertEquals(array[0].intValue(), 8);
        assertEquals(array[1].intValue(), 4);
        assertEquals(array[2].intValue(), 7);
        assertEquals(array[3].intValue(), 3);
        assertEquals(array[4].intValue(), 1);
        assertEquals(array[5].intValue(), 6);
        assertEquals(array[6].intValue(), 2);
        assertEquals(array[7].intValue(), 2);
    }

    @Test
    public void testGivenExample() {
        Integer[] array = {10, 13, 17, 11, 7, 3, 15, 1, 9, 5 };
        heapify(array, 0, 10);
        assertEquals(array[0].intValue(), 17);
        assertEquals(array[1].intValue(), 13);
        assertEquals(array[2].intValue(), 15);
        assertEquals(array[3].intValue(), 11);
        assertEquals(array[4].intValue(), 7);
        assertEquals(array[5].intValue(), 3);
        assertEquals(array[6].intValue(), 10);
        assertEquals(array[7].intValue(), 1);
        assertEquals(array[8].intValue(), 9);
        assertEquals(array[9].intValue(), 5);
    }

    @Test
    public void testIgnoreNoiseBelow() {
        Integer[] array = {10, 13, 17, 11, 7, 3, 15, 1, 9, 5, 71, 82, 3, 99, 1 };
        heapify(array, 0, 10);
        assertEquals(array[0].intValue(), 17);
        assertEquals(array[1].intValue(), 13);
        assertEquals(array[2].intValue(), 15);
        assertEquals(array[3].intValue(), 11);
        assertEquals(array[4].intValue(), 7);
        assertEquals(array[5].intValue(), 3);
        assertEquals(array[6].intValue(), 10);
        assertEquals(array[7].intValue(), 1);
        assertEquals(array[8].intValue(), 9);
        assertEquals(array[9].intValue(), 5);
        assertEquals(array[10].intValue(), 71);
        assertEquals(array[11].intValue(), 82);
        assertEquals(array[12].intValue(), 3);
        assertEquals(array[13].intValue(), 99);
        assertEquals(array[14].intValue(), 1);
    }

    @Test
    public void testNoiseWithin() {
        Integer[] array = { 2, 10, 99, 17, 13, 27, 81, 3, 15, 11 };
        heapify(array, 1, 10);
        assertEquals(array[0].intValue(), 2);
        assertEquals(array[1].intValue(), 17);
        assertEquals(array[2].intValue(), 99);
        assertEquals(array[3].intValue(), 15);
        assertEquals(array[4].intValue(), 13);
        assertEquals(array[5].intValue(), 27);
        assertEquals(array[6].intValue(), 81);
        assertEquals(array[7].intValue(), 3);
        assertEquals(array[8].intValue(), 10);
        assertEquals(array[9].intValue(), 11);

    }
}