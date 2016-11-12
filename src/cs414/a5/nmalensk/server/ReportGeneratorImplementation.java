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

    public ReportGeneratorImplementation() throws RemoteException {
    }

    public Map<LocalDateTime, BigDecimal> collectDaysWithSales(TransactionLogInterface log,
                                                                LocalDateTime start,
                                                               LocalDateTime finish) throws RemoteException {
        Map<Integer, TicketInterface> assignedTickets = log.getAssignedTickets();
        Map<LocalDateTime, BigDecimal> salesMap = new TreeMap<>();
        for (Integer key : assignedTickets.keySet()) {
            TicketInterface ticket = assignedTickets.get(key);
            if (ticketPaidAndInRange(ticket, start, finish)) {
                if (salesMap.containsKey(ticket.getExitTime().truncatedTo(ChronoUnit.DAYS))) {
                    addTicketSaleToThatDay(salesMap, ticket);
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

        for (LocalDateTime key : map.keySet()) {
            totalForDay = totalForDay.add(map.get(key));
            totalForRange = totalForRange.add(map.get(key));
            salesReport += key + ": $" + totalForDay + "\n";
            totalForDay = BigDecimal.ZERO;
        }
        salesReport += "Total sales in range: $" + totalForRange + "\n";
        salesReport += "Average sales per day in range: $" +
                totalForRange.divide(BigDecimal.valueOf(map.size()), BigDecimal.ROUND_HALF_UP) + "\n";
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

    public String lostVersusNotTickets(TransactionLogInterface log,
                                 LocalDateTime start, LocalDateTime finish) throws RemoteException {
        Map<Integer, TicketInterface> assignedTickets = log.getAssignedTickets();
        String ticketReport = String.format("%-5s %5s %n", "# normal exits |", "# lost tickets");
        ticketReport += "--------------------------------\n";
        int lostTickets = 0;
        int normalTickets = 0;
        for (Integer key : assignedTickets.keySet()) {
            TicketInterface ticket = assignedTickets.get(key);
            if (ticketPaidAndInRange(ticket, start, finish)) {
                if (ticket.getLostStatus()) {
                    lostTickets++;
                } else {
                    normalTickets++;
                }
            }
        }
        ticketReport += String.format("%5s %10s %5s %n", normalTickets, "|", lostTickets);
        return ticketReport;
    }

    private boolean ticketPaidAndInRange(TicketInterface ticket,
                                           LocalDateTime start, LocalDateTime finish) throws RemoteException {
        if (ticket.getStatus().equals(TicketStatus.PAID) && ticket.getExitTime().compareTo(start) >= 0 &&
                ticket.getExitTime().compareTo(finish) <= 0) {
            return true;
        } else { return false; }
    }

    private void addTicketSaleToThatDay(Map<LocalDateTime, BigDecimal> map,
                                        TicketInterface t) throws RemoteException {
        map.put(t.getExitTime().truncatedTo(ChronoUnit.DAYS),
                map.get(t.getExitTime().truncatedTo(ChronoUnit.DAYS)).add(t.getPrice()));
    }
}
