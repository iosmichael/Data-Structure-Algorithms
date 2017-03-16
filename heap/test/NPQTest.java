package test;

import impl.NaivePriorityQueue;

public class NPQTest extends PriorityQueueTest {

    protected void resetIntEmpty() {
        ipq = new NaivePriorityQueue<Integer>(array.length, iCompo);
    }

    protected void resetWidgetPopulated() {
        wpq = new NaivePriorityQueue<Widget>(itably, wCompo);
    }

}
