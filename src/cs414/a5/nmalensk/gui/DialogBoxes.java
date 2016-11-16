package cs414.a5.nmalensk.gui;

import javax.swing.*;
import java.awt.*;

public class DialogBoxes {

    public String inputDialog(String message, String title) {
        JTextField field = new JTextField(20);
        JLabel label = new JLabel(message);
        JPanel panel = new JPanel(new BorderLayout(5, 2));
        panel.add(label, BorderLayout.NORTH);
        panel.add(field);
        JOptionPane.showMessageDialog(null, panel, title, JOptionPane.PLAIN_MESSAGE, null);

        return field.getText();
    }

    public void alertDialog(String message, int alertType) {
        JOptionPane.showMessageDialog(null, message, "Attention!", alertType);
    }

}
