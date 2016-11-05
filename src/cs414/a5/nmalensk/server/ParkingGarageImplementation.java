package cs414.a5.nmalensk.server;

import cs414.a5.nmalensk.common.GarageGateInterface;
import cs414.a5.nmalensk.common.OccupancySignInterface;
import cs414.a5.nmalensk.common.ParkingGarageInterface;
import cs414.a5.nmalensk.common.TransactionLogInterface;

import java.rmi.server.UnicastRemoteObject;

public class ParkingGarageImplementation extends UnicastRemoteObject implements ParkingGarageInterface{

    private static OccupancySignInterface sign;
    private GarageGateInterface gate;
    private TransactionLogInterface log;

    public ParkingGarageImplementation(int spaces) throws java.rmi.RemoteException {
        sign = new OccupancySignImplementation(spaces);
        gate = new GarageGateImplementation();
        log = new TransactionLogImplementation();
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
