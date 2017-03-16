package test;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import org.junit.Test;

import adt.FullContainerException;
import adt.Stack;

public abstract class StackTest {
	
	protected Stack<String> testStack;
	
	protected abstract void reset();
	
	protected String[] data = 
		{ "Augustus", "Tiberius", "Caligula", "Claudius", "Nero",
			"Galba", "Otho", "Vitellius", "Vespasian", "Titus",
			"Domitian", "Nerva", "Trajan", "Hadrian", "Antoninus Pius",
			"Marcus Aurelius", "Commodus" };

	protected void populate(int size) {
		for (int i = 0; i < size && i < data.length; i++)
			testStack.push(data[i]);
	}

	@Test
	public void initialEmpty() {
		reset();
		assertTrue(testStack.isEmpty());
	}
	
	@Test
	public void initialTopPop() {
		reset();
		boolean caught = false;
		try {
			testStack.pop();
		} catch(NoSuchElementException nsee) {
			caught = true;
		}
		assertTrue(caught);
		caught = false;
		try {
			testStack.top();
		} catch(NoSuchElementException nsee) {
			caught = true;
		}
		assertTrue(caught);
	}
	
	@Test
	public void plainPush() {
		reset();
		try {
			populate(6);
		} catch(FullContainerException fce) {
		}
	}

	@Test
	public void pushEmpty() {
		reset();
		try { 
			populate(1);
			assertFalse(testStack.isEmpty());
		} catch(FullContainerException fce) {
			assertTrue(testStack.isEmpty());
		}
		
	}
	
	@Test
	public void pushTop() {
		reset();
		try {
			populate(1);
			assertEquals(data[0], testStack.top());
		} catch(FullContainerException fce) {
		}
	}

	@Test
	public void pushesTop() {
		reset();
		try {
			populate(8);
			assertEquals(data[7], testStack.top());
		} catch(FullContainerException fce) {
		}
	}

    @Test
    public void pushesTop2() {
        reset();
        try {
            populate(5);
            assertEquals(data[4], testStack.top());
        } catch(FullContainerException fce) {
        }
    }

	@Test
	public void pushNonEmpty() {
		reset();
		try {
			populate(1);
			assertFalse(testStack.isEmpty());
		} catch(FullContainerException fce) {
			assertTrue(testStack.isEmpty());
		}

	}

	@Test
	public void pushesNonEmpty() {
		reset();
		try {
			populate(1);
			try {
				populate(8);
			} catch (FullContainerException fce) {
			}
			assertFalse(testStack.isEmpty());
		
		} catch(FullContainerException fce) {
			assertTrue(testStack.isEmpty());
		}
	}

	@Test
	public void pushTopTop() {
		reset();
		try {
			populate(1);
			testStack.top();
			assertEquals(data[0], testStack.top());
		}catch(FullContainerException fce) {
		}
	}
	
	@Test
	public void pushesTopTop() {
		reset();
		try {
			populate(8);
			testStack.top();
			assertEquals(data[7], testStack.top());
		}catch(FullContainerException fce) {
		}
		
	}
	
	@Test
	public void pushPop() {
		reset();
		try {
			populate(1);
			assertEquals(data[0], testStack.pop());
			assertTrue(testStack.isEmpty());
			boolean caught = false;
			try {
				testStack.top();
			} catch (NoSuchElementException nsee) {
				caught = true;
			}
			assertTrue(caught);
			caught = false;
			try {
				testStack.pop();
			} catch (NoSuchElementException nsee) {
				caught = true;
			}
		} catch(FullContainerException fce) {
		}
	}
	
	@Test
	public void pushesPop() {
		reset();
		try {
			populate(8);
			assertEquals(data[7], testStack.pop());
			assertFalse(testStack.isEmpty());
			assertEquals(data[6], testStack.top());
		} catch(FullContainerException fce) {
		}
	}

	@Test
	public void pushesPops() {
		reset();
		try {
			populate(8);
			for (int i = 7; i >= 0; i--) {
				assertFalse(testStack.isEmpty());
				assertEquals(data[i], testStack.top());
				assertEquals(data[i], testStack.pop());
			}
			assertTrue(testStack.isEmpty());
			boolean caught = false;
			try {
				testStack.top();
			} catch (NoSuchElementException nsee) {
				caught = true;
			}
			assertTrue(caught);
			caught = false;
			try {
				testStack.pop();
			} catch (NoSuchElementException nsee) {
				caught = true;
			}
			assertTrue(caught);
		} catch(FullContainerException fce) {
		}
		
	}
	
	@Test
	public void pushesPopsPushesPops() {
		reset();
		try {
			populate(6);
			for (int i = 5; i > 3; i--) {
				assertFalse(testStack.isEmpty());
				assertEquals(data[i], testStack.top());
				assertEquals(data[i], testStack.pop());
			}
			assertFalse(testStack.isEmpty());
			for (int i = 6; i < 10; i++)
				testStack.push(data[i]);
			for (int i = 9; i > 5; i--) {
				assertFalse(testStack.isEmpty());
				assertEquals(data[i], testStack.top());
				assertEquals(data[i], testStack.pop());
			}
			for (int i = 3; i >= 0; i--) {
				assertFalse(testStack.isEmpty());
				assertEquals(data[i], testStack.top());
				assertEquals(data[i], testStack.pop());
			}
			assertTrue(testStack.isEmpty());
			boolean caught = false;
			try {
				testStack.top();
			} catch (NoSuchElementException nsee) {
				caught = true;
			}
			assertTrue(caught);
			caught = false;
			try {
				testStack.pop();
			} catch (NoSuchElementException nsee) {
				caught = true;
			}
			assertTrue(caught);
			
		}catch(FullContainerException fce) {
		}
	}
	
}
