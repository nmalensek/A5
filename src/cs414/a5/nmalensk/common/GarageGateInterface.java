package cs414.a5.nmalensk.common;

import cs414.a5.nmalensk.gui.GateGUI;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.time.LocalDateTime;

public interface GarageGateInterface extends java.rmi.Remote {
     void admitCustomer(OccupancySignInterface sign) throws RemoteException;

     void expelCustomer(OccupancySignInterface sign) throws RemoteException;

     int createTicket(TransactionLogInterface log, BigDecimal price) throws RemoteException;

     boolean checkTicket(TransactionLogInterface transl, int customerTicket) throws RemoteException;

     int createAndUpdateLostTicket(TransactionLogInterface log) throws RemoteException;

     LocalDateTime getTime() throws RemoteException;

     LocalDateTime standardExitTime() throws RemoteException;

     String getName() throws RemoteException;

    void registerGateGUI(GateGUIInterface gui) throws RemoteException;

    void updateGUI() throws RemoteException;
}
