package test;

import impl.BasicRecursiveBSTMap;

public class BRBSTMTest extends MapTest {

    protected void reset() {
        testMap = new BasicRecursiveBSTMap<String, String>();
    }

}
