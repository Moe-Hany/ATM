import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class Withdrawal extends javax.swing.JFrame {
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

    public Withdrawal(String username) {
        initComponents();
        this.username = username;
    }

    private void initComponents() {

        WelcomeL = new javax.swing.JLabel();
        WithdrawL = new javax.swing.JLabel();
        WithdrawF = new javax.swing.JTextField();
        SubmitB = new javax.swing.JButton();
        CancelB = new javax.swing.JButton();
        CurrencyL = new javax.swing.JLabel();
        Background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        WelcomeL.setFont(new java.awt.Font("Copperplate", 1, 24)); // NOI18N
        WelcomeL.setText("Withdrawal");
        getContentPane().add(WelcomeL);
        WelcomeL.setBounds(220, 70, 400, 100);

        WithdrawL.setFont(new java.awt.Font("Copperplate", 0, 16)); // NOI18N
        WithdrawL.setText("Enter the amout you want to withdraw");
        getContentPane().add(WithdrawL);
        WithdrawL.setBounds(220, 210, 360, 40);

        WithdrawF.setFont(new java.awt.Font("Copperplate", 0, 16)); // NOI18N
        getContentPane().add(WithdrawF);
        WithdrawF.setBounds(220, 270, 200, 30);

        SubmitB.setBackground(new java.awt.Color(204, 204, 204));
        SubmitB.setFont(new java.awt.Font("Copperplate", 0, 14)); // NOI18N
        SubmitB.setText("Submit");
        SubmitB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
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
        String withdrawAmountStr = WithdrawF.getText();
        if (withdrawAmountStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a withdrawal amount.");
            return;
        }

        // Check if the input contains only numeric characters
        if (!withdrawAmountStr.matches("\\d+(\\.\\d+)?")) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter a valid number.");
            return;
        }

        double withdrawAmount = Double.parseDouble(withdrawAmountStr);

        if (withdrawAmount > 5000) {
            JOptionPane.showMessageDialog(this, "Maximum withdrawal limit exceeded. Please enter a lower amount.");
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

                // Check if the balance is sufficient for withdrawal
                if (currentBalance >= withdrawAmount) {
                    double updatedBalance = currentBalance - withdrawAmount;

                    // Update the balance in the table
                    String updateBalanceQuery = "UPDATE registration SET balance = ? WHERE username = ?";
                    PreparedStatement updateBalanceStmt = conn.prepareStatement(updateBalanceQuery);
                    updateBalanceStmt.setDouble(1, updatedBalance);
                    updateBalanceStmt.setString(2, username);
                    updateBalanceStmt.executeUpdate();

                    // Display a success message
                    JOptionPane.showMessageDialog(this,
                            "Withdrawal successful! Your new balance is: " + updatedBalance);

                    // Close the statement
                    updateBalanceStmt.close();
                    this.dispose();
                    Menu m = new Menu(username);
                    m.setVisible(true);
                    m.setSize(794, 731);
                    m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    m.setResizable(false);
                } else {
                    // Display an error message if the balance is insufficient
                    JOptionPane.showMessageDialog(this, "Insufficient balance. Please enter a lower amount.");
                }
            }

            getBalanceStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Withdrawal failed. Please try again.");
        }

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
    private javax.swing.JButton SubmitB;
    private javax.swing.JButton CancelB;
    private javax.swing.JLabel WelcomeL;
    private javax.swing.JTextField WithdrawF;
    private javax.swing.JLabel WithdrawL;
}