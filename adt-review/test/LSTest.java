package test;

import impl.ArrayMap;
import impl.ListStack;
import impl.MapList;


public class LSTest extends StackTest {

	protected void reset() {
		testStack = new ListStack<String>(new MapList<String>(new ArrayMap<Integer,String>()));
	}


}
