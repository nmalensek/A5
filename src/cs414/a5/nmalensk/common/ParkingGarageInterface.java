package cs414.a5.nmalensk.common;

import java.rmi.RemoteException;
import java.util.List;

public interface ParkingGarageInterface extends java.rmi.Remote {

    public int getOpenSpaces() throws RemoteException;
    public OccupancySignInterface getSign() throws RemoteException;
    public TransactionLogInterface getTLog() throws RemoteException;
    public List getGateList() throws RemoteException;
    public void gateInitialized(GarageGateInterface gate) throws RemoteException;

}
