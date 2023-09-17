import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
public class RegistrationPart2 extends JFrame {

    String Id;

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

public RegistrationPart2(String Id) {
    initComponents();
    this.Id = Id;
}

private void initComponents() {

    ChooseUserandPassL = new javax.swing.JLabel();
    UserL = new javax.swing.JLabel();
    UserF = new javax.swing.JTextField();
    PassL = new javax.swing.JLabel();
    RePassL = new javax.swing.JLabel();
    PassF = new javax.swing.JPasswordField(4);
    RePassF = new javax.swing.JPasswordField(4);
    SubmitB = new javax.swing.JButton();
    Background = new javax.swing.JLabel();

    setLayout(null);

    ChooseUserandPassL.setFont(new java.awt.Font("Copperplate", 0, 20)); // NOI18N
    ChooseUserandPassL.setText("Choose Your user and PIN");
    add(ChooseUserandPassL);
    ChooseUserandPassL.setBounds(230, 110, 340, 40);

    UserL.setFont(new java.awt.Font("Copperplate", 0, 14)); // NOI18N
    UserL.setText("Enter Username");
    add(UserL);
    UserL.setBounds(330, 190, 140, 17);

    UserF.setFont(new java.awt.Font("Copperplate", 0, 14)); // NOI18N
    add(UserF);
    UserF.setBounds(330, 210, 130, 21);

    PassL.setFont(new java.awt.Font("Copperplate", 0, 14)); // NOI18N
    PassL.setText("Enter your PIN");
    PassL.setMaximumSize(new java.awt.Dimension(116, 15));
    PassL.setMinimumSize(new java.awt.Dimension(116, 15));
    PassL.setPreferredSize(new java.awt.Dimension(116, 15));
    add(PassL);
    PassL.setBounds(330, 250, 160, 15);

    RePassL.setFont(new java.awt.Font("Copperplate", 0, 14)); // NOI18N
    RePassL.setText("RE-Enter your PIN");
    add(RePassL);
    RePassL.setBounds(330, 300, 200, 15);

    PassF.setFont(new java.awt.Font("Copperplate", 0, 14)); // NOI18N
    PassF.setPreferredSize(new java.awt.Dimension(130, 21));
    PassF.setSize(new java.awt.Dimension(130, 21));
    add(PassF);
    PassF.setBounds(330, 270, 130, 21);

    RePassF.setFont(new java.awt.Font("Copperplate", 0, 14)); // NOI18N
    add(RePassF);
    RePassF.setBounds(330, 320, 130, 21);

    SubmitB.setBackground(new java.awt.Color(204, 204, 204));
    SubmitB.setFont(new java.awt.Font("Copperplate", 0, 14)); // NOI18N
    SubmitB.setText("Submit");
    add(SubmitB);
    SubmitB.setBounds(340, 360, 100, 30);
    SubmitB.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            SubmitBActionPerformed(evt);
        }
    });

    Background.setIcon(new javax.swing.ImageIcon(getClass().getResource("ATM.jpg"))); // NOI18N
    add(Background);
    Background.setBounds(0, 0, 794, 731);
}

public boolean isUsernameExist(String username) {
    boolean isExist = false;
    try (Connection conn = getConnection()) {
        String query = "SELECT * FROM registration WHERE username = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            isExist = true;
        }
        rs.close();
        stmt.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return isExist;
}

public void insertUserData(String username, String password) {
    try (Connection conn = getConnection()) {
        String query = "UPDATE registration SET username = ?, password = ? WHERE Id = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, username);
        stmt.setString(2, password);
        stmt.setString(3, Id);

        stmt.executeUpdate();
        stmt.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

public void SubmitBActionPerformed(ActionEvent evt) {
    String username = UserF.getText();
    String password = new String(PassF.getPassword());
    String reEnteredPassword = new String(RePassF.getPassword());
    String pin = PassF.getText();

    // Validate PIN
    if (!pin.matches("\\d{4}")) {
        JOptionPane.showMessageDialog(this, "PIN must be a 4-digit number.");
        return;
    }

    if (isUsernameExist(username)) {
        JOptionPane.showMessageDialog(this, "Username already exists. Please choose a different username.");
        return;
    }

    if (!password.equals(reEnteredPassword)) {
        JOptionPane.showMessageDialog(this, "Passwords do not match. Please re-enter your PIN.");
        return;
    }

    insertUserData(username, password);
    Login L = new Login();
    L.setVisible(true);
    L.setSize(794, 731);
    L.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    L.setResizable(false);
    this.dispose();
}

private javax.swing.JLabel Background;
private javax.swing.JLabel ChooseUserandPassL;
private javax.swing.JPasswordField PassF;
private javax.swing.JLabel PassL;
private javax.swing.JPasswordField RePassF;
private javax.swing.JLabel RePassL;
private javax.swing.JButton SubmitB;
private javax.swing.JTextField UserF;
private javax.swing.JLabel UserL;
}