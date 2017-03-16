package test;

import static org.junit.Assert.assertEquals;
import impl.GraphFactory;

import org.junit.Test;

import adt.WeightedGraph;
import adt.WeightedGraph.WeightedEdge;
import alg.SSSP;

public abstract class SSSPTest {

    protected SSSP ssspAlg;
    
    protected abstract void reset();
    
    public void runSSSP(int start, double correctWeight) {
        reset();
        WeightedGraph g = GraphFactory.weightedDirectedALGraphFromFile("tinyEWD.txt");
        double totalWeight = 0.0;
        
        for (WeightedEdge e : ssspAlg.sssp(g, start)) {
            //System.out.println(e.first + " " + e.second + " " + e.weight);
            totalWeight += e.weight;
        }
        
        assertEquals(correctWeight, totalWeight, 0.01);

    }
    
    @Test
    public void tinyGraph0() {
        runSSSP(0, 2.56);
    }
    @Test
    public void tinyGraph1() {
        runSSSP(1, 3.34);
    }
    @Test
    public void tinyGraph2() {
        runSSSP(2, 2.78);
    }
    @Test
    public void tinyGraph3() {
        runSSSP(3, 3.37);
    }
    @Test
    public void tinyGraph4() {
        runSSSP(4, 2.93);
    }
    @Test
    public void tinyGraph5() {
        runSSSP(5, 2.74);
    }
    @Test
    public void tinyGraph6() {
        runSSSP(6, 3.24);
    }
    @Test
    public void tinyGraph7() {
        runSSSP(7, 2.84);
    }

}
