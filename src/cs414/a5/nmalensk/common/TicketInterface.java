package cs414.a5.nmalensk.common;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.time.LocalDateTime;

public interface TicketInterface extends java.rmi.Remote {
    public LocalDateTime getEntryTime() throws RemoteException;
    public LocalDateTime getExitTime() throws RemoteException;
    public void setExitTime(LocalDateTime exitTime) throws RemoteException;
    public int getTicketID() throws RemoteException;
    public BigDecimal getPrice() throws RemoteException;
    public void setPrice(BigDecimal newPrice) throws RemoteException;
    public TicketStatus getStatus() throws RemoteException;
    public void setStatus(TicketStatus status) throws RemoteException;
    public void setCurrentTicketID(int id) throws RemoteException;
    public String getEntryGate() throws RemoteException;
    public String getExitGate() throws RemoteException;
    public void setEntryGate(String newEntryGate) throws RemoteException;
    public void setExitGate(String newExitGate) throws RemoteException;
}
