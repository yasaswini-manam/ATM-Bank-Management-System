**ATM Bank Management System**

A Java Swing-based ATM Bank Management System with MySQL integration that simulates basic banking operations. This project is designed for learning and demonstration purposes and mimics the functionality of a real ATM.

🖥️ Features
*User Authentication*

Signup: Create a new bank account.

Login: Secure user authentication with account credentials.

*Banking Operations*

Balance Enquiry: Check the current account balance.

Deposit & Withdraw: Manage account funds.

Fast Cash: Quick withdrawals of preset amounts.

Mini Statement: View recent transactions.

PIN Change: Update account PIN securely.

*Database Integration*

Fully integrated with MySQL to store user accounts, transactions, and authentication details.

*User Interface*

Built using Java Swing for a graphical, interactive experience.

💻 Technologies Used

Programming Language: Java

GUI Framework: Java Swing

Database: MySQL

IDE: Eclipse / IntelliJ IDEA / NetBeans (any Java IDE)

Build Tool: Java SDK 1.8+

📂 Project Structure
ATM-Bank-Management-System/
│
├─ src/
│   ├─ Login.java
│   ├─ Signup.java
│   ├─ BalanceEnquiry.java
│   ├─ MiniStatement.java
│   ├─ FastCash.java
│   └─ PinChange.java
│
├─ database/
│   └─ atm.sql           # SQL file for database setup
│
├─ README.md
└─ .gitignore

**⚙️ Setup Instructions**
Clone the Repository
git clone <repository-url>
cd ATM-Bank-Management-System

Set Up the MySQL Database

Create a database named atmdb.

Import atm.sql to set up the necessary tables.

mysql -u root -p atmdb < database/atm.sql

Configure Database Connection

Update database credentials in DBConnection.java:

String url = "jdbc:mysql://localhost:3306/atmdb";
String user = "root";
String password = "your_password";

Run the Project

Open the project in your Java IDE.

Run Login.java to start the ATM interface.

**🚀 Future Enhancements**

Support for multiple account types (Savings, Current).

Add transaction history export as PDF or CSV.

Implement encryption for sensitive user data.

Integration with real banking APIs for simulation.
