package cs414.a5.nmalensk.common;

public interface OccupancySignInterface extends java.rmi.Remote {
    public int getOpenSpaces() throws java.rmi.RemoteException;
    public String welcomeMessage() throws java.rmi.RemoteException;
    public void addOpenSpaces(int newlyOpenSpace) throws java.rmi.RemoteException;

}
