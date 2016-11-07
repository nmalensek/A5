package cs414.a5.nmalensk.common;

import cs414.a5.nmalensk.server.TicketImplementation;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.Map;

public interface TransactionLogInterface extends java.rmi.Remote {
    public LocalDateTime retrieveEntryTime(int ticketID) throws RemoteException;
    public void addTicket(TicketInterface newTicket) throws RemoteException;
    public boolean isValidTicket(int inputTicketID) throws RemoteException;
    public void modifyTicket(int modTicket,
                             LocalDateTime exitTime,
                             TicketStatus newStatus,
                             boolean isLost, String exitGate) throws RemoteException;
    public BigDecimal getTicketPrice(int ticketID) throws RemoteException;
    public BigDecimal calculateTicketPrice(TicketInterface ticket,
                                           boolean isLost) throws RemoteException;
    public Map<LocalDateTime, BigDecimal> collectDaysWithSales(LocalDateTime start,
                                                               LocalDateTime finish)
                                                                throws RemoteException;
    public String printDailySalesReport(Map<LocalDateTime,
                                        BigDecimal> map) throws RemoteException;
    public String printHourlyOccupancyData(LocalDateTime start,
                                         LocalDateTime finish) throws RemoteException;
}
