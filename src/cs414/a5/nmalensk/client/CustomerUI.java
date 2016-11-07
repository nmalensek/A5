package cs414.a5.nmalensk.client;

import cs414.a5.nmalensk.common.GarageGateInterface;
import cs414.a5.nmalensk.common.OccupancySignInterface;
import cs414.a5.nmalensk.common.ParkingGarageInterface;
import cs414.a5.nmalensk.common.TransactionLogInterface;

import java.math.BigDecimal;
import java.rmi.RemoteException;

import static cs414.a5.nmalensk.client.TextInput.pressEnter;
import static cs414.a5.nmalensk.client.TextInput.userInput;

public class CustomerUI {
    private ParkingGarageInterface pGI;
    private GarageGateInterface gGI;
    private OccupancySignInterface oSI;
    private TransactionLogInterface tLI;
    private BigDecimal ticketPrice = BigDecimal.ZERO;

    public CustomerUI(ParkingGarageInterface pGI, GarageGateInterface gGI) throws RemoteException {
        this.pGI = pGI;
        this.gGI = gGI;
        getRemoteObjects();
    }

    public void getRemoteObjects() throws RemoteException {
        oSI = pGI.getSign();
        tLI = pGI.getTLog();
    }

    PhysicalGarageGate physicalGate = new PhysicalGarageGate();
    PaymentHandler handler = new PaymentHandler();

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
        if (choice.equals("1") && thereAreStillOpenSpaces()) {
            enterGarage();
        } else if (choice.equals("2")) {
            exitGarage();
        } else if (choice.equals("q")) {
            return false;
        } else {
            System.out.println("Invalid input or no more spaces available!\n");
        }
        return true;
    }

    private void fullOptions() throws RemoteException {
        physicalGate.fullMessage();
        String choice = userInput();
        if (!choice.equals("2")) {
            fullOptions();
        } else {
            exitGarage();
        }
    }

    public void enterGarage() throws RemoteException {
        int newTicket = gGI.createTicket(tLI, ticketPrice);
        physicalGate.printTicket(newTicket, tLI.retrieveEntryTime(newTicket));
        pressEnter();
        physicalGate.openGate("enter");
        gGI.admitCustomer(oSI);
        pressEnter();
        physicalGate.closeGate();
    }

    private void exitGarage() throws RemoteException {
        System.out.println("Please enter your ticket ID or 1 for a lost/unavailable ticket:");
        checkTicketValidity();
        physicalGate.openGate("exit");
        pressEnter();
        physicalGate.closeGate();
    }

    private void checkTicketValidity() throws RemoteException {
        while (true) {
            try {
                int exitTicket = Integer.parseInt(userInput());
                if (exitTicket == 1) {
                    int lostTicket = gGI.createAndUpdateLostTicket(tLI);
                    handler.promptForTotal(tLI, lostTicket);
                    gGI.expelCustomer(oSI);
                    break;
                } else if(gGI.checkTicket(tLI, exitTicket)) {
                    System.out.println("Ticket accepted!");
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

    private boolean thereAreStillOpenSpaces() throws RemoteException {
        if (oSI.getOpenSpaces() > 0) return true;
        return false;
    }

}