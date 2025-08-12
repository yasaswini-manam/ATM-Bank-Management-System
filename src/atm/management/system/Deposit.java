package atm.management.system;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class Deposit extends JFrame implements ActionListener {
    
    JTextField t1;
    JButton b1, b2;
    JLabel l1;
    String pin;
    
    Deposit(String pin) {
        this.pin = pin;

        // Get screen dimensions
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        // Load and scale the image to fullscreen
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/atm2.jpg"));
        Image i2 = i1.getImage().getScaledInstance(screenWidth, screenHeight, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel background = new JLabel(i3);
        background.setBounds(0, 0, screenWidth, screenHeight);
        add(background);

        setLayout(null);

        // Label: ENTER AMOUNT
        l1 = new JLabel("ENTER AMOUNT YOU WANT TO DEPOSIT");
        l1.setForeground(Color.WHITE);
        l1.setFont(new Font("System", Font.BOLD, 18));
        l1.setBounds(screenWidth / 2 - 20, screenHeight / 2 - 100, 400, 30);
        background.add(l1);

        // Text Field
        t1 = new JTextField();
        t1.setFont(new Font("Raleway", Font.BOLD, 22));
        t1.setBounds(screenWidth / 2 - 10, screenHeight / 2 - 40, 320, 30);
        background.add(t1);

        // Deposit Button
        b1 = new JButton("DEPOSIT");
        b1.setBounds(screenWidth / 2 + 75, screenHeight / 2 + 30, 150, 35);
        background.add(b1);

        // Back Button
        b2 = new JButton("BACK");
        b2.setBounds(screenWidth / 2 + 75, screenHeight / 2 + 80, 150, 35);
        background.add(b2);

        b1.addActionListener(this);
        b2.addActionListener(this);

        // Frame properties
        setSize(screenWidth, screenHeight);
        setUndecorated(true);
        setLocation(0, 0);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            String amount = t1.getText();
            Date date = new Date();

            if (ae.getSource() == b1) {
                if (amount.equals("")) {
                    JOptionPane.showMessageDialog(null, "Please enter the Amount you want to Deposit");
                } else {
                    // Validate if the entered amount is numeric
                    if (!isNumeric(amount)) {
                        JOptionPane.showMessageDialog(null, "Please enter a valid numeric amount.");
                        return;
                    }

                    Conn c1 = new Conn();
                    String query = "INSERT INTO bank (pin, mode, date, amount) VALUES('" + pin + "', 'Deposit', '" + date + "', '" + amount + "')";
                    c1.s.executeUpdate(query); 

                    JOptionPane.showMessageDialog(null, "Rs. " + amount + " Deposited Successfully");
                    setVisible(false);
                    new Transactions(pin).setVisible(true);
                }

            } else if (ae.getSource() == b2) {
                setVisible(false);
                new Transactions(pin).setVisible(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Helper method to check if the input is numeric
    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);  // Try to parse the string as a double
            return true;  // If successful, it is numeric
        } catch (NumberFormatException e) {
            return false;  // If an exception occurs, the string is not numeric
        }
    } 

    public static void main(String[] args) {
        new Deposit("").setVisible(true);
    }
}
