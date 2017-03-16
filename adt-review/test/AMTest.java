package test;

import impl.ArrayMap;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class AMTest extends MapTest {

    protected void reset() {
        testMap = new ArrayMap<String, String>();
    }
}
