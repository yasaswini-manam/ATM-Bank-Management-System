package atm.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Date;
import java.text.SimpleDateFormat;

public class FundTransfer extends JFrame implements ActionListener {
    JTextField senderField, receiverField, amountField;
    JComboBox<String> transferTypeCombo;
    JButton transferButton, backButton;
    String pin;

    FundTransfer(String pin) {
        this.pin = pin;

        setTitle("Fund Transfer");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Full screen
        setUndecorated(true); // Removes window borders

        // Load background image
        ImageIcon bgIcon = new ImageIcon(ClassLoader.getSystemResource("icons/atm6.jpeg"));
        Image bgImage = bgIcon.getImage().getScaledInstance(1920, 1080, Image.SCALE_SMOOTH);
        JLabel background = new JLabel(new ImageIcon(bgImage));
        background.setLayout(new GridBagLayout()); // For center alignment
        setContentPane(background);

        // Replace/add inside your FundTransfer constructor after setContentPane(background)

// Center panel (larger with more padding)
JPanel panel = new JPanel();
panel.setPreferredSize(new Dimension(500, 350));  // Increased size
panel.setBackground(new Color(255, 255, 255, 200)); // Semi-transparent white
panel.setLayout(new GridLayout(6, 2, 20, 20));      // More spacing
panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40)); // More padding

// Fonts
Font labelFont = new Font("Arial", Font.BOLD, 18);
Font fieldFont = new Font("Arial", Font.PLAIN, 16);

// Title
JLabel title = new JLabel("Fund Transfer");
title.setFont(new Font("Arial", Font.BOLD, 32));
title.setHorizontalAlignment(SwingConstants.CENTER);
title.setForeground(Color.BLACK);

// Labels & Fields
JLabel senderLabel = new JLabel("Sender Account:");
senderLabel.setFont(labelFont);
senderField = new JTextField();
senderField.setFont(fieldFont);
senderField.setEditable(false);

JLabel receiverLabel = new JLabel("Receiver Account:");
receiverLabel.setFont(labelFont);
receiverField = new JTextField();
receiverField.setFont(fieldFont);

JLabel amountLabel = new JLabel("Amount:");
amountLabel.setFont(labelFont);
amountField = new JTextField();
amountField.setFont(fieldFont);

JLabel typeLabel = new JLabel("Transfer Type:");
typeLabel.setFont(labelFont);
transferTypeCombo = new JComboBox<>(new String[]{"Internal", "External"});
transferTypeCombo.setFont(fieldFont);

// Buttons
transferButton = new JButton("Transfer");
transferButton.setFont(new Font("Arial", Font.BOLD, 16));
transferButton.setBackground(new Color(0, 128, 255));
transferButton.setForeground(Color.WHITE);
transferButton.addActionListener(this);

backButton = new JButton("Back");
backButton.setFont(new Font("Arial", Font.BOLD, 16));
backButton.setBackground(new Color(128, 128, 128));
backButton.setForeground(Color.WHITE);
backButton.addActionListener(this);

        // Container panel to center everything
        JPanel container = new JPanel(new BorderLayout());
        container.setOpaque(false);
        container.add(title, BorderLayout.NORTH);
        // Add components to the panel
panel.add(senderLabel);
panel.add(senderField);

panel.add(receiverLabel);
panel.add(receiverField);

panel.add(amountLabel);
panel.add(amountField);

panel.add(typeLabel);
panel.add(transferTypeCombo);

