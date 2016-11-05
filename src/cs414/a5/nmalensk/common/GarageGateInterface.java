package cs414.a5.nmalensk.common;

import java.math.BigDecimal;

public interface GarageGateInterface extends java.rmi.Remote {
    public void admitCustomer(OccupancySignInterface sign) throws java.rmi.RemoteException;

    public void expelCustomer(OccupancySignInterface sign) throws java.rmi.RemoteException;

    public int createTicket(TransactionLogInterface log, BigDecimal price) throws java.rmi.RemoteException;
//
//    public boolean checkTicket(TransactionLog transl, int customerTicket) throws java.rmi.RemoteException;
//
//    public void createAndUpdateLostTicket(TransactionLog log,
//                                          PaymentHandler handler,
//                                          OccupancySign sign) throws java.rmi.RemoteException;
//
//    public void closeGate() throws java.rmi.RemoteException;
//
//    public void printTicket(Ticket currentTicket) throws java.rmi.RemoteException;
//
//    public void printFullMessage() throws java.rmi.RemoteException;
//
//    public LocalDateTime getTime() throws java.rmi.RemoteException;
//
//    public LocalDateTime standardExitTime() throws java.rmi.RemoteException;
}
