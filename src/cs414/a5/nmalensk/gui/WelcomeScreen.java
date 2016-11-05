package cs414.a5.nmalensk.gui;

import javax.swing.*;
import java.awt.event.*;

public class WelcomeScreen extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;

    public WelcomeScreen() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onAdministrator();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCustomer();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onAdministrator() {
        dispose();
        CustomerGUI customerGUI = new CustomerGUI();
        customerGUI.pack();
        customerGUI.setVisible(true);
    }

    private void onCustomer() {
        System.out.println("customer");
        dispose();
    }

    private void onCancel() {

    }

    public static void main(String[] args) {
        WelcomeScreen dialog = new WelcomeScreen();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
