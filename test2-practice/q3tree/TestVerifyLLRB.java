package q3tree;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

public class TestVerifyLLRB {

    private static class ArrayIterator implements Iterator<Integer> {
        private int[] internal;
        private int i;
        public ArrayIterator(int[] internal) { 
            this.internal = internal; 
            i = 0;
        }
        
        public boolean hasNext() {
            return i < internal.length;
        }

        public Integer next() {
            return internal[i++];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    
    private boolean checkBlackHeight(RBNode node, Iterator<Integer> hit) {
        return node == null ||
                (checkBlackHeight(node.left, hit) &&
                 node.blackHeight == hit.next() &&
                 checkBlackHeight(node.right, hit));
    }
    
    

    
    @Test
    public void a() {
        RBNode x = RBNode.rbFactory("(.A.)");
        assertTrue(VerifyLLRB.verifyLLRB(x));
        checkBlackHeight(x, new ArrayIterator(new int[]{1}));
    }
    @Test
    public void b() {
        RBNode x = RBNode.rbFactory("((.A.)B(.C.))");
        assertTrue(VerifyLLRB.verifyLLRB(x));
        checkBlackHeight(x, new ArrayIterator(new int[]{1,2,1}));
   }
    @Test
    public void c() {
        RBNode x = RBNode.rbFactory("([.A.]B.)");
        assertTrue(VerifyLLRB.verifyLLRB(x));
        checkBlackHeight(x, new ArrayIterator(new int[]{0,1}));
   }

    @Test
    public void d() {
        RBNode x = RBNode.rbFactory("([(.A.)B(.C.)]D(.E.))");
        assertTrue(VerifyLLRB.verifyLLRB(x));
        checkBlackHeight(x, new ArrayIterator(new int[]{1,1,1,2,1}));
    }
    @Test
    public void e() {
        RBNode x = RBNode.rbFactory("([(.A.)B(.C.)]D([.E.]F.))");
        assertTrue(VerifyLLRB.verifyLLRB(x));
        checkBlackHeight(x, new ArrayIterator(new int[]{1,1,1,2,0,1}));
    }
    @Test
    public void f() {
        RBNode x = RBNode.rbFactory("([(.A.)B([.C.]D.)]E(.F.))");
        assertTrue(VerifyLLRB.verifyLLRB(x));
        checkBlackHeight(x, new ArrayIterator(new int[]{1,1,0,1,2,1}));
    }

    @Test
    public void g() {
        RBNode x = RBNode.rbFactory("((.A.)B.)");
        assertFalse(VerifyLLRB.verifyLLRB(x));
    }

    @Test
    public void h() {
        RBNode x = RBNode.rbFactory("([.A.]B(.C.))");
        assertFalse(VerifyLLRB.verifyLLRB(x));
    }

    @Test
    public void i() {
        RBNode x = RBNode.rbFactory("((.A.)B[(.C.)D(.E.)])");
        assertFalse(VerifyLLRB.verifyLLRB(x));
    }

    @Test
    public void j() {
        RBNode x = RBNode.rbFactory("([[.A.]B.]C.)");
        assertFalse(VerifyLLRB.verifyLLRB(x));
    }

    @Test
    public void k() {
        RBNode x = RBNode.rbFactory("([(.A.)B(.C.)]D([.E.]F[.G.]))");
        assertFalse(VerifyLLRB.verifyLLRB(x));
    }

    @Test
    public void l() {
        RBNode x = RBNode.rbFactory("([(.A.)B(.C.)]D([(.E.)F(.G.)H.))");
        assertFalse(VerifyLLRB.verifyLLRB(x));
    }


}
