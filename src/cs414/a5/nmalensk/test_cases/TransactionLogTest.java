package cs414.a5.nmalensk.test_cases;

import cs414.a5.nmalensk.deprecated.domain_logic.Ticket;
import cs414.a5.nmalensk.deprecated.domain_logic.TicketStatus;
import cs414.a5.nmalensk.deprecated.domain_logic.TransactionLog;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static org.junit.Assert.*;

public class TransactionLogTest {

    private TransactionLog testLog;
    private Ticket testTicket1;
    private Ticket testTicket2;
    private Ticket testTicket3;
    private Ticket testTicket4;
    private Ticket testTicket5;
    private Ticket testTicket6;
    private Ticket testTicket7;
    private Ticket testTicket8;
    private Ticket testTicket9;
    private Ticket testTicket10;
    private BigDecimal testPrice;
    private LocalDateTime now = LocalDateTime.now();
    private LocalDateTime oneHourLater = now.plusHours(1).truncatedTo(ChronoUnit.SECONDS);
    private LocalDateTime oneHourBefore = now.minusHours(1).truncatedTo(ChronoUnit.SECONDS);
    private LocalDateTime thirtyMinutesBefore = now.minusMinutes(30).truncatedTo(ChronoUnit.SECONDS);
    private LocalDateTime oneDayBefore = now.minusDays(1).truncatedTo(ChronoUnit.SECONDS);
    private LocalDateTime threeDaysBefore = now.minusDays(3).truncatedTo(ChronoUnit.SECONDS);
    private LocalDateTime oneWeekBefore = now.minusWeeks(1).truncatedTo(ChronoUnit.SECONDS);
    private LocalDateTime oneMonthBefore = now.minusMonths(1).truncatedTo(ChronoUnit.SECONDS);
    private LocalDateTime twoMonthsBefore = now.minusMonths(2).truncatedTo(ChronoUnit.SECONDS);
    private LocalDateTime max = LocalDateTime.MAX;
    private ArrayList<Ticket> ticketList;
    private Map<LocalDateTime, BigDecimal> ticketMap;
    private Map<Integer, Integer> occupancyMap;

    @Before
    public void setUp() {
        testLog = new TransactionLog();
        testPrice = new BigDecimal("7.50").setScale(2, RoundingMode.HALF_UP);
        testTicket1 = new Ticket(now, max, testPrice, TicketStatus.UNPAID);
        testTicket2 = new Ticket(oneHourLater, max, testPrice, TicketStatus.UNPAID);
        testTicket3 = new Ticket(oneHourBefore, max, testPrice, TicketStatus.UNPAID);
        testTicket4 = new Ticket(thirtyMinutesBefore, max, testPrice, TicketStatus.UNPAID);
        testTicket5 = new Ticket(oneDayBefore, max, testPrice, TicketStatus.UNPAID);
        testTicket6 = new Ticket(threeDaysBefore, max, testPrice, TicketStatus.UNPAID);
        testTicket7 = new Ticket(oneWeekBefore, max, testPrice, TicketStatus.UNPAID);
        testTicket8 = new Ticket(oneMonthBefore, max, testPrice, TicketStatus.UNPAID);
        testTicket9 = new Ticket(twoMonthsBefore, max, testPrice, TicketStatus.UNPAID);

        testLog.addTicket(testTicket1);
        testLog.addTicket(testTicket2);
        testLog.addTicket(testTicket3);
        testLog.addTicket(testTicket4);
        testLog.addTicket(testTicket5);
        testLog.addTicket(testTicket6);
        testLog.addTicket(testTicket7);
        testLog.addTicket(testTicket8);


        ticketList = new ArrayList<>();
        ticketMap = new HashMap<>();
        occupancyMap = new HashMap<>();
    }

    @After
    public void tearDown() {
        testTicket9.setCurrentTicketID(100);
        testLog = null;
        testPrice = null;
        testTicket1 = null;
        testTicket2 = null;
        testTicket3 = null;

    }

    @Test
    public void testIncrementTicketA() {
        assertEquals(103, testTicket3.getTicketID());
    }

    @Test
    public void testIncrementTicketB() {
        testTicket10 = new Ticket(now, max, testPrice, TicketStatus.UNPAID);
        assertEquals(110, testTicket10.getTicketID());
    }

