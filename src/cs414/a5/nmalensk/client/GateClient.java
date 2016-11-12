package cs414.a5.nmalensk.client;

import cs414.a5.nmalensk.common.GarageGateInterface;
import cs414.a5.nmalensk.common.GateGUIInterface;
import cs414.a5.nmalensk.common.ParkingGarageInterface;
import cs414.a5.nmalensk.gui.GateGUI;
import cs414.a5.nmalensk.server.GarageGateImplementation;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class GateClient {

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
            GarageGateInterface gGI = pGI.initializeGarageGate(gateName);
            pGI.gateInitialized(gGI);
            GateGUIInterface menu = new GateGUI(pGI, gGI, pGI.getSign());
            gGI.registerGateGUI(menu.exportGUI());
            menu.showGUI();
        } catch (RemoteException re) {
            re.printStackTrace();
            System.exit(-1);
        }
    }
}
