package cs414.a5.nmalensk.gui;

import cs414.a5.nmalensk.common.GateGUIInterface;
import cs414.a5.nmalensk.common.TransactionLogInterface;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.rmi.RemoteException;

public class PaymentHandlerGUI {
    private JPanel paymentPanel;
    private JButton btnCashPayment;
    private JButton btnCreditPayment;
    private BigDecimal total;
    private DialogBoxes dialogs;
    private int ticketID;
    private JLabel lblAmountDue;

    public PaymentHandlerGUI() {
        dialogs = new DialogBoxes();
    }

    public void showPaymentGUI(GateGUIInterface mainMenu, BigDecimal total,
                               TransactionLogInterface log) throws RemoteException {
        paymentPanel = new JPanel();
        paymentPanel.setBounds(6, 67, 426, 400);
        paymentPanel.setLayout(null);

        mainMenu.setLayer(paymentPanel);

        btnCashPayment = new JButton("Cash payment");
        btnCashPayment.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    cashPayment(log);
                } catch (RemoteException | NullPointerException renpe) {

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
        lblAmountDue.setBounds(130, 6, 150, 16);
        paymentPanel.add(lblAmountDue);

        mainMenu.hideInitialPane();
        mainMenu.addPanel(paymentPanel);
        paymentPanel.setVisible(true);
    }

    public void hidePaymentPanel() {
        paymentPanel.setVisible(false);
    }

    public void updateTotal(BigDecimal newTotal) {
        lblAmountDue.setText("Amount due: " + String.valueOf(newTotal));
    }

    public void promptForTotal(TransactionLogInterface log, int ticketID,
                               GateGUIInterface mainMenu) throws RemoteException {
        total = log.getTicketPrice(ticketID);
        this.ticketID = ticketID;

        showPaymentGUI(mainMenu, total, log);
    }

    private BigDecimal handleCashPayment() {
        BigDecimal paymentAmount = BigDecimal.ZERO;
        try {
            paymentAmount = new BigDecimal(dialogs.inputDialog("Please insert cash:", "Insert cash"));
        } catch (NumberFormatException e) {
            dialogs.alertDialog("Please enter a valid amount (numbers only)",
                    JOptionPane.ERROR_MESSAGE);
        }
        return paymentAmount;
    }

    private BigDecimal handleCreditPayment(BigDecimal amountDue) {
        try {
            int cardNumber = Integer.parseInt(
            dialogs.inputDialog("Please insert credit card (type number):",
                    "Enter card number"));
        } catch (NumberFormatException e) {
            dialogs.alertDialog("Please enter a valid card number (numbers only)!",
                    JOptionPane.ERROR_MESSAGE);
            handleCreditPayment(amountDue);
        }
        return amountDue;
    }

    private void cashPayment(TransactionLogInterface log) throws RemoteException {
        total = total.subtract(handleCashPayment());
        if (total.compareTo(BigDecimal.ZERO) > 0) {
            updateTotal(total);
        } else {
            giveChange(total.negate());
            log.markTicketPaid(ticketID);
            hidePaymentPanel();
        }
    }

    private void cardPayment(TransactionLogInterface log) throws RemoteException {
        total = total.subtract(handleCreditPayment(total));
        dialogs.alertDialog("Payment accepted!", JOptionPane.INFORMATION_MESSAGE);
        log.markTicketPaid(ticketID);
        hidePaymentPanel();
    }

    private void giveChange(BigDecimal changeAmount) {
        dialogs.alertDialog("Your change is " + changeAmount + "," +
        " Please take change", JOptionPane.INFORMATION_MESSAGE);
    }
}