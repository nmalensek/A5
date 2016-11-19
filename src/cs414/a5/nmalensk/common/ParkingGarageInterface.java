package cs414.a5.nmalensk.common;

import java.rmi.RemoteException;
import java.util.List;

 public interface ParkingGarageInterface extends java.rmi.Remote {

     int getOpenSpaces() throws RemoteException;
     OccupancySignInterface getSign() throws RemoteException;
     TransactionLogInterface getTLog() throws RemoteException;
     GarageGateInterface initializeGarageGate(String gateName) throws RemoteException;
     List getGateList() throws RemoteException;
     void gateInitialized(GarageGateInterface gate) throws RemoteException;
     void gateShutDown(GarageGateInterface gate) throws RemoteException;

}
