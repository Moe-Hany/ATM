import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class Main extends javax.swing.JFrame {

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
    public Main() {
        initComponents();
    }
                        
    private void initComponents() {

        WelcomeL = new JLabel();
        RegisterB = new javax.swing.JButton();
        Login = new javax.swing.JButton();
        Background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        WelcomeL.setFont(new java.awt.Font("Copperplate", 1, 24)); // NOI18N
        WelcomeL.setText("Welome to A&M Bank System");
        getContentPane().add(WelcomeL);
        WelcomeL.setBounds(220, 70, 400, 100);

        RegisterB.setBackground(new java.awt.Color(204, 204, 204));
        RegisterB.setFont(new java.awt.Font("Copperplate", 0, 14)); // NOI18N
        RegisterB.setText("Register");
        RegisterB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RegisterBActionPerformed(evt);
            }
        });
        getContentPane().add(RegisterB);
        RegisterB.setBounds(310, 320, 180, 30);

        Login.setBackground(new java.awt.Color(204, 204, 204));
        Login.setFont(new java.awt.Font("Copperplate", 0, 14)); // NOI18N
        Login.setText("Login");
        Login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LoginActionPerformed(evt);
            }
        });
        getContentPane().add(Login);
        Login.setBounds(310, 260, 180, 30);

        Background.setIcon(new javax.swing.ImageIcon(getClass().getResource("ATM.jpg"))); // NOI18N
        getContentPane().add(Background);
        Background.setBounds(0, 0, 794, 731);

        pack();
    }
    // </editor-fold>                        

    private void RegisterBActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
        if (evt.getSource() == RegisterB) {
            Registration frame = new Registration();
            frame.setVisible(true);
            frame.setSize(794, 731);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            this.dispose();
        }
    }                                         

    private void LoginActionPerformed(java.awt.event.ActionEvent evt) {                                      
        // TODO add your handling code here:
        if (evt.getSource() == Login) {
            Login frame = new Login();
            frame.setVisible(true);
            frame.setSize(794, 731);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            this.dispose();
        }
    }                                     

    public static void main(String args[]) {
        Main frame = new Main();
        frame.setVisible(true);
        frame.setSize(794, 731);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
 
    }

    private javax.swing.JLabel Background;
    private javax.swing.JButton Login;
    private javax.swing.JButton RegisterB;
    private javax.swing.JLabel WelcomeL;
}
