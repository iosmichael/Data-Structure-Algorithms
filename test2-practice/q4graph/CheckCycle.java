package q4graph;

/**
 * CheckCycle
 * 
 * Placeholder class for the static method checkCycle() which
 * tests whether a directed graph has a cycle that includes a given
 * vertex.
 * 
 * CSCI 345
 * Test 2 Practice Problem 4.
 */
public class CheckCycle {

    /**
     * Test to see if the given directed graph has a cycle that
     * includes the given vertex (start); or, equivalently,
     * tests to see if there exists a path from start back
     * to itself. The "trivial cycle" of zero edges that 
     * exists for each node doesn't count. There must be
     * at least one edge in the cycle. Note, however, that if start
     * has a self loop, that counts as a cycle of length 1.)
     * @param g The directed graph in which to look for a cycle
     * @param start The vertex from which to start (and finish) the search
     * @return True if the graph contains a cycle involving the given
     * vertex, false otherwise.
     */
    public static boolean checkCycle(Graph g, int start) {
    	ListStack<Integer> stack = new ListStack<Integer>();
    	int[] visits = new int[g.numVertices()];
    	stack.push(start);
    	while(!stack.isEmpty()){
    		int u = stack.pop();
    		for(int v : g.adjacents(u)){
    			if(v == start) return true;
    			if(visits[v] == 0){
    				visits[v]++;
        			stack.push(v);	
    			}
    		}
    	}
    	return false;
    }
    
}
