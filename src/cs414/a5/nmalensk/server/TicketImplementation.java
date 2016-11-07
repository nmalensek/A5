package cs414.a5.nmalensk.server;

import cs414.a5.nmalensk.common.TicketInterface;
import cs414.a5.nmalensk.common.TicketStatus;

import java.math.BigDecimal;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;

public class TicketImplementation
        extends UnicastRemoteObject
        implements TicketInterface {

    private int ticketID;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private BigDecimal price;
    private TicketStatus status;
    private String entryGate;
    private String exitGate;
    private static int currentTicketID = 100;

    public TicketImplementation(LocalDateTime entryTime, LocalDateTime exitTime,
                                BigDecimal price,
                                TicketStatus status,
                                String entryGate, String exitGate) throws java.rmi.RemoteException {
        this.ticketID = incrementTicketID();
        this.entryTime = entryTime;
        this.exitTime = exitTime;
        this.price = price;
        this.status = status;
        this.entryGate = entryGate;
        this.exitGate = exitGate;
    }

    public int incrementTicketID() {
        currentTicketID += 1;
        return currentTicketID;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public void setExitTime(LocalDateTime exitTime) {
        this.exitTime = exitTime;
    }

    public int getTicketID() {
        return ticketID;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal newPrice) {
        this.price = newPrice;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public void setCurrentTicketID(int id) {
        currentTicketID = id;
    }

    public String getEntryGate() {
        return entryGate;
    }

    public String getExitGate() {
        return exitGate;
    }

    public void setEntryGate(String newEntryGate) {
        this.entryGate = newEntryGate;
    }

    public void setExitGate(String newExitGate) {
        this.exitGate = newExitGate;
    }
}
