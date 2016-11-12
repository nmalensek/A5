package cs414.a5.nmalensk.test_cases;

import cs414.a5.nmalensk.domain_logic.GarageGate;
import cs414.a5.nmalensk.domain_logic.Ticket;
import cs414.a5.nmalensk.domain_logic.TicketStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

public class TicketTest {
    GarageGate gate;
    Ticket testTicket;
    LocalDateTime oneOclock;
    LocalDateTime twoOclock;

    @Before
    public void setUp() {

        oneOclock = LocalDateTime.of(2016, 10, 25, 13, 0);
        twoOclock = LocalDateTime.of(2016, 10, 25, 14, 0);

        testTicket = new Ticket(oneOclock, LocalDateTime.MAX,
                BigDecimal.ZERO, TicketStatus.UNPAID);
    }

    @After
    public void tearDown() {
        testTicket.setCurrentTicketID(100);
        testTicket = null;
    }


    @Test
    public void testGetEntryTime() {
        assertEquals(oneOclock, testTicket.getEntryTime());
    }

    @Test
    public void testGetExitTime() {
        assertEquals(LocalDateTime.MAX, testTicket.getExitTime());
    }

    @Test
    public void testSetExitTime() {
        testTicket.setExitTime(twoOclock);
        assertEquals(twoOclock, testTicket.getExitTime());
    }

    @Test
    public void testGetTicketID() {
        assertEquals(101, testTicket.getTicketID());
    }

    @Test
    public void testGetPrice() {
        assertEquals(BigDecimal.ZERO, testTicket.getPrice());
    }

    @Test
    public void testSetPrice() {
        BigDecimal two = new BigDecimal("2.00");
        testTicket.setPrice(two);
        assertEquals(two, testTicket.getPrice());
    }

    @Test
    public void testGetStatus() {
        assertEquals(TicketStatus.UNPAID, testTicket.getStatus());
    }

    @Test
    public void testSetStatusA() {
        testTicket.setStatus(TicketStatus.PAID);
        assertEquals(TicketStatus.PAID, testTicket.getStatus());
    }

    @Test
    public void testSetStatusB() {
        testTicket.setStatus(TicketStatus.UNPAID);
        assertEquals(TicketStatus.UNPAID, testTicket.getStatus());
    }
}
