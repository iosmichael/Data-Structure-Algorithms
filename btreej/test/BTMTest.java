package test;

import impl.BTreeMap;

public class BTMTest extends MapTest {

    protected void reset() {
        testMap = new BTreeMap<String, String>(10);
    }
    
}
