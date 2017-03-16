package test;

import alg.OptimizedPrimMinSpanTree;

public class OPMSTTest extends MinSpanTreeTest {

    protected void reset() {
        mstAlg = new OptimizedPrimMinSpanTree();
    }

}
