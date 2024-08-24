import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class PPE_inventory_management_system  {

    public static void main(String[] args) {
        // Create the frame
        JFrame frame = new JFrame("PPE Inventory Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 300);

        // Create panel for user ID and password
        JPanel panel = new JPanel(new GridLayout(3, 2));
        JLabel userLabel = new JLabel("User ID:");
        JTextField userText = new JTextField();
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passText = new JPasswordField();

        // Add components to panel
        panel.add(userLabel);
        panel.add(userText);
        panel.add(passLabel);
        panel.add(passText);

        // Create login button and result label
        JButton loginButton = new JButton("Login");
        JLabel resultLabel = new JLabel("", SwingConstants.CENTER);

        // Add action listener to login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userID = userText.getText();
                String password = new String(passText.getPassword());

                // Define valid user ID and password
                String validUserID = "admin";
                String validPassword = "password123";

                // Check if input matches valid credentials
                if (userID.equals(validUserID) && password.equals(validPassword)) {
                    resultLabel.setText("Login successful!");
                    AdminPage.main(args);
                } else {
                    resultLabel.setText("Invalid user ID or password.");
                }
            }
        });

        // Set layout for the frame
        frame.setLayout(new BorderLayout());
        
        // Add panel, button, and result label to frame
        frame.add(panel, BorderLayout.CENTER);
        frame.add(loginButton, BorderLayout.SOUTH);
        frame.add(resultLabel, BorderLayout.NORTH);

        // Make the frame visible
        frame.setVisible(true);
    }
}

