package atm.management.system;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.io.File;
import java.io.FileWriter;
import java.text.DecimalFormat;

public class MiniStatement extends JFrame implements ActionListener {
    
    JButton b1, b2;
    JLabel l1, l4;
    StringBuilder statementContent = new StringBuilder();
    
    MiniStatement(String pin) {
        super("Mini Statement");
        getContentPane().setBackground(Color.WHITE);
        setSize(400,600);
        setLocation(20,20);
        
        setLayout(new BorderLayout());
        
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(null);
        contentPanel.setBackground(Color.WHITE);
        
        l1 = new JLabel();
        l1.setBounds(20, 140, 400, 200);
        contentPanel.add(l1);
        
        JLabel l2 = new JLabel("Indian Bank");
        l2.setBounds(150, 20, 100, 20);
        contentPanel.add(l2);
        
        JLabel l3 = new JLabel();
        l3.setBounds(20, 80, 300, 20);
        contentPanel.add(l3);
        
        l4 = new JLabel();
        l4.setBounds(20, 400, 350, 20);
        l4.setFont(new Font("System", Font.BOLD, 14));
        contentPanel.add(l4);
        
        JPanel separatorPanel = new JPanel();
        separatorPanel.setBackground(Color.BLACK);
        separatorPanel.setBounds(20, 390, 350, 1);
        contentPanel.add(separatorPanel);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBounds(20, 500, 350, 30);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 0));
        buttonPanel.setBackground(Color.WHITE);
        contentPanel.add(buttonPanel);
        
        b1 = new JButton("Exit");
        b2 = new JButton("Print");
        buttonPanel.add(b1);
        buttonPanel.add(b2);
        
        b1.addActionListener(this);
        b2.addActionListener(this);
        
        add(contentPanel, BorderLayout.CENTER);
        
        // Load card details from the login table with masked card number
        try 
        {
            Conn c = new Conn();
ResultSet rs = c.s.executeQuery("SELECT * FROM login WHERE pin = '"+pin+"' LIMIT 1");
if(rs.next()) {
    String card = rs.getString("cardno");
   
    l3.setText("Card Number: " + card);
    statementContent.append("Card Number: ").append(card).append("\n\n");
}

        } catch(Exception e) {
            System.out.println("Error loading card details: " + e);
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading card details", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        // Load transactions and calculate balance
        try {
            double balance = 0;
            StringBuilder htmlContent = new StringBuilder("<html>");
            
            Conn c1 = new Conn();
            ResultSet rs = c1.s.executeQuery("SELECT * FROM bank WHERE pin = '"+pin+"' ORDER BY date DESC LIMIT 10"); // Show latest 10 transactions
            
            while(rs.next()) {
                String date = rs.getString("date");
                String mode = rs.getString("mode");
                String amountStr = rs.getString("amount");
                
                htmlContent.append(date)
                          .append("&nbsp;&nbsp;&nbsp;&nbsp;")
                          .append(mode)
                          .append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;")
                          .append(amountStr)
                          .append("<br><br>");
                
                statementContent.append(date).append("    ")
                               .append(mode).append("    ")
                               .append(amountStr).append("\n");
                
                double amount = Double.parseDouble(amountStr);
                if(mode.equals("Deposit") || mode.equals("Received")) {
                    balance += amount;
                } else if(mode.equals("Withdrawal") || mode.equals("Transfer")) {
                    balance -= amount;
                }
            }
            
            htmlContent.append("</html>");
            l1.setText(htmlContent.toString());
            
            DecimalFormat df = new DecimalFormat("0.00");
            String formattedBalance = df.format(balance);
            
            String balText = "Your total balance is Rs " + formattedBalance;
            l4.setText(balText);
            l4.setForeground(new Color(0, 100, 0));
            
            statementContent.append("\n").append("----------------------------------------").append("\n");
            statementContent.append(balText);
            
        } catch(Exception e) {
            System.out.println("Error calculating balance: " + e);
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading transactions", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        revalidate();
        repaint();
    }
    
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == b1) {
            this.setVisible(false);
        } else if(ae.getSource() == b2) {
            try {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setSelectedFile(new File("MiniStatement.txt"));
                int option = fileChooser.showSaveDialog(this);
                if(option == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try(FileWriter fw = new FileWriter(file)) {
                        fw.write(statementContent.toString());
                        JOptionPane.showMessageDialog(this, "Statement saved to " + file.getAbsolutePath());
                    }
                }       
            } catch(Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error saving statement", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public static void main(String[] args) {
        new MiniStatement("5050").setVisible(true);
    }
}