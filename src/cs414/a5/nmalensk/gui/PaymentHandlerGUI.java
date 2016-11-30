package cs414.a5.nmalensk.gui;

import cs414.a5.nmalensk.common.GateGUIInterface;
import cs414.a5.nmalensk.common.TransactionLogInterface;
import cs414.a5.nmalensk.gui_methods.PaymentHandlerMethods;

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
    private int ticketID;
    private JLabel lblAmountDue;
    private PaymentHandlerMethods handlerMethods;

    public void retrieveTotal(TransactionLogInterface log, int ticketID,
                              GateGUIInterface mainMenu) throws RemoteException {
        total = log.getTicketPrice(ticketID);
        this.ticketID = ticketID;

        showPaymentGUI(mainMenu, total, log);
    }

    public void updateTotal(BigDecimal newTotal) {
        lblAmountDue.setText("Amount due: " + String.valueOf(newTotal));
    }

    public void hidePaymentGUI() {
        paymentPanel.setVisible(false);
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
                    handlerMethods.cashPayment(log);
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
                    handlerMethods.cardPayment(log);
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
        handlerMethods = new PaymentHandlerMethods(this, total, ticketID);
    }
}