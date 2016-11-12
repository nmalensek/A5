package cs414.a5.nmalensk.server;

import cs414.a5.nmalensk.common.OccupancySignInterface;

import java.rmi.server.UnicastRemoteObject;

public class OccupancySignImplementation
        extends UnicastRemoteObject
        implements OccupancySignInterface {

    private int totalSpaces;
    private int filledSpaces = 0;

    public OccupancySignImplementation(int totalSpaces)
    throws java.rmi.RemoteException {
        this.totalSpaces = totalSpaces;
    }

    public String welcomeMessage() {
        String welcome = "Welcome to the parking garage!\n" +
                "Space available in garage: " + getOpenSpaces() + "\n";
        return welcome;
    }

    public int getOpenSpaces() {
        int openSpaces = (this.totalSpaces - filledSpaces);
        return openSpaces;
    }

    public void addOpenSpaces(int newlyOpenSpace) {
        filledSpaces -= newlyOpenSpace;
    }
}
