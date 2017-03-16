package exper;

import impl.GraphFactory;
import adt.WeightedGraph;
import alg.KruskalMinSpanTree;
import alg.MinSpanTree;
import alg.OptimizedPrimMinSpanTree;
import alg.PrimMinSpanTree;

public class MSTExperiment {

    private static long runExperiment(MinSpanTree mstAlg, WeightedGraph g) {
     long fore = System.nanoTime();
     mstAlg.minSpanTree(g);
     long aft = System.nanoTime();
     return aft - fore;
    }
    
    public static void main(String[] args) {
        long kResultsL = 0,
                kResultsM = 0,
                pResultsL = 0,
                pResultsM = 0,
                oPResultsL = 0,
                oPResultsM = 0;
        
        MinSpanTree kruskal = new KruskalMinSpanTree();
        MinSpanTree prim = new PrimMinSpanTree();
        MinSpanTree optimizedPrim = new OptimizedPrimMinSpanTree();

        // untimed "practice" runs
        for (int i = 0; i < 100; i++){
            WeightedGraph gAL = GraphFactory.weightedUndirectedALGraphRandom(100, 500);
            runExperiment(prim, gAL);
            runExperiment(optimizedPrim, gAL);
            runExperiment(kruskal, gAL);
            WeightedGraph gAM = GraphFactory.weightedUndirectedAMGraphCopy(gAL);
            runExperiment(kruskal, gAM);
            runExperiment(prim, gAM);
            runExperiment(optimizedPrim, gAM);
        }
        
        
        for (int i = 0; i < 5; i++) {
            WeightedGraph gAL = GraphFactory.weightedUndirectedALGraphRandom(100, 500);
                        
            pResultsL += runExperiment(prim, gAL);
            oPResultsL += runExperiment(optimizedPrim, gAL);
            kResultsL += runExperiment(kruskal, gAL);

            //WeightedGraph gAM = GraphFactory.weightedUndirectedAMGraphCopy(gAL);
            WeightedGraph gAM =GraphFactory.weightedUndirectedAMGraphRandom(100, 500);
                    
            kResultsM += runExperiment(kruskal, gAM);
            pResultsM += runExperiment(prim, gAM);
            oPResultsM += runExperiment(optimizedPrim, gAM);
        }
        
        kResultsL /= 5;
        kResultsM /= 5;
        pResultsL /= 5;
        pResultsM /= 5;
        oPResultsL /= 5;
        oPResultsM /= 5;
          
        System.out.println("\t\tAdjList \tAdjMatrix");
        System.out.println("Kruskal:\t" + kResultsL + "\t\t" + kResultsM);
        System.out.println("Prim:\t\t" + pResultsL + "\t\t" + pResultsM);
        System.out.println("OptPrim:\t" + oPResultsL + "\t\t" + oPResultsM);
        
    }
    
}
