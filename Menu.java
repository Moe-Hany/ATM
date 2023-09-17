import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
public class Menu extends javax.swing.JFrame {
    String username;
    String firstName;
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
    public Menu(String username) {
        initComponents();
        this.username = username;
        this.firstName = getFirstName(username); // Get the first name
        WelcomeL.setText("Welcome, " + firstName);
    }

                        
    private void initComponents() {

        WelcomeL = new javax.swing.JLabel();
        WithdrawalB = new javax.swing.JButton();
        BalanceB = new javax.swing.JButton();
        DepositB = new javax.swing.JButton();
        EditB = new javax.swing.JButton();
        TransferB = new javax.swing.JButton();
        CancelB = new javax.swing.JButton();
        Background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        WelcomeL.setFont(new java.awt.Font("Copperplate", 1, 24)); // NOI18N
        String firstName = getFirstName(username);
        WelcomeL.setText("Welcome, " + firstName);  
        getContentPane().add(WelcomeL);
        WelcomeL.setBounds(220, 70, 400, 100);

        WithdrawalB.setBackground(new java.awt.Color(204, 204, 204));
        WithdrawalB.setFont(new java.awt.Font("Copperplate", 0, 14)); // NOI18N
        WithdrawalB.setText("Withdrawal");
        WithdrawalB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                WithdrawalBActionPerformed(evt);
            }
        });
        getContentPane().add(WithdrawalB);
        WithdrawalB.setBounds(180, 210, 140, 40);

        BalanceB.setBackground(new java.awt.Color(204, 204, 204));
        BalanceB.setFont(new java.awt.Font("Copperplate", 0, 14)); // NOI18N
        BalanceB.setText("Balance Check");
        BalanceB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BalanceBActionPerformed(evt);
            }
        });
        getContentPane().add(BalanceB);
        BalanceB.setBounds(480, 210, 140, 40);

        DepositB.setBackground(new java.awt.Color(204, 204, 204));
        DepositB.setFont(new java.awt.Font("Copperplate", 0, 14)); // NOI18N
        DepositB.setText("Deposit");
        DepositB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DepositBActionPerformed(evt);
            }
        });
        getContentPane().add(DepositB);
        DepositB.setBounds(180, 280, 140, 40);

        EditB.setBackground(new java.awt.Color(204, 204, 204));
        EditB.setFont(new java.awt.Font("Copperplate", 0, 14)); // NOI18N
        EditB.setText("Edit Info");
        EditB.setPreferredSize(new java.awt.Dimension(118, 23));
        EditB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditBActionPerformed(evt);
            }
        });
        getContentPane().add(EditB);
        EditB.setBounds(480, 280, 140, 40);

        TransferB.setBackground(new java.awt.Color(204, 204, 204));
        TransferB.setFont(new java.awt.Font("Copperplate", 0, 14)); // NOI18N
        TransferB.setText("Transfer");
        TransferB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TransferBActionPerformed(evt);
            }
        });
        getContentPane().add(TransferB);
        TransferB.setBounds(180, 350, 140, 40);

        CancelB.setBackground(new java.awt.Color(204, 204, 204));
        CancelB.setFont(new java.awt.Font("Copperplate", 0, 14)); // NOI18N
        CancelB.setText("Cancel");
        CancelB.setPreferredSize(new java.awt.Dimension(120, 23));
        CancelB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelBActionPerformed(evt);
            }
        });
        getContentPane().add(CancelB);
        CancelB.setBounds(480, 350, 140, 40);

        Background.setIcon(new javax.swing.ImageIcon(getClass().getResource("ATM.jpg"))); // NOI18N
        getContentPane().add(Background);
        Background.setBounds(0, 0, 794, 731);

        pack();
    } 
    private String getFirstName(String username) {
        String firstName = "";

        try (Connection conn = getConnection()) {
            String query = "SELECT first_name FROM registration WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                firstName = rs.getString("first_name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return firstName;
    }
          
    private void WithdrawalBActionPerformed(java.awt.event.ActionEvent evt) {                                            
        Withdrawal w = new Withdrawal(username);
        w.setVisible(true);
        w.setSize(794, 731);
        w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        w.setResizable(false);
        this.dispose();
    }
private void BalanceBActionPerformed(java.awt.event.ActionEvent evt) {

    // Get the username from the user
    String username = this.username;

    // Get the connection to the database
    Connection conn = getConnection();

    // Create a statement
    Statement stmt = null;

    try {

        // Create a SELECT statement to get the balance
        stmt = conn.createStatement();
        String sql = "SELECT balance FROM registration WHERE username = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1, username);
        ResultSet rs = preparedStatement.executeQuery();

        // Check if the balance was found
        if (rs.next()) {

            // Get the balance
            Double balance = rs.getDouble("balance");

            // Display the balance in a message box
            JOptionPane.showMessageDialog(null, "Your balance is " + balance+ " SR");

        } else {

            // No balance was found
            JOptionPane.showMessageDialog(null, "No balance found for user " + username);

        }

    } catch (SQLException e) {

        // Handle the exception
        e.printStackTrace();

    } finally {

        // Close the statement
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Close the connection
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
    private void DepositBActionPerformed(java.awt.event.ActionEvent evt) {                                          
        Deposit d = new Deposit(username);
        d.setVisible(true);
        d.setSize(794, 731);
        d.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        d.setResizable(false);
        this.dispose();
    }
    private void EditBActionPerformed(java.awt.event.ActionEvent evt) {                                       
        Edit e = new Edit(username);
        e.setVisible(true);
        e.setSize(794, 731);
        e.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        e.setResizable(false);
        this.dispose();
    }
    private void TransferBActionPerformed(java.awt.event.ActionEvent evt) {  
        Transfer t = new Transfer(username);
        t.setVisible(true);
        t.setSize(794, 731);
        t.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        t.setResizable(false);
        this.dispose();                                        
    }            

    private void CancelBActionPerformed(java.awt.event.ActionEvent evt) {  
        Main m = new Main();
        m.setVisible(true);
        m.setSize(794, 731);
        m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        m.setResizable(false);
        this.dispose();

    }                                       



    private javax.swing.JLabel Background;
    private javax.swing.JButton BalanceB;
    private javax.swing.JButton CancelB;
    private javax.swing.JButton DepositB;
    private javax.swing.JButton EditB;
    private javax.swing.JButton TransferB;
    private javax.swing.JLabel WelcomeL;
    private javax.swing.JButton WithdrawalB;
}