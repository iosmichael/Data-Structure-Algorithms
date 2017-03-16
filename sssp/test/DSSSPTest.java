package test;

import alg.DijkstraSSSP;

public class DSSSPTest extends SSSPTest {

    protected void reset() {
        ssspAlg = new DijkstraSSSP();
    }

}
