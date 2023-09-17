import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.*;

public class Check extends JFrame{

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
    public Check() {
        initComponents();
    }

                        
    private void initComponents() {

        ChosseUserandPassL = new javax.swing.JLabel();
        UserL = new javax.swing.JLabel();
        IdL = new javax.swing.JLabel();
        NextB = new javax.swing.JButton();
        UserF = new javax.swing.JTextField();
        IdF = new javax.swing.JTextField();
        Background = new javax.swing.JLabel();

        setLayout(null);

        ChosseUserandPassL.setFont(new java.awt.Font("Copperplate", 0, 20)); // NOI18N
        ChosseUserandPassL.setText("Forget PIN Form");
        add(ChosseUserandPassL);
        ChosseUserandPassL.setBounds(230, 110, 340, 40);

        UserL.setFont(new java.awt.Font("Copperplate", 0, 14)); // NOI18N
        UserL.setText("Enter your Username");
        UserL.setMaximumSize(new java.awt.Dimension(116, 15));
        UserL.setMinimumSize(new java.awt.Dimension(116, 15));
        UserL.setPreferredSize(new java.awt.Dimension(116, 15));
        add(UserL);
        UserL.setBounds(310, 180, 200, 15);

        IdL.setFont(new java.awt.Font("Copperplate", 0, 14)); // NOI18N
        IdL.setText("Enter your ID");
        add(IdL);
        IdL.setBounds(310, 240, 220, 20);

        NextB.setBackground(new java.awt.Color(204, 204, 204));
        NextB.setFont(new java.awt.Font("Copperplate", 0, 14)); // NOI18N
        NextB.setText("Next");
        add(NextB);
        NextB.setBounds(340, 310, 100, 30);
        NextB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NextBActionPerformed(evt);
            }
        });

        add(UserF);
        UserF.setBounds(310, 210, 160, 21);

        add(IdF);
        IdF.setBounds(310, 270, 160, 21);

        Background.setIcon(new javax.swing.ImageIcon(getClass().getResource("ATM.jpg"))); // NOI18N
        add(Background);
        Background.setBounds(0, 0, 794, 731);
    }
    private void NextBActionPerformed(java.awt.event.ActionEvent evt) {
        String username = UserF.getText();
        String idNumber = IdF.getText();

        // Check if the username and ID number exist in the table
        boolean userExists = checkUserExists(username, idNumber);

        if (userExists) {
            // Pass the ID number to the next frame
            ForgotPass forgotPass = new ForgotPass(idNumber);
            forgotPass.setVisible(true);
            forgotPass.setSize(794, 731);
            forgotPass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            forgotPass.setResizable(false);
            dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Invalid username or ID number");
        }
    }
    private boolean checkUserExists(String username, String idNumber) {
        boolean exists = false;

        try (Connection conn = getConnection()) {
            String query = "SELECT * FROM registration WHERE username = ? AND id_number = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, idNumber);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                exists = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exists;
    }

    private int getUserId(String username, String idNumber) {
        int userId = 0;

        try (Connection conn = getConnection()) {
            String query = "SELECT Id FROM registration WHERE username = ? AND id_number = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, idNumber);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                userId = rs.getInt("Id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userId;
    }

                                  


    // Variables declaration - do not modify                     
    private javax.swing.JLabel Background;
    private javax.swing.JLabel ChosseUserandPassL;
    private javax.swing.JTextField IdF;
    private javax.swing.JLabel IdL;
    private javax.swing.JButton NextB;
    private javax.swing.JTextField UserF;
    private javax.swing.JLabel UserL;
}
