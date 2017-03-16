package test;

import impl.AVLBSTMap;

public class AVLBSTMTest extends MapTest {

    /**
     * Are we in debugging mode? 
     */
    public static boolean DEBUG = true;

    protected void reset() {
        AVLBSTMap.DEBUG = true;
        testMap = new AVLBSTMap<String,String>();
    }

}
