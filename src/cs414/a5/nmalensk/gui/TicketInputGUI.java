package cs414.a5.nmalensk.gui;

import cs414.a5.nmalensk.common.GarageGateInterface;
import cs414.a5.nmalensk.common.GateGUIInterface;
import cs414.a5.nmalensk.common.TransactionLogInterface;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

public class TicketInputGUI {

    private JPanel ticketPanel;
    private JButton okButton;
    private JButton lostTicketButton;
    private JLabel lblEnterTicket;
    private DialogBoxes dialogs;

    public TicketInputGUI() {
        this.dialogs = new DialogBoxes();
    }

    public void showTicketInput(GateGUIInterface mainMenu, GarageGateInterface gGI,
                                  TransactionLogInterface tLI, PaymentHandlerGUI handler) throws RemoteException {
        ticketPanel = new JPanel();
        ticketPanel.setBounds(6, 67, 426, 400);
        ticketPanel.setLayout(null);
        ticketPanel.setVisible(false);

        JTextField field = new JTextField(20);
        field.setLayout(null);
        field.setBounds(140, 56, 133, 29);
        ticketPanel.add(field);

        mainMenu.setLayer(ticketPanel);

        okButton = new JButton("Ok");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String input = field.getText();
                    handleValidTicket(mainMenu, gGI, tLI, handler, input);
                } catch (RemoteException e1) {
                    e1.printStackTrace();
                } catch (NumberFormatException nfe) {
                    dialogs.alertDialog("Please enter a valid ticket ID (numbers only)!",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        okButton.setBounds(40, 100, 133, 29);
        ticketPanel.add(okButton);

        lostTicketButton = new JButton("Lost ticket");
        lostTicketButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ticketPanel.setVisible(false);
                try {
                    mainMenu.removePanel(ticketPanel);
                    handleLostTicket(mainMenu, gGI, tLI, handler);
                } catch (RemoteException e1) {
                    e1.printStackTrace();
                }
            }
        });
        lostTicketButton.setBounds(231, 100, 147, 29);
        ticketPanel.add(lostTicketButton);

        lblEnterTicket = new JLabel("Please enter your ticket ID:");
        lblEnterTicket.setBounds(120, 6, 300, 80);
        ticketPanel.add(lblEnterTicket);

        mainMenu.hideInitialPane();
        mainMenu.addPanel(ticketPanel);
        ticketPanel.setVisible(true);
    }


    private void handleValidTicket(GateGUIInterface menu, GarageGateInterface gate, TransactionLogInterface log,
                                   PaymentHandlerGUI handler, String input) throws RemoteException,
            NumberFormatException {
            int exitTicket = Integer.parseInt(input);
            if (gate.checkTicket(log, exitTicket)) {
                dialogs.alertDialog("Ticket accepted!", JOptionPane.INFORMATION_MESSAGE);
                ticketPanel.setVisible(false);
                menu.removePanel(ticketPanel);
                handler.retrieveTotal(log, exitTicket, menu);
            } else {
                dialogs.alertDialog("Invalid ticket ID, re-enter or select 'Lost ticket'.",
                        JOptionPane.ERROR_MESSAGE);
            }
    }

    private void handleLostTicket(GateGUIInterface menu, GarageGateInterface gate, TransactionLogInterface log,
                                  PaymentHandlerGUI handler) throws RemoteException {
        int lostTicket = gate.createAndUpdateLostTicket(log);
        ticketPanel.setVisible(false);
        menu.removePanel(ticketPanel);
        handler.retrieveTotal(log, lostTicket, menu);
    }
}
