package cs414.a5.nmalensk.common;

import java.rmi.RemoteException;
import java.time.LocalDateTime;

public interface GateReportGeneratorInterface extends java.rmi.Remote {

    String printGateEntries(TransactionLogInterface log,
                            LocalDateTime start,
                            LocalDateTime finish) throws RemoteException;

    String printGateExits(TransactionLogInterface log,
                          LocalDateTime start,
                          LocalDateTime finish) throws RemoteException;
}
