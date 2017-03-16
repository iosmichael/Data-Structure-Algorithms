package test;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

import adt.Map;

public abstract class MapTest {

	protected Map<String,String> testMap;

	protected abstract void reset();
	
	private String[] data = {	
	 "Minnesota", "Minneapolis",
     "Texas", "Dallas",
     "Oregon", "Seattle",
     "New Jersey", "Newark",
     "Pennsylvania", "Philadelphia",
     "Massachusetts", "Springfield",
     "Arizona", "Tuscon",
     "Michigan", "Ann Arbor",
     "Ohio", "Cincinatti",
     "New York", "Buffalo",
     "Florida", "Orlando",
     "Colorado", "Boulder",
     "Alabama", "Jackson",
     "Kentucky", "Louisville",
     "Kanas", "Wichita",
     "Alaska", "Vasilia" };

	private String[] otherData = { "Wisconsin", "Oklahoma", "Washington" };
	
	protected void populate(int pairs) {
		for (int i = 0; i < pairs; i++)
			testMap.put(data[2 * i], data[2 * i + 1]);
	}

	/* testing containsKey */
	@Test
	public void emptyContainsKey() {
		reset();
		for (int i = 0; i < data.length; i += 2)
			assertFalse(testMap.containsKey(data[i]));
		for (int i = 0; i < otherData.length; i++)
			assertFalse(testMap.containsKey(otherData[i]));
	}

	/* testing put */
	@Test
	public void putContainsKey() {
		reset();
		populate(data.length / 2);
		for (int i = 0; i < data.length; i += 2)
			assertTrue(testMap.containsKey(data[i]));
		for (int i = 0; i < otherData.length; i++)
			assertFalse(testMap.containsKey(otherData[i]));
	}

	/* testing get */
	@Test
	public void emptyGet() {
		reset();
		for (int i = 0; i < data.length; i += 2)
			assertEquals(null, testMap.get(data[i]));
		for (int i = 0; i < otherData.length; i++)
			assertEquals(null, testMap.get(otherData[i]));
		
	}
	
	@Test
	public void putGet() {
		reset();
		populate(data.length / 2);
		for (int i = 0; i < data.length; i += 2)
			assertEquals(data[i+1], testMap.get(data[i]));
		for (int i = 0; i < otherData.length; i++)
			assertEquals(null, testMap.get(otherData[i]));
	}

	/* Testing replacement */

	@Test
	public void putReplace() {
		reset();
		populate(data.length / 2);
		testMap.put("Alaska", "Barrows");
		assertTrue(testMap.containsKey("Alaska"));
		for (int i = 0; i < data.length; i += 2)
			if (data[i].equals("Alaska"))
				assertEquals("Barrows", testMap.get(data[i]));
			else 
				assertEquals(data[i+1], testMap.get(data[i]));
		for (int i = 0; i < otherData.length; i++)
			assertEquals(null, testMap.get(otherData[i]));
	}

	/* Testing iterator */
	@Test
	public void emptyIterator() {
		reset();
		int i = 0;
		for (Iterator<String> it = testMap.iterator(); it.hasNext(); )
			i++;
		assertEquals(0, i);
	}

	@Test
	public void populatedIterator() {
		reset();
		populate(data.length/ 2);
		boolean[] founds = new boolean[data.length / 2];
		for (int i = 0; i < founds.length; i++)
			founds[i] = false;
		for (Iterator<String> it = testMap.iterator(); it.hasNext(); ) {
			String key = it.next();
			boolean foundIt = false;
			for (int i = 0; i < data.length && ! foundIt; i += 2) {
				if (data[i].equals(key)) {
					// key returned from iterator has right value in map
					assertEquals(data[i+1], testMap.get(key));
					// iterator hasn't returned this key before
					assertFalse("Repeated key: " + key, founds[i/2]);
					founds[i/2] = true;
					foundIt = true;
				}
			}
			// key returned by iterator was a real key (it was found in raw data)
			assertTrue("Extraneous key: " + key, foundIt);
		}
		for (int i = 0; i < founds.length; i++)
			assertTrue("Missed key: " + data[i*2], founds[i]);
	}

