package cs414.a5.nmalensk.client;

import cs414.a5.nmalensk.common.ParkingGarageInterface;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ParkingGarageClient {
    //TODO make multiple gates at server startup, command line args for each client to access diff gate

    public static void main(String[] args) {
        ParkingGarageInterface pGI = null;
        try {
            pGI = (ParkingGarageInterface)
                    Naming.lookup("rmi://" + args[0] + ":" + args[1] + "/ParkingGarageService");
        } catch (RemoteException re) {
            re.printStackTrace();
            System.exit(-1);
        } catch (NotBoundException nbe) {
            nbe.printStackTrace();
            System.exit(-1);
        } catch (MalformedURLException mre) {
            mre.printStackTrace();
            System.exit(-1);
        }

        try {
            ParkingGarage park = new ParkingGarage(pGI);
            park.initialMessage();
        } catch (RemoteException re) {
            re.printStackTrace();
            System.exit(-1);
        }
    }
}
