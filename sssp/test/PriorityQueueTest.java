package test;

import static org.junit.Assert.*;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Test;

import adt.PriorityQueue;

public abstract class PriorityQueueTest {

    // --- for tests on int priority queues ---
    int[] array = { 33, 22, 66, 99, 11, 88, 55, 77, 44};
    protected PriorityQueue<Integer> ipq;
    protected abstract void resetIntEmpty();
    protected Comparator<Integer> iCompo = new Comparator<Integer>() {
        public int compare(Integer o1, Integer o2) {
            return o1 - o2;
        }
    };
    
    
    // --- for tests on Widget priority queues ---
    protected class Widget { 
        final int index;
        Widget(int index) { this.index = index; }
        public boolean equals(Object o) {
            return o instanceof Widget && ((Widget) o).index == index;
        }
    }
    protected PriorityQueue<Widget> wpq;
    private int[] priorities = { 10, 40, 20, 60, 30, 70, 80, 50, 90, 0 };
    private void wpRestore() {
        priorities[3] = 60;
        priorities[0] = 10;
    }
    protected Comparator<Widget> wCompo = new Comparator<Widget>() {
        public int compare(Widget o1, Widget o2) {
            return priorities[o1.index] - priorities[o2.index];
        }
    };
    protected Iterable<Widget> itably = new Iterable<Widget>() {
        public Iterator<Widget> iterator() {
            return new Iterator<Widget>() {
                int i = 0;
                public boolean hasNext() {
                    return i < priorities.length;
                }

                public Widget next() {
                    if (! hasNext()) throw new NoSuchElementException();
                    return new Widget(i++);
                }

                public void remove() {
                    throw new UnsupportedOperationException();
                }

            };
        }
    };
    protected abstract void resetWidgetPopulated();

    
    
    @Test
    public void testIInsertOne() {
        resetIntEmpty();
        for (int i = 0; i < 1; i++) 
            ipq.insert(array[i]);
         assertEquals(33, ipq.extractMax().intValue());  
    }
    
    @Test
    public void testIInsertFew() {
        resetIntEmpty();
        for (int i = 0; i < 5; i++)
            ipq.insert(array[i]);
        assertEquals(99, ipq.extractMax().intValue());
    }
    
    @Test
    public void testIInsertFewStraightLine() {
        resetIntEmpty();
        for (int i = 0; i < 5; i++)
            ipq.insert(i);
        assertEquals(4, ipq.extractMax().intValue());
    }
    
    @Test
    public void testIExtractMaxFew() {
        resetIntEmpty();
       for (int i = 0; i < 4; i++) {
            ipq.insert(array[i]);
            if (i % 3 == 1)
                ipq.extractMax().intValue();
        }
        assertEquals(99, ipq.extractMax().intValue());  
    }
    
    
    @Test
    public void testIExtractMaxMany() {
        resetIntEmpty();
        for (int i = 0; i < 7; i++) {
            ipq.insert(array[i]);
            if (i % 3 == 1)
                ipq.extractMax();
        }
        assertEquals(88, ipq.extractMax().intValue());  
    }
    
    @Test
    public void testIExtractMaxAll() {
        resetIntEmpty();
        for (int i = 0; i < array.length; i++) {
            ipq.insert(array[i]);
            if (i % 3 == 1)
                ipq.extractMax();
        }
        assertEquals(77, ipq.extractMax().intValue());  
        assertEquals(66, ipq.extractMax().intValue());  
        assertEquals(55, ipq.extractMax().intValue());  
        assertEquals(44, ipq.extractMax().intValue());  
        assertEquals(22, ipq.extractMax().intValue());  
        assertEquals(11, ipq.extractMax().intValue());  
    }

    @Test
    public void testWFull() {
        wpRestore();
        resetWidgetPopulated();
        assertFalse(wpq.isEmpty());
        assertTrue(wpq.isFull());
    }
    
    @Test
    public void testWMed() {
        wpRestore();
        resetWidgetPopulated();
        wpq.extractMax();
        wpq.extractMax();
        wpq.extractMax();
        assertFalse(wpq.isEmpty());
        assertFalse(wpq.isFull());
    }
    
    @Test
    public void testWEmpty() {
        wpRestore();
        resetWidgetPopulated();
        wpq.extractMax();
        wpq.extractMax();
        wpq.extractMax();
        wpq.extractMax();
        wpq.extractMax();
        wpq.extractMax();
        wpq.extractMax();
        wpq.extractMax();
        wpq.extractMax();
        wpq.extractMax();
        assertTrue(wpq.isEmpty());
        assertFalse(wpq.isFull());
    }

    @Test
    public void testWMaxInitial() {
        wpRestore();
        resetWidgetPopulated();
        assertEquals(wpq.max(), new Widget(8));
    }
    
    @Test
    public void testWExtractMaxInitial() {
        wpRestore();
        resetWidgetPopulated();
        assertEquals(wpq.extractMax(), new Widget(8));
        
    }
    
    @Test
    public void testWMaxMed() {
        wpRestore();
        resetWidgetPopulated();
        wpq.extractMax();
        wpq.extractMax();
        wpq.extractMax();
        assertEquals(wpq.max(), new Widget(3));
    }

    @Test
    public void testWContainsInitial() {
        wpRestore();
        resetWidgetPopulated();
        for (int i = 0; i < priorities.length; i++) 
            assertTrue(wpq.contains(new Widget(i)));
        
    }

    @Test
    public void testWContainsMed() {
        wpRestore();
        resetWidgetPopulated();
        wpq.extractMax();
        wpq.extractMax();
        wpq.extractMax();
        for (int i = 0; i < priorities.length; i++)
            if (i == 5 || i == 6 || i == 8)
                assertFalse(wpq.contains(new Widget(i)));
            else 
                assertTrue(wpq.contains(new Widget(i)));
    }

    @Test
    public void testWIncreaseKeyToMax() {
        wpRestore();
        resetWidgetPopulated();
        priorities[3] = 99;
        wpq.increaseKey(new Widget(3));
        assertEquals(wpq.max(), new Widget(3));
    }
    
    @Test
    public void testWIncreaseKeyToMed() {
        wpRestore();
        resetWidgetPopulated();
        priorities[0] = 55;
        wpq.increaseKey(new Widget(0));
        wpq.extractMax();
        wpq.extractMax();
        wpq.extractMax();
        wpq.extractMax();

        assertEquals(wpq.max(), new Widget(0));
    }
    
    
}
