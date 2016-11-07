package cs414.a5.nmalensk.common;

import cs414.a5.nmalensk.server.TicketImplementation;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.Map;

public interface TransactionLogInterface extends java.rmi.Remote {
    LocalDateTime retrieveEntryTime(int ticketID) throws RemoteException;

    void addTicket(TicketInterface newTicket) throws RemoteException;

    boolean isValidTicket(int inputTicketID) throws RemoteException;

    void modifyTicket(int modTicket,
                      LocalDateTime exitTime,
                      boolean isLost, String exitGate) throws RemoteException;

    BigDecimal getTicketPrice(int ticketID) throws RemoteException;

    BigDecimal calculateTicketPrice(TicketInterface ticket,
                                    boolean isLost) throws RemoteException;

    void markTicketPaid(int ticketID) throws RemoteException;

    Map<LocalDateTime, BigDecimal> collectDaysWithSales(LocalDateTime start,
                                                        LocalDateTime finish)
            throws RemoteException;

    String printDailySalesReport(Map<LocalDateTime,
            BigDecimal> map) throws RemoteException;

    String printHourlyOccupancyData(LocalDateTime start,
                                    LocalDateTime finish) throws RemoteException;

    String printGateEntries(LocalDateTime start,
                            LocalDateTime finish) throws RemoteException;

    String printGateExits(LocalDateTime start,
                            LocalDateTime finish) throws RemoteException;
}
