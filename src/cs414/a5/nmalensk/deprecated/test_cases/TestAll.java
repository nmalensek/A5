package cs414.a5.nmalensk.deprecated.test_cases;

import junit.framework.JUnit4TestAdapter;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        GarageGateTest.class,
        OccupancySignTest.class,
        TicketTest.class,
        TransactionLogTest.class,
})
public class TestAll {
    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(TestAll.class);
    }

}