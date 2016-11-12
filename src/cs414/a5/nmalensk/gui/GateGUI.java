package cs414.a5.nmalensk.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import cs414.a5.nmalensk.client.CustomerUI;
import cs414.a5.nmalensk.common.*;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.awt.event.ActionEvent;
import java.rmi.server.UnicastRemoteObject;
import javax.swing.JLabel;

public class GateGUI extends UnicastRemoteObject implements GateGUIInterface {

	private JPanel contentPane;
    private OccupancySignInterface oSI;
    private GarageGateInterface gGI;
	private JFrame customerGUI;
	private JLabel openSpaces;
    private JLabel lblSpaceAvailable;
    private int test = 0;

	public GateGUI(ParkingGarageInterface pGI, GarageGateInterface gGI,
				   OccupancySignInterface oSI) throws RemoteException {
        this.oSI = oSI;
        this.gGI = gGI;
        CustomerUI cUI = new CustomerUI(pGI, gGI);
		customerGUI = new JFrame();
		customerGUI.setTitle("Welcome to the parking garage!");
		customerGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		customerGUI.setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		customerGUI.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton enterButton = new JButton("Enter Garage");
		enterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(oSI.getOpenSpaces() == 0) { enterButton.setEnabled(false); }
					else {
                        cUI.enterGarage();
                    }
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}
		});
		enterButton.setBounds(116, 75, 226, 43);
		contentPane.add(enterButton);
		
		JButton exitButton = new JButton("Exit Garage");
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("exit");
			}
		});
		exitButton.setBounds(116, 199, 226, 43);
		contentPane.add(exitButton);
		
		lblSpaceAvailable = new JLabel("Spaces: " + oSI.getOpenSpaces());
		lblSpaceAvailable.setBounds(26, 28, 114, 16);
		contentPane.add(lblSpaceAvailable);

        openSpaces = new JLabel(String.valueOf(oSI.getOpenSpaces()));
        openSpaces.setBounds(152, 28, 61, 16);
        contentPane.add(openSpaces);

//		showGUI();
	}

	public void showGUI() {
		customerGUI.setVisible(true);
	}

	public void refreshWindow() throws RemoteException {
        ++test;
        this.lblSpaceAvailable.setText("Spaces: " + oSI.getOpenSpaces());
        this.openSpaces.setText(String.valueOf(test));
        customerGUI.repaint();
        customerGUI.revalidate();
        System.out.println("refreshed");
    }

    public GateGUIInterface exportGUI() throws RemoteException {
        return this;
    }

}
