import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
public class Edit extends javax.swing.JFrame {
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

    public Edit(String username) {
        initComponents();
        this.username = username;
    }
                       
    private void initComponents() {

        WelcomeL = new javax.swing.JLabel();
        CancelB = new javax.swing.JButton();
        ChangeUserB = new javax.swing.JButton();
        ChangePassB = new javax.swing.JButton();
        Background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        WelcomeL.setFont(new java.awt.Font("Copperplate", 1, 24)); // NOI18N
        WelcomeL.setText("Edit Info");
        getContentPane().add(WelcomeL);
        WelcomeL.setBounds(220, 70, 400, 100);

        CancelB.setBackground(new java.awt.Color(204, 204, 204));
        CancelB.setFont(new java.awt.Font("Copperplate", 0, 14)); // NOI18N
        CancelB.setText("Cancel");
        CancelB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelBActionPerformed(evt);
            }
        });
        getContentPane().add(CancelB);
        CancelB.setBounds(300, 340, 170, 40);

        ChangeUserB.setBackground(new java.awt.Color(204, 204, 204));
        ChangeUserB.setFont(new java.awt.Font("Copperplate", 0, 14)); // NOI18N
        ChangeUserB.setText("Change Username");
        ChangeUserB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChangeUserBActionPerformed(evt);
            }
        });
        getContentPane().add(ChangeUserB);
        ChangeUserB.setBounds(300, 200, 170, 40);

        ChangePassB.setBackground(new java.awt.Color(204, 204, 204));
        ChangePassB.setFont(new java.awt.Font("Copperplate", 0, 14)); // NOI18N
        ChangePassB.setText("Change PIN");
        ChangePassB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChangePassBActionPerformed(evt);
            }
        });
        getContentPane().add(ChangePassB);
        ChangePassB.setBounds(300, 270, 170, 40);

        Background.setIcon(new javax.swing.ImageIcon(getClass().getResource("ATM.jpg"))); // NOI18N
        getContentPane().add(Background);
        Background.setBounds(0, 0, 794, 731);

        pack();
    }                      

    private void CancelBActionPerformed(java.awt.event.ActionEvent evt) {    
        if (evt.getSource() == CancelB) {
            this.dispose();
            Menu M = new Menu(username);
            M.setVisible(true);
            M.setSize(794, 731);
            M.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            M.setResizable(false);
        }                                    
    }                                       

    private void ChangeUserBActionPerformed(java.awt.event.ActionEvent evt) {

        // Get the old username from the user
        String oldUsername = this.username;
    
        // Get the new username from the user
        String newUsername = JOptionPane.showInputDialog("Enter your new username: ");
    
        // Check if the new username is already taken
        Connection conn = getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
    
        try {
    
            // Create a SELECT statement to check if the new username is already taken
            String sql = "SELECT username FROM registration WHERE username = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, newUsername);
            rs = stmt.executeQuery();
    
            // Check if the new username was found
            if (rs.next()) {
    
                // The new username is already taken
                JOptionPane.showMessageDialog(null, "The new username is already taken. Please choose a different username.");
    
            }  else {
                            // The current password is correct
                            // Update the username and password in the database
                            sql = "UPDATE registration SET username = ? WHERE username = ?";
                            stmt = conn.prepareStatement(sql);
                            stmt.setString(1, newUsername);

                            int rowsAffected = stmt.executeUpdate();
    
                            if (rowsAffected > 0) {
                                // Close the statement and connection
                                stmt.close();
                                conn.close();
    
                                // Update the username in the user interface
                                this.username = newUsername;
                                WelcomeL.setText("Edit Info - " + newUsername);
    
                                // Show a message box to confirm that the username and password have been changed
                                JOptionPane.showMessageDialog(null, "Your username have been changed.");
                            } else {
                                JOptionPane.showMessageDialog(null, "Failed to update username a.");
                            }
                        }
            
            
            }

         catch (SQLException e) {
            e.printStackTrace();
        } finally {
    
            // Close the statement, result set, and connection
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (rs != null) {
                    rs.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
    
        }
    
    }
                                         

    private void ChangePassBActionPerformed(java.awt.event.ActionEvent evt) {  
        if (evt.getSource() == ChangePassB) {
            this.dispose();
            UpdatePass UP = new UpdatePass(username);
            UP.setVisible(true);
            UP.setSize(794, 731);
            UP.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            UP.setResizable(false);
        }

    }                                           


    private javax.swing.JLabel Background;
    private javax.swing.JButton CancelB;
    private javax.swing.JButton ChangePassB;
    private javax.swing.JButton ChangeUserB;
    private javax.swing.JLabel WelcomeL;
}