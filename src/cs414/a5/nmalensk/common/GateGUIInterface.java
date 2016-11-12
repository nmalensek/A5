package cs414.a5.nmalensk.common;

import cs414.a5.nmalensk.gui.GateGUI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GateGUIInterface extends Remote {

    void showGUI() throws RemoteException;
    GateGUIInterface exportGUI() throws RemoteException;
    void refreshWindow() throws RemoteException;
}
