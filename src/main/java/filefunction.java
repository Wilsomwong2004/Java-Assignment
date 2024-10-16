/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author songj and weisheng
 */

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Choice;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.TextField;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class filefunction extends JFrame{


    //reusable code
    public static JLabel addLabel(JPanel panel, String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, 100, 30);
        label.setFont(new Font("Agency FB", Font.BOLD, 20));
        panel.add(label);
        return label;
    }
    
    public static TextField addTextField(JPanel panel, int x, int y) {
        TextField textField = new TextField(30);
        textField.setBounds(x, y, 300, 30);
        panel.add(textField);
        return textField;
    }

    public static void clearTextFields(TextField... textFields) {
        for (TextField textField : textFields) {
            textField.setText("");
        }
    } 


    public static void performSetup(JPanel contentPane, CardLayout cardLayout, String[] allfiles, Runnable onCompletion) {
        // Define the actions to take based on missing files
        Runnable checkPPE = () -> {
            if (!new File(allfiles[2]).exists()) {
                initialiseppePanel(cardLayout, contentPane, allfiles[2], () -> onCompletion.run());
            } else {
                  onCompletion.run();
            }
        };
    
        Runnable checkHospital = () -> {
            if (!new File(allfiles[1]).exists()) {
                initialiseHospitalPanel(cardLayout, contentPane, allfiles[1], checkPPE);
            } else {
                checkPPE.run();
            }
        };
    
        // Check suppliers first, then hospitals, and finally PPE
        if (!new File(allfiles[0]).exists()) {
            initialiseSupplierPanel(cardLayout, contentPane, allfiles[0], checkHospital);
        } else {
            checkHospital.run();
        }
    }

    private static void initialiseSupplierPanel(CardLayout cardLayout, Container contentPane, String filename, Runnable onComplete) {
        createFile(filename);
        int[] submissionCount = {0};
    
        for (int i = 0; i < 3; i++) {
            JPanel panel = new JPanel(null);
    
            JPanel headerPanel = new JPanel();
            headerPanel.setBounds(0, 0, 600, 80);
            headerPanel.setBackground(new java.awt.Color(54, 57, 63)); 
            panel.add(headerPanel);

            JLabel label = new JLabel("New Supplier", SwingConstants.CENTER);
            label.setFont(new Font("Agency FB", Font.BOLD, 36));
            label.setForeground(new java.awt.Color(255, 255, 255)); // White text
            headerPanel.setLayout(new BorderLayout());
            headerPanel.add(label, BorderLayout.CENTER);

            // Name label and text field
            addLabel(panel, "Name:", 50, 100);
            TextField t1 = addTextField(panel, 150, 100);

            // Address label and text field (larger field for multiline input)
            addLabel(panel, "Address:", 50, 150);
            TextField t2 = new TextField();
            t2.setBounds(150, 150, 300, 50); // Larger text area
            t2.setBackground(new java.awt.Color(255, 255, 255));
            t2.setForeground(new java.awt.Color(0, 0, 0));
            panel.add(t2);

            // Phone Number label and text field
            addLabel(panel, "Phone Number:", 50, 230);
            TextField t3 = addTextField(panel, 150, 230);

            // Submit button styled like the image
            JButton button = new JButton("Submit");
            button.setFont(new Font("Agency FB", Font.BOLD, 20));
            button.setBackground(new java.awt.Color(54, 57, 63)); // Gray color
            button.setForeground(new java.awt.Color(255, 255, 255)); // White text
            button.setBounds(225, 300, 150, 40); // Centered button
            panel.add(button);
            
            int index = i;
            button.addActionListener(e -> {
                String input1 = t1.getText();
                String input2 = t2.getText();
                String input3 = t3.getText();
                if (!input1.isEmpty() && !input2.isEmpty() && !input3.isEmpty()) {
                    if (!input1.trim().matches("[a-zA-Z ]+")) {
                        JOptionPane.showMessageDialog(null, "Name should only contain letters and spaces.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (!input3.matches("\\d+")) {
                        JOptionPane.showMessageDialog(null, "Phone number should only contain digits.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    filefunction.ADD_DATA("suppliers.txt",null, t1.getText(), t2.getText(), t3.getText());
                    clearTextFields(t1, t2, t3);
                    submissionCount[0]++;
                    if (submissionCount[0] < 3) {
                        JOptionPane.showMessageDialog(panel, "Supplier data submitted.\nNumber of suppliers left: " + (3 - submissionCount[0]), "Message", JOptionPane.INFORMATION_MESSAGE);
                        cardLayout.show(contentPane, "Initialise Supplier " + (index + 1));
                    } else {
                        JOptionPane.showMessageDialog(panel, "All supplier data has been entered.", "Message", JOptionPane.INFORMATION_MESSAGE);
                        onComplete.run(); // Trigger the next action when all data is entered
                    }
                } else {
                    JOptionPane.showMessageDialog(panel, "Please ensure that all details are written.", "Input Error Message", JOptionPane.WARNING_MESSAGE);
                }
            });
    
            contentPane.add(panel, "Initialise Supplier" + i);
        }
        cardLayout.show(contentPane, "Initialise Supplier0");
    }
    
    private static void initialiseHospitalPanel(CardLayout cardLayout, Container contentPane, String filename, Runnable onComplete) {
        createFile(filename);
        int[] submissionCount = {0};
    
        for (int i = 0; i < 3; i++) {
            JPanel panel = new JPanel(null);
    
            JPanel headerPanel = new JPanel();
            headerPanel.setBounds(0, 0, 600, 80);
            headerPanel.setBackground(new java.awt.Color(54, 57, 63)); // Dark background
            panel.add(headerPanel);

            JLabel label = new JLabel("New Hospital", SwingConstants.CENTER);
            label.setFont(new Font("Agency FB", Font.BOLD, 36));
            label.setForeground(new java.awt.Color(255, 255, 255)); // White text
            headerPanel.setLayout(new BorderLayout());
            headerPanel.add(label, BorderLayout.CENTER);

            addLabel(panel, "Name:", 50, 100);
            TextField t1 = addTextField(panel, 150, 100);

            addLabel(panel, "Address:", 50, 150);
            TextField t2 = new TextField();
            t2.setBounds(150, 150, 300, 50); // Larger text area
            t2.setBackground(new java.awt.Color(255, 255, 255));
            t2.setForeground(new java.awt.Color(0, 0, 0));
            panel.add(t2);

            addLabel(panel, "Phone Number:", 50, 230);
            TextField t3 = addTextField(panel, 150, 230);

            JButton button = new JButton("Submit");
            button.setFont(new Font("Agency FB", Font.BOLD, 20));
            button.setBackground(new java.awt.Color(54, 57, 63)); 
            button.setForeground(new java.awt.Color(255, 255, 255));
            button.setBounds(225, 300, 150, 40);
            panel.add(button);
    
            int index = i;
            button.addActionListener(e -> {
                String input1 = t1.getText();
                String input2 = t2.getText();
                String input3 = t3.getText();
                if (!input1.isEmpty() && !input2.isEmpty() && !input3.isEmpty()) {    
                    if (!input1.trim().matches("[a-zA-Z ]+")) {
                        JOptionPane.showMessageDialog(null, "Name should only contain letters and spaces.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (!input3.matches("\\d+")) {
                        JOptionPane.showMessageDialog(null, "Phone number should only contain digits.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    ADD_DATA("hospitals.txt",null, t1.getText(), t2.getText(), t3.getText());
                    clearTextFields(t1, t2, t3);
                    submissionCount[0]++;
                    if (submissionCount[0] < 3) {
                        JOptionPane.showMessageDialog(panel, "Hospital data submitted.\nNumber of hospitals left: " + (3 - submissionCount[0]), "Message", JOptionPane.INFORMATION_MESSAGE);
                        cardLayout.show(contentPane, "Initialise Hospital " + (index + 1));
                    } else {
                        JOptionPane.showMessageDialog(panel, "All hospital data has been entered.", "Message", JOptionPane.INFORMATION_MESSAGE);
                        onComplete.run();
                    }
                } else {
                    JOptionPane.showMessageDialog(panel, "Please ensure that all details are written.", "Input Error Message", JOptionPane.WARNING_MESSAGE);
                }
            });
    
            contentPane.add(panel, "Initialise Hospital" + i);
        }
    
        cardLayout.show(contentPane, "Initialise Hospital0");
    }
   
    public static void initialiseppePanel(CardLayout cardLayout,Container contentPane,String filename, Runnable onComplete){
        createFile(filename);
        String[] itemCode = {"HC","FS","MS","GL","GW","SC"};
        String[] itemName = {"Head Cover","Face Shield","Mask","Gloves","Gown","Shoe Covers"};  
        int[] submissionCount = {0};

        for (int i = 0; i < 6; i++) {
            JPanel panel = new JPanel(null);

            JPanel headerPanel = new JPanel();
            headerPanel.setBounds(0, 0, 600, 80);
            headerPanel.setBackground(new java.awt.Color(54, 57, 63));
            panel.add(headerPanel);

            JLabel label = new JLabel("New PPE Item", SwingConstants.CENTER);
            label.setFont(new Font("Agency FB", Font.BOLD, 36));
            label.setForeground(new java.awt.Color(255, 255, 255)); // White text
            headerPanel.setLayout(new BorderLayout());
            headerPanel.add(label, BorderLayout.CENTER);

            addLabel(panel, "Item Code:", 50, 100);
            JLabel codeLabel = new JLabel(itemCode[i]);
            codeLabel.setBounds(150, 100, 300, 30);
            codeLabel.setFont(new Font("Agency FB", Font.BOLD, 20));
            panel.add(codeLabel);

            addLabel(panel, "Item Name:", 50, 150);
            JLabel nameLabel = new JLabel(itemName[i]);
            nameLabel.setBounds(150, 150, 300, 30);
            nameLabel.setFont(new Font("Agency FB", Font.BOLD, 20));
            panel.add(nameLabel);

            addLabel(panel, "Supplier Code:", 50, 200);
            Choice t3 = new Choice();
            List<String> allID = GET_ALL_ID("suppliers.txt");
            for (String id : allID) {
                t3.add(id);
            }
            t3.setBounds(150, 200, 300, 30);
            panel.add(t3);

            addLabel(panel, "Price:", 50, 250);
            TextField t4 = addTextField(panel, 150, 250);

            // Quantity (fixed value)
            addLabel(panel, "Quantity:", 50, 300);
            JLabel quantityLabel = new JLabel("100");
            quantityLabel.setBounds(150, 300, 300, 30);
            quantityLabel.setFont(new Font("Agency FB", Font.BOLD, 20));
            panel.add(quantityLabel);

            // Submit button styled like the image
            JButton button = new JButton("Submit");
            button.setFont(new Font("Agency FB", Font.BOLD, 20));
            button.setBackground(new java.awt.Color(54, 57, 63));
            button.setForeground(new java.awt.Color(255, 255, 255));
            button.setBounds(225, 350, 150, 40); // Centered button
            panel.add(button);
    
            int index = i;
            button.addActionListener(e -> {
                String input1 = itemCode[index];
                String input2 = itemName[index];
                String input3 = t3.getSelectedItem();
                String input4 = t4.getText();
                String input5 = "100";
                if (!input3.isEmpty()&& !input4.isEmpty()) {
                    try {
                        double price = Double.parseDouble(input4);
                        if (price <= 0) {
                            JOptionPane.showMessageDialog(null, "Price should be a positive number.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Price should be a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    ADD_DATA("ppe.txt",null, input1, input2, input3, input4,input5);
                    t3.select(0);
                    submissionCount[0]++;
                    if (submissionCount[0] < 6) {
                        JOptionPane.showMessageDialog(panel, "PPE item data submitted.\nNumber of PPE Item left: " + (6 - submissionCount[0]), "Message", JOptionPane.INFORMATION_MESSAGE);
                        cardLayout.show(contentPane, "Initialise Item" + (index + 1));
                    } else {
                        JOptionPane.showMessageDialog(panel, "All PPE item data has been entered.", "Message", JOptionPane.INFORMATION_MESSAGE);
                        onComplete.run();
                    }
                } else {
                    JOptionPane.showMessageDialog(panel, "Please ensure that all details are written.", "Input Error Message", JOptionPane.WARNING_MESSAGE);
                }
            });
            contentPane.add(panel, "Initialise Item" + i);
        }
        cardLayout.show(contentPane, "Initialise Item0");
    }
    
//    public static void welcomeToPage(CardLayout cardLayout, JPanel contentPane, Runnable onCompletion) {
//        JPanel proceedPanel = new JPanel();
//        proceedPanel.setLayout(new BorderLayout());
//
//        JButton proceedButton = new JButton("Setup complete. Press to continue to main system");
//        proceedButton.setFont(new Font("Agency FB", Font.BOLD, 30));
//        proceedButton.addActionListener(e -> {
//            if (onCompletion != null) {
//                onCompletion.run();
//            }
//        });
//        proceedPanel.add(proceedButton, BorderLayout.CENTER);
//        
//        contentPane.add(proceedPanel, "ProceedPanel");
//        cardLayout.show(contentPane, "ProceedPanel");
//    }
    
    public static String createFile(String filename){
        try {
            File newFile = new File(filename);
            if (newFile.createNewFile()){
                System.out.println(newFile.getName()+" doesn't exist. A new one is created");
            } 
        } catch (IOException e) {
            System.out.println("An error occured.");
            e.printStackTrace();
        }
        return filename;
    }

    //FILES FUNCTION
    public static List<String[]> loadDataFromFile(String filename, DefaultTableModel tableModel)throws Exception {
        List<String[]> allrecords = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String rec;
        while((rec= br.readLine()) != null){
            String [] record = rec.trim().split(";");
            tableModel.addRow(record);
            allrecords.add(record);
        }
        br.close();
        return allrecords;
    }
    
    public static String generateNewID(String filename) throws IOException {
        String lastID = "00000";
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                String[] data = currentLine.split(";");
                if (data.length > 0) {
                    String fullID = data[0];
                    String[] idParts = fullID.split("-");
                    if (idParts.length > 1) {
                        String numericPart = idParts[1];
                        if (numericPart.matches("\\d+")) {  // Ensure it's numeric
                            lastID = numericPart;
                        }
                    }
                }
            }
        } catch (NoSuchElementException e) {
            return String.format("%05d", 1);
        }
        int newIDNum = Integer.parseInt(lastID) + 1;
        return String.format("%05d", newIDNum);
    }

    public static boolean isDuplicateID(String string, String input1) {
        //does item code got same id as input1
        try (BufferedReader reader = new BufferedReader(new FileReader(string))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length > 0 && parts[0].equals(input1)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static void removeDuplicateEntries(String filename) {
        File inputFile = new File(filename);
        File tempFile = new File("temp_" + filename);
        Set<String> seenIds = new HashSet<>();
    
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
    
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length > 0) {
                    String id = parts[0];
                    if (!seenIds.contains(id)) {
                        writer.write(line);
                        writer.newLine();
                        seenIds.add(id);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        // Replace the original file with the de-duplicated file
        if (!inputFile.delete()) {
            System.out.println("Could not delete the original file");
            return;
        }
        if (!tempFile.renameTo(inputFile)) {
            System.out.println("Could not rename temp file");
        }
    }

    public static List<String> GET_ALL_ID(String filename){
        List<String> ids = new ArrayList<>();
        File file = new File(filename);
        try (BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line;
            while ((line = reader.readLine()) != null){
                String[] data = line.split(";");
                if (!data[0].isEmpty()){
                    ids.add(data[0].trim());
                } else{
                    System.out.println("No IDs found.");
                }
            }
        } catch (IOException e){
            System.err.println("An error occurred while searching.");
            e.printStackTrace();
        }
        return ids;
    }

    public static void ADD_DATA(String filename , DefaultTableModel tableModel, String... data) {
        File file = new File(filename);
        if (!file.exists()) {
            createFile(filename);
        }

        removeDuplicateEntries(filename);

        try (BufferedWriter myWriter = new BufferedWriter(new FileWriter(filename, true))) {
            List<String> lines = new ArrayList<>();

            if(filename.equals("ppe.txt")){
                lines.addAll(Arrays.asList(data));
                myWriter.write(String.join(";", lines));
                myWriter.newLine();
            }else {
                String newID = "";
                switch (filename) {
                    case "suppliers.txt" -> newID = "S-" + generateNewID(filename);
                    case "hospitals.txt" -> newID = "H-" + generateNewID(filename);
                    case "users.txt" -> {
                        if (data.length > 0 && data[0].equals("Admin")) {
                            newID = "Staff-00000";
                        } else {
                            newID = "Staff-" + generateNewID(filename);
                        }
                    }
                    case "transactions.txt" -> newID = "T-" + generateNewID(filename);
                }
                lines.add(newID);
                lines.addAll(Arrays.asList(data));
                myWriter.write(String.join(";", lines));
                myWriter.newLine();
            }  
            if (tableModel != null) {
                tableModel.addRow(lines.toArray(String[]::new));
            }

        } catch (IOException e) {
            System.err.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }

    public static String SEARCH_DATA (String filename,String search, Component parentComponent){
        File file = new File(filename);
        try (BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line;
            while ((line = reader.readLine()) != null){
                String[] data = line.split(";");
                if (search.equals(data[0])){
                    return line;
                }
            }
            JOptionPane.showMessageDialog(parentComponent,"Results not found. Please try again.", "Error", JOptionPane.WARNING_MESSAGE);
            return null;
        } catch (IOException e){
            System.err.println("An error occurred while searching.");
            e.printStackTrace();
            return null;
        }
    }

    public static boolean EDIT_DATA(String filename, String[] newData) {
        List<String> lines = new ArrayList<>();
        File file = new File(filename);
        boolean done = false;
    
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] existingData = line.split(";");
                if (existingData[0].equals(newData[0])) {
                    line = String.join(";", newData);
                    done = true;
                }
                lines.add(line); //add all lines into array list
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    
        // Overwrite the file with the modified content
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String l : lines) {
                writer.write(l);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return done;
    }

    public static void DELETE_DATA(String filename, String search,Component parentComponent,DefaultTableModel model,int sRow){
        File inputFile = new File(filename);
        File tempFile = new File("temp.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            boolean deleted = false; //to validate results
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                if (search.equals(data[0])) {
                    int response = JOptionPane.showConfirmDialog(parentComponent, "Are you sure you want to delete this?", "Confirmation Message", JOptionPane.YES_NO_OPTION);
                    if (response == JOptionPane.YES_OPTION){
                        deleted = true;
                        model.removeRow(sRow);
                        JOptionPane.showMessageDialog(parentComponent, "Record deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                        continue;// Skip writing this line to the temp file
                    } else{
                        JOptionPane.showConfirmDialog(parentComponent, "Cancellation of deletion completed.", "Cancellation Message", JOptionPane.PLAIN_MESSAGE);
                        deleted = false;
                    }
                }
                // line is rewritten if no results or if user doesn't confirm deletion
                writer.write(line + System.lineSeparator());
            }

            if (!deleted) {
                System.out.println("Results not found");
            }
        } catch (IOException e) {e.printStackTrace();}

        // Delete the original file and replace it with the temp file
        if (inputFile.delete()) {
            if (tempFile.renameTo(inputFile)) {
                System.out.println("Data deleted successfully.");
            }
        } else {
            System.out.println("Error. File isn't deleted.");
        }
    }

    public static void REMOVE_DATA(String fileName, String idToRemove) {
        File inputFile = new File(fileName);
        File tempFile = new File("temp_" + fileName);

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length > 0 && !parts[0].equals(idToRemove)) {
                    writer.write(line + System.lineSeparator());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (inputFile.delete()) {
            if (!tempFile.renameTo(inputFile)) {
                System.err.println("Could not rename temp file");
            }
        } else {
            System.err.println("Could not delete original file");
        }
    }
}

/**
 *
 * @author songj and weisheng
 */
