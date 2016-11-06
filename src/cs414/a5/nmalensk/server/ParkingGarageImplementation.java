package cs414.a5.nmalensk.server;

import cs414.a5.nmalensk.common.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ParkingGarageImplementation
        extends UnicastRemoteObject
        implements ParkingGarageInterface{

    private static OccupancySignInterface sign;
    private GarageGateInterface gate;
    private TransactionLogInterface log;

    public ParkingGarageImplementation(int spaces) throws java.rmi.RemoteException {
        sign = new OccupancySignImplementation(spaces);
        gate = new GarageGateImplementation();
        log = new TransactionLogImplementation();
        FakeTicketGeneration fakeTickets = new FakeTicketGeneration(log);
    }

    public OccupancySignInterface getSign() throws java.rmi.RemoteException {
        return sign;
    }

    public GarageGateInterface getGate() throws java.rmi.RemoteException {
        return gate;
    }

    public TransactionLogInterface getTLog() throws java.rmi.RemoteException {
        return log;
    }

    public int getOpenSpaces() throws java.rmi.RemoteException {
        return sign.getOpenSpaces();
    }

}
