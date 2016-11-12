package cs414.a5.nmalensk.server;

import cs414.a5.nmalensk.common.GarageGateInterface;
import cs414.a5.nmalensk.common.TicketInterface;
import cs414.a5.nmalensk.common.TicketStatus;
import cs414.a5.nmalensk.common.TransactionLogInterface;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionLogImplementation
        extends UnicastRemoteObject
        implements TransactionLogInterface {

    private List<GarageGateInterface> gateList;

    public TransactionLogImplementation(List gateList) throws java.rmi.RemoteException {
        this.gateList = gateList;
    }

    private Map<Integer, TicketInterface> assignedTickets = new HashMap<>();

    public Map<Integer, TicketInterface> getAssignedTickets() { return assignedTickets; }

    public LocalDateTime retrieveEntryTime(int ticketID) throws RemoteException {
        TicketInterface ticketToGet = assignedTickets.get(ticketID);
        return ticketToGet.getEntryTime();
    }

    public void addTicket(TicketInterface newTicket) {
        try {
            assignedTickets.put(newTicket.getTicketID(), newTicket);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public boolean isValidTicket(int inputTicketID) throws RemoteException {
        TicketInterface checkTicket = assignedTickets.get(inputTicketID);
        if (assignedTickets.get(inputTicketID) != null &&
                checkTicket.getStatus().equals(TicketStatus.UNPAID)) return true;
        return false;
    }

    public void modifyTicket(int modTicket,
                             LocalDateTime exitTime,
                             boolean isLost, String exitGate) throws RemoteException {
        TicketInterface ticket = assignedTickets.get(modTicket);
        ticket.setExitTime(exitTime);
        ticket.setPrice(calculateTicketPrice(ticket, isLost));
        ticket.setExitGate(exitGate);
        ticket.setLostStatus(isLost);
    }

    public BigDecimal getTicketPrice(int ticketID) throws RemoteException {
        TicketInterface ticketToGet = assignedTickets.get(ticketID);
        return ticketToGet.getPrice();
    }

    public BigDecimal calculateTicketPrice(TicketInterface ticket, boolean isLost) throws RemoteException {
        if (isLost) {
            return ticket.getPrice();
        }
        BigDecimal hours =
                new BigDecimal(ticket.getEntryTime().until(ticket.getExitTime(), ChronoUnit.HOURS));
        BigDecimal ratePerHour = new BigDecimal("1.00").setScale(2, RoundingMode.HALF_UP);

        return ratePerHour.multiply(hours).add(BigDecimal.ONE);
    }

    public void markTicketPaid(int ticketID) throws RemoteException {
        TicketInterface ticket = assignedTickets.get(ticketID);
        ticket.setStatus(TicketStatus.PAID);
        System.out.println("Ticket " + ticketID + " successfully exited");
    }
}
