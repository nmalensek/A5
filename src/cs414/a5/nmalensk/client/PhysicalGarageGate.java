package cs414.a5.nmalensk.client;

import cs414.a5.nmalensk.common.GateGUIInterface;
import cs414.a5.nmalensk.gui.GateActionPanes;

import javax.swing.*;
import java.rmi.RemoteException;
import java.time.LocalDateTime;

public class PhysicalGarageGate {

    public void openGate(GateGUIInterface menu, String action) throws RemoteException {
        GateActionPanes openPane = new GateActionPanes(menu);
        openPane.finishedMoving(action  );
        openPane.gateMovement();
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

}
