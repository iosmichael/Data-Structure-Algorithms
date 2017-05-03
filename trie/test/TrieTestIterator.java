package test;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;

import org.junit.Test;

public class TrieTestIterator extends TrieTest {

    @Test
    public void initialIterator() {
        reset();
        int i = 0;
        for (Iterator<String> it = testSet.iterator(); it.hasNext(); )
            i++;
        assertEquals(0, i);
    }

    @Test
    public void onlySingletonIterator() {
        reset();
        testSet.add(data[0]);
        clearMarks();
        for (Iterator<String> it = testSet.iterator(); it.hasNext(); )
            marks[indexForDatum(it.next())]++;
        assertEquals(1, marks[0]);
        for (int i = 1; i < marks.length; i++)
            assertEquals(0, marks[i]);
    }

    @Test
    public void onlyMultipleIterator() {
        reset();
        testSet.add(data[0]);
        testSet.add(data[0]);
        testSet.add(data[0]);
        clearMarks();
        for (Iterator<String> it = testSet.iterator(); it.hasNext(); )
            marks[indexForDatum(it.next())]++;
        assertEquals(1, marks[0]);
        for (int i = 1; i < marks.length; i++)
            assertEquals(0, marks[i]);
    }

    @Test
    public void manyPlannedIterator() {
        reset();
        populate();
        clearMarks();
        for (Iterator<String> it = testSet.iterator(); it.hasNext(); )
            marks[indexForDatum(it.next())]++;
        for (int i = 0; i < marks.length; i++) 
            assertEquals(1, marks[i]);
    }

    @Test
    public void manyRandomIterator() {
        reset();
        for (int i = 0; i < 50; i++)
            testSet.add(data[randy.nextInt(data.length)]);
        clearMarks();
        for (Iterator<String> it = testSet.iterator(); it.hasNext(); )
            marks[indexForDatum(it.next())]++;
        for (int i = 0; i < marks.length; i++)
            assertEquals(testSet.contains(data[i])? 1 : 0, marks[i]);
    }

    @Test
    public void removeSomeIterator() {
        reset();
        populate();
        testSet.remove(data[3]);
        testSet.remove(data[4]);
        testSet.remove(data[9]);
        clearMarks();
        for (Iterator<String> it = testSet.iterator(); it.hasNext(); )
            marks[indexForDatum(it.next())]++;
        for (int i = 0; i < marks.length; i++)
            if (i == 3 || i == 4 || i == 9)
                assertEquals(0, marks[i]);
            else
                assertEquals(1, marks[i]);
    }

    @Test
    public void removeAllIterator() {
        reset();
        populate();
        for (int i = 0; i < data.length; i++)
            testSet.remove(data[i]);
        int i = 0;
        for (Iterator<String> it = testSet.iterator(); it.hasNext(); ) {
            it.next();
            i++;
        }
        assertEquals(0, i);
    }

    @Test
    public void addAfterRemoveIterator() {
        reset();
        populate();
        for (int i = 0; i < data.length; i++)
            testSet.remove(data[i]);
        testSet.add(data[0]);
        testSet.add(data[1]);
        testSet.add(data[0]);
        clearMarks();
        for (Iterator<String> it = testSet.iterator(); it.hasNext(); )
            marks[indexForDatum(it.next())]++;
        assertEquals(1, marks[0]);
        assertEquals(1, marks[1]);
        for (int i = 2; i < marks.length; i++)
            assertEquals(0, marks[i]);
        
    }
}
