package q1adt;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

public class TestFastBag {

    private static String[] actualKeys = 
        { "Adam", "Beatrice", "Christopher", "Deborah",
                "Emmit", "Fuchia", "George", "Henrietta",
                "Ichabod", "Jasmine", "Karl", "Louisa",
                "Mike", "Nina", "Obed", "Petrina",
                "Quirinius", "Rachel", "Steve", "Trudy",
                "Ultimus", "Veronica", "Winston", "Xena",
                "Yelemis", "Ziporah"
        };


    private static int[] targetCounts = 
        { 5, 3, 1, 2, 0, 4, 5, 2, 3, 1, 1, 2, 4, 2,
        2, 2, 1, 4, 6, 4, 3, 2, 5, 3, 2, 1 
        };

    private static Iterator<String> keysInOrder() {
        return new Iterator<String>() {
            
            int i = 0;
            
            public boolean hasNext() {
                return i < actualKeys.length;
            }

            public String next() {
                return actualKeys[i++];
            }

			public void remove() {
				throw new UnsupportedOperationException();
			}

        };
    }
    
	@Test
	public void realAllEmpty() {
		Bag<String> testBag = new FastBag(keysInOrder(), actualKeys.length);
		for (String key : actualKeys)
			assertEquals(0, testBag.count(key));
	}

	@Test 
	public void spuriousAllEmpty() {
		Bag<String> testBag = new FastBag(keysInOrder(), actualKeys.length);
		assertEquals(0, testBag.count("Tintinabula"));
	}

	private void populate(Bag<String> testBag) {
	    for (int i = 0; i < targetCounts.length; i++)
	        for (int j = 0; j < targetCounts[i]; j++)
	            testBag.add(actualKeys[i]);
	}
	
	@Test 
	public void realPopulated() {
	    Bag<String> testBag = new FastBag(keysInOrder(), actualKeys.length);
	    populate(testBag);
	    for (int i = 0; i < actualKeys.length; i++)
	        assertEquals(targetCounts[i], testBag.count(actualKeys[i]));
	}
	
	@Test
	public void spuriousPopulated() {
	    Bag<String> testBag = new FastBag(keysInOrder(), actualKeys.length);
	    populate(testBag);
	    assertEquals(0, testBag.count("Tintinabula"));
	}

	@Test
	public void emptyCount() {
	    Bag<String> testBag = new FastBag(keysInOrder(), actualKeys.length);
	    assertEquals(0, testBag.size());
	}

	@Test
	public void populatedCount() {
	    Bag<String> testBag = new FastBag(keysInOrder(), actualKeys.length);
	    populate(testBag);
	    int total = 0;
	    for (int x : targetCounts)
	        total += x;
	    assertEquals(total, testBag.size());
	}
	
	@Test
	public void populatedRemove() {
	    Bag<String> testBag = new FastBag(keysInOrder(), actualKeys.length);
	    populate(testBag);
	    testBag.remove(actualKeys[5]);
	    testBag.remove(actualKeys[17]);
	    for (int i = 0; i < actualKeys.length; i++)
	        if (i == 5 || i == 17)
	            assertEquals(0, testBag.count(actualKeys[i]));
	        else
	            assertEquals(targetCounts[i], testBag.count(actualKeys[i]));
	       int total = 0 - targetCounts[5] - targetCounts[17];
	        for (int x : targetCounts)
	            total += x;
	        assertEquals(total, testBag.size());
	}

	private int[] findCounts(Iterator<String> it) {
	    int[] toReturn = new int[actualKeys.length];
	    while(it.hasNext()) {
	        String key = it.next();
	        int index = key.charAt(0) - 'A';
	        assertEquals(actualKeys[index], key);
	        toReturn[index] += 1;
	    }
	    return toReturn;
	}
	
	@Test
	public void emptyIterator() {
	    Bag<String> testBag = new FastBag(keysInOrder(), actualKeys.length);
	    int[] itResults = findCounts(testBag.iterator());
	    for (int x : itResults)
	        assertEquals(0, x);
	}

	@Test
	public void populatedIterator() {
	    Bag<String> testBag = new FastBag(keysInOrder(), actualKeys.length);
	    populate(testBag);
	    int[] itResults = findCounts(testBag.iterator());
	    for (int i = 0; i < itResults.length; i++)
	        assertEquals(targetCounts[i], itResults[i]);
	}	
	
	   @Test
	    public void removeIterator() {
	        Bag<String> testBag = new FastBag(keysInOrder(), actualKeys.length);
	        populate(testBag);
            testBag.remove(actualKeys[5]);
            testBag.remove(actualKeys[17]);
	        int[] itResults = findCounts(testBag.iterator());
	        for (int i = 0; i < itResults.length; i++)
	            if (i == 5 || i == 17)
	                assertEquals(0, itResults[i]);
	            else
	                assertEquals(targetCounts[i], itResults[i]);
	    }   
	    

}
