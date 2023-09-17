import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
public class Deposit extends JFrame {

    String username;
    public Connection getConnection() {
        Connection conn = null;
        String url = "jdbc:mysql://localhost:3306/ATM";
        String username = "root";
        String password = "12345678";
    
        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return conn;
    }
    public Deposit(String username) {
        initComponents();
        this.username = username;
    }
                     
    private void initComponents() {

        WelcomeL = new javax.swing.JLabel();
        DepositL = new javax.swing.JLabel();
        DepositF = new javax.swing.JTextField();
        SubmitB = new javax.swing.JButton();
        CancelB = new javax.swing.JButton();
        CurrencyL = new javax.swing.JLabel();
        Background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        WelcomeL.setFont(new java.awt.Font("Copperplate", 1, 24)); // NOI18N
        WelcomeL.setText("Deposit");
        getContentPane().add(WelcomeL);
        WelcomeL.setBounds(220, 70, 400, 100);

        DepositL.setFont(new java.awt.Font("Copperplate", 0, 16)); // NOI18N
        DepositL.setText("Enter the amout you want to Deposit");
        getContentPane().add(DepositL);
        DepositL.setBounds(220, 210, 360, 40);

        DepositF.setFont(new java.awt.Font("Copperplate", 0, 16)); // NOI18N
        getContentPane().add(DepositF);
        DepositF.setBounds(220, 270, 200, 30);

        SubmitB.setBackground(new java.awt.Color(204, 204, 204));
        SubmitB.setFont(new java.awt.Font("Copperplate", 0, 14)); // NOI18N
        SubmitB.setText("Submit");
        SubmitB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubmitBActionPerformed(evt);
            }
        });
        getContentPane().add(SubmitB);
        SubmitB.setBounds(490, 270, 100, 30);

        CancelB.setBackground(new java.awt.Color(204, 204, 204));
        CancelB.setFont(new java.awt.Font("Copperplate", 0, 14)); // NOI18N
        CancelB.setText("Cancel");
        CancelB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                CancelBActionPerformed(evt);
            }
        });
        getContentPane().add(CancelB);
        CancelB.setBounds(490, 300, 100, 30);
        

        CurrencyL.setFont(new java.awt.Font("Copperplate", 0, 15)); // NOI18N
        CurrencyL.setText("SR");
        getContentPane().add(CurrencyL);
        CurrencyL.setBounds(430, 280, 42, 17);

        Background.setIcon(new javax.swing.ImageIcon(getClass().getResource("ATM.jpg"))); // NOI18N
        getContentPane().add(Background);
        Background.setBounds(0, 0, 794, 731);

        pack();
    }                      


    private void SubmitBActionPerformed(java.awt.event.ActionEvent evt) {
        String depositAmountStr = DepositF.getText();
        
        // Check if the input is empty
        if (depositAmountStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a deposit amount.");
            return;
        }
        
        // Check if the input contains only numeric characters
        if (!depositAmountStr.matches("\\d+(\\.\\d+)?")) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter a valid number.");
            return;
        }
        
        double depositAmount = Double.parseDouble(depositAmountStr);
        
        // Check if the deposit amount exceeds the maximum limit of 25000 SR
        if (depositAmount > 25000) {
            JOptionPane.showMessageDialog(this, "Maximum deposit limit exceeded. Please enter a lower amount.");
            return;
        }
        
    
        try (Connection conn = getConnection()) {
            // Retrieve the current balance of the user
            String getBalanceQuery = "SELECT balance FROM registration WHERE username = ?";
            PreparedStatement getBalanceStmt = conn.prepareStatement(getBalanceQuery);
            getBalanceStmt.setString(1, username);
            ResultSet balanceRs = getBalanceStmt.executeQuery();
    
            if (balanceRs.next()) {
                double currentBalance = balanceRs.getDouble("balance");
                double updatedBalance = currentBalance + depositAmount;
    
                // Update the balance in the table
                String updateBalanceQuery = "UPDATE registration SET balance = ? WHERE username = ?";
                PreparedStatement updateBalanceStmt = conn.prepareStatement(updateBalanceQuery);
                updateBalanceStmt.setDouble(1, updatedBalance);
                updateBalanceStmt.setString(2, username);
                updateBalanceStmt.executeUpdate();
    
                // Display a success message
                JOptionPane.showMessageDialog(this, "Deposit successful! Your new balance is: " + updatedBalance+" SR");
    
                // Close the statement and result set
                updateBalanceStmt.close();
            }
    
            // Close the statement and connection
            getBalanceStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Deposit failed. Please try again.");
        }
    
        // Close the deposit window and open the menu window
        this.dispose();
        Menu m = new Menu(username);
        m.setVisible(true);
        m.setSize(794, 731);
        m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        m.setResizable(false);
    }
     
    private void CancelBActionPerformed(java.awt.event.ActionEvent evt) {                                         
        this.dispose();
        Menu m = new Menu(username);
        m.setVisible(true);
        m.setSize(794, 731);
        m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        m.setResizable(false);
    }



    private javax.swing.JLabel Background;
    private javax.swing.JLabel CurrencyL;
    private javax.swing.JTextField DepositF;
    private javax.swing.JLabel DepositL;
    private javax.swing.JButton SubmitB;
    private javax.swing.JButton CancelB;
    private javax.swing.JLabel WelcomeL;
}