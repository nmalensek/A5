package cs414.a5.nmalensk.server;

import cs414.a5.nmalensk.common.ParkingGarageInterface;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class ParkingGarageServer {
    private String url;

    public ParkingGarageServer(int spacesInGarage, String url) {
        this.url = url;

        try {
            LocateRegistry.createRegistry(2500);
            System.setProperty("java.rmi.server.localhost", "120.0.0.1");
            ParkingGarageInterface pG = new ParkingGarageImplementation(spacesInGarage);
            Naming.rebind(url, pG);
            System.out.println("Parking garage server running...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int spacesInGarage = Integer.parseInt(args[0]);
        String url = "rmi://" + args[1] + ":" + args[2] + "/ParkingGarageService";
        new ParkingGarageServer(spacesInGarage, url);
    }

}
