package atm.management.system;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener {
JLabel l1, l2, l3;
JTextField tf1;
JPasswordField pf2;
JButton b1, b2, b3;

public Login() {
    setTitle("AUTOMATIC TELLER MACHINE");

    // Debug: Show if we're reaching constructor
    System.out.println("Login constructor called");

    // Set full screen
    setExtendedState(JFrame.MAXIMIZED_BOTH);
    setUndecorated(true);
    setLayout(null);

    // Debug: Check screen dimensions
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    System.out.println("Screen size: " + screenSize.width + "x" + screenSize.height);

    // Load background image with error handling
    ImageIcon backgroundIcon = null;
    try {
        backgroundIcon = new ImageIcon(ClassLoader.getSystemResource("icons/atm4.jpeg"));
        System.out.println("Background image loaded successfully");
    } catch (Exception e) {
        System.out.println("Error loading background image: " + e.getMessage());
        // Fallback to color if image fails
        getContentPane().setBackground(Color.BLUE);
    }

    if (backgroundIcon != null) {
        Image backgroundImage = backgroundIcon.getImage().getScaledInstance(
            screenSize.width, 
            screenSize.height, 
            Image.SCALE_SMOOTH
        );
        JLabel background = new JLabel(new ImageIcon(backgroundImage));
        background.setBounds(0, 0, screenSize.width, screenSize.height);
        add(background);

        // Debug: Confirm background added
        System.out.println("Background label added to frame");

        // Create content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setBounds(
            (screenSize.width - 800) / 2,
            (screenSize.height - 480) / 2,
            800, 480
        );
        contentPanel.setLayout(null);
        contentPanel.setOpaque(false);
        background.add(contentPanel);

        // Debug: Confirm panel creation
        System.out.println("Content panel created at: " + contentPanel.getBounds());

        // Add components to content panel
        addComponents(contentPanel);
    } else {
        // If no background image, add components directly to frame
        System.out.println("Adding components directly to frame");
        addComponents(this);
    }

    // Add ESC key listener
    getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
        KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Cancel");
    getRootPane().getActionMap().put("Cancel", new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    });

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);

    // Debug: Confirm frame is visible
    System.out.println("Frame set to visible");
}

private void addComponents(Container container) {
    System.out.println("Adding components to container");

   ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/bank2.jpeg"));
Image i2 = i1.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
ImageIcon i3 = new ImageIcon(i2);
JLabel l11 = new JLabel(i3);

// Assuming your frame or panel width is 800 (adjust as needed)
int frameWidth = 800;
int xPosition = (frameWidth - 100) / 2; // Centering horizontally
int yPosition = 10; // A little padding from the top

l11.setBounds(xPosition, yPosition, 100, 100);
container.add(l11);

    int baseY = 130; // Start Y-position for other components, adjusted down

    // Welcome label
    l1 = new JLabel("WELCOME TO ATM");
    l1.setFont(new Font("Osward", Font.BOLD, 38));
    l1.setForeground(Color.WHITE);
    l1.setBounds(200, baseY, 450, 40);
    container.add(l1);

    // Card No label and field
    l2 = new JLabel("Card No:");
    l2.setFont(new Font("Raleway", Font.BOLD, 28));
    l2.setForeground(Color.WHITE);
    l2.setBounds(125, baseY + 80, 375, 30);
    container.add(l2);

    tf1 = new JTextField(15);
    tf1.setBounds(300, baseY + 80, 230, 30);
    tf1.setFont(new Font("Arial", Font.BOLD, 14));
    container.add(tf1);

    // PIN label and field
    l3 = new JLabel("PIN:");
    l3.setFont(new Font("Raleway", Font.BOLD, 28));
    l3.setForeground(Color.WHITE);
    l3.setBounds(125, baseY + 150, 375, 30);
    container.add(l3);

    pf2 = new JPasswordField(15);
    pf2.setFont(new Font("Arial", Font.BOLD, 14));
    pf2.setBounds(300, baseY + 150, 230, 30);
    container.add(pf2);

    // Buttons
    b1 = new JButton("SIGN IN");
    b1.setBackground(new Color(0, 90, 150, 150));
    b1.setForeground(Color.WHITE);
    b1.setFont(new Font("Arial", Font.BOLD, 14));
    b1.setBounds(300, baseY + 230, 100, 30);
    b1.addActionListener(this);
    container.add(b1);

    b2 = new JButton("CLEAR");
    b2.setBackground(new Color(0, 90, 150, 150));
    b2.setForeground(Color.WHITE);
    b2.setFont(new Font("Arial", Font.BOLD, 14));
    b2.setBounds(430, baseY + 230, 100, 30);
    b2.addActionListener(this);
    container.add(b2);

    b3 = new JButton("SIGN UP");
    b3.setBackground(new Color(0, 90, 150, 150));
    b3.setForeground(Color.WHITE);
    b3.setFont(new Font("Arial", Font.BOLD, 14));
    b3.setBounds(300, baseY + 280, 230, 30);
    b3.addActionListener(this);
    container.add(b3);

    System.out.println("All components added to container");
}


public void actionPerformed(ActionEvent ae) {
    try {
        if (ae.getSource() == b1) {
            Conn c1 = new Conn();
            String cardno = tf1.getText();
            String pin = String.valueOf(pf2.getPassword());
            String q = "select * from login where cardno = '" + cardno + "' and pin = '" + pin + "'";

            ResultSet rs = c1.s.executeQuery(q);
            if (rs.next()) {
                setVisible(false);
                new SelectAction(pin).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Incorrect Card Number or PIN");
            }
        } else if (ae.getSource() == b2) {
            tf1.setText("");
            pf2.setText("");
        } else if (ae.getSource() == b3) {
            setVisible(false);
            new Signup1().setVisible(true);
        }
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
    }
}

public static void main(String[] args) {
    // Debug: Start message
    System.out.println("Application starting...");
    SwingUtilities.invokeLater(() -> {
        new Login();
    });
}
}