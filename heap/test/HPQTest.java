package test;

import impl.HeapPriorityQueue;

public class HPQTest extends PriorityQueueTest {

    protected void resetIntEmpty() {
        ipq = new HeapPriorityQueue<Integer>(array.length, iCompo);
    }

    protected void resetWidgetPopulated() {
        wpq = new HeapPriorityQueue<Widget>(itably, wCompo); 
    }

}
