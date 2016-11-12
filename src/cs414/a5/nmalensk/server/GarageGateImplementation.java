package cs414.a5.nmalensk.server;

import cs414.a5.nmalensk.common.*;
import cs414.a5.nmalensk.gui.GateGUI;

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
    private String gateName;
    private GateGUIInterface gui;

    public GarageGateImplementation(String gateName) throws java.rmi.RemoteException {
        this.gateName = gateName;
    }

    public void admitCustomer(OccupancySignInterface sign) throws RemoteException {
        sign.addOpenSpaces(-1);
    }

    public void expelCustomer(OccupancySignInterface sign) throws RemoteException {
            sign.addOpenSpaces(1);
    }

    public synchronized int createTicket(TransactionLogInterface log, BigDecimal price) throws RemoteException {
        TicketInterface newTicket = new TicketImplementation(getTime(),
                standardExitTime(), price, TicketStatus.UNPAID, gateName, "still in garage");
        log.addTicket(newTicket);
        System.out.println("Ticket " + newTicket.getTicketID() + " successfully created!");
        return newTicket.getTicketID();
    }

    public boolean checkTicket(TransactionLogInterface transl, int customerTicket) throws RemoteException {
        if (transl.isValidTicket(customerTicket)) {
            transl.modifyTicket(customerTicket, getTime(), false, gateName);
            return true;
        }
        return false;
    }

    public int createAndUpdateLostTicket(TransactionLogInterface log) throws RemoteException {
        int lostTicketID = createTicket(log, lostTicketPrice);
        log.modifyTicket(lostTicketID, getTime(), true, gateName);
        return lostTicketID;
    }

    public LocalDateTime getTime() {
        LocalDateTime timestamp = LocalDateTime.now();
        return timestamp.truncatedTo(MINUTES);
    }

    public LocalDateTime standardExitTime() {
        LocalDateTime exitTime = LocalDateTime.MAX;
        return exitTime;
    }

    public String getName() {
        return gateName;
    }

    public void registerGateGUI(GateGUIInterface gui) throws RemoteException {
        this.gui = gui;
    }

    public void updateGUI() throws RemoteException {
        gui.refreshWindow();
    }
}
