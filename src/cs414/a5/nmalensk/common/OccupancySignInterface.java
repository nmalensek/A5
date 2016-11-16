package cs414.a5.nmalensk.common;

public interface OccupancySignInterface extends java.rmi.Remote {
    int getOpenSpaces() throws java.rmi.RemoteException;
    void addOpenSpaces(int newlyOpenSpace) throws java.rmi.RemoteException;

}
