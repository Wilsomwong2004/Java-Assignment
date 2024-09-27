import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.*;

public class ForgetPasswordForm extends JFrame {
    private LoginSystem loginSystem;
    private String username;
    private String email;
    private JTextField verificationCodeField;
    private JPasswordField newPasswordField;
    private JPasswordField confirmPasswordField;
    private JButton submitButton;
    private JLabel messageLabel;
    private String verificationCode;
    private boolean codeVerified = false;
    private JPanel panel;

    public ForgetPasswordForm(LoginSystem loginSystem, String username, String email) {
        this.loginSystem = loginSystem;
        this.username = username;
        this.email = email;
        initComponents();
        sendVerificationCode();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Reset Password");
        setSize(300, 200);
        setLocationRelativeTo(null);

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        verificationCodeField = new JTextField(20);
        newPasswordField = new JPasswordField(20);
        confirmPasswordField = new JPasswordField(20);
        submitButton = new JButton("Submit");
        messageLabel = new JLabel("Enter verification code sent to your email");

        panel.add(messageLabel);
        panel.add(verificationCodeField);
        panel.add(submitButton);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!codeVerified) {
                    verifyCode();
                } else {
                    resetPassword();
                }
            }
        });

        add(panel);
    }

    private void sendVerificationCode() {
        verificationCode = generateVerificationCode();
        // In a real application, you would send this code via email
        System.out.println("Verification code for " + email + ": " + verificationCode);
        JOptionPane.showMessageDialog(this, "Verification code sent to " + email);
    }

    private void verifyCode() {
        if (verificationCodeField.getText().equals(verificationCode)) {
            codeVerified = true;
            messageLabel.setText("Enter new password");
            panel.removeAll();
            panel.add(messageLabel);
            panel.add(newPasswordField);
            panel.add(new JLabel("Confirm new password"));
            panel.add(confirmPasswordField);
            panel.add(submitButton);
            panel.revalidate();
            panel.repaint();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid verification code", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetPassword() {
        String newPassword = new String(newPasswordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        if (newPassword.equals(confirmPassword)) {
            loginSystem.updateUserPassword(username, newPassword);
            JOptionPane.showMessageDialog(this, "Password reset successfully");
            this.dispose();
            loginSystem.showLoginSystem();
        } else {
            JOptionPane.showMessageDialog(this, "Passwords do not match", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String generateVerificationCode() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(1000000));
    }
}