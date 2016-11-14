package cs414.a5.nmalensk.client;

import cs414.a5.nmalensk.common.GateGUIInterface;
import cs414.a5.nmalensk.gui.CustomerActionPanes;

import javax.swing.*;
import java.rmi.RemoteException;
import java.time.LocalDateTime;

public class PhysicalGarageGate {

    public void openGate(GateGUIInterface menu) throws RemoteException {
        CustomerActionPanes openPane = new CustomerActionPanes(menu);
        openPane.gateMovement("opening");
    }

    public void closeGate(GateGUIInterface menu) throws RemoteException {
        CustomerActionPanes closePane = new CustomerActionPanes(menu);
//        closePane.gateMovement("closing");
//        System.out.println("******** Gate is closing ********");
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("******** Gate is closed ********\n");
    }

    public void printTicket(int currentTicketID, LocalDateTime entryTime) {
        Object[] button = {"Take ticket"};
        JOptionPane.showOptionDialog(null, "-------------------------\n" +
                entryTime + "\n" +
                "TicketID: " + currentTicketID + "\n" +
                "-------------------------\n" +
                "Please take your ticket. The ticket ID must be presented upon exit.", "Printing ticket",
                JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, button, button[0]);

    }

    public void fullMessage() {
        System.out.println("No more space available in the garage, please wait for someone to exit!");
        System.out.println();
        System.out.println("Existing customers - enter 2 to exit the garage");
    }


}
