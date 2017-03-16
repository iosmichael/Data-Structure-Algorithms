package impl;

import java.util.Iterator;

import adt.Map;
import adt.Set;

public class BasicHashSet<E> implements Set<E> {

    private Map<E, Object> internal;
    
    public BasicHashSet(int size) {
        internal = new BasicHashMap<E, Object>(size);
    }
    
    public Iterator<E> iterator() {
        return internal.iterator();
    }

    public void add(E item) {
        internal.put(item, null);
    }

    public boolean contains(E item) {
        return internal.containsKey(item);
    }

    public void remove(E item) {
        internal.remove(item);
    }

    public int size() {
        int count = 0;
        for (E item : internal) count++;
        return count;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

}
