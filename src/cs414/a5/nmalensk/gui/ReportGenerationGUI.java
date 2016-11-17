package cs414.a5.nmalensk.gui;

import cs414.a5.nmalensk.common.GateReportGeneratorInterface;
import cs414.a5.nmalensk.common.ReportGeneratorInterface;
import cs414.a5.nmalensk.common.TransactionLogInterface;
import cs414.a5.nmalensk.server.GateReportGeneratorImplementation;
import cs414.a5.nmalensk.server.ReportGeneratorImplementation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.List;

public class ReportGenerationGUI {

    private JPanel reportPanel;
    private JLabel lblSelectReportType;
    private JLabel lblStart;
    private JLabel lblEnd;
    private JButton btnHourlyOccupancy;
    private JButton btnSales;
    private JButton btnGateEntryexit;
    private JButton btnNormallostTickets;
    private JButton btnBack;
    private TransactionLogInterface log;
    private List gateList;
    private DialogBoxes dialog;
    private ReportGeneratorInterface rGI;
    private GateReportGeneratorInterface gateReports;

    public ReportGenerationGUI(TransactionLogInterface log, List gateList) throws RemoteException {
        this.log = log;
        this.gateList = gateList;
        dialog = new DialogBoxes();
        rGI = new ReportGeneratorImplementation();
        gateReports = new GateReportGeneratorImplementation(gateList);
    }

    /**
     * @wbp.parser.entryPoint
     */
    public void showReportGenerationGUI(AdministratorGUI adminGUI, LocalDateTime start,
                                        LocalDateTime end) {

        reportPanel = new JPanel();
        reportPanel.setBounds(0, 0, 438, 266);
        reportPanel.setLayout(null);
        reportPanel.setVisible(true);

        lblSelectReportType = new JLabel("Select report type to generate for range");
        lblSelectReportType.setBounds(88, 6, 259, 16);
        reportPanel.add(lblSelectReportType);

        lblStart = new JLabel(String.valueOf(start) + "     -");
        lblStart.setBounds(61, 34, 162, 16);
        reportPanel.add(lblStart);

        lblEnd = new JLabel(String.valueOf(end));
        lblEnd.setBounds(235, 34, 134, 16);
        reportPanel.add(lblEnd);

        btnHourlyOccupancy = new JButton("Hourly occupancy");
        btnHourlyOccupancy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    generateOccupancyReport(start, end);
                } catch (RemoteException e1) {
                    e1.printStackTrace();
                }
            }
        });
        btnHourlyOccupancy.setBounds(33, 99, 157, 29);
        reportPanel.add(btnHourlyOccupancy);

        btnSales = new JButton("Sales");
        btnSales.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    generateSalesReport(start, end);
                } catch (RemoteException e1) {
                    e1.printStackTrace();
                }
            }
        });
        btnSales.setBounds(235, 99, 157, 29);
        reportPanel.add(btnSales);

        btnGateEntryexit = new JButton("Gate entries/exits");
        btnGateEntryexit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    generateGateReport(start, end);
                } catch (RemoteException e1) {
                    e1.printStackTrace();
                }
            }
        });
        btnGateEntryexit.setBounds(34, 168, 157, 29);
        reportPanel.add(btnGateEntryexit);

        btnNormallostTickets = new JButton("Normal/lost tickets");
        btnNormallostTickets.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    generateLostTicketReport(start, end);
                } catch (RemoteException e1) {
                    e1.printStackTrace();
                }
            }
        });
        btnNormallostTickets.setBounds(235, 168, 157, 29);
        reportPanel.add(btnNormallostTickets);

        btnBack = new JButton("Back");
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                reportPanel.setVisible(false);
                adminGUI.removePanel(reportPanel);
                adminGUI.showInitialPane();
            }
        });
        btnBack.setBounds(157, 219, 117, 29);
        reportPanel.add(btnBack);

        adminGUI.setLayer(reportPanel);
        adminGUI.getContentPane().add(reportPanel);
    }

    private void generateOccupancyReport(LocalDateTime start, LocalDateTime end) throws RemoteException {
        dialog.alertDialog(rGI.printHourlyOccupancyData(log, start, end),
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void generateSalesReport(LocalDateTime start, LocalDateTime end) throws RemoteException {
        dialog.alertDialog(rGI.printDailySalesReport(rGI.collectDaysWithSales(log, start, end)),
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void generateGateReport(LocalDateTime start, LocalDateTime end) throws RemoteException {
        dialog.alertDialog(gateReports.printGateEntries(log, start, end),
                JOptionPane.INFORMATION_MESSAGE);
        dialog.alertDialog(gateReports.printGateExits(log, start, end),
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void generateLostTicketReport(LocalDateTime start, LocalDateTime end) throws RemoteException {
        dialog.alertDialog(rGI.lostVersusNotTickets(log, start, end),
                JOptionPane.INFORMATION_MESSAGE);
    }

}
