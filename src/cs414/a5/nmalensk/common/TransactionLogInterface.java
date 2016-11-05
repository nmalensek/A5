package cs414.a5.nmalensk.common;

import cs414.a5.nmalensk.server.TicketImplementation;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

public interface TransactionLogInterface extends java.rmi.Remote {
    public void addTicket(TicketInterface newTicket) throws java.rmi.RemoteException;
    public boolean isValidTicket(int inputTicketID) throws java.rmi.RemoteException;
    public void modifyTicket(int modTicket,
                             LocalDateTime exitTime,
                             TicketStatus newStatus,
                             boolean isLost) throws java.rmi.RemoteException;
    public BigDecimal getTicketPrice(int ticketID) throws java.rmi.RemoteException;
    public BigDecimal calculateTicketPrice(TicketImplementation ticket,
                                           boolean isLost) throws java.rmi.RemoteException;
    public Map<LocalDateTime, BigDecimal> collectDaysWithSales(LocalDateTime start,
                                                               LocalDateTime finish)
                                                                throws java.rmi.RemoteException;
    public void printDailySalesReport(Map<LocalDateTime,
                                        BigDecimal> map) throws java.rmi.RemoteException;
    public void printHourlyOccupancyData(LocalDateTime start,
                                         LocalDateTime finish) throws java.rmi.RemoteException;
}
