package cs414.a5.nmalensk.server;

import cs414.a5.nmalensk.common.GarageGateInterface;
import cs414.a5.nmalensk.common.TicketInterface;
import cs414.a5.nmalensk.common.TicketStatus;
import cs414.a5.nmalensk.common.TransactionLogInterface;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class TransactionLogImplementation
        extends UnicastRemoteObject
        implements TransactionLogInterface {

    private List gateList;

    //TODO split report methods into separate class, make getter for assignedTickets and use that to construct new class
    public TransactionLogImplementation(List gateList) throws java.rmi.RemoteException {
        this.gateList = gateList;
    }

    private Map<Integer, TicketInterface> assignedTickets = new HashMap<>();

    public LocalDateTime retrieveEntryTime(int ticketID) throws RemoteException {
        TicketInterface ticketToGet = assignedTickets.get(ticketID);
        return ticketToGet.getEntryTime();
    }

    public void addTicket(TicketInterface newTicket) {
        try {
            assignedTickets.put(newTicket.getTicketID(), newTicket);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public boolean isValidTicket(int inputTicketID) throws RemoteException {
        TicketInterface checkTicket = assignedTickets.get(inputTicketID);
        if (assignedTickets.get(inputTicketID) != null &&
                checkTicket.getStatus().equals(TicketStatus.UNPAID)) return true;
        return false;
    }

    public void modifyTicket(int modTicket,
                             LocalDateTime exitTime,
                             boolean isLost, String exitGate) throws RemoteException {
        TicketInterface ticket = assignedTickets.get(modTicket);
        ticket.setExitTime(exitTime);
        ticket.setPrice(calculateTicketPrice(ticket, isLost));
        ticket.setExitGate(exitGate);
    }

    public BigDecimal getTicketPrice(int ticketID) throws RemoteException {
        TicketInterface ticketToGet = assignedTickets.get(ticketID);
        return ticketToGet.getPrice();
    }

    public BigDecimal calculateTicketPrice(TicketInterface ticket, boolean isLost) throws RemoteException {
        if (isLost) {
            return ticket.getPrice();
        }
        BigDecimal hours =
                new BigDecimal(ticket.getEntryTime().until(ticket.getExitTime(), ChronoUnit.HOURS));
        BigDecimal ratePerHour = new BigDecimal("1.00").setScale(2, RoundingMode.HALF_UP);

        return ratePerHour.multiply(hours).add(BigDecimal.ONE);
    }

    public void markTicketPaid(int ticketID) throws RemoteException {
        TicketInterface ticket = assignedTickets.get(ticketID);
        ticket.setStatus(TicketStatus.PAID);
        System.out.println("Ticket " + ticketID + " successfully exited");
    }

    public Map<LocalDateTime, BigDecimal> collectDaysWithSales(LocalDateTime start,
                                                               LocalDateTime finish) throws RemoteException {
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

    public String printDailySalesReport(Map<LocalDateTime, BigDecimal> map) {
        BigDecimal totalForDay = new BigDecimal("0.00").setScale(2, RoundingMode.HALF_UP);
        BigDecimal totalForRange = new BigDecimal("0.00").setScale(2, RoundingMode.HALF_UP);
        String salesReport = "";

        for (LocalDateTime key : map.keySet()) {
            totalForDay = totalForDay.add(map.get(key));
            totalForRange = totalForRange.add(map.get(key));
            salesReport += key + ": $" + totalForDay + "\n";
            totalForDay = BigDecimal.ZERO;
        }
        salesReport += "Total sales in range: $" + totalForRange;
        return salesReport;
    }

    public String printHourlyOccupancyData(LocalDateTime start, LocalDateTime finish) throws RemoteException {
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

    public String printGateEntries(LocalDateTime start, LocalDateTime finish) throws RemoteException {
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

    public String printGateExits(LocalDateTime start, LocalDateTime finish) throws RemoteException {
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
