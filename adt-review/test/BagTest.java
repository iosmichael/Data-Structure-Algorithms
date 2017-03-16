package test;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.Random;

import org.junit.Test;

import adt.Bag;

public abstract class BagTest {

	protected Bag<String> testBag;

	private static Random randy = new Random(System.currentTimeMillis());
	
	private void populate() {
		for (int i = 0; i < data.length; i++)
			for (int j = i; j < data.length; j++)
				testBag.add(data[j]);
	}

	
	protected abstract void reset();
	
	protected String[] data = 
		{ "Augustus", "Tiberius", "Caligula", "Claudius", "Nero",
			"Galba", "Otho", "Vitellius", "Vespasian", "Titus",
			"Domitian", "Nerva", "Trajan", "Hadrian", "Antoninus Pius",
			"Marcus Aurelius", "Commodus" };
	
	// let's hope that the implicit static initializer statements
	// are executed in the order they appear in the file
	protected int[] marks = new int[data.length];
	
	protected void clearMarks() {
		for (int i = 0; i < marks.length; i++)
			marks[i] = 0;
	}

	protected int indexForDatum(String datum) {
		int index = -1;
		for (int i = 0; i < data.length && index == -1; i++)
			if (data[i].equals(datum))
				index = i;
		return index;
	}
	
	@Test
	public void initialEmpty() {
		reset();
		assertTrue(testBag.isEmpty());
	}

	@Test
	public void initialSize() {
		reset();
		assertEquals(0, testBag.size());
	}
	
	@Test
	public void addNotEmpty() {
		reset();
		testBag.add(data[0]);
		assertFalse(testBag.isEmpty());
	}

	@Test
	public void addSize() {
		reset();
		testBag.add(data[0]);
		assertEquals(1, testBag.size());
	}

	@Test
	public void twoAddsUniqueSize() {
		reset();
		testBag.add(data[0]);
		testBag.add(data[1]);
		assertFalse(testBag.isEmpty());
		assertEquals(2, testBag.size());
	}

	@Test
	public void twoAddsIdenticalSize() {
		reset();
		testBag.add(data[0]);
		testBag.add(data[0]);
		assertFalse(testBag.isEmpty());
		assertEquals(2, testBag.size());
	}

	@Test
	public void addsUnique() {
		reset();
		for (int i = 0; i < 8; i++) 
			testBag.add(data[i]);
		assertFalse(testBag.isEmpty());
		assertEquals(8, testBag.size());
	}
	
	@Test
	public void addsIdentical() {
		reset();
		for (int i = 0; i < 8; i++) 
			testBag.add(data[0]);
		assertFalse(testBag.isEmpty());
		assertEquals(8, testBag.size());
	}

	@Test
	public void addsMix() {
		reset();
		for (int i = 0; i < 50; i++)
			testBag.add(data[randy.nextInt(data.length)]);
		assertFalse(testBag.isEmpty());
		assertEquals(50, testBag.size());
	}

	@Test
	public void addsLots() {
		reset();
		populate();
		assertFalse(testBag.isEmpty());
		assertEquals(data.length*(data.length + 1)/2, testBag.size());
	}
	
	@Test
	public void countInitial() {
		reset();
		assertEquals(0, testBag.count(data[0]));
	}
	
	@Test
	public void countOne() {
		reset();
		testBag.add(data[0]);
		assertEquals(1, testBag.count(data[0]));
	}

	@Test
	public void countTwoUnique() {
		reset();
		testBag.add(data[0]);
		testBag.add(data[1]);
		assertEquals(1, testBag.count(data[0]));
		assertEquals(1, testBag.count(data[1]));
	}

	@Test
	public void countTwoIdentical() {
		reset();
		testBag.add(data[0]);
		testBag.add(data[0]);
		assertEquals(2, testBag.count(data[0]));
	}

	
	@Test
	public void countLots() {
		reset();
		populate();
		for (int i = 0; i < data.length; i++)
			assertEquals(i+1, testBag.count(data[i]));
	}

	@Test
	public void removeInitial() {
		reset();
		testBag.remove(data[0]);
		assertEquals(0, testBag.count(data[0]));
		assertEquals(0, testBag.size());
		assertTrue(testBag.isEmpty());
	}

