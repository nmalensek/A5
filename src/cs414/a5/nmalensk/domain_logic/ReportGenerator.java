package cs414.a5.nmalensk.domain_logic;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import static cs414.a5.nmalensk.client.TextInput.userInput;

public class ReportGenerator {
    private LocalDateTime start;
    private LocalDateTime end;

    public void generateCustomReport(TransactionLog log, String reportType) {
        generateStartDate();
        generateEndDate();
        if (reportType.equals("occupancy")) {
            log.printHourlyOccupancyData(start, end);
        } else if (reportType.equals("sales")) {
            log.printDailySalesReport(log.collectDaysWithSales(start, end));
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
