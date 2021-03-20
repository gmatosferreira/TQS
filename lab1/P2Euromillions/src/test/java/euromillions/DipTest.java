/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package euromillions;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

/**
 * @author ico0
 */
public class DipTest {

    private Dip instance;


    public DipTest() {
    }

    @BeforeEach
    public void setUp() {
        instance = new Dip(new int[]{10, 20, 30, 40, 50}, new int[]{1, 2});
    }

    @AfterEach
    public void tearDown() {
        instance = null;
    }


    @Test
    public void testConstructorFromBadArrays() {
        assertThrows(IllegalArgumentException.class, () -> new Dip(new int[]{1,2,3}, new int[]{1, 2}), "A dip must have 5 numbers");
        assertThrows(IllegalArgumentException.class, () -> new Dip(new int[]{1,2,3,4,5,6}, new int[]{1, 2}), "A dip must have 5 numbers");
        assertThrows(IllegalArgumentException.class, () -> new Dip(new int[]{1,2,3, 4, 5}, new int[]{1}), "A dip must have 2 starts");
        assertThrows(IllegalArgumentException.class, () -> new Dip(new int[]{1,2,3, 4, 5}, new int[]{1,2,3}), "A dip must have 2 starts");
    }

    @Test
    public void testFormat() {
        // note: correct the implementation of the format(), not the test...
        String result = instance.format();
        assertEquals("N[ 10 20 30 40 50] S[  1  2]", result, "format as string: formatted string not as expected. ");
    }

}
