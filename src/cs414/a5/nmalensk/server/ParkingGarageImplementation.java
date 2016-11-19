package cs414.a5.nmalensk.server;

import cs414.a5.nmalensk.common.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ParkingGarageImplementation
        extends UnicastRemoteObject
        implements ParkingGarageInterface{

    private static OccupancySignInterface sign;
    private TransactionLogInterface log;
    private List<GarageGateInterface> gateList = new ArrayList();

    public ParkingGarageImplementation(int spaces) throws java.rmi.RemoteException {
        sign = new OccupancySignImplementation(spaces);
        log = new TransactionLogImplementation(getGateList());
        FakeTicketGeneration fakeTickets = new FakeTicketGeneration(log);
    }

    public OccupancySignInterface getSign() throws java.rmi.RemoteException {
        return sign;
    }

    public TransactionLogInterface getTLog() throws java.rmi.RemoteException {
        return log;
    }

    public GarageGateInterface initializeGarageGate(String gateName) throws RemoteException {
        GarageGateInterface newGate = new GarageGateImplementation(gateName);
        return newGate;
    }

    public int getOpenSpaces() throws java.rmi.RemoteException {
        return sign.getOpenSpaces();
    }

    public List getGateList() throws RemoteException {
        return gateList;
    }

    public void gateInitialized(GarageGateInterface gate) throws RemoteException {
        System.out.println("Gate " + gate.getName()  + " online");
        gateList.add(gate);
    }

    public void gateShutDown(GarageGateInterface gate) throws RemoteException {
        System.out.println("Gate " + gate.getName() + " offline");
        gateList.remove(gate);
    }

}
