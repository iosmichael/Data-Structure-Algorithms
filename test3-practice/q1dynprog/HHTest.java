package q1dynprog;

import static org.junit.Assert.*;

import org.junit.Test;

public class HHTest {

    @Test
    public void testOneJog() {
        assertEquals(21, HeroHall.bestTreasure(new int[] {1,  4,  10, 3}, 
                new int[] {8,2,4, 6}, 
                new int[] {6, 3, 2}));
    }
    
    @Test
    public void testGoStraight() {
        assertEquals(7, HeroHall.bestTreasure(new int[]{2, 2, 1,2},
                new int[]{1,3,1,1}, 
                new int[]{8,5,7}));
    }

    @Test
    public void testZigZag() {
        assertEquals(33, HeroHall.bestTreasure(new int[]{5,8,3,6}, 
                new int[]{9,2,16,1}, 
                new int[]{2,1,3}));
    }

    @Test
    public void testAntiGreedy() {
        assertEquals(55, HeroHall.bestTreasure(new int[]{5, 12, 1}, 
                new int[]{3, 2, 50}, 
                new int[]{3, 30}));
    }
}