	@Test
	public void removeSpurious() {
		reset();
		testBag.add(data[0]);
		testBag.add(data[1]);
		testBag.add(data[0]);
		testBag.remove(data[2]);
		assertEquals(2, testBag.count(data[0]));
		assertEquals(1, testBag.count(data[1]));
		assertEquals(0, testBag.count(data[2]));
		assertEquals(3, testBag.size());
		assertFalse(testBag.isEmpty());
	}

	@Test
	public void removeOnlySingleton() {
		reset();
		testBag.add(data[0]);
		testBag.remove(data[0]);
		assertEquals(0, testBag.count(data[0]));
		assertEquals(0, testBag.size());
		assertTrue(testBag.isEmpty());
	}

	@Test
	public void removeOnlyMultiple() {
		reset();
		testBag.add(data[0]);
		testBag.add(data[0]);
		testBag.add(data[0]);
		testBag.remove(data[0]);
		assertEquals(0, testBag.count(data[0]));
		assertEquals(0, testBag.size());
		assertTrue(testBag.isEmpty());
	}
		
	@Test
	public void removeSingletonAmongMany() {
		reset();
		testBag.add(data[0]);
		testBag.add(data[1]);
		testBag.add(data[2]);
		testBag.add(data[1]);
		testBag.add(data[3]);
		testBag.add(data[4]);
		testBag.add(data[1]);
		testBag.add(data[4]);
		testBag.remove(data[0]);
		assertEquals(0, testBag.count(data[0]));
		assertEquals(3, testBag.count(data[1]));
		assertEquals(1, testBag.count(data[2]));
		assertEquals(1, testBag.count(data[3]));
		assertEquals(2, testBag.count(data[4]));
		assertEquals(7, testBag.size());
		assertFalse(testBag.isEmpty());
	}

	@Test
	public void removeMultipleAmongMany() {
		reset();
		testBag.add(data[0]);
		testBag.add(data[1]);
		testBag.add(data[2]);
		testBag.add(data[1]);
		testBag.add(data[0]);
		testBag.add(data[3]);
		testBag.add(data[4]);
		testBag.add(data[1]);
		testBag.add(data[4]);
		testBag.add(data[0]);
		testBag.remove(data[0]);
		assertEquals(0, testBag.count(data[0]));
		assertEquals(3, testBag.count(data[1]));
		assertEquals(1, testBag.count(data[2]));
		assertEquals(1, testBag.count(data[3]));
		assertEquals(2, testBag.count(data[4]));
		assertEquals(7, testBag.size());
		assertFalse(testBag.isEmpty());
	}

	@Test
	public void addSameAfterRemove() {
		reset();
		testBag.add(data[0]);
		testBag.add(data[1]);
		testBag.add(data[2]);
		testBag.add(data[1]);
		testBag.add(data[0]);
		testBag.add(data[3]);
		testBag.add(data[4]);
		testBag.add(data[1]);
		testBag.add(data[4]);
		testBag.add(data[0]);
		testBag.remove(data[0]);
		testBag.add(data[0]);
		assertEquals(1, testBag.count(data[0]));
		assertEquals(3, testBag.count(data[1]));
		assertEquals(1, testBag.count(data[2]));
		assertEquals(1, testBag.count(data[3]));
		assertEquals(2, testBag.count(data[4]));
		assertEquals(8, testBag.size());
		assertFalse(testBag.isEmpty());
	}

	@Test
	public void addDifferentAfterRemove() {
		reset();
		testBag.add(data[0]);
		testBag.add(data[1]);
		testBag.add(data[2]);
		testBag.add(data[1]);
		testBag.add(data[0]);
		testBag.add(data[3]);
		testBag.add(data[4]);
		testBag.add(data[1]);
		testBag.add(data[4]);
		testBag.add(data[0]);
		testBag.remove(data[0]);
		testBag.add(data[4]);
		testBag.add(data[5]);
		assertEquals(0, testBag.count(data[0]));
		assertEquals(3, testBag.count(data[1]));
		assertEquals(1, testBag.count(data[2]));
		assertEquals(1, testBag.count(data[3]));
		assertEquals(3, testBag.count(data[4]));
		assertEquals(1, testBag.count(data[5]));
		assertEquals(9, testBag.size());
		assertFalse(testBag.isEmpty());
	}

