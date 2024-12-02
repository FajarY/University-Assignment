import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private static String Username = "Fajar";
    private static String Password = "its";
    private static String msg;

    public static void main(String[] args) {
        Main gui = new Main();
        gui.go();
    }

    public void go() {
        JFrame frame = new JFrame("Login Form");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10));

        JLabel lblUsername = new JLabel("Username:");
        JLabel lblPassword = new JLabel("Password:");

        txtUsername = new JTextField(20);
        txtPassword = new JPasswordField(20);

        JButton btnLogin = new JButton("Login");
        btnLogin.addActionListener(new LoginListener());

        JButton btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(new CancelListener());

        panel.add(lblUsername);
        panel.add(txtUsername);
        panel.add(lblPassword);
        panel.add(txtPassword);
        panel.add(btnLogin);
        panel.add(btnCancel);

        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.setSize(600, 200);
        frame.setVisible(true);
    }

    public class LoginListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (Username.equals(txtUsername.getText())) {
                if (Password.equals(new String(txtPassword.getPassword()))) {
                    msg = "Login Granted!";
                } else {
                    msg = "Login Denied";
                }
            } else {
                msg = "Login Denied";
            }
            JOptionPane.showMessageDialog(null, msg);
        }
    }
    public class CancelListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            txtUsername.setText("");
            txtPassword.setText("");
            txtUsername.requestFocus();
        }
    }
}