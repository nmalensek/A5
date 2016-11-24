package cs414.a5.nmalensk.client;

import cs414.a5.nmalensk.common.*;
import cs414.a5.nmalensk.gui.PaymentHandlerGUI;
import cs414.a5.nmalensk.gui.TicketInputGUI;

import java.math.BigDecimal;
import java.rmi.RemoteException;

public class CustomerUI {
    private ParkingGarageInterface pGI;
    private GarageGateInterface gGI;
    private OccupancySignInterface oSI;
    private TransactionLogInterface tLI;
    private BigDecimal ticketPrice = BigDecimal.ZERO;

    public CustomerUI(ParkingGarageInterface pGI, GarageGateInterface gGI) throws RemoteException {
        this.pGI = pGI;
        this.gGI = gGI;
        oSI = pGI.getSign();
        tLI = pGI.getTLog();
    }

    PhysicalGarageGate physicalGate = new PhysicalGarageGate();
    PaymentHandlerGUI handler = new PaymentHandlerGUI();

    public void enterGarage(GateGUIInterface menu) throws RemoteException {
        int newTicket = gGI.createTicket(tLI, ticketPrice);
        physicalGate.printTicket(newTicket, tLI.retrieveEntryTime(newTicket));
        physicalGate.openGate(menu, "enter");
        oSI.addOpenSpaces(-1);
        tLI.updateGates();
    }

    public void exitGarage(GateGUIInterface menu) throws RemoteException {
        checkTicketValidity(menu);
        physicalGate.openGate(menu, "exit");
        oSI.addOpenSpaces(1);
        tLI.updateGates();
    }

    private void checkTicketValidity(GateGUIInterface menu) throws RemoteException {
        TicketInputGUI tIG = new TicketInputGUI();
        tIG.showTicketInput(menu, gGI, tLI, handler);
    }
}