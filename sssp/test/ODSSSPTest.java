package test;

import alg.OptimizedDijkstraSSSP;

public class ODSSSPTest extends SSSPTest {

    protected void reset() {
        ssspAlg = new OptimizedDijkstraSSSP();
    }

}
