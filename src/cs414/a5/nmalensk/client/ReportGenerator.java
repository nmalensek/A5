package cs414.a5.nmalensk.client;

import cs414.a5.nmalensk.common.TransactionLogInterface;
import cs414.a5.nmalensk.domain_logic.TransactionLog;

import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import static cs414.a5.nmalensk.client.TextInput.userInput;

public class ReportGenerator {
    private LocalDateTime start;
    private LocalDateTime end;
    private TransactionLogInterface tLI;

    public ReportGenerator(TransactionLogInterface log) {
        tLI = log;
    }

    public void generateCustomReport(String reportType) throws RemoteException {
        generateStartDate();
        generateEndDate();
        if (reportType.equals("occupancy")) {
            System.out.println(tLI.printHourlyOccupancyData(start, end));
        } else if (reportType.equals("sales")) {
            System.out.println(tLI.printDailySalesReport(tLI.collectDaysWithSales(start, end)));
        }
    }

    private String askForDateString(String startOrEnd) {
        String date = "";
        System.out.println("Please enter " + startOrEnd + " date in format YYYY-MM-dd:");
        date += userInput();
        return date;
    }

    private String askForTimeString(String startOrEnd) {
        String choice = "";
        System.out.println("Please enter " + startOrEnd + " time in format HH:mm:");
        choice += userInput();
        return choice;
    }

    private LocalDateTime parseDateTimeInput(String date, String time)
            throws DateTimeParseException {
        return LocalDateTime.parse(date + "T" + time);
    }

    private void generateStartDate() {
        try {
            start = parseDateTimeInput(askForDateString("start (oldest)"),
                    askForTimeString("start (oldest)"));
        } catch (DateTimeParseException e) {
            System.out.println("Invalid input!");
            generateStartDate();
        }
    }

    private void generateEndDate() {
        try {
            end = parseDateTimeInput(askForDateString("end (newest)"),
                    askForTimeString("end (newest)"));
        } catch (DateTimeParseException e) {
            System.out.println("Invalid input!");
            generateEndDate();
        }
    }

    public void occupancyReportHeader() {
    }

}