    @Test
    public void testAddTicket() {
        assertTrue(testLog.isValidTicket(101));
    }

    @Test
    public void testIsValidTicketA() {
        assertTrue(testLog.isValidTicket(101));
    }

    @Test
    public void testIsValidTicketB() {
        testTicket1.setStatus(TicketStatus.PAID);
        assertFalse(testLog.isValidTicket(1));
    }

    @Test
    public void testIsValidTicketC() {
        assertFalse(testLog.isValidTicket(100));
    }

    @Test
    public void testModifyTicketA() {
        testLog.modifyTicket(101, oneHourLater, TicketStatus.PAID, false);
        assertEquals(TicketStatus.PAID, testTicket1.getStatus());
    }

    @Test
    public void testModifyTicketB() {
        testLog.modifyTicket(101, oneHourLater, TicketStatus.PAID, false);
        assertEquals(oneHourLater, testTicket1.getExitTime());
    }

    @Test (expected = NullPointerException.class)
    public void testModifyTicketC() {
        testLog.modifyTicket(0, oneHourLater, TicketStatus.PAID, false);
    }

    @Test
    public void testGetTicketPriceA() {
        testLog.modifyTicket(101, now.plusHours(2), TicketStatus.PAID, false);
        BigDecimal twoHours = new BigDecimal("3.00");
        assertEquals(twoHours, testLog.getTicketPrice(101));
    }

    @Test
    public void testGetTicketPriceB() {
        testLog.modifyTicket(101, now.plusMinutes(30), TicketStatus.PAID, false);
        BigDecimal twoHours = new BigDecimal("1.00");
        assertEquals(twoHours, testLog.getTicketPrice(101));
    }

    @Test
    public void testCollectDaysWithSalesA() {
        testTicket1.setStatus(TicketStatus.PAID);
        testTicket3.setStatus(TicketStatus.PAID);
        testTicket4.setStatus(TicketStatus.PAID);
        testTicket5.setStatus(TicketStatus.PAID);
        testTicket1.setExitTime(thirtyMinutesBefore);
        testTicket3.setExitTime(oneHourBefore);
        testTicket4.setExitTime(oneDayBefore);
        testTicket5.setExitTime(threeDaysBefore);
        ticketMap = testLog.collectDaysWithSales(twoMonthsBefore, now);
        assertEquals(3, ticketMap.size());
    }

    @Test
    public void testCollectDaysWithSalesB() {
        testTicket1.setStatus(TicketStatus.PAID);
        testTicket1.setExitTime(thirtyMinutesBefore);
        ticketMap = testLog.collectDaysWithSales(twoMonthsBefore, now);
        LocalDateTime key = now.truncatedTo(ChronoUnit.DAYS);
        assertEquals(testPrice, ticketMap.get(key));
    }

    @Test
    public void testCollectDaysWithSalesC() {
        testTicket1.setStatus(TicketStatus.PAID);
        testTicket3.setStatus(TicketStatus.PAID);
        testTicket4.setStatus(TicketStatus.PAID);
        testTicket1.setExitTime(thirtyMinutesBefore);
        testTicket3.setExitTime(oneHourBefore);
        testTicket4.setExitTime(oneHourBefore);
        ticketMap = testLog.collectDaysWithSales(twoMonthsBefore, now);
        BigDecimal totalForRange = new BigDecimal("22.50");
        LocalDateTime key = now.truncatedTo(ChronoUnit.DAYS);
        assertEquals(totalForRange, ticketMap.get(key));
    }

    @Test
    public void testCollectDaysWithSalesD() {
        testLog.modifyTicket(101, oneHourLater, TicketStatus.PAID, false);
        ticketMap = testLog.collectDaysWithSales(twoMonthsBefore, oneHourLater);
        LocalDateTime key = now.truncatedTo(ChronoUnit.DAYS);
        BigDecimal price = new BigDecimal("1.00");
        assertEquals(price, ticketMap.get(key));
    }

    @Test
    public void testCollectDaysWithSalesE() {
        testLog.modifyTicket(101, oneHourLater, TicketStatus.PAID, false);
        ticketMap = testLog.collectDaysWithSales(now, oneHourLater);
        LocalDateTime key = now.truncatedTo(ChronoUnit.DAYS);
        BigDecimal price = new BigDecimal("1.00");
        assertEquals(price, ticketMap.get(key));
    }
}