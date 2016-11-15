package cs414.a5.nmalensk.client;

import cs414.a5.nmalensk.common.*;
import cs414.a5.nmalensk.gui.DialogBoxes;

import javax.swing.*;
import java.math.BigDecimal;
import java.rmi.RemoteException;

public class CustomerUI {
    private ParkingGarageInterface pGI;
    private GarageGateInterface gGI;
    private OccupancySignInterface oSI;
    private TransactionLogInterface tLI;
    private BigDecimal ticketPrice = BigDecimal.ZERO;
    DialogBoxes dialog = new DialogBoxes();

    public CustomerUI(ParkingGarageInterface pGI, GarageGateInterface gGI) throws RemoteException {
        this.pGI = pGI;
        this.gGI = gGI;
        oSI = pGI.getSign();
        tLI = pGI.getTLog();
    }

    PhysicalGarageGate physicalGate = new PhysicalGarageGate();
    PaymentHandler handler = new PaymentHandler();

    public void enterGarage(GateGUIInterface menu) throws RemoteException {
        int newTicket = gGI.createTicket(tLI, ticketPrice);
        physicalGate.printTicket(newTicket, tLI.retrieveEntryTime(newTicket));
        physicalGate.openGate(menu, "enter");
        oSI.addOpenSpaces(-1);
        tLI.updateGates();
    }

    public void exitGarage(GateGUIInterface menu) throws RemoteException {
        checkTicketValidity();
        physicalGate.openGate(menu, "exit");
        oSI.addOpenSpaces(1);
        tLI.updateGates();
    }

    private void checkTicketValidity() throws RemoteException {
        while (true) {
            try {
                int exitTicket = Integer.parseInt(
                        dialog.inputDialog("Please enter your ticket ID or 1 for a lost/unavailable ticket:")
                );
                if (exitTicket == 1) {
                    handleLostTicket();
                    break;
                } else if(gGI.checkTicket(tLI, exitTicket)) {
                    handleValidTicket(exitTicket);
                    break;
                } else {
                    dialog.alertDialog("Not a valid ticket ID, please re-enter or enter 1 for " +
                            "a lost/unavailable ticket", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e){
                dialog.alertDialog("Please enter a valid ticket ID format (numbers only)!",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void handleLostTicket() throws RemoteException {
        int lostTicket = gGI.createAndUpdateLostTicket(tLI);
        handler.promptForTotal(tLI, lostTicket);
    }

    private void handleValidTicket(int exitTicket) throws RemoteException {
        dialog.alertDialog("Ticket accepted!", JOptionPane.INFORMATION_MESSAGE);
        handler.promptForTotal(tLI, exitTicket);
    }


}