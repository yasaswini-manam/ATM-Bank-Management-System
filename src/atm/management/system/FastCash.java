package atm.management.system;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.Date;

public class FastCash extends JFrame implements ActionListener {
    JLabel l1, l2;
    JButton b1, b2, b3, b4, b5, b6, b7;
    String pin;
    String cardno; // Add cardno field

    FastCash(String pin) { // Modify constructor to accept cardno
        this.pin = pin;
       // this.cardno = cardno;
        
        // Set the frame properties first
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setLocation(0, 0);
        setUndecorated(true);
        setLayout(null);
        
        // Load and scale the image to fit the screen
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/atm2.jpg"));
        Image i2 = i1.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l3 = new JLabel(i3);
        l3.setBounds(0, 0, getWidth(), getHeight());
        add(l3);
        
        // Calculate the center of the frame for better positioning
        int centerX = getWidth() / 2;
        int startY = getHeight() / 2 - 50;
        
        // Create and configure components
        l1 = new JLabel("SELECT WITHDRAWAL AMOUNT");
        l1.setForeground(Color.WHITE);
        l1.setFont(new Font("System", Font.BOLD, 20));
        
        // Create buttons with consistent size
        int buttonWidth = 160;
        int buttonHeight = 40;
        int buttonGap = 20;
        int leftColumn = centerX - buttonWidth - buttonGap/2;
        int rightColumn = centerX + buttonGap/2;
        
        b1 = new JButton("Rs 100");
        b2 = new JButton("Rs 500");
        b3 = new JButton("Rs 1000");
        b4 = new JButton("Rs 2000");
        b5 = new JButton("Rs 5000");
        b6 = new JButton("Rs 10000");
        b7 = new JButton("BACK");
        
        // Style the buttons
        Font buttonFont = new Font("System", Font.BOLD, 16);
        Color buttonColor = new Color(65, 125, 225);
        Color buttonTextColor = Color.WHITE;
        
        JButton[] buttons = {b1, b2, b3, b4, b5, b6, b7};
        for (JButton button : buttons) {
            button.setFont(buttonFont);
            button.setBackground(buttonColor);
            button.setForeground(buttonTextColor);
            button.setFocusPainted(false);
            button.setBorder(BorderFactory.createRaisedBevelBorder());
            button.addActionListener(this);
        }
        
        // Position components
        l1.setBounds(centerX - 175, startY - 60, 350, 35);
        l3.add(l1);
        
        b1.setBounds(leftColumn, startY, buttonWidth, buttonHeight);
        b2.setBounds(rightColumn, startY, buttonWidth, buttonHeight);
        b3.setBounds(leftColumn, startY + buttonHeight + buttonGap, buttonWidth, buttonHeight);
        b4.setBounds(rightColumn, startY + buttonHeight + buttonGap, buttonWidth, buttonHeight);
        b5.setBounds(leftColumn, startY + 2*(buttonHeight + buttonGap), buttonWidth, buttonHeight);
        b6.setBounds(rightColumn, startY + 2*(buttonHeight + buttonGap), buttonWidth, buttonHeight);
        b7.setBounds(centerX - buttonWidth/2, startY + 3*(buttonHeight + buttonGap), buttonWidth, buttonHeight);
        
        l3.add(b1);
        l3.add(b2);
        l3.add(b3);
        l3.add(b4);
        l3.add(b5);
        l3.add(b6);
        l3.add(b7);
        
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            String amount = "";
            if (ae.getSource() != b7) {
                amount = ((JButton)ae.getSource()).getText().substring(3);
            }
            
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("select * from bank where pin = '"+pin+"'");
            int balance = 0;
            while (rs.next()) {
                if (rs.getString("mode").equals("Deposit")) {
                    balance += Integer.parseInt(rs.getString("amount"));
                } else {
                    balance -= Integer.parseInt(rs.getString("amount"));
                }
            }
            
            if (ae.getSource() != b7 && balance < Integer.parseInt(amount)) {
                JOptionPane.showMessageDialog(null, "Insufficient Balance");
                return;
            }
            
            if (ae.getSource() == b7) {
                this.setVisible(false);
                new Transactions(pin).setVisible(true); // Pass cardno to Transactions
            } else {
                Date date = new Date();
                // Include all columns in the correct order: pin, mode, date, amount, cardno
                String query = "insert into bank values('"+pin+"', 'Withdrawal', '"+date+"', '"+amount+"', '"+cardno+"')";
                c.s.executeUpdate(query);
                JOptionPane.showMessageDialog(null, "Rs. "+amount+" Debited Successfully");
                
                setVisible(false);
                new Transactions(pin).setVisible(true); // Pass cardno to Transactions
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        new FastCash("pin").setVisible(true); // Initialize with empty pin and cardno for testing
    }
}