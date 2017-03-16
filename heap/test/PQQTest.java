package test;

import impl.PQQueue;

public class PQQTest extends QueueTest {

    protected void reset() {
        testQueue = new PQQueue<String>(20);
    }

}