	@Test
	public void replacedIterator() {
		reset();
		populate(data.length / 2);
		testMap.put("Alaska", "Barrows");
		boolean[] founds = new boolean[data.length / 2];
		for (int i = 0; i < founds.length; i++)
			founds[i] = false;
		for (Iterator<String> it = testMap.iterator(); it.hasNext(); ) {
			String key = it.next();
			boolean foundIt = false;
			for (int i = 0; i < data.length && ! foundIt; i += 2) {
				if (data[i].equals(key)) {
					// key returned from iterator has right value in map
					if (key.equals("Alaska"))
						assertEquals("Barrows", testMap.get(key));
					else 
						assertEquals(data[i+1], testMap.get(key));
					// iterator hasn't returned this key before
					assertFalse("Repeated key: " + key, founds[i/2]);
					founds[i/2] = true;
					foundIt = true;
				}
			}
			// key returned by iterator was a real key (it was found in raw data)
			assertTrue("Extraneous key: " + key, foundIt);
		}
		for (int i = 0; i < founds.length; i++)
			assertTrue("Missed key: " + data[i*2], founds[i]);
	}

	/* Testing remove */
	@Test
	public void emptyRemove() {
		reset();
		testMap.remove("Alaska");
		for (int i = 0; i < data.length; i += 2)
			assertFalse(testMap.containsKey(data[i]));
		for (int i = 0; i < otherData.length; i++)
			assertFalse(testMap.containsKey(otherData[i]));
	}

	@Test
	public void populatedRemove() {
		reset();
		populate(data.length / 2);
		testMap.remove("Alaska");
		assertFalse(testMap.containsKey("Alaska"));
		for (int i = 0; i < data.length; i += 2)
			if (data[i].equals("Alaska"))
				assertEquals(null, testMap.get(data[i]));
			else
				assertEquals(data[i+1], testMap.get(data[i]));
		for (int i = 0; i < otherData.length; i++)
			assertEquals(null, testMap.get(otherData[i]));
	}
	
	@Test
	public void populatedRemoveSpurious() {
		reset();
		populate(data.length / 2);
		testMap.remove("Wisconsin");
		for (int i = 0; i < data.length; i += 2)
			assertEquals(data[i+1], testMap.get(data[i]));
		for (int i = 0; i < otherData.length; i++)
			assertEquals(null, testMap.get(otherData[i]));

	}

	@Test
	public void removeMiddleAndEnd() {
        reset();
        populate(4);
        testMap.remove("Texas");
        testMap.remove("New Jersey");
        assertTrue(testMap.containsKey(data[0]));
        assertFalse(testMap.containsKey(data[2]));
        assertTrue(testMap.containsKey(data[4]));
        assertFalse(testMap.containsKey(data[6]));
	    
	}



	@Test
	public void removeIterator() {
		reset();
		populate(data.length / 2);
		testMap.remove("Alaska");
		boolean[] founds = new boolean[(data.length / 2) - 1];
		for (int i = 0; i < founds.length; i++)
			founds[i] = false;
		for (Iterator<String> it = testMap.iterator(); it.hasNext(); ) {
			String key = it.next();
			boolean foundIt = false;
			for (int i = 0; i < founds.length && ! foundIt; i++) {
				if (data[2 * i].equals(key)) {
					// key returned from iterator has right value in map
					assertEquals(data[2 * i+1], testMap.get(key));
					// iterator hasn't returned this key before
					assertFalse("Repeated key: " + key, founds[i]);
					founds[i] = true;
					foundIt = true;
				}
			}
			// key returned by iterator was a real key (it was found in raw data)
			assertTrue("Extraneous key: " + key, foundIt);
		}
		for (int i = 0; i < founds.length; i++)
			assertTrue("Missed key: " + data[i*2], founds[i]);

	}
	
	@Test
	public void removeSomeIterator() {
	    reset();
	    populate(data.length / 2);
	    testMap.remove(data[4]);
	    for (int i = 0; i < data.length; i+=2)
	        if (i == 4)
	            assertFalse(testMap.containsKey(data[i]));
	        else
	            assertTrue(testMap.containsKey(data[i]));
	}	
	
	@Test
	public void putNull() {
	    reset();
	    testMap.put("Alice", "x");
	    testMap.put("Bob", null);
	    assertTrue(testMap.containsKey("Alice"));
	    assertTrue(testMap.containsKey("Bob"));
	    assertFalse(testMap.containsKey("Carol"));
	    assertEquals("x", testMap.get("Alice"));
	    assertEquals(null, testMap.get("Bob"));
	    assertEquals(null, testMap.get("Carol"));
	}

	@Test
	public void stressComparison() {
	    reset();
        populate(data.length / 2);
        char[] rawChars = { 'M', 'j', 'h', 'r'};
        rawChars[0] += 2;
        rawChars[1] -= 2;
        rawChars[2] += 1;
        rawChars[3] -= 3;
        String key = new String(rawChars);
        assertTrue(testMap.containsKey(key));
        assertEquals("Cincinatti", testMap.get(key));
	}
	
}
