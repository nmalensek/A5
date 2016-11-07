package cs414.a5.nmalensk.server;

import cs414.a5.nmalensk.common.TicketInterface;
import cs414.a5.nmalensk.common.TicketStatus;
import cs414.a5.nmalensk.common.TransactionLogInterface;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.rmi.RemoteException;
import java.time.LocalDateTime;

public class FakeTicketGeneration {
    private TransactionLogInterface tLog;

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

    private TicketInterface notLeftYet = new TicketImplementation(
            twoOclock, LocalDateTime.MAX, testPrice, TicketStatus.UNPAID, "testGate", "testGate");

    private TicketInterface[] ticketList = {
            new TicketImplementation(twoOclock, max, testPrice, TicketStatus.UNPAID, "testGate", "testGate"),
            new TicketImplementation(threeOclock, max, testPrice, TicketStatus.UNPAID, "testGate", "testGate"),
            new TicketImplementation(threeOclock, max, testPrice, TicketStatus.UNPAID, "testGate", "testGate"),
            new TicketImplementation(fourOclock, max, testPrice, TicketStatus.UNPAID, "testGate", "testGate"),
            new TicketImplementation(fourOclock, max, testPrice, TicketStatus.UNPAID, "testGate", "testGate"),
            new TicketImplementation(fiveOclock, max, testPrice, TicketStatus.UNPAID, "testGate", "testGate"),
            new TicketImplementation(twoFiveOneOclock, max, testPrice, TicketStatus.UNPAID, "testGate", "testGate"),
            new TicketImplementation(twoFiveTwoOclock, max, testPrice, TicketStatus.UNPAID, "testGate", "testGate"),
            new TicketImplementation(twoFourElevenOclock, max, testPrice, TicketStatus.UNPAID, "testGate", "testGate"),
            new TicketImplementation(twoFourTenOclock, max, testPrice, TicketStatus.UNPAID, "testGate", "testGate"),
            new TicketImplementation(seventeenOneOclock, max, testPrice, TicketStatus.UNPAID, "testGate", "testGate"),
            new TicketImplementation(seventeenThreeOclock, max, testPrice, TicketStatus.UNPAID, "testGate", "testGate"),
            new TicketImplementation(seventeenThreeOclock, max, testPrice, TicketStatus.UNPAID, "testGate", "testGate"),

    };

    private static int[] durationList = {
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 3
    };


    public FakeTicketGeneration(TransactionLogInterface tLog) throws RemoteException {
        this.tLog = tLog;
        addTickets();
        payTickets();
    }

    public void addTickets() {
        try {
            tLog.addTicket(notLeftYet);
            for (TicketInterface t : ticketList) {
                tLog.addTicket(t);
            }
        } catch (RemoteException rE) {
            System.out.println("Unable to generate fake tickets");
            rE.printStackTrace();
        }

    }

    public void payTickets() {
        try {
            for (int i = 0; i < ticketList.length; ++i){
                tLog.modifyTicket(ticketList[i].getTicketID(),
                        ticketList[i].getEntryTime().plusHours(durationList[i]),
                        TicketStatus.PAID, false, "testGate");
        }
        } catch (RemoteException rE) {
            System.out.println("Unable to mark fake tickets as paid");
            rE.printStackTrace();
        }
    }


}

