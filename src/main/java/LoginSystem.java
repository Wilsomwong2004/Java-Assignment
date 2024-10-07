/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author songj and weisheng
 */

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class LoginSystem extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame
     */
    private static final String USER_FILE = "users.txt";
    private Map<String, String> users = new HashMap<>();
    private Map<String, String> userEmails = new HashMap<>();
    private Map<String, String> userNames = new HashMap<>();
    private Map<String, String> userRoles = new HashMap<>();
    private javax.swing.JButton jButton3;
    private String currentUserID = "";
    private String currentPassword = "";
    
    public LoginSystem() {
        setTitle("PPE Item Inventory System");
        initComponents();
        getContentPane().setBackground(Color.white);
        loadUsers();
        setupPlaceholders();
    }

    private void loadUsers() {
        File file = new File(USER_FILE);
        if (!file.exists() || file.length() == 0) {
            String newStaffID = "Staff-00000";
            filefunction.REMOVE_DATA(USER_FILE, newStaffID);
            filefunction.ADD_DATA(USER_FILE, null, "Admin", "0000", "admin@gmail.com", "Male", "Admin");
            JOptionPane.showMessageDialog(rootPane, 
            "Welcome to the PPE Item Inventory System! \nSince it's your new time here, this is your default ID and password. You can change it later on. \nStaff ID: Staff-00000 \nPassword: 0000",
            "First User Data", JOptionPane.INFORMATION_MESSAGE);
        } else {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(";");
                    if (parts.length >= 6) {
                        String userId = parts[0];
                        String name = parts[1];
                        String password = parts[2];
                        String email = parts[3];
                        String role = parts[5];
                        
                        users.put(userId, password);
                        userNames.put(userId, name);
                        userEmails.put(userId, email);
                        userRoles.put(userId, role);
                        
                        //System.out.println("Loaded user: " + userId + ", Password: " + password); // Debug print
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error reading user file", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void setupPlaceholders() {
        jTextField2.setText("Ex.Staff-00000");
        jTextField2.setForeground(Color.GRAY);
        jTextField2.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (jTextField2.getText().equals("Ex.Staff-00000")) {
                    jTextField2.setText("");
                    jTextField2.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (jTextField2.getText().isEmpty()) {
                    jTextField2.setForeground(Color.GRAY);
                    jTextField2.setText("Ex.Staff-00000");
                }
            }
        });


        jPasswordField1.setText("Ex.0000");
        jPasswordField1.setForeground(Color.GRAY);
        jPasswordField1.setEchoChar((char) 0);
        jPasswordField1.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(jPasswordField1.getPassword()).equals("Ex.0000")) {
                    jPasswordField1.setText("");
                    jPasswordField1.setForeground(Color.BLACK);
                    jPasswordField1.setEchoChar('*');
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (String.valueOf(jPasswordField1.getPassword()).isEmpty()) {
                    jPasswordField1.setEchoChar((char) 0);
                    jPasswordField1.setForeground(Color.GRAY);
                    jPasswordField1.setText("Ex.0000");
                }
            }
        });
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    private void initComponents() {

        popupMenu1 = new java.awt.PopupMenu();
        jButton1 = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        jPasswordField1 = new javax.swing.JPasswordField();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        JPanel jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        popupMenu1.setLabel("popupMenu1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setBackground(new java.awt.Color(51, 51, 51));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Login");
        jButton1.setActionCommand("Loginbutton");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("UserID :");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Password :");

        ImageIcon loginIcon = ResourceLoader.loadIcon("/pic/inventory-icon-for-your-website-mobile-presentation-and-logo-design-free-vector.jpg");
        if (loginIcon != null) {
            jLabel2.setIcon(loginIcon);
        }
        
        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel3.setText("Forget Password? ");

        jButton3.setText("Click here");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(153, 0, 102));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Show");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Login Page");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(103, 103, 103))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(34, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(18, 18, 18))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(109, 109, 109)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(61, 61, 61)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(33, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(136, 136, 136))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4)))
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jButton3))
                .addGap(33, 33, 33)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );

        jPasswordField1.getAccessibleContext().setAccessibleName("");

        pack();
    }// </editor-fold>

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        attemptLogin();
    }

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
        String userID = JOptionPane.showInputDialog(this, "Enter your UserID:");
        if (userID != null && !userID.isEmpty()) {
            if ("Staff-00000".equals(userID)){
                JOptionPane.showMessageDialog(this, "Admin cannot change the password.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (users.containsKey(userID)) {
                String email = userEmails.get(userID);
                if (email != null) {
                    ForgetPasswordForm forgetPasswordForm = new ForgetPasswordForm(this, userID, email);
                    forgetPasswordForm.setVisible(true);
                    this.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(this, "No email associated with this account.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "UserID not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        if (jButton2.getText().equals("Show")) {
            jPasswordField1.setEchoChar((char) 0);
            jButton2.setText("Hide");
        } else {
            jPasswordField1.setEchoChar('*');
            jButton2.setText("Show");
        }
    }
    
    private void attemptLogin() {
        currentUserID = jTextField2.getText().trim();
        currentPassword = new String(jPasswordField1.getPassword());
    
        if (users.containsKey(currentUserID)) {
            if (users.get(currentUserID).equals(currentPassword)) {
                String role = userRoles.get(currentUserID);
                String name = userNames.get(currentUserID);
                JOptionPane.showMessageDialog(this, "Login successful! Welcome, " + name, "Success", JOptionPane.INFORMATION_MESSAGE);
                
                // Instead of creating AdminPage directly, call a new method
                transitionToAdminPage(role);
                
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid password", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "UserID not found", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void transitionToAdminPage(String role) {
        String[] allfiles = {"suppliers.txt", "hospitals.txt", "ppe.txt"};
        
        boolean setupNeeded = false;
        for (String file : allfiles) {
            if (!new File(file).exists()) {
                setupNeeded = true;
                break;
            }
        }
        
        if (!setupNeeded) {
            AdminPage adminPage = new AdminPage(role);
            adminPage.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "System setup required. Click OK to begin initialization", "Message", 
            JOptionPane.INFORMATION_MESSAGE);
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    JFrame frame = new JFrame("File Check");
                    JPanel contentPane = new JPanel();
                    CardLayout cardLayout = new CardLayout();
                    contentPane.setLayout(cardLayout);
                    frame.setContentPane(contentPane);
                    frame.setSize(600, 500);
                    frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                    // Add a WindowListener to show a warning dialog when the user attempts to close the window
                    frame.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosing(WindowEvent e) {
                            JOptionPane.showMessageDialog(null,
                                    "You cannot close this panel until the process is completed.",
                                    "Warning",
                                    JOptionPane.WARNING_MESSAGE);
                        }
                    });
                    
                    filefunction.performSetup(contentPane, cardLayout, allfiles, () -> {
                        frame.dispose();
                        AdminPage adminPage = new AdminPage(role);
                        adminPage.setVisible(true);
                    });
                    
                    frame.setVisible(true);
                }
            });
        }
    }

    public void updateUserPassword(String userID, String newPassword) {
        if (users.containsKey(userID)) {
            users.put(userID, newPassword);

            // Update the file
            List<String> updatedLines = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(";");
                    if (parts[0].equals(userID)) {
                        // Update the password for this user
                        parts[2] = newPassword;
                        line = String.join(";", parts);
                    }
                    updatedLines.add(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            // Write the updated content back to the file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_FILE))) {
                for (String line : updatedLines) {
                    writer.write(line);
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Reload users to ensure consistency
            loadUsers();
        }
    }

    public void showLoginSystem() {
        this.setVisible(true);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LoginSystem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginSystem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginSystem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginSystem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginSystem().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JTextField jTextField2;
    private java.awt.PopupMenu popupMenu1;
    // End of variables declaration
}

/**
 *
 * @author songj and weisheng
 */
