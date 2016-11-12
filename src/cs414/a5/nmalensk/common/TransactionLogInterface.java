package cs414.a5.nmalensk.common;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.Map;

public interface TransactionLogInterface extends java.rmi.Remote {
    LocalDateTime retrieveEntryTime(int ticketID) throws RemoteException;

    Map<Integer, TicketInterface> getAssignedTickets() throws RemoteException;

    void addTicket(TicketInterface newTicket) throws RemoteException;

    boolean isValidTicket(int inputTicketID) throws RemoteException;

    void modifyTicket(int modTicket,
                      LocalDateTime exitTime,
                      boolean isLost, String exitGate) throws RemoteException;

    BigDecimal getTicketPrice(int ticketID) throws RemoteException;

    BigDecimal calculateTicketPrice(TicketInterface ticket,
                                    boolean isLost) throws RemoteException;

    void markTicketPaid(int ticketID) throws RemoteException;
}
