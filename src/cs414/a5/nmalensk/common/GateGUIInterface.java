package cs414.a5.nmalensk.common;

import cs414.a5.nmalensk.gui.GateGUI;

import javax.swing.*;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GateGUIInterface extends Remote {

    void showGUI() throws RemoteException;
    GateGUIInterface exportGUI() throws RemoteException;
    void refreshWindow() throws RemoteException;
    void addPanel(JPanel panel) throws RemoteException;
    void removePanel(JPanel panel) throws RemoteException;
    void showEntryPane() throws RemoteException;
    void hideEntryPane() throws RemoteException;
    void showMovementPane() throws RemoteException;
    void hideMovementPane() throws RemoteException;
    void showInitialPane() throws RemoteException;
    void hideInitialPane() throws RemoteException;
    void createFrame() throws RemoteException;
}
