package cs414.a5.nmalensk.client;

import cs414.a5.nmalensk.common.ParkingGarageInterface;
import cs414.a5.nmalensk.gui.AdministratorGUI;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class AdministratorClient {

    private static AdministratorGUI adminGUI;

    public static void main(String[] args) {
        ParkingGarageInterface pGI = null;
        try {
            pGI = (ParkingGarageInterface)
                    Naming.lookup("rmi://" + args[0] + ":" + args[1] + "/ParkingGarageService");
        } catch (RemoteException | NotBoundException | MalformedURLException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        try {
            adminGUI = new AdministratorGUI(pGI);
            adminGUI.setVisible(true);
        } catch (RemoteException re) {
            re.printStackTrace();
            System.exit(-1);
        }
    }
}