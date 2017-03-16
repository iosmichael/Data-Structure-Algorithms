package exper;

import impl.GraphFactory;
import adt.WeightedGraph;
import alg.BellmanFordSSSP;
import alg.DijkstraSSSP;
import alg.OptimizedDijkstraSSSP;
import alg.SSSP;

public class SSSPExperiment {

    private static long runExperiment(SSSP sspAlg, WeightedGraph g) {
     long fore = System.nanoTime();
     sspAlg.sssp(g, 0);
     long aft = System.nanoTime();
     return aft - fore;
    }
    
    public static void main(String[] args) {
        long bfResultsL = 0,
                bfResultsM = 0,
                dResultsL = 0,
                dResultsM = 0,
                oDResultsL = 0,
                oDResultsM = 0;
        
        SSSP bellmanFord = new BellmanFordSSSP();
        SSSP dijkstra = new DijkstraSSSP();
        SSSP optimizedDijkstra = new OptimizedDijkstraSSSP();
               
        // untimed "practice" runs
        for (int i = 0; i < 100; i++){
            WeightedGraph gAL = GraphFactory.weightedUndirectedALGraphRandom(100, 500);
            runExperiment(bellmanFord, gAL);
            runExperiment(dijkstra, gAL);
            runExperiment(optimizedDijkstra, gAL);
            
            WeightedGraph gAM = GraphFactory.weightedUndirectedAMGraphCopy(gAL);
            runExperiment(bellmanFord, gAM);
            runExperiment(dijkstra, gAM);
            runExperiment(optimizedDijkstra, gAM);
        }
        for (int i = 0; i < 5; i++) {
            WeightedGraph gAL = GraphFactory.weightedUndirectedALGraphRandom(100, 500);
            
            
            bfResultsL += runExperiment(bellmanFord, gAL);
            dResultsL += runExperiment(dijkstra, gAL);
            oDResultsL += runExperiment(optimizedDijkstra, gAL);

            WeightedGraph gAM = GraphFactory.weightedUndirectedAMGraphCopy(gAL);
            //WeightedGraph gAM =GraphFactory.weightedUndirectedAMGraphRandom(100, 500);
                    
            bfResultsM += runExperiment(bellmanFord, gAM);
            dResultsM += runExperiment(dijkstra, gAM);
            oDResultsM += runExperiment(optimizedDijkstra, gAM);
        }
        
        bfResultsL /= 5;
        bfResultsM /= 5;
        dResultsL /= 5;
        dResultsM /= 5;
        oDResultsL /= 5;
        oDResultsM /= 5;
          
        System.out.println("\t\tAdjList \tAdjMatrix");
        System.out.println("BellmanFord:\t" + bfResultsL + "\t" + bfResultsM);
        System.out.println("Dijkstra:\t" + dResultsL + "\t\t" + dResultsM);
        System.out.println("OptDijk:\t" + oDResultsL + "\t\t" + oDResultsM);
        
    }
    
}
