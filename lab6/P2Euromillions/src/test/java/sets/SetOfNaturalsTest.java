/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sets;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

/**
 * @author ico0
 */
public class SetOfNaturalsTest {
    private SetOfNaturals setA;
    private SetOfNaturals setB;
    private SetOfNaturals setC;
    private SetOfNaturals setD;

    @BeforeEach
    public void setUp() {
        setA = new SetOfNaturals();
        setB = SetOfNaturals.fromArray(new int[]{10, 20, 30, 40, 50, 60});

        setC = new SetOfNaturals();
        for (int i = 5; i < 50; i++) {
            setC.add(i * 10);
        }
        setD = SetOfNaturals.fromArray(new int[]{30, 40, 50, 60, 10, 20});
    }

    @AfterEach
    public void tearDown() {
        setA = setB = setC = setD = null;
    }

    @Test
    public void testAddElement() {
        setA.add(99);
        assertTrue(setA.contains(99), "add: added element not found in set.");
        assertEquals(1, setA.size(), "size: unexpected size after add.");

        setB.add(11);
        assertTrue(setB.contains(11), "add: added element not found in set.");
        assertEquals(7, setB.size(), "add: elements count not as expected.");

        assertThrows(IllegalArgumentException.class, () -> setB.add(10), "add: duplicate number insert did not throw error.");

    }

    @Test
    public void testAddBadArray() {
        int[] elems = new int[]{10, 20, -30};

        // must fail with exception
        assertThrows(IllegalArgumentException.class, () -> setA.add(elems), "add: not natural number (negative) insert did not throw error.");
    }


    @Test
    public void testIntersectForNoIntersection() {
        assertFalse(setA.intersects(setB), "no intersection but was reported as existing");
        assertTrue(setB.intersects(setC), "intersection exists but not reported");
    }

    @Test
    public void testSize() {
        assertEquals(0, setA.size(), "size: empty set does not have size 0");
    }

    @Test
    public void testContains() {
        assertFalse(setA.contains(10), "contains: set does not contain but report it does");
    }

    @Test
    public void testEquals() {
        assertFalse(setA.equals(setB), "equals: different sets reported as equal");

        setA.add(new int[]{10, 20, 30, 40, 50, 60});
        assertTrue(setA.equals(setB), "equals: equal sets reported as different");

    }


}
