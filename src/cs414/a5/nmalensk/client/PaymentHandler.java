package cs414.a5.nmalensk.client;


import cs414.a5.nmalensk.domain_logic.TransactionLog;

import java.math.BigDecimal;

public class PaymentHandler {

    public void promptForTotal(TransactionLog log, int ticketID) {
        BigDecimal total = log.getTicketPrice(ticketID);
        BigDecimal zero = new BigDecimal("0.00");
        while (total.compareTo(zero) > 0) {
            System.out.println("Balance due: " + total);
            System.out.println("Please enter 1 to pay cash or 2 to pay credit");
            total = total.subtract(verifyInput(total, zero));
        }
        if (total.compareTo(zero) < 0) {
            giveChange(total.negate());
        }
        System.out.println("Payment accepted, have a nice day!");
    }

    public BigDecimal verifyInput(BigDecimal amountDue, BigDecimal nothing) {
        String paymentType = TextInput.userInput();
        if (paymentType.equals("1")) {
            return handleCashPayment();
        } else if (paymentType.equals("2")) {
            return handleCreditPayment(amountDue);
        } else {
            System.out.println("Not a valid payment option, please re-enter!");
            return nothing;
        }
    }

    private BigDecimal handleCashPayment() {
        BigDecimal paymentAmount = null;
        System.out.println("Please insert cash:");
        try {
            paymentAmount = new BigDecimal(TextInput.userInput());
        } catch (NumberFormatException e) {
            System.out.println("Please enter an amount");
            handleCashPayment();
        }
        return paymentAmount;
    }

    private BigDecimal handleCreditPayment(BigDecimal amountDue) {
        System.out.println("Please insert credit card (press enter):");
        TextInput.pressEnter();
        return amountDue;
    }

    private void giveChange(BigDecimal changeAmount) {
        System.out.println("Your change is " + changeAmount);
        System.out.println("Please take change (press enter)");
        TextInput.pressEnter();
    }

}