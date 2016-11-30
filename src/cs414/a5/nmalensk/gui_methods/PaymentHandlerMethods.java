package cs414.a5.nmalensk.gui_methods;

import cs414.a5.nmalensk.common.TransactionLogInterface;
import cs414.a5.nmalensk.gui.DialogBoxes;
import cs414.a5.nmalensk.gui.PaymentHandlerGUI;

import javax.swing.*;
import java.math.BigDecimal;
import java.rmi.RemoteException;

public class PaymentHandlerMethods {
    private PaymentHandlerGUI paymentHandlerGUI;
    private DialogBoxes dialogs;
    private BigDecimal total;
    private int ticketID;

    public PaymentHandlerMethods(PaymentHandlerGUI paymentHandlerGUI, BigDecimal total, int ticketID) {
        this.paymentHandlerGUI = paymentHandlerGUI;
        this.total = total;
        this.ticketID = ticketID;
        dialogs = new DialogBoxes();
    }

    public void cashPayment(TransactionLogInterface log) throws RemoteException {
        total = total.subtract(handleCashInput());
        if (total.compareTo(BigDecimal.ZERO) > 0) {
            paymentHandlerGUI.updateTotal(total);
        } else {
            giveChange(total.negate());
            log.markTicketPaid(ticketID);
            paymentHandlerGUI.hidePaymentGUI();
        }
    }

    private BigDecimal handleCashInput() {
        BigDecimal paymentAmount = BigDecimal.ZERO;
        try {
            paymentAmount = new BigDecimal(dialogs.inputDialog("Please insert cash:", "Insert cash"));
        } catch (NumberFormatException e) {
            dialogs.alertDialog("Please enter a valid amount (numbers only)",
                    JOptionPane.ERROR_MESSAGE);
        }
        return paymentAmount;
    }

    private void giveChange(BigDecimal changeAmount) {
        dialogs.alertDialog("Your change is " + changeAmount + "," +
                " Please take change", JOptionPane.INFORMATION_MESSAGE);
    }

    public void cardPayment(TransactionLogInterface log) throws RemoteException {
        total = total.subtract(handleCreditInput(total));
        dialogs.alertDialog("Payment accepted!", JOptionPane.INFORMATION_MESSAGE);
        log.markTicketPaid(ticketID);
        paymentHandlerGUI.hidePaymentGUI();
    }

    private BigDecimal handleCreditInput(BigDecimal amountDue) {
        try {
            int cardNumber = Integer.parseInt(
                    dialogs.inputDialog("Please insert credit card (type number):",
                            "Enter card number"));
        } catch (NumberFormatException e) {
            dialogs.alertDialog("Please enter a valid card number (numbers only)!",
                    JOptionPane.ERROR_MESSAGE);
            handleCreditInput(amountDue);
        }
        return amountDue;
    }
}
