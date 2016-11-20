package cs414.a5.nmalensk.server;

import cs414.a5.nmalensk.common.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class GateReportGeneratorImplementation
extends UnicastRemoteObject
implements GateReportGeneratorInterface {

    private List gateList;

    public GateReportGeneratorImplementation(List gateList) throws RemoteException {
        this.gateList = gateList;
    }

    public String printGateEntries(TransactionLogInterface log,
                                   LocalDateTime start, LocalDateTime finish) throws RemoteException {
        Map<Integer, TicketInterface> assignedTickets = log.getAssignedTickets();
        String gateReport = String.format("%-5s %5s %8s %n", "Gate  |", "# cars entered |", "# cars exited");
        gateReport += "----------------------------------\n";
        for (int listPosition = 0; listPosition < gateList.size(); ++listPosition) {
            int numEntries = 0;
            int numExits = 0;
            GarageGateInterface gGI = (GarageGateInterface) gateList.get(listPosition);
            for (Integer key : assignedTickets.keySet()) {
                TicketInterface ticket = assignedTickets.get(key);
                if (gGI.getName().equals(ticket.getEntryGate()) && enteredInRange(ticket, start, finish)) {
                    numEntries += addGateEntries(gGI, ticket);
                }
                if (gGI.getName().equals(ticket.getExitGate()) && exitedInRange(ticket, start, finish)) {
                    numExits += addGateExits(gGI, ticket);
                }
            }
            gateReport += String.format("%-6s %1s %13s %11s %7s %n", gGI.getName(),
                    "|", numEntries, "|", numExits);
        }
        return gateReport;
    }

    private boolean enteredInRange(TicketInterface ticket, LocalDateTime start, LocalDateTime finish)
            throws RemoteException {
        if (ticket.getEntryTime().compareTo(start) >= 0 && ticket.getEntryTime().compareTo(finish) <= 0) {
            return true;
        } else {
            return false;
        }
    }

    private boolean exitedInRange(TicketInterface ticket, LocalDateTime start, LocalDateTime finish)
            throws RemoteException {
        if (ticket.getExitTime().compareTo(start) >= 0 && ticket.getExitTime().compareTo(finish) <= 0) {
            return true;
        } else {
            return false;
        }
    }

    private int addGateEntries(GarageGateInterface gGI, TicketInterface ticket) {
            return 1;
    }

    private int addGateExits(GarageGateInterface gGI, TicketInterface ticket) {
            return 1;
    }

}