	@Test 
	public void removeSomeOfMany() {
		reset();
		populate();
		testBag.remove(data[3]);
		testBag.remove(data[4]);
		testBag.remove(data[9]);
		for (int i = 0; i < data.length; i++)
			if (i == 3 || i == 4 || i == 9)
				assertEquals(0, testBag.count(data[i]));
			else
				assertEquals(i+1, testBag.count(data[i]));
		assertEquals(data.length*(data.length + 1)/2 - 19, testBag.size());
	}

	@Test
	public void removeAllOfMany() {
		reset();
		populate();
		for (int i = 0; i < data.length; i++)
			testBag.remove(data[i]);
		for (int i = 0; i < data.length; i++)
			assertEquals(0, testBag.count(data[i]));
		assertEquals(0, testBag.size());
	}

	@Test
	public void initialIterator() {
		reset();
		int i = 0;
		for (Iterator<String> it = testBag.iterator(); it.hasNext(); )
			i++;
		assertEquals(0, i);
	}

	@Test
	public void onlySingletonIterator() {
		reset();
		testBag.add(data[0]);
		clearMarks();
		for (Iterator<String> it = testBag.iterator(); it.hasNext(); )
			marks[indexForDatum(it.next())]++;
		assertEquals(1, marks[0]);
		for (int i = 1; i < marks.length; i++)
			assertEquals(0, marks[i]);
	}

	@Test
	public void onlyMultipleIterator() {
		reset();
		testBag.add(data[0]);
		testBag.add(data[0]);
		testBag.add(data[0]);
		clearMarks();
		for (Iterator<String> it = testBag.iterator(); it.hasNext(); )
			marks[indexForDatum(it.next())]++;
		assertEquals(3, marks[0]);
		for (int i = 1; i < marks.length; i++)
			assertEquals(0, marks[i]);
	}

	@Test
	public void manyPlannedIterator() {
		reset();
		populate();
		clearMarks();
		for (Iterator<String> it = testBag.iterator(); it.hasNext(); )
			marks[indexForDatum(it.next())]++;
		for (int i = 0; i < marks.length; i++)
			assertEquals(i+1, marks[i]);
	}

	@Test
	public void manyRandomIterator() {
		reset();
		for (int i = 0; i < 50; i++)
			testBag.add(data[randy.nextInt(data.length)]);
		clearMarks();
		for (Iterator<String> it = testBag.iterator(); it.hasNext(); )
			marks[indexForDatum(it.next())]++;
		for (int i = 0; i < marks.length; i++)
			assertEquals(testBag.count(data[i]), marks[i]);
	}

	@Test
	public void removeSomeIterator() {
		reset();
		populate();
		testBag.remove(data[3]);
		testBag.remove(data[4]);
		testBag.remove(data[9]);
		clearMarks();
		for (Iterator<String> it = testBag.iterator(); it.hasNext(); )
			marks[indexForDatum(it.next())]++;
		for (int i = 0; i < marks.length; i++)
			if (i == 3 || i == 4 || i == 9)
				assertEquals(0, marks[i]);
			else
				assertEquals(i+1, marks[i]);
	}

	@Test
	public void removeAllIterator() {
		reset();
		populate();
		for (int i = 0; i < data.length; i++)
			testBag.remove(data[i]);
		int i = 0;
		for (Iterator<String> it = testBag.iterator(); it.hasNext(); i++)
			it.next();
		assertEquals(0, i);
	}

	@Test
	public void addAfterRemoveIterator() {
		reset();
		populate();
		for (int i = 0; i < data.length; i++)
			testBag.remove(data[i]);
		testBag.add(data[0]);
		testBag.add(data[1]);
		testBag.add(data[0]);
		clearMarks();
		for (Iterator<String> it = testBag.iterator(); it.hasNext(); )
			marks[indexForDatum(it.next())]++;
		assertEquals(2, marks[0]);
		assertEquals(1, marks[1]);
		for (int i = 2; i < marks.length; i++)
			assertEquals(0, marks[i]);
	}
	
}

