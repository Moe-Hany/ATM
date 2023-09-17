import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class Login extends javax.swing.JFrame {

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

    public Login() {
        initComponents();
    }

    private void initComponents() {

        WelcomeL = new javax.swing.JLabel();
        UserL = new javax.swing.JLabel();
        UserF = new javax.swing.JTextField();
        PassL = new javax.swing.JLabel();
        PassF = new javax.swing.JPasswordField();
        FpassB = new javax.swing.JButton();
        LoginL = new javax.swing.JLabel();
        LoginB = new javax.swing.JButton();
        Background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        WelcomeL.setFont(new java.awt.Font("Copperplate", 1, 24));
        WelcomeL.setText("Welcome to A&M Bank System");
        getContentPane().add(WelcomeL);
        WelcomeL.setBounds(220, 70, 400, 100);

        UserL.setFont(new java.awt.Font("Copperplate", 0, 16));
        UserL.setText("Enter Your Username");
        getContentPane().add(UserL);
        UserL.setBounds(310, 190, 190, 30);

        UserF.setFont(new java.awt.Font("Copperplate", 0, 15));
        getContentPane().add(UserF);
        UserF.setBounds(310, 220, 180, 30);

        PassL.setFont(new java.awt.Font("Copperplate", 0, 16));
        PassL.setText("Enter Your PIN ");
        getContentPane().add(PassL);
        PassL.setBounds(310, 250, 180, 20);
        getContentPane().add(PassF);
        PassF.setBounds(310, 270, 180, 30);

        LoginB.setBackground(new java.awt.Color(204, 204, 204));
        LoginB.setFont(new java.awt.Font("Copperplate", 0, 14));
        LoginB.setText("Login");
        LoginB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LoginBActionPerformed(evt);
            }
        });
        getContentPane().add(LoginB);
        LoginB.setBounds(310, 310, 180, 30);

        LoginL.setFont(new java.awt.Font("Copperplate", 0, 18));
        LoginL.setText("Login");
        getContentPane().add(LoginL);
        LoginL.setBounds(370, 150, 60, 30);

        FpassB.setBackground(new java.awt.Color(204, 204, 204));
        FpassB.setFont(new java.awt.Font("Copperplate", 0, 14));
        FpassB.setText("Forgot PIN");
        FpassB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FpassBActionPerformed(evt);
            }
        });
        getContentPane().add(FpassB);
        FpassB.setBounds(310, 360, 180, 30);

        Background.setIcon(new javax.swing.ImageIcon(getClass().getResource("ATM.jpg")));
        getContentPane().add(Background);
        Background.setBounds(0, 0, 794, 731);

        pack();
    }

    private boolean validateUser(String username, String password) {
        try (Connection conn = getConnection()) {
            String query = "SELECT * FROM registration WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            // Check if the result set has any rows
            if (rs.next()) {
                rs.close();
                stmt.close();
                return true; // User credentials are valid
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false; // User credentials are invalid
    }

    private void LoginBActionPerformed(java.awt.event.ActionEvent evt) {
        String username = UserF.getText();
        String password = new String(PassF.getPassword());

        if (validateUser(username, password)) {
            JOptionPane.showMessageDialog(this, "Login successful!");
            // User credentials are valid
            Menu frame = new Menu(username);
            frame.setVisible(true);
            frame.setSize(794, 731);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            this.dispose();

            // Proceed with your logic here, such as opening a new frame or performing additional operations
        } else {
            // User credentials are invalid
            JOptionPane.showMessageDialog(this, "Invalid username or PIN. Please try again.");
        }
    }

    private void FpassBActionPerformed(java.awt.event.ActionEvent evt) {
            Check frame = new Check();
            frame.setVisible(true);
            frame.setSize(794, 731);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            this.dispose();
    }



    private javax.swing.JLabel Background;
    private javax.swing.JButton FpassB;
    private javax.swing.JButton LoginB;
    private javax.swing.JLabel LoginL;
    private javax.swing.JPasswordField PassF;
    private javax.swing.JLabel PassL;
    private javax.swing.JTextField UserF;
    private javax.swing.JLabel UserL;
    private javax.swing.JLabel WelcomeL;
}
