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
        private static int currentTicketID = 100;

        public TicketImplementation(LocalDateTime entryTime, LocalDateTime exitTime,
                                    BigDecimal price,
                                    TicketStatus status) throws java.rmi.RemoteException {
            this.ticketID = incrementTicketID();
            this.entryTime = entryTime;
            this.exitTime = exitTime;
            this.price = price;
            this.status = status;
        }

        private int incrementTicketID() {
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

        public void setPrice(BigDecimal newPrice) { this.price = newPrice; }

        public TicketStatus getStatus() {
            return status;
        }

        public void setStatus(TicketStatus status) {
            this.status = status;
        }

        public void setCurrentTicketID(int id) { currentTicketID = id; }
}
