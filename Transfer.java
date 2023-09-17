import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class Transfer extends javax.swing.JFrame {
    String senderUsername;

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

    public Transfer(String senderUsername) {
        initComponents();
        this.senderUsername = senderUsername;
    }

    private void initComponents() {
        WelcomeL = new javax.swing.JLabel();
        TransferL = new javax.swing.JLabel();
        TransferF = new javax.swing.JTextField();
        ReceiverL = new javax.swing.JLabel();
        ReceiverF = new javax.swing.JTextField();
        SubmitB = new javax.swing.JButton();
        CancelB = new javax.swing.JButton();
        CurrencyL = new javax.swing.JLabel();
        Background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        WelcomeL.setFont(new java.awt.Font("Copperplate", 1, 24)); // NOI18N
        WelcomeL.setText("Transfer");
        getContentPane().add(WelcomeL);
        WelcomeL.setBounds(220, 70, 400, 100);

        TransferL.setFont(new java.awt.Font("Copperplate", 0, 16)); // NOI18N
        TransferL.setText("Enter the amount you want to transfer");
        getContentPane().add(TransferL);
        TransferL.setBounds(220, 210, 360, 40);

        TransferF.setFont(new java.awt.Font("Copperplate", 0, 16)); // NOI18N
        getContentPane().add(TransferF);
        TransferF.setBounds(220, 270, 200, 30);

        ReceiverL.setFont(new java.awt.Font("Copperplate", 0, 16)); // NOI18N
        ReceiverL.setText("Enter the username or ID of the receiver");
        getContentPane().add(ReceiverL);
        ReceiverL.setBounds(220, 310, 400, 40);

        ReceiverF.setFont(new java.awt.Font("Copperplate", 0, 16)); // NOI18N
        getContentPane().add(ReceiverF);
        ReceiverF.setBounds(220, 370, 200, 30);

        SubmitB.setBackground(new java.awt.Color(204, 204, 204));
        SubmitB.setFont(new java.awt.Font("Copperplate", 0, 14)); // NOI18N
        SubmitB.setText("Submit");
        SubmitB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                SubmitBActionPerformed(evt);
            }
        });
        getContentPane().add(SubmitB);
        SubmitB.setBounds(490, 360, 100, 30);

        CancelB.setBackground(new java.awt.Color(204, 204, 204));
        CancelB.setFont(new java.awt.Font("Copperplate", 0, 14)); // NOI18N
        CancelB.setText("Cancel");
        CancelB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                CancelBActionPerformed(evt);
            }
        });
        getContentPane().add(CancelB);
        CancelB.setBounds(490, 390, 100, 30);

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
        String transferAmountStr = TransferF.getText();
        if (transferAmountStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a transfer amount.");
            return;
        }
        
        // Check if the input contains only numeric characters
        if (!transferAmountStr.matches("\\d+(\\.\\d+)?")) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter a valid number.");
            return;
        }
        
        double transferAmount = Double.parseDouble(transferAmountStr);
        String receiverIdentifier = ReceiverF.getText();

        try (Connection conn = getConnection()) {
            // Retrieve the current balance of the sender
            String getSenderBalanceQuery = "SELECT balance FROM registration WHERE username = ?";
            PreparedStatement getSenderBalanceStmt = conn.prepareStatement(getSenderBalanceQuery);
            getSenderBalanceStmt.setString(1, senderUsername);
            ResultSet senderBalanceRs = getSenderBalanceStmt.executeQuery();

            if (senderBalanceRs.next()) {
                double senderBalance = senderBalanceRs.getDouble("balance");

                // Check if the sender's balance is sufficient for transfer
                if (senderBalance >= transferAmount) {
                    // Retrieve the receiver's details
                    String getReceiverQuery = "SELECT * FROM registration WHERE username = ? OR id_number = ?";
                    PreparedStatement getReceiverStmt = conn.prepareStatement(getReceiverQuery);
                    getReceiverStmt.setString(1, receiverIdentifier);
                    getReceiverStmt.setString(2, receiverIdentifier);
                    ResultSet receiverRs = getReceiverStmt.executeQuery();

                    if (receiverRs.next()) {
                        String receiverUsername = receiverRs.getString("username");
                        double receiverBalance = receiverRs.getDouble("balance");

                        // Update the balances for the sender and receiver
                        double updatedSenderBalance = senderBalance - transferAmount;
                        double updatedReceiverBalance = receiverBalance + transferAmount;

                        // Update the sender's balance
                        String updateSenderBalanceQuery = "UPDATE registration SET balance = ? WHERE username = ?";
                        PreparedStatement updateSenderBalanceStmt = conn.prepareStatement(updateSenderBalanceQuery);
                        updateSenderBalanceStmt.setDouble(1, updatedSenderBalance);
                        updateSenderBalanceStmt.setString(2, senderUsername);
                        updateSenderBalanceStmt.executeUpdate();
                        updateSenderBalanceStmt.close();

                        // Update the receiver's balance
                        String updateReceiverBalanceQuery = "UPDATE registration SET balance = ? WHERE username = ?";
                        PreparedStatement updateReceiverBalanceStmt = conn.prepareStatement(updateReceiverBalanceQuery);
                        updateReceiverBalanceStmt.setDouble(1, updatedReceiverBalance);
                        updateReceiverBalanceStmt.setString(2, receiverUsername);
                        updateReceiverBalanceStmt.executeUpdate();
                        updateReceiverBalanceStmt.close();

                        // Display a success message
                        JOptionPane.showMessageDialog(this, "Transfer successful! Your new balance is: " + updatedSenderBalance);

                        this.dispose();
                        Menu m = new Menu(senderUsername);
                        m.setVisible(true);
                        m.setSize(794, 731);
                        m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        m.setResizable(false);
                    } else {
                        // Display an error message if the receiver is not found
                        JOptionPane.showMessageDialog(this, "Receiver not found. Please enter a valid username or ID.");
                    }

                    receiverRs.close();
                    getReceiverStmt.close();
                } else {
                    // Display an error message if the sender's balance is insufficient
                    JOptionPane.showMessageDialog(this, "Insufficient balance. Please enter a lower amount.");
                }
            }

            // Close the result sets and statements
            senderBalanceRs.close();
            getSenderBalanceStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Transfer failed. Please try again.");
        }
    }

    private void CancelBActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
        Menu m = new Menu(senderUsername);
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
    private javax.swing.JLabel TransferL;
    private javax.swing.JTextField TransferF;
    private javax.swing.JLabel ReceiverL;
    private javax.swing.JTextField ReceiverF;
}
