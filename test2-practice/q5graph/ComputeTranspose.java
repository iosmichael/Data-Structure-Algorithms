package q5graph;

/**
 * ComputeTranspose
 * 
 * Placeholder class for the static method computeTranspose() which
 * compute the transpose of a given directed graph.
 * 
 * CSCI 345
 * Test 2 Practice Problem 5.
 */
public class ComputeTranspose {

    /**
     * Compute the transpose graph of the given directed graph g.
     * That is, build a new graph with the same number of
     * vertices as g but only those edges that do not exist
     * in g.
     * @param g The directed graph to compute the transpose of
     * @return The transpose of the given graph
     */
    public static AdjListGraph computeTranspose(AdjListGraph g) {
    	AdjListGraph.ALGBuilder builder = new AdjListGraph.ALGBuilder(g.numVertices());
    	int [] visits = new int [g.numVertices()];
    	LinkedList<Integer> list = new LinkedList<Integer>();
    	list.add(0);
    	visits[0]++;
    	while(list.size()!=0){
    		int u = list.remove(0);
    		for(int v: g.adjacents(u)){
    			if(visits[v]==0){
    				list.add(v);
    				visits[v]++;
    			}
    			builder.connect(v, u);
    		}
    	}
    	return builder.getGraph();
    }
}
