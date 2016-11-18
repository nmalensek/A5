package cs414.a5.nmalensk.test_cases;

import cs414.a5.nmalensk.deprecated.domain_logic.GarageGate;
import cs414.a5.nmalensk.deprecated.domain_logic.OccupancySign;
import cs414.a5.nmalensk.deprecated.domain_logic.Ticket;
import cs414.a5.nmalensk.deprecated.domain_logic.TransactionLog;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class GarageGateTest {
    GarageGate gate;
    Ticket testTicket;
    Ticket secondTicket;
    OccupancySign sign;
    TransactionLog log;
    private BigDecimal ticketPrice;

    @Before
    public void setUp() {
        gate = new GarageGate();
        sign = new OccupancySign(10);
        log = new TransactionLog();
        ticketPrice = new BigDecimal("15.00").setScale(2, RoundingMode.HALF_UP);

    }

    @After
    public void tearDown() {
        gate = null;

    }

    @Test
    public void testIncrementTicketID() {
        testTicket = gate.createTicket(log, ticketPrice);
        secondTicket = gate.createTicket(log, ticketPrice);
        assertEquals(101, testTicket.getTicketID());
        assertEquals(102, secondTicket.getTicketID());
    }

}
