package cs414.a5.nmalensk.client;

import cs414.a5.nmalensk.common.ReportGeneratorInterface;
import cs414.a5.nmalensk.common.TransactionLogInterface;
import cs414.a5.nmalensk.domain_logic.TransactionLog;
import cs414.a5.nmalensk.server.ReportGeneratorImplementation;

import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

import static cs414.a5.nmalensk.client.TextInput.userInput;

public class ReportGenerationUI {
    private LocalDateTime start;
    private LocalDateTime end;
    private TransactionLogInterface tLI;
    private ReportGeneratorInterface rGI;

    public ReportGenerationUI(TransactionLogInterface log, List gateList) throws RemoteException {
        tLI = log;
        rGI = new ReportGeneratorImplementation(gateList);
    }

    public void generateCustomReport(String reportType) throws RemoteException {
        generateStartDate();
        generateEndDate();
        if (reportType.equals("occupancy")) {
            System.out.println(rGI.printHourlyOccupancyData(tLI, start, end));
        } else if (reportType.equals("sales")) {
            System.out.println(rGI.printDailySalesReport(rGI.collectDaysWithSales(tLI, start, end)));
        } else if (reportType.equals("gate")) {
            System.out.println(rGI.printGateEntries(tLI, start, end));
            System.out.println();
            System.out.println(rGI.printGateExits(tLI, start, end));
        } else if (reportType.equals("tickets")) {
            System.out.println(rGI.lostVersusNotTickets(tLI, start, end));
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
}
