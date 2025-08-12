package atm.management.system;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Transactions extends JFrame implements ActionListener {

    JButton depositBtn, withdrawBtn, fastCashBtn, miniStmtBtn, pinChangeBtn, balanceEnquiryBtn, exitBtn;
    String pin;

    Transactions(String pin) {
        this.pin = pin;

        // Get full screen dimensions
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        // Background image full screen
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/atm1.jpg"));
        Image i2 = i1.getImage().getScaledInstance(screenWidth, screenHeight, Image.SCALE_SMOOTH);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel background = new JLabel(i3);
        background.setBounds(0, 0, screenWidth, screenHeight);
        add(background);
        background.setLayout(null);

        // Label
       JLabel label = new JLabel("Please Select Your Transaction");
label.setForeground(Color.WHITE);
label.setFont(new Font("System", Font.BOLD, 24));
// Adjust the vertical position to move the label down (increased from -200 to -100, for example)
label.setBounds(screenWidth / 2 - 170, screenHeight / 2 - 150, 400, 35); 
background.add(label);


        // Button setup
        Font buttonFont = new Font("System", Font.BOLD, 16);
        int btnWidth = 200;
        int btnHeight = 40;
        int col1X = screenWidth / 2 - 210;
        int col2X = screenWidth / 2 + 20;
        int startY = screenHeight / 2 - 100;
        int gapY = 60;

        depositBtn = createButton("DEPOSIT", col1X, startY, btnWidth, btnHeight, buttonFont, background);
        withdrawBtn = createButton("CASH WITHDRAWAL", col2X, startY, btnWidth, btnHeight, buttonFont, background);

        fastCashBtn = createButton("FAST CASH", col1X, startY + gapY, btnWidth, btnHeight, buttonFont, background);
        miniStmtBtn = createButton("MINI STATEMENT", col2X, startY + gapY, btnWidth, btnHeight, buttonFont, background);

        pinChangeBtn = createButton("PIN CHANGE", col1X, startY + gapY * 2, btnWidth, btnHeight, buttonFont, background);
        balanceEnquiryBtn = createButton("BALANCE ENQUIRY", col2X, startY + gapY * 2, btnWidth, btnHeight, buttonFont, background);

        exitBtn = createButton("EXIT", col2X, startY + gapY * 3, btnWidth, btnHeight, buttonFont, background);

        // Frame settings
        setLayout(null);
        setSize(screenWidth, screenHeight);
        setLocation(0, 0);
        setUndecorated(true);
        setVisible(true);
    }

    private JButton createButton(String text, int x, int y, int w, int h, Font font, Container container) {
        JButton btn = new JButton(text);
        btn.setFont(font);
        btn.setBounds(x, y, w, h);
        btn.addActionListener(this);
        container.add(btn);
        return btn;
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == depositBtn) {
            setVisible(false);
            new Deposit(pin).setVisible(true);
        } else if (ae.getSource() == withdrawBtn) {
            setVisible(false);
            new Withdrawl(pin).setVisible(true);
        } else if (ae.getSource() == fastCashBtn) {
            setVisible(false);
            new FastCash(pin).setVisible(true);
        } else if (ae.getSource() == miniStmtBtn) {
            new MiniStatement(pin).setVisible(true);
        } else if (ae.getSource() == pinChangeBtn) {
            setVisible(false);
            new Pin(pin).setVisible(true);
        } else if (ae.getSource() == balanceEnquiryBtn) {
            setVisible(false);
            new BalanceEnquiry(pin).setVisible(true);
        } else if (ae.getSource() == exitBtn) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new Transactions("").setVisible(true);
    }
}
