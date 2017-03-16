package test;

import impl.SortedPriorityQueue;
import test.PriorityQueueTest.Widget;

public class SPQTest extends PriorityQueueTest {

    protected void resetIntEmpty() {
        ipq = new SortedPriorityQueue<Integer>(array.length, iCompo);
    }

    protected void resetWidgetPopulated() {
        wpq = new SortedPriorityQueue<Widget>(itably, wCompo);
    }

}
