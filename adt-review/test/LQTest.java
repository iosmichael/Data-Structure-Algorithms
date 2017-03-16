package test;

import impl.ArrayMap;
import impl.ListQueue;
import impl.MapList;


public class LQTest extends QueueTest {

	protected void reset() {
		testQueue = new ListQueue<String>(new MapList<String>(new ArrayMap<Integer, String>()));
	}


}
