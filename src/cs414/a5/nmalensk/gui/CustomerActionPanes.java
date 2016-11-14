package cs414.a5.nmalensk.gui;


import cs414.a5.nmalensk.common.GateGUIInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;

public class CustomerActionPanes implements ActionListener, PropertyChangeListener {

    private GateGUIInterface mainMenu;
    private Timer timer;
    private JLayeredPane layeredPane;

    public CustomerActionPanes(GateGUIInterface mainMenu) {
        this.mainMenu = mainMenu;
        timer = new Timer(5000, this);
    }

    public void gateMovement(String action) throws RemoteException {
        mainMenu.hideInitialPane();
        mainMenu.showMovementPane();
        timer.start();
    }

    public void propertyChange(PropertyChangeEvent e) {
    }

    public void actionPerformed(ActionEvent action) {
        try {
            closePane();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void closePane() throws RemoteException {
        mainMenu.hideMovementPane();
        mainMenu.showInitialPane();
    }

}
