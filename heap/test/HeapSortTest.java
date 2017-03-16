package test;

import static org.junit.Assert.*;
import impl.HeapSorter;

import org.junit.Before;
import org.junit.Test;

public class HeapSortTest extends HeapifyTest {

    static boolean isSorted(int[] array) {
        boolean inOrderSoFar = true;
        for (int i = 1; inOrderSoFar && i < array.length; i++)
            inOrderSoFar &= array[i] >= array[i-1];
        return inOrderSoFar;
    }
    
    @Test
    public void testSelf() {
        assertTrue(isSorted(new int[0]));
        int[] array1 = {1};
        assertTrue(isSorted(array1));
        int[] array2 = {1, 2};
        assertTrue(isSorted(array2));
        int[] array3 = {1, 2, 3, 4, 5, 6, 7};
        assertTrue(isSorted(array3));
        int[] array4 = {1, 10, 10, 14, 91, 203, 203, 203, 999};
        assertTrue(isSorted(array4));
        int[] array5 = { 2, 6, 4 };
        assertFalse(isSorted(array5));        
    }
    
    @Test
    public void testEmpty() {
        HeapSorter.sort(new int[0]);
    }

    @Test
    public void testSizeOne() {
        int[] array = { 5 };
        HeapSorter.sort(array);
        assertTrue(isSorted(array));
    }
    
    @Test
    public void testNonTrivial() {
        int[] array = { 33, 22, 66, 99, 11, 88, 55, 77, 44 };
        HeapSorter.sort(array);
        assertTrue(isSorted(array));
    }
    

    
    
}
