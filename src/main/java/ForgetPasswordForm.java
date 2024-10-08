import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class ForgetPasswordForm extends JFrame {
    private LoginSystem loginSystem;
    private String userID;
    private String email;
    private JTextField verificationCodeField;
    private JPasswordField newPasswordField;
    private JPasswordField confirmPasswordField;
    private JButton submitButton;
    private JLabel messageLabel;
    private String verificationCode;
    private boolean codeVerified = false;
    private JPanel panel;

    public ForgetPasswordForm(LoginSystem loginSystem, String userID, String email) {
        this.loginSystem = loginSystem;
        this.userID = userID;
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
        System.out.println("Verification code for " + email + ": " + verificationCode);
        JOptionPane.showMessageDialog(this, "Verification code sent to " + email);
        JOptionPane.showMessageDialog(this, "The vertification code is" +    verificationCode);
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

        if (!newPassword.isEmpty() && !confirmPassword.isEmpty()){
            if (newPassword.equals(confirmPassword)) {
                try {
                    loginSystem.updateUserPassword(userID, newPassword);
                    JOptionPane.showMessageDialog(this, "Password reset successfully");
                    this.dispose();
                    loginSystem.showLoginSystem();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Error resetting password: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Passwords do not match", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        else {
            JOptionPane.showMessageDialog(this, "Please make sure password field is not blank", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
    }

    private String generateVerificationCode() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(1000000));
    }
}