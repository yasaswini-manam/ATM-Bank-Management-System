package atm.management.system;

import java.sql.*;

public class Conn {
    Connection c;
    Statement s;

    public Conn() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/atmmanagementsystem", "root", "|manam| |yasu|");
            s = c.createStatement();
            System.out.println("Connected to database successfully.");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        new Conn(); // For testing
    }
}
