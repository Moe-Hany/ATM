import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.*;
public class ForgotPass extends JFrame {
    String idNumber;
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
    public ForgotPass(String idNumber) {
        initComponents();
        this.idNumber = idNumber;
    }
                       
    private void initComponents() {

        ChosseUserandPassL = new javax.swing.JLabel();
        PassL = new javax.swing.JLabel();
        RePassL = new javax.swing.JLabel();
        PassF = new javax.swing.JPasswordField();
        RePassF = new javax.swing.JPasswordField();
        SubmitB = new javax.swing.JButton();
        Background = new javax.swing.JLabel();

        setLayout(null);

        ChosseUserandPassL.setFont(new java.awt.Font("Copperplate", 0, 20)); // NOI18N
        ChosseUserandPassL.setText("Choose Your New PIN");
        add(ChosseUserandPassL);
        ChosseUserandPassL.setBounds(230, 110, 340, 40);

        PassL.setFont(new java.awt.Font("Copperplate", 0, 14)); // NOI18N
        PassL.setText("Enter your New PIN");
        PassL.setMaximumSize(new java.awt.Dimension(116, 15));
        PassL.setMinimumSize(new java.awt.Dimension(116, 15));
        PassL.setPreferredSize(new java.awt.Dimension(116, 15));
        add(PassL);
        PassL.setBounds(310, 180, 200, 15);

        RePassL.setFont(new java.awt.Font("Copperplate", 0, 14)); // NOI18N
        RePassL.setText("RE-Enter your New PIN");
        add(RePassL);
        RePassL.setBounds(310, 240, 220, 20);

        PassF.setFont(new java.awt.Font("Copperplate", 0, 14)); // NOI18N
        PassF.setPreferredSize(new java.awt.Dimension(130, 21));
        add(PassF);
        PassF.setBounds(310, 210, 160, 21);

        RePassF.setFont(new java.awt.Font("Copperplate", 0, 14)); // NOI18N
        add(RePassF);
        RePassF.setBounds(310, 270, 160, 21);

        SubmitB.setBackground(new java.awt.Color(204, 204, 204));
        SubmitB.setFont(new java.awt.Font("Copperplate", 0, 14)); // NOI18N
        SubmitB.setText("Submit");
        add(SubmitB);
        SubmitB.setBounds(340, 310, 100, 30);
        SubmitB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                SubmitBActionPerformed(evt);
            }
        });

        Background.setIcon(new javax.swing.ImageIcon(getClass().getResource("ATM.jpg"))); // NOI18N
        add(Background);
        Background.setBounds(0, 0, 794, 731);
    }                      

    private void SubmitBActionPerformed(ActionEvent evt) {
        String password = new String(PassF.getPassword());
        String reenteredPassword = new String(RePassF.getPassword());
        String pin = PassF.getText();
        if (!pin.matches("\\d{4}")) {
            JOptionPane.showMessageDialog(null, "PIN must be a 4-digit number.");
            return;
        }
        if (password.equals(reenteredPassword)) {
            // Update the password in the database
            boolean passwordUpdated = updatePassword(password);
    
            if (passwordUpdated) {
                // Navigate to the login frame
                Login login = new Login();
                login.setVisible(true);
                login.setSize(794, 731);
                login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                login.setResizable(false);
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Failed to update PIN. Please try again.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "PIN do not match. Please re-enter them.");
        }
    }
    
    private boolean updatePassword(String password) {
        boolean updated = false;
    
        try (Connection conn = getConnection()) {
            String query = "UPDATE registration SET password = ? WHERE id_number = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, password);
            stmt.setString(2, idNumber);
            int rowsAffected = stmt.executeUpdate();
    
            if (rowsAffected > 0) {
                updated = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return updated;
    }
    
                 
    private javax.swing.JLabel Background;
    private javax.swing.JLabel ChosseUserandPassL;
    private javax.swing.JPasswordField PassF;
    private javax.swing.JLabel PassL;
    private javax.swing.JPasswordField RePassF;
    private javax.swing.JLabel RePassL;
    private javax.swing.JButton SubmitB;
}