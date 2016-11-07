package cs414.a5.nmalensk.client;

import cs414.a5.nmalensk.common.GarageGateInterface;
import cs414.a5.nmalensk.common.ParkingGarageInterface;
import cs414.a5.nmalensk.server.GarageGateImplementation;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class GateClient {
    //TODO make multiple gates at server startup, command line args for each client to access diff gate

    public static void main(String[] args) {
        ParkingGarageInterface pGI = null;
        try {
            pGI = (ParkingGarageInterface)
                    Naming.lookup("rmi://" + args[0] + ":" + args[1] + "/ParkingGarageService");
        } catch (RemoteException | NotBoundException | MalformedURLException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        String gateName = args[2];

        try {
            GarageGateInterface gGI = new GarageGateImplementation(gateName);
            ParkingGarage park = new ParkingGarage(pGI, gGI);
            pGI.gateInitialized(gGI);
            park.initialMessage();
        } catch (RemoteException re) {
            re.printStackTrace();
            System.exit(-1);
        }
    }
}
