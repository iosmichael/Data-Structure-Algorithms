package test;

import static org.junit.Assert.*;
import impl.HeapPositionAware;
import impl.OptimizedHeapPriorityQueue;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Test;

import adt.PriorityQueue;

public class OptimizedHeapPriorityQueueTest {

    // --- for tests on int priority queues ---
    static class HPAInteger implements HeapPositionAware {

        final int val;
        int pos;
        
        HPAInteger(int val) { this.val = val; }
        
        public void setPosition(int pos) {
            this.pos = pos;
        }

        public int getPosition() {
            return pos;
        }

        public int intValue() {
            return val;
        }
        
        @Override
        public String toString() {
            return "<" + val + ">[" + pos + "]";
        }
    }

    static HPAInteger[] iArray = 
        { new HPAInteger(33), new HPAInteger(22), new HPAInteger(66), 
        new HPAInteger(99), new HPAInteger(11), new HPAInteger(88), 
        new HPAInteger(55), new HPAInteger(77), new HPAInteger(44)};

    protected PriorityQueue<HPAInteger> ipq;
    
    protected void resetIntEmpty() {
        ipq = new OptimizedHeapPriorityQueue<HPAInteger>(iArray.length, iCompo);
    }

    protected Comparator<HPAInteger> iCompo = new Comparator<HPAInteger>() {
        public int compare(HPAInteger o1, HPAInteger o2) {
            return o1.val - o2.val;
        }
    };
    
    
    // --- for tests on Widget priority queues ---
    protected static class Widget implements HeapPositionAware { 
        final int index;
        int pos;
        Widget(int index) { this.index = index; }
        public boolean equals(Object o) {
            return o instanceof Widget && ((Widget) o).index == index;
        }
        public void setPosition(int pos) {
            this.pos = pos;
        }

        public int getPosition() {
            return pos;
        }
        
        @Override
        public String toString() {
            return "<W" + index + ">[" + pos + "]";
        }
    }
    protected PriorityQueue<Widget> wpq;
    private static int[] priorities = { 10, 40, 20, 60, 30, 70, 80, 50, 90, 0 };
    protected static Iterable<Widget> itably = new Iterable<Widget>() {
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
    private static Widget[] wArray;
    static {
        wArray = new Widget[10];
        int i = 0;
        for (Widget w : itably)
            wArray[i++] = w;
    }
    private void wpRestore() {
        priorities[3] = 60;
        priorities[0] = 10;
    }
    protected Comparator<Widget> wCompo = new Comparator<Widget>() {
        public int compare(Widget o1, Widget o2) {
            return priorities[o1.index] - priorities[o2.index];
        }
    };
    

    protected void resetWidgetPopulated() {
        wpq = new OptimizedHeapPriorityQueue<Widget>(wArray, wCompo);
    }

    
    
    @Test
    public void testIInsert() {
        resetIntEmpty();
        for (int i = 0; i < 1; i++) 
            ipq.insert(iArray[i]);
         assertEquals(33, ipq.extractMax().intValue());  
    }
    
    @Test
    public void testIExtractMaxFew() {
        resetIntEmpty();
       for (int i = 0; i < 4; i++) {
            ipq.insert(iArray[i]);
            if (i % 3 == 1)
                ipq.extractMax().intValue();
        }
        assertEquals(99, ipq.extractMax().intValue());  
    }
    
    
    @Test
    public void testIExtractMaxMany() {
        resetIntEmpty();
        for (int i = 0; i < 7; i++) {
            ipq.insert(iArray[i]);
            if (i % 3 == 1)
                ipq.extractMax();
        }
        assertEquals(88, ipq.extractMax().intValue());  
    }
    
    @Test
    public void testIExtractMaxAll() {
        resetIntEmpty();
        for (int i = 0; i < iArray.length; i++) {
            ipq.insert(iArray[i]);
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
            assertTrue(wpq.contains(wArray[i]));
        
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
                assertTrue(wpq.contains(wArray[i]));
    }

    @Test
    public void testWIncreaseKeyToMax() {
        wpRestore();
        resetWidgetPopulated();
        priorities[3] = 99;
        //wpq.increaseKey(new Widget(3));
        wpq.increaseKey(wArray[3]);
        //assertEquals(wpq.max(), new Widget(3));
        assertEquals(wpq.max(), wArray[3]);
    }
    
    @Test
    public void testWIncreaseKeyToMed() {
        wpRestore();
        resetWidgetPopulated();
        priorities[0] = 55;
        //wpq.increaseKey(new Widget(0));
        wpq.increaseKey(wArray[0]);
        wpq.extractMax();
        wpq.extractMax();
        wpq.extractMax();
        wpq.extractMax();

        //assertEquals(wpq.max(), new Widget(0));
        assertEquals(wpq.max(), wArray[0]);
    }
    
    
}
