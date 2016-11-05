package cs414.a5.nmalensk.domain_logic;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

public class FakeTicketGeneration {
    private TransactionLog tLog;

    private static BigDecimal testPrice = new BigDecimal("0.00").setScale(2, RoundingMode.HALF_UP);

    private static LocalDateTime twoOclock = LocalDateTime.of(2016, 10, 26, 14, 0, 0);
    private static LocalDateTime threeOclock = LocalDateTime.of(2016, 10, 26, 15, 0, 0);
    private static LocalDateTime fourOclock = LocalDateTime.of(2016, 10, 26, 16, 0, 0);
    private static LocalDateTime fiveOclock = LocalDateTime.of(2016, 10, 26, 17, 0, 0);

    private static LocalDateTime twoFiveOneOclock = LocalDateTime.of(2016, 10, 25, 13, 0, 0);
    private static LocalDateTime twoFiveTwoOclock = LocalDateTime.of(2016, 10, 25, 14, 0, 0);

    private static LocalDateTime twoFourElevenOclock = LocalDateTime.of(2016, 10, 24, 11, 0, 0);
    private static LocalDateTime twoFourTenOclock = LocalDateTime.of(2016, 10, 24, 10, 0, 0);

    private static LocalDateTime seventeenOneOclock = LocalDateTime.of(2016, 10, 17, 13, 0, 0);
    private static LocalDateTime seventeenThreeOclock = LocalDateTime.of(2016, 10, 17, 15, 0, 0);

    private static LocalDateTime max = LocalDateTime.MAX;

    private static Ticket notLeftYet = new Ticket(twoOclock, LocalDateTime.MAX, testPrice, TicketStatus.UNPAID);

    private static Ticket[] ticketList = {
            new Ticket(twoOclock, max, testPrice, TicketStatus.UNPAID),
            new Ticket(threeOclock, max, testPrice, TicketStatus.UNPAID),
            new Ticket(threeOclock, max, testPrice, TicketStatus.UNPAID),
            new Ticket(fourOclock, max, testPrice, TicketStatus.UNPAID),
            new Ticket(fourOclock, max, testPrice, TicketStatus.UNPAID),
            new Ticket(fiveOclock, max, testPrice, TicketStatus.UNPAID),
            new Ticket(twoFiveOneOclock, max, testPrice, TicketStatus.UNPAID),
            new Ticket(twoFiveTwoOclock, max, testPrice, TicketStatus.UNPAID),
            new Ticket(twoFourElevenOclock, max, testPrice, TicketStatus.UNPAID),
            new Ticket(twoFourTenOclock, max, testPrice, TicketStatus.UNPAID),
            new Ticket(seventeenOneOclock, max, testPrice, TicketStatus.UNPAID),
            new Ticket(seventeenThreeOclock, max, testPrice, TicketStatus.UNPAID),
            new Ticket(seventeenThreeOclock, max, testPrice, TicketStatus.UNPAID),

    };

    private static int[] durationList = {
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 3
    };


    public FakeTicketGeneration(TransactionLog tLog) {
        this.tLog = tLog;
        addTickets();
        payTickets();
    }

    public void addTickets() {
        tLog.addTicket(notLeftYet);
        for (Ticket t : ticketList) {
            tLog.addTicket(t);
        }
    }

    public void payTickets() {
        for (int i = 0; i < ticketList.length; ++i){
            tLog.modifyTicket(ticketList[i].getTicketID(),
                    ticketList[i].getEntryTime().plusHours(durationList[i]),
                    TicketStatus.PAID, false);
        }
    }


}

