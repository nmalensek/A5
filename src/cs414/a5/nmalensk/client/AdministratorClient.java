package cs414.a5.nmalensk.client;

import cs414.a5.nmalensk.common.ParkingGarageInterface;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import static cs414.a5.nmalensk.client.TextInput.userInput;

public class AdministratorClient {

    private static ReportGenerationUI reportGeneratorUI;

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
            reportGeneratorUI = new ReportGenerationUI(pGI.getTLog(), pGI.getGateList());
            MainMenu();
        } catch (RemoteException re) {
            re.printStackTrace();
            System.exit(-1);
        }
    }

    private static void MainMenu() throws RemoteException {
        while(true) {
            System.out.println("Choose from the following:");
            System.out.println("Please enter 1 to make an occupancy report (cars in garage per hour)");
            System.out.println("or 2 to make a sales report (sales per day and total in specified range)");
            System.out.println("3 - report on entries/exits by gate for range");
            String choice = userInput();
            optionsCheck(choice);
        }
    }

    private static void optionsCheck(String choice) throws RemoteException {
        if (choice.equals("1")) { reportGeneratorUI.generateCustomReport("occupancy"); }
        else if (choice.equals("2")) { reportGeneratorUI.generateCustomReport("sales"); }
        else if (choice.equals("3")) { reportGeneratorUI.generateCustomReport("gate"); }
        else {
            System.out.println("Please enter a valid option!");
        }
    }
}