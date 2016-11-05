package cs414.a5.nmalensk.client;

import cs414.a5.nmalensk.common.GarageGateInterface;
import cs414.a5.nmalensk.common.OccupancySignInterface;
import cs414.a5.nmalensk.common.ParkingGarageInterface;
import cs414.a5.nmalensk.common.TransactionLogInterface;
import cs414.a5.nmalensk.domain_logic.*;

import java.math.BigDecimal;
import java.rmi.RemoteException;

import static cs414.a5.nmalensk.client.TextInput.pressEnter;
import static cs414.a5.nmalensk.client.TextInput.userInput;

public class ParkingGarage {
    private ParkingGarageInterface pGI;
    private GarageGateInterface gGI;
    private OccupancySignInterface oSI;
    private TransactionLogInterface tLI;
    private BigDecimal ticketPrice = BigDecimal.ZERO;

    public ParkingGarage(ParkingGarageInterface pGI) throws RemoteException {
        this.pGI = pGI;
        getRemoteObjects();
    }

    public void getRemoteObjects() throws RemoteException {
        oSI = pGI.getSign();
        gGI = pGI.getGate();
        tLI = pGI.getTLog();
    }

    PhysicalGarageGate physicalGate = new PhysicalGarageGate();
    PaymentHandler handler = new PaymentHandler();
//    FakeTicketGeneration fakeTickets = new FakeTicketGeneration(log);
//    ReportGenerator reportGenerator = new ReportGenerator();

    public void initialMessage() throws RemoteException {
        System.out.println(oSI.welcomeMessage());
        while (oSI.getOpenSpaces() > 0 && vacantOptions()) {
            System.out.println(oSI.welcomeMessage());
        }
        if (oSI.getOpenSpaces() <= 0) {
            fullOptions();
            initialMessage();
        }
    }

    private boolean vacantOptions() throws RemoteException {
        System.out.println("Please enter 1 to enter the garage or 2 to exit the garage.");
        String choice = userInput();
        if (optionsCheck(choice)) {
            return true;
        }
        return false;
    }

    private boolean optionsCheck(String choice) throws RemoteException {
        if (choice.equals("1")) {
            enterGarage();
        } else if (choice.equals("2")) {
            exitGarage();
        } else if (choice.equals("q")) {
            return false;
        } else {
            System.out.println("Invalid input!");
            vacantOptions();
        }
        return true;
    }

    private void fullOptions() {
        physicalGate.fullMessage();
        String choice = userInput();
        if (!choice.equals("2")) {
            fullOptions();
        } else {
            exitGarage();
        }
    }

    public void enterGarage() throws RemoteException {
        physicalGate.printTicket(gGI.createTicket(tLI, ticketPrice));
        pressEnter();
        physicalGate.openGate("enter");
        gGI.admitCustomer(oSI);
        pressEnter();
        physicalGate.closeGate();
    }

    private void exitGarage() {
        System.out.println("Please enter your ticket ID or 1 for a lost/unavailable ticket:");
        checkTicketValidity();
        pressEnter();
        physicalGate.closeGate();
    }

    private void checkTicketValidity() throws RemoteException {
        while (true) {
            try {
                int exitTicket = Integer.parseInt(userInput());
                if (exitTicket == 1) {
                    gGI.createAndUpdateLostTicket(tLI, handler, oSI);
                    gGI.expelCustomer(oSI);
                    break;
                } else if(gGI.checkTicket(tLI, exitTicket)) {
                    handler.promptForTotal(tLI, exitTicket);
                    gGI.expelCustomer(oSI);
                    break;
                } else {
                    System.out.println("Not a valid ticket ID, please re-enter or enter 1 for " +
                            "a lost/unavailable ticket:");
                }
            } catch (NumberFormatException e){
                System.out.println("Please enter a valid ticket ID format (numbers only):");
            }
        }
    }

//    public void generateCustomReport(String reportType) {
//        reportGenerator.generateCustomReport(log, reportType);
//    }
}