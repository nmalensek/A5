package cs414.a5.nmalensk.deprecated.test_cases;

import cs414.a5.nmalensk.deprecated.domain_logic.OccupancySign;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class OccupancySignTest {

    OccupancySign testSign;

    @Before
    public void setUp() {
        testSign = new OccupancySign(10);

    }

    @After
    public void tearDown() {
        testSign = null;

    }

    @Test
    public void testGetOpenSpaces() {
        assertEquals(10, testSign.getOpenSpaces());
    }

    @Test
    public void testAddOpenSpacesA() {
      testSign.addOpenSpaces(1);
        assertEquals(11, testSign.getOpenSpaces());
    }

    @Test
    public void testAddOpenSpacesB() {
        testSign.addOpenSpaces(-1);
        assertEquals(9, testSign.getOpenSpaces());
    }
}
