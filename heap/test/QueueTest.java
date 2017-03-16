package test;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import org.junit.Test;

import adt.FullContainerException; 
import adt.Queue;

public abstract class QueueTest {
	protected Queue<String> testQueue;
	
	protected abstract void reset();
	
	protected String[] data = 
		{ "Augustus", "Tiberius", "Caligula", "Claudius", "Nero",
			"Galba", "Otho", "Vitellius", "Vespasian", "Titus",
			"Domitian", "Nerva", "Trajan", "Hadrian", "Antoninus Pius",
			"Marcus Aurelius", "Commodus" };

	protected void populate(int size) {
		for (int i = 0; i < size && i < data.length; i++)
			testQueue.enqueue(data[i]);
	}

	@Test
	public void initialEmpty() {
		reset();
		assertTrue(testQueue.isEmpty());
	}
	
	@Test
	public void initialFrontRemove() {
		reset();
		boolean caught = false;
		try {
			testQueue.remove();
		} catch(NoSuchElementException nsee) {
			caught = true;
		}
		assertTrue(caught);
		caught = false;
		try {
			testQueue.front();
		} catch(NoSuchElementException nsee) {
			caught = true;
		}
		assertTrue(caught);
	}
	
	@Test
	public void plainEnqueue() {
		reset();
		try {
			populate(6);
		} catch(FullContainerException fce) {
		}
	}

	@Test
	public void enqueueEmpty() {
		reset();
		try {
			populate(1);
			assertFalse(testQueue.isEmpty());
		} catch(FullContainerException fce) {
			assertTrue(testQueue.isEmpty());
		}
	}
	
	@Test
	public void enqueueFront() {
		reset();
		try {
			populate(1);
			assertEquals(data[0], testQueue.front());
		} catch(FullContainerException fce) {
		}
	}

	@Test
	public void enqueuesfront() {
		reset();
		try {
			populate(8);
			assertEquals(data[0], testQueue.front());
		} catch(FullContainerException fce) {
		}
	}

	@Test
	public void enqueueNonEmpty() {
		reset();
		try {
			populate(1);
			assertFalse(testQueue.isEmpty());
		} catch(FullContainerException fce) {
			assertTrue(testQueue.isEmpty());
		}

	}

	@Test
	public void enqueuesNonEmpty() {
		reset();
		try {
			populate(1);
			try {
				populate(8);
			} catch (FullContainerException fce) {
			}
			assertFalse(testQueue.isEmpty());
		
		} catch(FullContainerException fce) {
			assertTrue(testQueue.isEmpty());
		}
	}

	@Test
	public void enqueueFrontFront() {
		reset();
		try {
			populate(1);
			testQueue.front();
			assertEquals(data[0], testQueue.front());
		}catch(FullContainerException fce) {
		}
	}
	
	@Test
	public void enqueuesFrontFront() {
		reset();
		try {
			populate(8);
			testQueue.front();
			assertEquals(data[0], testQueue.front());
		}catch(FullContainerException fce) {
		}
		
	}
	
	@Test
	public void enqueueRemove() {
		reset();
		try {
			populate(1);
			assertEquals(data[0], testQueue.remove());
			assertTrue(testQueue.isEmpty());
			boolean caught = false;
			try {
				testQueue.front();
			} catch (NoSuchElementException nsee) {
				caught = true;
			}
			assertTrue(caught);
			caught = false;
			try {
				testQueue.remove();
			} catch (NoSuchElementException nsee) {
				caught = true;
			}
		} catch(FullContainerException fce) {
		}
	}
	
	@Test
	public void enqueuesRemove() {
		reset();
		try {
			populate(8);
			assertEquals(data[0], testQueue.remove());
			assertFalse(testQueue.isEmpty());
			assertEquals(data[1], testQueue.front());
		} catch(FullContainerException fce) {
		}
	}

	@Test
	public void enqueuesRemoves() {
		reset();
		try {
			populate(8);
			for (int i = 0; i < 8; i++) {
				assertFalse(testQueue.isEmpty());
				assertEquals(data[i], testQueue.front());
				assertEquals(data[i], testQueue.remove());
			}
			assertTrue(testQueue.isEmpty());
			boolean caught = false;
			try {
				testQueue.front();
			} catch (NoSuchElementException nsee) {
				caught = true;
			}
			assertTrue(caught);
			caught = false;
			try {
				testQueue.remove();
			} catch (NoSuchElementException nsee) {
				caught = true;
			}
			assertTrue(caught);
		} catch(FullContainerException fce) {
		}
		
	}
	
	@Test
	public void enqueuesRemovesEnqueuesRemoves() {
		reset();
		try {
			populate(6);
			for (int i = 0; i < 2; i++) {
				assertFalse(testQueue.isEmpty());
				assertEquals(data[i], testQueue.front());
				assertEquals(data[i], testQueue.remove());
			}
			assertFalse(testQueue.isEmpty());
			for (int i = 6; i < 10; i++)
				testQueue.enqueue(data[i]);
			for (int i = 2; i < 10; i++) {
				assertFalse(testQueue.isEmpty());
				assertEquals(data[i], testQueue.front());
				assertEquals(data[i], testQueue.remove());
			}
			assertTrue(testQueue.isEmpty());
			boolean caught = false;
			try {
				testQueue.front();
			} catch (NoSuchElementException nsee) {
				caught = true;
			}
			assertTrue(caught);
			caught = false;
			try {
				testQueue.remove();
			} catch (NoSuchElementException nsee) {
				caught = true;
			}
			assertTrue(caught);
			
		}catch(FullContainerException fce) {
		}
	}

	@Test
	public void testFull() {
	    reset();
	    boolean caught = false;
        populate(data.length);
        testQueue.enqueue("Severus");
        testQueue.enqueue("Caracalla");
        testQueue.enqueue("Macrinus");
	    try {
	        testQueue.enqueue("Elagabalus");
	    } catch (FullContainerException fce) {
	        caught = true;
	    }
	    assertTrue(caught);
	}


}
