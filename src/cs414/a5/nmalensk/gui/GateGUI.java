package cs414.a5.nmalensk.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import cs414.a5.nmalensk.client.CustomerUI;
import cs414.a5.nmalensk.common.*;

import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.awt.event.ActionEvent;
import java.rmi.server.UnicastRemoteObject;
import java.awt.FlowLayout;

public class GateGUI extends UnicastRemoteObject implements GateGUIInterface {

	private JPanel contentPane;
    private OccupancySignInterface oSI;
    private GarageGateInterface gGI;
	private CustomerUI cUI;
	private JFrame customerGUI;
	private GateGUI GUI;
	private JLabel lblSpaceAvailable;
	private JPanel initialPanel;
	private JLayeredPane mainPane;
	private JButton enterGarageButton;
	private ParkingGarageInterface pGI;

	public GateGUI(ParkingGarageInterface pGI, GarageGateInterface gGI,
				   OccupancySignInterface oSI) throws RemoteException {
        this.oSI = oSI;
        this.gGI = gGI;
        this.cUI = new CustomerUI(pGI, gGI);
		this.GUI = this;
		this.pGI = pGI;
	}

	public void createFrame() throws RemoteException {
		customerGUI = new JFrame();
		customerGUI.setTitle("Welcome to the parking garage! You are at gate " + gGI.getName());
		customerGUI.setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		customerGUI.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		mainPane = new JLayeredPane();
		mainPane.setBounds(6, 6, 438, 266);
		contentPane.add(mainPane);
		
		initialPanel = new JPanel();
		initialPanel.setBounds(6, 95, 426, 106);
		mainPane.add(initialPanel);
		initialPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		enterGarageButton = new JButton("Enter Garage");
		enterGarageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(oSI.getOpenSpaces() == 0) {
						enterGarageButton.setEnabled(false);
					lblSpaceAvailable.setText("Spaces: No space available!");}
					else {
						cUI.enterGarage(GUI);
					}
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}
		});
		initialPanel.add(enterGarageButton);
		
		JButton exitGarageButton = new JButton("Exit Garage");
		exitGarageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					cUI.exitGarage(GUI);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}
		});
		initialPanel.add(exitGarageButton);

		customerGUI.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				try {
					pGI.gateShutDown(gGI);
					System.exit(0);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		});
		
		lblSpaceAvailable = new JLabel();
		lblSpaceAvailable.setBounds(6, 18, 200, 16);
		lblSpaceAvailable.setText("Spaces: " + oSI.getOpenSpaces());
		mainPane.add(lblSpaceAvailable);
	}

	public void setLayer(JPanel panel) {mainPane.setLayer(panel, 1);}

	public void addPanel(JPanel panel) { mainPane.add(panel); }
	public void removePanel(JPanel panel) { mainPane.remove(panel); }

	public void showGUI() {
		customerGUI.setVisible(true);
	}

	public void showInitialPane() { initialPanel.setVisible(true); }
	public void hideInitialPane() { initialPanel.setVisible(false); }

	public void refreshWindow() throws RemoteException {
        isSpaceAvailable();
        customerGUI.repaint();
        customerGUI.revalidate();
    }

    private void isSpaceAvailable() throws RemoteException {
		if (oSI.getOpenSpaces() == 0) {
			enterGarageButton.setEnabled(false);
			this.lblSpaceAvailable.setText("Spaces: No space available!");
		} else {
			enterGarageButton.setEnabled(true);
			this.lblSpaceAvailable.setText("Spaces: " + oSI.getOpenSpaces());
		}
	}

    public GateGUIInterface exportGUI() throws RemoteException {
        return this;
    }
}
