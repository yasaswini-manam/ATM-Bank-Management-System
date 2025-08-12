package atm.management.system;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import javax.swing.*;
import java.util.*;
import java.text.DecimalFormat;

class BalanceEnquiry extends JFrame implements ActionListener {
    JTextField t1, t2;
    JButton b1, b2, b3;
    JLabel l1, l2, l3;
    String pin;
    
    BalanceEnquiry(String pin) {
        this.pin = pin;
        
        // Set frame properties for full screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize);
        setLocation(0, 0);
        setUndecorated(true);
        setLayout(null);
        
        // Load and scale the image to fit the full screen
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/atm2.jpg"));
        Image i2 = i1.getImage().getScaledInstance(screenSize.width, screenSize.height, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel backgroundLabel = new JLabel(i3);
        backgroundLabel.setBounds(0, 0, screenSize.width, screenSize.height);
        add(backgroundLabel);
        
        // Calculate center positions for better component placement
        int centerX = screenSize.width / 2;
        int centerY = screenSize.height / 2;
        
        // Balance label with improved styling
        l1 = new JLabel();
        l1.setForeground(Color.WHITE);
        l1.setFont(new Font("System", Font.BOLD, 22));
        
        // BACK button with better styling
        b1 = new JButton("BACK");
        b1.setFont(new Font("System", Font.BOLD, 16));
        b1.setBackground(new Color(65, 125, 225));
        b1.setForeground(Color.WHITE);
        b1.setFocusPainted(false);
        b1.setBorder(BorderFactory.createRaisedBevelBorder());
        
        // Position components with proper centering
        l1.setBounds(centerX - 90, centerY - 100, 500, 35);
        backgroundLabel.add(l1);
        
        b1.setBounds(centerX + 5, centerY + 10, 200, 40);
        backgroundLabel.add(b1);
        
        // Calculate and display the balance
        double balance = 0;
        try {
            Conn c1 = new Conn();
            ResultSet rs = c1.s.executeQuery("SELECT * FROM bank where pin = '" + pin + "' ORDER BY date");
            while (rs.next()) {
                String mode = rs.getString("mode");
                double amount = Double.parseDouble(rs.getString("amount"));
                
                if (mode.equals("Deposit") || mode.equals("Received")) {
                    balance += amount;
                } else if (mode.equals("Withdrawal") || mode.equals("Transfer")) {
                    balance -= amount;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Format the balance with two decimal places
        DecimalFormat df = new DecimalFormat("0.00");
        String formattedBalance = df.format(balance);
        
        // Set balance text with currency formatting
        l1.setText("Your Current Account Balance is Rs " + formattedBalance);
        
        // Add action listener and make frame visible
        b1.addActionListener(this);
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae) {
        setVisible(false);
        new Transactions(pin).setVisible(true);
    }
    
    public static void main(String[] args) {
        new BalanceEnquiry("").setVisible(true);
    }
}