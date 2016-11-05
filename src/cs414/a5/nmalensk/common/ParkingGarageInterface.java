package cs414.a5.nmalensk.common;

public interface ParkingGarageInterface extends java.rmi.Remote {

    public int getOpenSpaces() throws java.rmi.RemoteException;
    public OccupancySignInterface getSign() throws java.rmi.RemoteException;
    public GarageGateInterface getGate() throws java.rmi.RemoteException;
    public TransactionLogInterface getTLog() throws java.rmi.RemoteException;

}
