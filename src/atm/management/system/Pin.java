package atm.management.system;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class Pin extends JFrame implements ActionListener {

    JPasswordField t1, t2;
    JButton b1, b2;
    JLabel l1, l2, l3;
    String pin;

    Pin(String pin) {
        this.pin = pin;

        // Get screen dimensions
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        // Load and scale background image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/atm2.jpg"));
        Image i2 = i1.getImage().getScaledInstance(screenWidth, screenHeight, Image.SCALE_SMOOTH);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel background = new JLabel(i3);
        background.setBounds(0, 0, screenWidth, screenHeight);
        add(background);
        background.setLayout(null);

        // Offset added here (shift UI slightly to the right)
        int offsetX = screenWidth / 2 - 150 + 100;  // moved 100px right
        int baseY = screenHeight / 2 - 150;

        // Title
        l1 = new JLabel("CHANGE YOUR PIN");
        l1.setFont(new Font("System", Font.BOLD, 24));
        l1.setForeground(Color.WHITE);
        l1.setBounds(offsetX, baseY, 300, 35);
        background.add(l1);

        // New PIN
        l2 = new JLabel("New PIN:");
        l2.setFont(new Font("System", Font.BOLD, 18));
        l2.setForeground(Color.WHITE);
        l2.setBounds(offsetX, baseY + 60, 150, 30);
        background.add(l2);

        t1 = new JPasswordField();
        t1.setFont(new Font("Raleway", Font.BOLD, 22));
        t1.setBounds(offsetX + 170, baseY + 60, 200, 30);
        background.add(t1);

        // Re-enter New PIN
        l3 = new JLabel("Re-Enter New PIN:");
        l3.setFont(new Font("System", Font.BOLD, 18));
        l3.setForeground(Color.WHITE);
        l3.setBounds(offsetX, baseY + 110, 200, 30);
        background.add(l3);

        t2 = new JPasswordField();
        t2.setFont(new Font("Raleway", Font.BOLD, 22));
        t2.setBounds(offsetX + 170, baseY + 110, 200, 30);
        background.add(t2);

        // Buttons
        b1 = new JButton("CHANGE");
        b1.setBounds(offsetX + 30, baseY + 180, 150, 35);
        b1.setFont(new Font("System", Font.BOLD, 16));
        b1.addActionListener(this);
        background.add(b1);

        b2 = new JButton("BACK");
        b2.setBounds(offsetX + 200, baseY + 180, 150, 35);
        b2.setFont(new Font("System", Font.BOLD, 16));
        b2.addActionListener(this);
        background.add(b2);

        // Frame settings
        setSize(screenWidth, screenHeight);
        setLocation(0, 0);
        setUndecorated(true);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            String npin = t1.getText();
            String rpin = t2.getText();

            if (!npin.equals(rpin)) {
                JOptionPane.showMessageDialog(null, "Entered PIN does not match");
                return;
            }

            if (ae.getSource() == b1) {
                if (npin.equals("")) {
                    JOptionPane.showMessageDialog(null, "Enter New PIN");
                    return;
                }
                if (rpin.equals("")) {
                    JOptionPane.showMessageDialog(null, "Re-Enter new PIN");
                    return;
                }

                Conn c1 = new Conn();
                String q1 = "update bank set pin = '" + rpin + "' where pin = '" + pin + "'";
                String q2 = "update login set pin = '" + rpin + "' where pin = '" + pin + "'";
                String q3 = "update signup3 set pin = '" + rpin + "' where pin = '" + pin + "'";

                c1.s.executeUpdate(q1);
                c1.s.executeUpdate(q2);
                c1.s.executeUpdate(q3);

                JOptionPane.showMessageDialog(null, "PIN changed successfully");
                setVisible(false);
                new Transactions(rpin).setVisible(true);

            } else if (ae.getSource() == b2) {
                setVisible(false);
                new Transactions(pin).setVisible(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Pin("").setVisible(true);
    }
}
