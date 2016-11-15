package cs414.a5.nmalensk.gui;


import cs414.a5.nmalensk.common.GateGUIInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;

public class CustomerActionPanes {

    private GateGUIInterface mainMenu;
    private JPanel enterPanel;
    private JPanel movementPanel;

    public CustomerActionPanes(GateGUIInterface mainMenu) {
        this.mainMenu = mainMenu;
    }

    public void gateMovement() throws RemoteException {

        movementPanel = new JPanel();
        movementPanel.setBounds(6, 90, 426, 134);
        movementPanel.setLayout(null);
        movementPanel.setVisible(false);

        JLabel lblGateIsOpening = new JLabel("Gate is now closed...");
        lblGateIsOpening.setBounds(107, 6, 217, 29);
        movementPanel.add(lblGateIsOpening);

        JButton okButton = new JButton("Ok");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                movementPanel.setVisible(false);
                try {
                    mainMenu.removePanel(movementPanel);
                    mainMenu.showInitialPane();
                } catch (RemoteException e1) {
                    e1.printStackTrace();
                }
            }
        });
        okButton.setBounds(143, 57, 117, 29);
        movementPanel.add(okButton);

        movementPanel.setVisible(true);
        mainMenu.addPanel(movementPanel);
    }

    public void finishedMoving(String action) throws RemoteException {
        enterPanel = new JPanel();
        enterPanel.setBounds(6, 90, 426, 134);
        enterPanel.setLayout(null);
        enterPanel.setVisible(false);

        JLabel lblGateIsOpen = new JLabel("Gate is open, please " + action + " garage");
        lblGateIsOpen.setBounds(107, 6, 217, 29);
        enterPanel.add(lblGateIsOpen);

        JButton enterButton = new JButton("Ok");
        enterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                enterPanel.setVisible(false);
                try {
                    mainMenu.removePanel(enterPanel);
                } catch (RemoteException e1) {
                    e1.printStackTrace();
                }
            }
        });
        enterButton.setBounds(143, 57, 117, 29);
        enterPanel.add(enterButton);

        enterPanel.setVisible(true);
        mainMenu.addPanel(enterPanel);

        mainMenu.hideInitialPane();
    }

}
