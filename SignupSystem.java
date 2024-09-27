import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Pattern;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class SignupSystem extends JFrame {
    private JTextField userIdField, usernameField, emailField;
    private JPasswordField passwordField;
    private JRadioButton maleRadio, femaleRadio, adminRadio, staffRadio;
    private JCheckBox termsCheckbox;
    private JButton signupButton, backButton;

    public SignupSystem() {
        setTitle("PPE Inventory System");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Title
        JLabel titleLabel = new JLabel("PPE Inventory System");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        // Welcome message
        JLabel welcomeLabel = new JLabel("Welcome to create a account for our system!");
        gbc.gridy = 1;
        add(welcomeLabel, gbc);

        // UserID
        gbc.gridwidth = 1;
        gbc.gridy = 2;
        add(new JLabel("UserID:"), gbc);
        userIdField = new JTextField(20);
        gbc.gridx = 1;
        add(userIdField, gbc);

        // Username
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Username:"), gbc);
        usernameField = new JTextField(20);
        gbc.gridx = 1;
        add(usernameField, gbc);

        // Password
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Password:"), gbc);
        passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        add(passwordField, gbc);

        // Email
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(new JLabel("Email:"), gbc);
        emailField = new JTextField(20);
        gbc.gridx = 1;
        add(emailField, gbc);

        // Gender
        gbc.gridx = 0;
        gbc.gridy = 6;
        add(new JLabel("Gender:"), gbc);
        maleRadio = new JRadioButton("Male");
        femaleRadio = new JRadioButton("Female");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleRadio);
        genderGroup.add(femaleRadio);
        JPanel genderPanel = new JPanel();
        genderPanel.add(maleRadio);
        genderPanel.add(femaleRadio);
        gbc.gridx = 1;
        add(genderPanel, gbc);

        // Roles
        gbc.gridx = 0;
        gbc.gridy = 7;
        add(new JLabel("Roles:"), gbc);
        adminRadio = new JRadioButton("Admin");
        staffRadio = new JRadioButton("Staff");
        ButtonGroup roleGroup = new ButtonGroup();
        roleGroup.add(adminRadio);
        roleGroup.add(staffRadio);
        JPanel rolePanel = new JPanel();
        rolePanel.add(adminRadio);
        rolePanel.add(staffRadio);
        gbc.gridx = 1;
        add(rolePanel, gbc);

        // Terms & Condition
        termsCheckbox = new JCheckBox("Accept Terms & Condition on our system");
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        add(termsCheckbox, gbc);

        // Buttons
        signupButton = new JButton("Signup");
        backButton = new JButton("Back");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(signupButton);
        buttonPanel.add(backButton);
        gbc.gridy = 9;
        add(buttonPanel, gbc);

        // Action listeners
        signupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                signUp();
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                goBack();
            }
        });
    }

    private void signUp() {
        if (!validateInputs()) {
            return;
        }

        String userId = userIdField.getText();
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String email = emailField.getText();
        String gender = maleRadio.isSelected() ? "Male" : "Female";
        String role = adminRadio.isSelected() ? "Admin" : "Staff";

        try {
            File file = new File("users.txt");
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(String.format("%s,%s,%s,%s,%s,%s\n", userId, username, password, email, gender, role));
            bw.close();

            JOptionPane.showMessageDialog(this, "Sign up successful!");
            // Go to main page
            // MainPage mainPage = new MainPage();
            // mainPage.setVisible(true);
            this.dispose();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error writing to file: " + ex.getMessage());
        }
    }

    private boolean validateInputs() {
        if (userIdField.getText().isEmpty() || usernameField.getText().isEmpty() ||
            passwordField.getPassword().length == 0 || emailField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.");
            return false;
        }

        if (!isValidEmail(emailField.getText())) {
            JOptionPane.showMessageDialog(this, "Invalid email format.");
            return false;
        }

        if (!maleRadio.isSelected() && !femaleRadio.isSelected()) {
            JOptionPane.showMessageDialog(this, "Please select a gender.");
            return false;
        }

        if (!adminRadio.isSelected() && !staffRadio.isSelected()) {
            JOptionPane.showMessageDialog(this, "Please select a role.");
            return false;
        }

        if (!termsCheckbox.isSelected()) {
            JOptionPane.showMessageDialog(this, "Please agree to our terms & conditions, or else you can't get the service from us.");
            return false;
        }

        return true;
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    private void goBack() {
        LoginSystem loginSystem = new LoginSystem();
        loginSystem.setVisible(true);
        this.dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SignupSystem().setVisible(true);
            }
        });
    }
}