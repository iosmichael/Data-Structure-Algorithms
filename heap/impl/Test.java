package impl;

import java.util.Comparator;

public class Test {

    public static  Comparator<Integer> comps = new Comparator<Integer>() {
        public int compare(Integer o1, Integer o2) {
            return o1 - o2;
        }
    };
    
    public static void main(String[] args) {

        int[] array = { 33, 22, 66, 99, 11, 88, 55, 77, 44};

        if (args[0].equals("heapsort"))
            testHeapsort(array);
        else if (args[0].equals("pq"))
            testPQ(array);
        else if (args[0].equals("queue"))
            testQ(array);
        else if (args[0].equals("stack"))
            testStack(array);
    }

    static public void testHeapsort(int[] array) {
        HeapSorter.sort(array);
        for (int i = 0; i < array.length; i++)
            System.out.print(array[i] + " ");
        System.out.println("");
    }

    // add elements to the priority queue.
    // after 2, 5, and 8 insertions, extract something.
    // Finally, extract until there are no more.
    // Correct result should be 33, 99, 88, 77, 66, 55, 44, 22, 11.
    static public void testPQ(int[] array) {
        HeapPriorityQueue<Integer> pq = new HeapPriorityQueue<Integer>(array.length, comps);
        for (int i = 0; i < array.length; i++) {
            pq.insert(array[i]);
            if (i % 3 == 1)
                System.out.print(pq.extractMax() + " ");
        }
        while (! pq.isEmpty())
            System.out.print(pq.extractMax() + " ");
        System.out.println("");
    }

    // Correct result should be 33, 22, 66, 99, 11, 88, 55, 77, 44
    static public void testQ(int[] array) {
        PQQueue<Integer> q = new PQQueue<Integer>(array.length);
        for (int i = 0; i < array.length; i++) {
            q.enqueue(array[i]);
            if (i % 3 == 1)
                System.out.print(q.remove() + " ");
        }
        while (! q.isEmpty())
            System.out.print(q.remove() + " ");
        System.out.println("");        
    }

    // Correct result should be 22, 11, 77, 44, 55, 88, 99, 66, 33
    static public void testStack(int[] array) {
        PQStack<Integer> st = new PQStack<Integer>(array.length);
        for (int i = 0; i < array.length; i++) {
            st.push(array[i]);
            if (i % 3 == 1)
                System.out.print(st.pop() + " ");
        }
        while (! st.isEmpty())
            System.out.print(st.pop() + " ");
        System.out.println("");        
    }


}
