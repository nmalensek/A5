package cs414.a5.nmalensk.domain_logic;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class TransactionLog {
    private Map<Integer, Ticket> assignedTickets = new HashMap<>();

    public void addTicket(Ticket newTicket) {
        assignedTickets.put(newTicket.getTicketID(), newTicket);
    }

    public boolean isValidTicket(int inputTicketID) {
        Ticket checkTicket = assignedTickets.get(inputTicketID);
        if (assignedTickets.get(inputTicketID) != null &&
                checkTicket.getStatus().equals(TicketStatus.UNPAID)) return true;
        return false;
    }

    public void modifyTicket(int modTicket, LocalDateTime exitTime, TicketStatus newStatus, boolean isLost) {
        Ticket ticket = assignedTickets.get(modTicket);
        ticket.setExitTime(exitTime);
        ticket.setStatus(newStatus);
        ticket.setPrice(calculateTicketPrice(ticket, isLost));
    }

    public BigDecimal getTicketPrice(int ticketID) {
        Ticket ticketToGet = assignedTickets.get(ticketID);
        return ticketToGet.getPrice();
    }

    public BigDecimal calculateTicketPrice(Ticket ticket, boolean isLost) {
        if (isLost) { return ticket.getPrice(); }
        BigDecimal hours =
                new BigDecimal(ticket.getEntryTime().until(ticket.getExitTime(), ChronoUnit.HOURS));
        BigDecimal ratePerHour = new BigDecimal("1.00").setScale(2, RoundingMode.HALF_UP);

        return ratePerHour.multiply(hours).add(BigDecimal.ONE);
    }

    public Map<LocalDateTime, BigDecimal> collectDaysWithSales(LocalDateTime start, LocalDateTime finish) {
        Map<LocalDateTime, BigDecimal> salesMap = new TreeMap<>();
        for (Integer key : assignedTickets.keySet()) {
            Ticket ticket = assignedTickets.get(key);
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

    public void printDailySalesReport(Map<LocalDateTime, BigDecimal> map) {
        BigDecimal totalForDay = new BigDecimal("0.00").setScale(2, RoundingMode.HALF_UP);
        BigDecimal totalForRange = new BigDecimal("0.00").setScale(2, RoundingMode.HALF_UP);

        for (LocalDateTime key : map.keySet()) {
            totalForDay = totalForDay.add(map.get(key));
            totalForRange = totalForRange.add(map.get(key));
            System.out.println(key + ": $" + totalForDay);
            totalForDay = BigDecimal.ZERO;
        }
        System.out.println("Total sales in range: $" + totalForRange);
    }

    public void printHourlyOccupancyData(LocalDateTime start, LocalDateTime finish) {
        System.out.printf("%-5s %5s %n", "Hour |", "# cars in garage");
        System.out.println("------------------------");
        for (int hour = 0; hour < 24; ++hour) {
            int numCars = 0;
            for (Integer key : assignedTickets.keySet()) {
                Ticket ticket = assignedTickets.get(key);
                if (ticket.getEntryTime().compareTo(start) >= 0 && ticket.getEntryTime().compareTo(finish) <= 0) {
                    if (ticket.getEntryTime().getHour() <= hour && ticket.getExitTime().getHour() >= hour) {
                        numCars++;
                    }
                }
            }
            System.out.printf("%-5s %1s %5s %n", hour, "|", numCars);
        }
    }

}
