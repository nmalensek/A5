package cs414.a5.nmalensk.common;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.Map;

public interface ReportGeneratorInterface extends java.rmi.Remote {

    Map<LocalDateTime, BigDecimal> collectDaysWithSales(TransactionLogInterface log,
                                        LocalDateTime start,
                                                        LocalDateTime finish)
            throws RemoteException;

    String printDailySalesReport(Map<LocalDateTime,
            BigDecimal> map) throws RemoteException;

    String printHourlyOccupancyData(TransactionLogInterface log,
                                    LocalDateTime start,
                                    LocalDateTime finish) throws RemoteException;

    String printGateEntries(TransactionLogInterface log,
                            LocalDateTime start,
                            LocalDateTime finish) throws RemoteException;

    String printGateExits(TransactionLogInterface log,
                          LocalDateTime start,
                          LocalDateTime finish) throws RemoteException;

    String lostVersusNotTickets(TransactionLogInterface log,
                          LocalDateTime start,
                          LocalDateTime finish) throws RemoteException;
}
