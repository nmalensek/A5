package cs414.a5.nmalensk.server;

import cs414.a5.nmalensk.client.PaymentHandler;
import cs414.a5.nmalensk.common.*;
import cs414.a5.nmalensk.domain_logic.OccupancySign;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;

import static java.time.temporal.ChronoUnit.MINUTES;

public class GarageGateImplementation
        extends UnicastRemoteObject
        implements GarageGateInterface {

    private BigDecimal lostTicketPrice = new BigDecimal("25.00").setScale(2, RoundingMode.HALF_UP);

    public GarageGateImplementation() throws java.rmi.RemoteException {
    }

    public void admitCustomer(OccupancySignInterface sign) throws RemoteException {
        sign.addOpenSpaces(-1);
    }

    public void expelCustomer(OccupancySignInterface sign) throws RemoteException {
            sign.addOpenSpaces(1);
    }

    public int createTicket(TransactionLogInterface log, BigDecimal price) throws RemoteException {
        TicketInterface newTicket = new TicketImplementation(getTime(),
                standardExitTime(), price, TicketStatus.UNPAID);
        log.addTicket(newTicket);
        return newTicket.getTicketID();
    }

    public boolean checkTicket(TransactionLogInterface transl, int customerTicket) throws RemoteException {
        if (transl.isValidTicket(customerTicket)) {
            transl.modifyTicket(customerTicket, getTime(), TicketStatus.PAID, false);
            System.out.println("Ticket accepted!");
            return true;
        }
        return false;
    }

    public void createAndUpdateLostTicket(TransactionLogInterface log,
                                          PaymentHandler handler, OccupancySign sign) throws RemoteException {
        int lostTicketID = createTicket(log, lostTicketPrice);
        log.modifyTicket(lostTicketID, getTime(), TicketStatus.PAID, true);
        handler.promptForTotal(log, lostTicketID);
    }
//
//    public void closeGate() { pGate.closeGate(); }
//
//    public void printTicket(Ticket currentTicket) { pGate.printTicket(currentTicket); }
//
//    public void printFullMessage() { pGate.fullMessage(); }
//
    public LocalDateTime getTime() {
        LocalDateTime timestamp = LocalDateTime.now();
        return timestamp.truncatedTo(MINUTES);
    }

    public LocalDateTime standardExitTime() {
        LocalDateTime exitTime = LocalDateTime.MAX;
        return exitTime;
    }
}
