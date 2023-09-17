import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.Date;
import java.util.*;

public class Registration extends JFrame {

    private DateCo birthdateComponent; // Birthdate selection component
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
    Random ran = new Random();
    long first4 = (ran.nextLong() % 9000L) + 1000L;
    String first = "" + Math.abs(first4);


    public Registration() {
        initComponents();
    }
                       
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        WelcomeL = new javax.swing.JLabel();
        LoginL = new javax.swing.JLabel();
        FnameL = new javax.swing.JLabel();
        LnameL = new javax.swing.JLabel();
        FnameF = new javax.swing.JTextField();
        LnameF = new javax.swing.JTextField();
        GenderL = new javax.swing.JLabel();
        MaleRB = new javax.swing.JRadioButton();
        FemaleRB = new javax.swing.JRadioButton();
        IdL = new javax.swing.JLabel();
        IdF = new javax.swing.JTextField();
        BirthdateL = new javax.swing.JLabel();
        NextB = new javax.swing.JButton();
        BackB = new javax.swing.JButton();
        Background = new javax.swing.JLabel();

        getContentPane().setLayout(null);

        WelcomeL.setFont(new java.awt.Font("Copperplate", 1, 24)); // NOI18N
        WelcomeL.setText("Welcome to A&M Bank System");
        getContentPane().add(WelcomeL);
        WelcomeL.setBounds(220, 70, 400, 100);

        LoginL.setFont(new java.awt.Font("Copperplate", 0, 18)); // NOI18N
        LoginL.setText("Registration Form");
        getContentPane().add(LoginL);
        LoginL.setBounds(310, 150, 180, 30);

        FnameL.setFont(new java.awt.Font("Copperplate", 0, 14)); // NOI18N
        FnameL.setText("Enter Your First Name ");
        getContentPane().add(FnameL);
        FnameL.setBounds(180, 210, 180, 30);

        LnameL.setFont(new java.awt.Font("Copperplate", 0, 14)); // NOI18N
        LnameL.setText("Enter Your Last Name ");
        getContentPane().add(LnameL);
        LnameL.setBounds(180, 270, 180, 20);

        FnameF.setFont(new java.awt.Font("Copperplate", 0, 14)); // NOI18N
        getContentPane().add(FnameF);
        FnameF.setBounds(180, 240, 160, 21);

        LnameF.setFont(new java.awt.Font("Copperplate", 0, 14)); // NOI18N
        getContentPane().add(LnameF);
        LnameF.setBounds(180, 290, 160, 20);

        GenderL.setFont(new java.awt.Font("Copperplate", 0, 14)); // NOI18N
        GenderL.setText("Chosse Your Gender");
        getContentPane().add(GenderL);
        GenderL.setBounds(180, 320, 180, 20);

        buttonGroup1.add(MaleRB);
        MaleRB.setFont(new java.awt.Font("Copperplate", 0, 14)); // NOI18N
        MaleRB.setText("Male");
        getContentPane().add(MaleRB);
        MaleRB.setBounds(180, 340, 90, 21);

        buttonGroup1.add(FemaleRB);
        FemaleRB.setFont(new java.awt.Font("Copperplate", 0, 14)); // NOI18N
        FemaleRB.setText("Female");
        getContentPane().add(FemaleRB);
        FemaleRB.setBounds(260, 340, 90, 19);

        IdL.setFont(new java.awt.Font("Copperplate", 0, 14)); // NOI18N
        IdL.setText("Enter Your ID/Passport NO:");
        getContentPane().add(IdL);
        IdL.setBounds(180, 370, 210, 20);

        IdF.setFont(new java.awt.Font("Copperplate", 0, 14)); // NOI18N
        getContentPane().add(IdF);
        IdF.setBounds(180, 390, 160, 20);

        BirthdateL.setFont(new java.awt.Font("Copperplate", 0, 14)); // NOI18N
        BirthdateL.setText("Enter Your Birthdate");
        BirthdateL.setLocation(new java.awt.Point(420, 210));
        getContentPane().add(BirthdateL);
        BirthdateL.setBounds(420, 210, 180, 30);

        BirthdateL.setFont(new java.awt.Font("Copperplate", 0, 14));
        BirthdateL.setText("Enter Your Birthdate");
        getContentPane().add(BirthdateL);
        BirthdateL.setBounds(420, 210, 180, 30);

        birthdateComponent = new DateCo();
        getContentPane().add(birthdateComponent);
        birthdateComponent.setBounds(420, 240, 160, 90);


        NextB.setBackground(new java.awt.Color(204, 204, 204));
        NextB.setFont(new java.awt.Font("Copperplate", 0, 14)); // NOI18N
        NextB.setText("Next");
        NextB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NextBActionPerformed(evt);
            }
        });

        getContentPane().add(NextB);
        NextB.setBounds(460, 340, 100, 30);

        BackB.setBackground(new java.awt.Color(204, 204, 204));
        BackB.setFont(new java.awt.Font("Copperplate", 0, 14)); // NOI18N
        BackB.setText("Back");
        BackB.setSize(new java.awt.Dimension(100, 30));
        BackB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackBActionPerformed(evt);
            }
        });
        getContentPane().add(BackB);
        BackB.setBounds(460, 370, 100, 30);

        Background.setIcon(new javax.swing.ImageIcon(getClass().getResource("ATM.jpg"))); // NOI18N
        getContentPane().add(Background);
        Background.setBounds(0, 0, 794, 731);

        pack();
    }   
    public void insertRegistrationData() {
        String firstName = FnameF.getText();
        String lastName = LnameF.getText();
        String gender = MaleRB.isSelected() ? "Male" : "Female";
        String idNumber = IdF.getText();
        Date birthdate = birthdateComponent.getSelectedDate();
        
        try (Connection conn = getConnection()) {
            String query = "INSERT INTO registration (Id, first_name, last_name, gender, id_number, birthdate) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, first);
            stmt.setString(2, firstName);
            stmt.setString(3, lastName);
            stmt.setString(4, gender);
            stmt.setString(5, idNumber);
            stmt.setDate(6, new java.sql.Date(birthdate.getTime()));
            
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }                   
   
        public void NextBActionPerformed(ActionEvent evt) {
            insertRegistrationData();

    
            RegistrationPart2 registrationPart2 = new RegistrationPart2(first);
            registrationPart2.setVisible(true);
            registrationPart2.setSize(794, 731);
            registrationPart2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            registrationPart2.setResizable(false);
            this.dispose();
        };

        public void BackBActionPerformed(ActionEvent evt) {
            Main main = new Main();
            main.setVisible(true);
            main.setSize(794, 731);
            main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            main.setResizable(false);
            this.dispose();
        };
                                    



    // Variables declaration - do not modify                     
    private javax.swing.JButton BackB;
    private javax.swing.JLabel Background;
    private javax.swing.JLabel BirthdateL;
    private javax.swing.JRadioButton FemaleRB;
    private javax.swing.JTextField FnameF;
    private javax.swing.JLabel FnameL;
    private javax.swing.JLabel GenderL;
    javax.swing.JTextField IdF;
    private javax.swing.JLabel IdL;
    private javax.swing.JTextField LnameF;
    private javax.swing.JLabel LnameL;
    private javax.swing.JLabel LoginL;
    private javax.swing.JRadioButton MaleRB;
    private javax.swing.JButton NextB;
    private javax.swing.JLabel WelcomeL;
    private javax.swing.ButtonGroup buttonGroup1;
}