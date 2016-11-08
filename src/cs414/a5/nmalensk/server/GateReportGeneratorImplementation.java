package cs414.a5.nmalensk.server;

import cs414.a5.nmalensk.common.GarageGateInterface;
import cs414.a5.nmalensk.common.GateReportGeneratorInterface;
import cs414.a5.nmalensk.common.TicketInterface;
import cs414.a5.nmalensk.common.TransactionLogInterface;

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
        String gateReport = String.format("%-5s %5s %n", "Gate  |", "# cars entered");
        gateReport += "------------------------\n";
        for (int listPosition = 0; listPosition < gateList.size(); ++listPosition) {
            int numEntries = 0;
            GarageGateInterface gGI = (GarageGateInterface) gateList.get(listPosition);
            for (Integer key : assignedTickets.keySet()) {
                TicketInterface ticket = assignedTickets.get(key);
                if (gGI.getName().equals(ticket.getEntryGate())) {
                    numEntries++;
                }
            }
            gateReport += String.format("%-5s %1s %5s %n", gGI.getName(), "|", numEntries);
        }
        return gateReport;
    }

    public String printGateExits(TransactionLogInterface log,
                                 LocalDateTime start, LocalDateTime finish) throws RemoteException {
        Map<Integer, TicketInterface> assignedTickets = log.getAssignedTickets();
        String gateReport = String.format("%-5s %5s %n", "Gate  |", "# cars exited");
        gateReport += "------------------------\n";
        for (int listPosition = 0; listPosition < gateList.size(); ++listPosition) {
            int numEntries = 0;
            GarageGateInterface gGI = (GarageGateInterface) gateList.get(listPosition);
            for (Integer key : assignedTickets.keySet()) {
                TicketInterface ticket = assignedTickets.get(key);
                if (gGI.getName().equals(ticket.getExitGate())) {
                    numEntries++;
                }
            }
            gateReport += String.format("%-5s %1s %5s %n", gGI.getName(), "|", numEntries);
        }
        return gateReport;
    }

}
