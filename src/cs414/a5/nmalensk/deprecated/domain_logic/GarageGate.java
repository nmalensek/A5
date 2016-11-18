package cs414.a5.nmalensk.deprecated.domain_logic;

import cs414.a5.nmalensk.gui.PaymentHandlerGUI;
import cs414.a5.nmalensk.client.PhysicalGarageGate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

import static java.time.temporal.ChronoUnit.MINUTES;

public class GarageGate {

    private BigDecimal lostTicketPrice = new BigDecimal("25.00").setScale(2, RoundingMode.HALF_UP);
    private PhysicalGarageGate pGate = new PhysicalGarageGate();

    public void admitCustomer(OccupancySign sign) {
        sign.addOpenSpaces(-1);
//        pGate.openGate("enter");
    }

    public void expelCustomer(OccupancySign sign) {
        sign.addOpenSpaces(1);
//        pGate.openGate("exit");
    }

    public Ticket createTicket(TransactionLog log, BigDecimal price) {
        Ticket newTicket = new Ticket(getTime(),
                standardExitTime(), price, TicketStatus.UNPAID);
        log.addTicket(newTicket);
        return newTicket;
    }

    public boolean checkTicket(TransactionLog transl, int customerTicket) {
        if (transl.isValidTicket(customerTicket)) {
            transl.modifyTicket(customerTicket, getTime(), TicketStatus.PAID, false);
            System.out.println("Ticket accepted!");
            return true;
        }
        return false;
    }

    public void createAndUpdateLostTicket(TransactionLog log, PaymentHandlerGUI handler, OccupancySign sign) {
        Ticket lostTicket = createTicket(log, lostTicketPrice);
        log.modifyTicket(lostTicket.getTicketID(), getTime(), TicketStatus.PAID, true);
//        handler.promptForTotal(log, lostTicket.getTicketID());
        expelCustomer(sign);
    }

    public void closeGate() {} //pGate.closeGate(); }

//    public void printTicket(Ticket currentTicket) { pGate.printTicket(currentTicket); }

//    public void printFullMessage() { pGate.fullMessage(); }

    private LocalDateTime getTime() {
        LocalDateTime timestamp = LocalDateTime.now();
        return timestamp.truncatedTo(MINUTES);
    }

    private LocalDateTime standardExitTime() {
        LocalDateTime exitTime = LocalDateTime.MAX;
        return exitTime;
    }
}
