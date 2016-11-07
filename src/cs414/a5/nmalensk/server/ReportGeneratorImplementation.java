package cs414.a5.nmalensk.server;

import cs414.a5.nmalensk.common.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ReportGeneratorImplementation
        extends UnicastRemoteObject
        implements ReportGeneratorInterface {

    private List gateList;


    public ReportGeneratorImplementation(List gateList) throws RemoteException {
        this.gateList = gateList;
    }

    public Map<LocalDateTime, BigDecimal> collectDaysWithSales(TransactionLogInterface log,
                                                                LocalDateTime start,
                                                               LocalDateTime finish) throws RemoteException {
        Map<Integer, TicketInterface> assignedTickets = log.getAssignedTickets();
        Map<LocalDateTime, BigDecimal> salesMap = new TreeMap<>();
        for (Integer key : assignedTickets.keySet()) {
            TicketInterface ticket = assignedTickets.get(key);
            if (ticket.getStatus().equals(TicketStatus.PAID) && ticket.getExitTime().compareTo(start) >= 0 &&
                    ticket.getExitTime().compareTo(finish) <= 0) {
                if (salesMap.containsKey(ticket.getExitTime().truncatedTo(ChronoUnit.DAYS))) {
                    salesMap.put(ticket.getExitTime().truncatedTo(ChronoUnit.DAYS),
                            salesMap.get(ticket.getExitTime().truncatedTo(ChronoUnit.DAYS)).add(ticket.getPrice()));
                } else {
                    salesMap.put(ticket.getExitTime().truncatedTo(ChronoUnit.DAYS), ticket.getPrice());
                }
            }
        }
        return salesMap;
    }

    public String printDailySalesReport(Map<LocalDateTime, BigDecimal> map) throws RemoteException {
        BigDecimal totalForDay = new BigDecimal("0.00").setScale(2, RoundingMode.HALF_UP);
        BigDecimal totalForRange = new BigDecimal("0.00").setScale(2, RoundingMode.HALF_UP);
        String salesReport = "";
        String ticketType = "";

        for (LocalDateTime key : map.keySet()) {
            ticketType +=
            totalForDay = totalForDay.add(map.get(key));
            totalForRange = totalForRange.add(map.get(key));
            salesReport += key + ": $" + totalForDay + "\n";
            totalForDay = BigDecimal.ZERO;
        }
        salesReport += "Total sales in range: $" + totalForRange;
        return salesReport;
    }


    public String printHourlyOccupancyData(TransactionLogInterface log,
                                           LocalDateTime start, LocalDateTime finish) throws RemoteException {
        Map<Integer, TicketInterface> assignedTickets = log.getAssignedTickets();
        String occReport = String.format("%-5s %5s %n", "Hour  |", "# cars in garage");
        occReport += "------------------------\n";
        for (int hour = 0; hour < 24; ++hour) {
            int numCars = 0;
            for (Integer key : assignedTickets.keySet()) {
                TicketInterface ticket = assignedTickets.get(key);
                if (ticket.getEntryTime().compareTo(start) >= 0 && ticket.getEntryTime().compareTo(finish) <= 0) {
                    if (ticket.getEntryTime().getHour() <= hour && ticket.getExitTime().getHour() >= hour) {
                        numCars++;
                    }
                }
            }
            occReport += String.format("%-5s %1s %5s %n", hour, "|", numCars);
        }
        return occReport;
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
