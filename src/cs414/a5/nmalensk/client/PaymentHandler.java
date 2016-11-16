package cs414.a5.nmalensk.client;


import cs414.a5.nmalensk.common.GateGUIInterface;
import cs414.a5.nmalensk.common.TransactionLogInterface;
import cs414.a5.nmalensk.gui.DialogBoxes;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.rmi.RemoteException;

public class PaymentHandler {
    private JPanel paymentPanel;
    private JButton btnCashPayment;
    private JButton btnCreditPayment;
    private BigDecimal total;
    private DialogBoxes dialogs;
    private int ticketID;
    private JLabel lblAmountDue;

    public PaymentHandler() {
        dialogs = new DialogBoxes();
    }

    public void promptForTotal(TransactionLogInterface log, int ticketID,
                               GateGUIInterface mainMenu) throws RemoteException {
        total = log.getTicketPrice(ticketID);
        this.ticketID = ticketID;

        showPaymentGUI(mainMenu, total, log);

//        BigDecimal zero = new BigDecimal("0.00");
//        while (total.compareTo(zero) > 0) {
//            total = total.subtract(verifyInput(total, zero));
//        }
//        if (total.compareTo(zero) < 0) {
//            giveChange(total.negate());
//        }
//        log.markTicketPaid(ticketID);
//        System.out.println("Payment accepted, have a nice day!");
    }

    public BigDecimal verifyInput(BigDecimal amountDue, BigDecimal nothingDeducted) {
        String paymentType = TextInput.userInput();
        if (paymentType.equals("1")) {
            return handleCashPayment();
        } else if (paymentType.equals("2")) {
            return handleCreditPayment(amountDue);
        } else {
            System.out.println("Not a valid payment option, please re-enter!");
            return nothingDeducted;
        }
    }

    private BigDecimal handleCashPayment() {
        BigDecimal paymentAmount = null;
        try {
            paymentAmount = new BigDecimal(dialogs.inputDialog("Please insert cash:", "Insert cash"));
        } catch (NumberFormatException e) {
            dialogs.alertDialog("Please enter a valid amount (numbers only)",
                    JOptionPane.ERROR_MESSAGE);
            handleCashPayment();
        }
        return paymentAmount;
    }

    private BigDecimal handleCreditPayment(BigDecimal amountDue) {
        try {
            int cardNumber = Integer.parseInt(
            dialogs.inputDialog("Please insert credit card (type number):",
                    "Enter card number"));
        } catch (NumberFormatException e) {
            dialogs.alertDialog("Please enter a valid ticket ID format (numbers only)!",
                    JOptionPane.ERROR_MESSAGE);
            handleCreditPayment(amountDue);
        }
        return amountDue;
    }

    private void cashPayment(TransactionLogInterface log) throws RemoteException {
        total = total.subtract(handleCashPayment());
        if (total.compareTo(BigDecimal.ZERO) > 0) {
            lblAmountDue.setText("Amount due: " + String.valueOf(total));
        } else {
            giveChange(total.negate());
            log.markTicketPaid(ticketID);
            paymentPanel.setVisible(false);
        }
    }

    private void cardPayment(TransactionLogInterface log) throws RemoteException {
        total = total.subtract(handleCreditPayment(total));
        dialogs.alertDialog("Payment accepted!", JOptionPane.INFORMATION_MESSAGE);
        log.markTicketPaid(ticketID);
        paymentPanel.setVisible(false);
    }

    private void giveChange(BigDecimal changeAmount) {
        dialogs.alertDialog("Your change is " + changeAmount + "," +
        " Please take change", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showPaymentGUI(GateGUIInterface mainMenu, BigDecimal total, TransactionLogInterface log) throws RemoteException {
        paymentPanel = new JPanel();
        paymentPanel.setBounds(6, 67, 426, 400);
        paymentPanel.setLayout(null);
        paymentPanel.setVisible(false);

        mainMenu.setLayer(paymentPanel);

        btnCashPayment = new JButton("Cash payment");
        btnCashPayment.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    cashPayment(log);
                } catch (RemoteException e1) {

                }
            }
        });
        btnCashPayment.setBounds(40, 56, 133, 29);
        paymentPanel.add(btnCashPayment);

        btnCreditPayment = new JButton("Credit payment");
        btnCreditPayment.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    cardPayment(log);
                } catch (RemoteException e1) {
                    e1.printStackTrace();
                }
            }
        });
        btnCreditPayment.setBounds(231, 56, 147, 29);
        paymentPanel.add(btnCreditPayment);

        lblAmountDue = new JLabel("Amount due: " + String.valueOf(total));
        lblAmountDue.setBounds(169, 6, 150, 16);
        paymentPanel.add(lblAmountDue);

        mainMenu.hideInitialPane();
        mainMenu.addPanel(paymentPanel);
        paymentPanel.setVisible(true);
    }
}