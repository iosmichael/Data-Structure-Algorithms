package alg;

import java.util.Comparator;

import impl.HeapPositionAware;

/**
 * HPAVertexRecord
 * 
 * Simple class to represent the record of a vertex
 * for use in Prim's algorithm and Dijkstra's algorithm
 * in a priority queue, modified so that it is aware
 * of its position in a heap.
 * 
 * @author Thomas VanDrunen
 * CSCI 345, Wheaton College
 * June 24, 2015
 */
class HPAVertexRecord implements HeapPositionAware {
    
    
    public static class VRComparator implements Comparator<HPAVertexRecord>{
        public int compare(HPAVertexRecord u, HPAVertexRecord v) {
            return Double.compare(v.distance, u.distance);
        }
        
    }
    
    /**
     * Which vertex is this?
     */
    final int id;

    /**
     * The (current) upper bound on distance.
     */
    double distance;

    /**
     * Where this thing is in the priority queue
     */
    private int pos;

    HPAVertexRecord(int id, double distance) {
        this.id = id;
        this.distance = distance;
    }

    public void setPosition(int pos) {
        this.pos = pos;
    }
    public int getPosition() {
        return pos;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
    
    public double getDistance() { return distance; }}