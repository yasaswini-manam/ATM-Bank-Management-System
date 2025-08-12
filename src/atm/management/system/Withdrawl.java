package atm.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Date;

public class Withdrawl extends JFrame implements ActionListener {

    JTextField t1;
    JButton b1, b2;
    JLabel l1, l2;
    String pin;

    Withdrawl(String pin) {
        this.pin = pin;

        // Set full screen
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setLayout(null);

        // Load and set background image for full screen
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("icons/atm2.jpg"));
        Image scaledImage = icon.getImage().getScaledInstance(Toolkit.getDefaultToolkit().getScreenSize().width,
                Toolkit.getDefaultToolkit().getScreenSize().height, Image.SCALE_SMOOTH);
        ImageIcon fullSizeIcon = new ImageIcon(scaledImage);
        JLabel background = new JLabel(fullSizeIcon);
        background.setBounds(0, 0, getWidth(), getHeight());
        setContentPane(background);
        background.setLayout(null);

        // Center positioning
        int centerX = Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 100;
        int y = 300;

        l1 = new JLabel("MAXIMUM WITHDRAWAL IS RS.10,000");
        l1.setForeground(Color.WHITE);
        l1.setFont(new Font("System", Font.BOLD, 18));
        l1.setBounds(centerX, y-30, 900, 95);
        background.add(l1);

        l2 = new JLabel("PLEASE ENTER YOUR AMOUNT");
        l2.setForeground(Color.WHITE);
        l2.setFont(new Font("System", Font.BOLD, 18));
        l2.setBounds(centerX, y -90, 450, 95);
        background.add(l2);

        t1 = new JTextField();
        t1.setFont(new Font("Raleway", Font.BOLD, 22));
        t1.setBounds(centerX, y + 60, 360, 35);
        background.add(t1);

        b1 = new JButton("WITHDRAW");
        b1.setBounds(centerX +10, y + 130, 150, 35);
        b1.setFont(new Font("System", Font.BOLD, 16));
        b1.addActionListener(this);
        background.add(b1);

        b2 = new JButton("BACK");
        b2.setBounds(centerX + 190, y + 130, 150, 35);
        b2.setFont(new Font("System", Font.BOLD, 16));
        b2.addActionListener(this);
        background.add(b2);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
    try {
        String amount = t1.getText();
        Date date = new Date();

        if (ae.getSource() == b1) {
            if (amount.equals("")) {
                JOptionPane.showMessageDialog(null, "Please enter the Amount you want to Withdraw");
            } else {
                int withdrawAmount = Integer.parseInt(amount);

                // Check if amount is in valid multiples
                if (withdrawAmount % 100 != 0) {
                    JOptionPane.showMessageDialog(null, "Please enter an amount in multiples of 100");
                    return;
                }

                // Check if amount exceeds max limit
                if (withdrawAmount > 10000) {
                    JOptionPane.showMessageDialog(null, "Maximum withdrawal limit is Rs. 10,000");
                    return;
                }

                Conn c1 = new Conn();
                ResultSet rs = c1.s.executeQuery("select * from bank where pin = '" + pin + "'");
                double balance = 0;
                while (rs.next()) {
                    if (rs.getString("mode").equals("Deposit")) {
                        balance += Double.parseDouble(rs.getString("amount"));
                    } else {
                        balance -= Double.parseDouble(rs.getString("amount"));
                    }
                }

                // Check if balance is sufficient
                if (balance < withdrawAmount) {
                    JOptionPane.showMessageDialog(null, "Insufficient Balance");
                    return;
                }

                // Insert withdrawal transaction
                c1.s.executeUpdate("INSERT INTO bank(pin, mode, date, amount) VALUES('" + pin + "', 'Withdrawal', '" + date + "', '" + amount + "')");

                JOptionPane.showMessageDialog(null, "Rs. " + amount + " Debited Successfully");

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


    public static void main(String[] args) {
        new Withdrawl("").setVisible(true);
    }
}
