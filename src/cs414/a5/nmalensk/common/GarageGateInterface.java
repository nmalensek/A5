package cs414.a5.nmalensk.common;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.time.LocalDateTime;

public interface GarageGateInterface extends java.rmi.Remote {
    public void admitCustomer(OccupancySignInterface sign) throws RemoteException;

    public void expelCustomer(OccupancySignInterface sign) throws RemoteException;

    public int createTicket(TransactionLogInterface log, BigDecimal price) throws RemoteException;

    public boolean checkTicket(TransactionLogInterface transl, int customerTicket) throws RemoteException;

    public int createAndUpdateLostTicket(TransactionLogInterface log) throws RemoteException;

    public LocalDateTime getTime() throws RemoteException;

    public LocalDateTime standardExitTime() throws RemoteException;

    public String getName() throws RemoteException;
}
