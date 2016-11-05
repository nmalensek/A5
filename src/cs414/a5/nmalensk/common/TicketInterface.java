package cs414.a5.nmalensk.common;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface TicketInterface extends java.rmi.Remote {
    public int incrementTicketID() throws java.rmi.RemoteException;
    public LocalDateTime getEntryTime() throws java.rmi.RemoteException;
    public LocalDateTime getExitTime() throws java.rmi.RemoteException;
    public void setExitTime(LocalDateTime exitTime) throws java.rmi.RemoteException;
    public int getTicketID() throws java.rmi.RemoteException;
    public BigDecimal getPrice() throws java.rmi.RemoteException;
    public void setPrice(BigDecimal newPrice) throws java.rmi.RemoteException;
    public TicketStatus getStatus() throws java.rmi.RemoteException;
    public void setStatus(TicketStatus status) throws java.rmi.RemoteException;
    public void setCurrentTicketID(int id) throws java.rmi.RemoteException;
}
