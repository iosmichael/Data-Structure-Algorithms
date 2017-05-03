package q2dynprog;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestLIS {

    @Test
    public void testIncludeFirst() {
        assertEquals(5, LongestIncreasingSubsequence.longIncrSubsq(new int[]{7, 15, 13, 8, 3, 11, 2, 0, 14, 5, 12, 4, 17}));
    }

    @Test
    public void testDoesntIncludeFirst() {
        assertEquals(5, LongestIncreasingSubsequence.longIncrSubsq(new int[]{8, 3, 21, 6, 19, 9, 17, 12, 15}));
    }
    
    @Test
    public void testAllIncreasing() {
        assertEquals(8, LongestIncreasingSubsequence.longIncrSubsq(new int[] {2, 5, 6, 12, 18, 19, 22, 36}));
    }
    
    @Test
    public void testAllDecreasing() {
        assertEquals(1, LongestIncreasingSubsequence.longIncrSubsq(new int[] {34, 22, 18, 17, 12, 9, 6, 4, 2}));
    }
    
}
