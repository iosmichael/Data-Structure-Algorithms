package test;

import impl.TraditionalRedBlackTreeMap;

public class TRBBSTMTest extends MapTest {

    protected void reset() {
        TraditionalRedBlackTreeMap.DEBUG = true;
        testMap = new TraditionalRedBlackTreeMap<String, String>();
    }

}