panel.add(transferButton);
panel.add(backButton);

        container.add(panel, BorderLayout.CENTER);

        background.add(container, new GridBagConstraints());

        autofillSenderAccount();
        setVisible(true);
    }

    private void autofillSenderAccount() {
        try {
            Conn conn = new Conn();
            PreparedStatement ps = conn.c.prepareStatement("SELECT cardno FROM login WHERE pin = ?");
            ps.setString(1, pin);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                senderField.setText(rs.getString("cardno"));
            } else {
                JOptionPane.showMessageDialog(null, "Invalid PIN!");
                dispose();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Database Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == transferButton) {
            String sender = senderField.getText();
            String receiver = receiverField.getText();
            String amountStr = amountField.getText();
            String type = (String) transferTypeCombo.getSelectedItem();

            if (receiver.isEmpty() || amountStr.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields");
                return;
            }

            double amount;
            try {
                amount = Double.parseDouble(amountStr);
                if (amount <= 0) throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Enter a valid amount");
                return;
            }

            try {
                Conn conn = new Conn();
                conn.c.setAutoCommit(false);

                // Calculate sender's balance
                PreparedStatement ps = conn.c.prepareStatement("SELECT mode, amount FROM bank WHERE pin = ?");
                ps.setString(1, pin);
                ResultSet rs = ps.executeQuery();

                double balance = 0;
                while (rs.next()) {
                    String mode = rs.getString("mode");
                    double amt = Double.parseDouble(rs.getString("amount"));
                    if ("Deposit".equalsIgnoreCase(mode) || "Received".equalsIgnoreCase(mode)) {
                        balance += amt;
                    } else if ("Withdrawal".equalsIgnoreCase(mode) || "Transfer".equalsIgnoreCase(mode)) {
                        balance -= amt;
                    }
                }

                if (balance < amount) {
                    JOptionPane.showMessageDialog(null, "Insufficient balance");
                    conn.c.rollback();
                    return;
                }

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String currentDate = dateFormat.format(new Date());

                // Deduct from sender
                PreparedStatement deduct = conn.c.prepareStatement(
                        "INSERT INTO bank VALUES(?, ?, ?, ?, ?)"
                );
                deduct.setString(1, pin);
                deduct.setString(2, "Transfer");
                deduct.setString(3, currentDate);
                deduct.setString(4, String.valueOf(amount));
                deduct.setString(5, sender);
                deduct.executeUpdate();

                if ("Internal".equalsIgnoreCase(type)) {
                    String receiverPin = null;
                    PreparedStatement findReceiver = conn.c.prepareStatement("SELECT pin FROM login WHERE cardno = ?");
                    findReceiver.setString(1, receiver);
                    ResultSet receiverRs = findReceiver.executeQuery();

                    if (receiverRs.next()) {
                        receiverPin = receiverRs.getString("pin");

                        PreparedStatement add = conn.c.prepareStatement(
                                "INSERT INTO bank VALUES(?, ?, ?, ?, ?)"
                        );
                        add.setString(1, receiverPin);
                        add.setString(2, "Received");
                        add.setString(3, currentDate);
                        add.setString(4, String.valueOf(amount));
                        add.setString(5, receiver);
                        add.executeUpdate();
                    } else {
                        JOptionPane.showMessageDialog(null, "Receiver account not found.");
                        conn.c.rollback();
                        return;
                    }
                } else {
                    PreparedStatement externalInsert = conn.c.prepareStatement(
                            "INSERT INTO external_transfers (from_account, to_account, amount, transfer_type, timestamp) VALUES (?, ?, ?, ?, ?)"
                    );
                    externalInsert.setString(1, sender);
                    externalInsert.setString(2, receiver);
                    externalInsert.setDouble(3, amount);
                    externalInsert.setString(4, type);
                    externalInsert.setString(5, currentDate);
                    externalInsert.executeUpdate();
                }

                PreparedStatement log = conn.c.prepareStatement(
                        "INSERT INTO transactions (from_account, to_account, amount, transfer_type, timestamp) VALUES (?, ?, ?, ?, ?)"
                );
                log.setString(1, sender);
                log.setString(2, receiver);
                log.setDouble(3, amount);
                log.setString(4, type);
                log.setString(5, currentDate);
                log.executeUpdate();

                conn.c.commit();
                JOptionPane.showMessageDialog(null, "Transfer successful");
                setVisible(false);
                new SelectAction(pin).setVisible(true);

            } catch (Exception e) {
                e.printStackTrace();
                try {
                    Conn conn = new Conn();
                    conn.c.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                JOptionPane.showMessageDialog(null, "Transfer failed: " + e.getMessage());
            }
        } else if (ae.getSource() == backButton) {
            setVisible(false);
            new SelectAction(pin).setVisible(true);
        }
    }

    public static void main(String[] args) {
        new FundTransfer("4720").setVisible(true); // Replace with valid PIN
    }
}
