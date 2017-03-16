package test;

import alg.BellmanFordSSSP;

public class BFSSSPTest extends SSSPTest {

    protected void reset() {
        ssspAlg = new BellmanFordSSSP();
    }

}
