package cs414.a5.nmalensk.gui;

import cs414.a5.nmalensk.common.TransactionLogInterface;

import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

public class AdministratorGUI extends JFrame {

	private JPanel contentPane;
	private JTextField txtStartdate;
	private JTextField txtStarttime;
	private JTextField txtEnddate;
	private JTextField txtEndTime;
	private JLabel lblEndDateformat;
	private JLabel lblStartTime;
	private JLabel lblEndTime;
	private JButton btnOk;
	private JLabel lblEnterTheDate;
	private DialogBoxes error;
	private JLayeredPane mainPane;
	private JPanel initialPanel;
	private TransactionLogInterface log;
	private List gateList;

	public AdministratorGUI(TransactionLogInterface log, List gateList) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		mainPane = new JLayeredPane();
		mainPane.setBounds(6, 6, 438, 266);
		contentPane.add(mainPane);
		
		initialPanel = new JPanel();
		initialPanel.setBounds(0, 0, 438, 266);
		mainPane.add(initialPanel);
		initialPanel.setLayout(null);
		
		txtStartdate = new JTextField();
		txtStartdate.setBounds(46, 84, 130, 26);
		initialPanel.add(txtStartdate);
		txtStartdate.setColumns(10);
		
		txtStarttime = new JTextField();
		txtStarttime.setBounds(237, 84, 130, 26);
		initialPanel.add(txtStarttime);
		txtStarttime.setColumns(10);
		
		txtEnddate = new JTextField();
		txtEnddate.setBounds(46, 168, 130, 26);
		initialPanel.add(txtEnddate);
		txtEnddate.setColumns(10);
		
		txtEndTime = new JTextField();
		txtEndTime.setBounds(237, 168, 130, 26);
		initialPanel.add(txtEndTime);
		txtEndTime.setColumns(10);
		
		JLabel lblStartDateformat = new JLabel("Start date (YYYY-MM-dd):");
		lblStartDateformat.setBounds(14, 56, 162, 16);
		initialPanel.add(lblStartDateformat);
		
		lblEndDateformat = new JLabel("End date (YYYY-MM-dd):");
		lblEndDateformat.setBounds(14, 140, 156, 16);
		initialPanel.add(lblEndDateformat);
		
		lblStartTime = new JLabel("Start time (HH:mm):");
		lblStartTime.setBounds(237, 56, 125, 16);
		initialPanel.add(lblStartTime);
		
		lblEndTime = new JLabel("End time (HH:mm):");
		lblEndTime.setBounds(237, 140, 119, 16);
		initialPanel.add(lblEndTime);
		
		btnOk = new JButton("Ok");
		btnOk.setBounds(175, 231, 75, 29);
		initialPanel.add(btnOk);
		
		lblEnterTheDate = new JLabel("Enter the date range to analyze:");
		lblEnterTheDate.setBounds(117, 6, 198, 16);
		initialPanel.add(lblEnterTheDate);
		
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					LocalDateTime start = parseDateTime(getDateTime(txtStartdate, txtStarttime));
					LocalDateTime end = parseDateTime(getDateTime(txtEnddate, txtEndTime));
					createReportGUI(start, end);
				} catch (DateTimeParseException dtpe) {
					error.alertDialog("Invalid input, please check your formatting!",
							JOptionPane.ERROR_MESSAGE);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}
		});

		error = new DialogBoxes();
		this.log = log;
		this.gateList = gateList;

		txtStartdate.setText("2016-10-01");
		txtEnddate.setText("2016-10-29");
		txtStarttime.setText("08:00");
		txtEndTime.setText("23:00");
	}

	private String getDateTime(JTextField dateField, JTextField timeField) {
		setDateTime(dateField, timeField);
		return dateField.getText() + "T" + timeField.getText();
	}

	private void setDateTime(JTextField dateField, JTextField timeField) {
		dateField.setText(dateField.getText());
		timeField.setText(timeField.getText());
	}

	private LocalDateTime parseDateTime(String dateTime) throws DateTimeParseException {
		return LocalDateTime.parse(dateTime);
	}

	private void createReportGUI(LocalDateTime start, LocalDateTime end) throws RemoteException {
		ReportGenerationGUI reportGenerationGUI = new ReportGenerationGUI(log, gateList);
		reportGenerationGUI.showReportGenerationGUI(this, start, end);
		hideInitialPane();
	}

	public void setLayer(JPanel panel) {mainPane.setLayer(panel, 1);}

	public void showInitialPane() { initialPanel.setVisible(true); }
	public void hideInitialPane() { initialPanel.setVisible(false); }

	public void removePanel(JPanel panel) { mainPane.remove(panel); }
}
