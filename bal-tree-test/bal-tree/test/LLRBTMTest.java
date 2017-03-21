package test;

import impl.LLRedBlackTreeMap;

public class LLRBTMTest extends MapTest {

    protected void reset() {
        testMap = new LLRedBlackTreeMap<String, String>();
    }

}
