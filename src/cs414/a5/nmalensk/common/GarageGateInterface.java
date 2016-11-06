package cs414.a5.nmalensk.common;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface GarageGateInterface extends java.rmi.Remote {
    public void admitCustomer(OccupancySignInterface sign) throws java.rmi.RemoteException;

    public void expelCustomer(OccupancySignInterface sign) throws java.rmi.RemoteException;

    public int createTicket(TransactionLogInterface log, BigDecimal price) throws java.rmi.RemoteException;

    public boolean checkTicket(TransactionLogInterface transl, int customerTicket) throws java.rmi.RemoteException;

    public int createAndUpdateLostTicket(TransactionLogInterface log) throws java.rmi.RemoteException;

    public LocalDateTime getTime() throws java.rmi.RemoteException;

    public LocalDateTime standardExitTime() throws java.rmi.RemoteException;
}
