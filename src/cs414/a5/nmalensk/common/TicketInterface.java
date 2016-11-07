package cs414.a5.nmalensk.common;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.time.LocalDateTime;

public interface TicketInterface extends java.rmi.Remote {
     LocalDateTime getEntryTime() throws RemoteException;
     LocalDateTime getExitTime() throws RemoteException;
     void setExitTime(LocalDateTime exitTime) throws RemoteException;
     int getTicketID() throws RemoteException;
     BigDecimal getPrice() throws RemoteException;
     void setPrice(BigDecimal newPrice) throws RemoteException;
     TicketStatus getStatus() throws RemoteException;
     void setStatus(TicketStatus status) throws RemoteException;
     void setCurrentTicketID(int id) throws RemoteException;
     String getEntryGate() throws RemoteException;
     String getExitGate() throws RemoteException;
     void setEntryGate(String newEntryGate) throws RemoteException;
     void setExitGate(String newExitGate) throws RemoteException;
     boolean getLostStatus() throws RemoteException;
     void setLostStatus(boolean lostStatus) throws RemoteException;
}
