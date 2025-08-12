package atm.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SelectAction extends JFrame implements ActionListener {
    String pin;
    JButton fundTransfer, transactions, exit;

    SelectAction(String pin) {
        this.pin = pin;

        setTitle("Select Action");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Full screen
        setUndecorated(true); // Remove title bar
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Background Image
        ImageIcon bgIcon = new ImageIcon(ClassLoader.getSystemResource("icons/atm3.jpeg"));
        Image bgImage = bgIcon.getImage().getScaledInstance(1920, 1080, Image.SCALE_SMOOTH);
        JLabel background = new JLabel(new ImageIcon(bgImage));
        background.setLayout(new GridBagLayout()); // Center layout
        setContentPane(background);

        // White transparent panel
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(400, 400));
        panel.setBackground(new Color(255, 255, 255, 200)); // Semi-transparent white
        panel.setLayout(new GridLayout(4, 1, 20, 20)); // Title + 3 buttons with gaps
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40)); // Padding

        // Title
        JLabel title = new JLabel("Select an Action");
        title.setFont(new Font("Arial", Font.BOLD, 32));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setForeground(Color.BLACK);

        // Buttons
        fundTransfer = new JButton("Fund Transfer");
        fundTransfer.setFont(new Font("Arial", Font.BOLD, 20));
        fundTransfer.setBackground(new Color(0, 128, 255));
        fundTransfer.setForeground(Color.WHITE);
        fundTransfer.addActionListener(this);

        transactions = new JButton("Transactions");
        transactions.setFont(new Font("Arial", Font.BOLD, 20));
        transactions.setBackground(new Color(0, 128, 255));
        transactions.setForeground(Color.WHITE);
        transactions.addActionListener(this);

        exit = new JButton("Exit");
        exit.setFont(new Font("Arial", Font.BOLD, 20));
        exit.setBackground(Color.GRAY);
        exit.setForeground(Color.WHITE);
        exit.addActionListener(this);

        // Add components to panel
        panel.add(title);
        panel.add(fundTransfer);
        panel.add(transactions);
        panel.add(exit);

        // Add panel to background center
        background.add(panel);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == fundTransfer) {
            setVisible(false);
            new FundTransfer(pin);
        } else if (ae.getSource() == transactions) {
            setVisible(false);
            new Transactions(pin);
        } else if (ae.getSource() == exit) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new SelectAction("1234");
    }
}