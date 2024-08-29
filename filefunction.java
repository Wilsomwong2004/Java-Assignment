import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import javax.swing.*;

public class filefunction{
    static String[] user = {"User ID ","Name ","Password ","User Type "};
    static String[] ppe = {"Item Code ","Item Name","Supplier Code","Quantity in stock "};
    static String[] supplier = {"Supplier Code ","Name ","Address ","Phone Number "};
    static String[] hospital = {"Hospital Code ","Name ","Address ","Phone Number "};

    public static void checkMissingFiles(JPanel PanelName, CardLayout cardLayout, Container contentPane) {
        String[] allfiles = {"suppliers.txt", "hospitals.txt", "ppe.txt"};
        
        if (!new File(allfiles[0]).exists()) {
            initialiseSupplierPanel(cardLayout, contentPane, allfiles[0], () -> {
                if (!new File(allfiles[1]).exists()) {
                    initialiseHospitalPanel(cardLayout, contentPane, allfiles[1],() -> {
                        if (!new File(allfiles[2]).exists()){
                            initialiseppePanel(cardLayout, contentPane, allfiles[2],() -> {
                                cardLayout.show(contentPane, "Main Panel");
                            });
                        }
                    });
                }
            });
        } else if (!new File(allfiles[1]).exists()) {
            initialiseHospitalPanel(cardLayout, contentPane, allfiles[1],() -> {
                if (!new File(allfiles[2]).exists()){
                    initialiseppePanel(cardLayout, contentPane, allfiles[2],() -> {
                        cardLayout.show(contentPane, "Main Panel");
                    });
                }
            });
        } else if (!new File(allfiles[2]).exists()){
            initialiseppePanel(cardLayout, contentPane, allfiles[2],() -> {
                cardLayout.show(contentPane, "Main Panel");
            });
        }
    }


    private static void initialiseSupplierPanel(CardLayout cardLayout, Container contentPane, String filename, Runnable onComplete) {
        createFile(filename);
        int[] submissionCount = {0};
    
        for (int i = 0; i < 3; i++) {
            JPanel panel = new JPanel(null);
    
            JLabel label = new JLabel("New Supplier");
            label.setFont(new Font("Agency FB", Font.BOLD, 50));
            label.setBounds(190, 0, 600, 100);
            panel.add(label);
    
            AdminPage.addLabel(panel, "Name:", 100, 150);
            TextField t1 = AdminPage.addTextField(panel, 200, 150);
            
            AdminPage.addLabel(panel, "Address:", 100, 200);
            TextField t2 = AdminPage.addTextField(panel, 200, 200);
            
            AdminPage.addLabel(panel, "Phone Number:", 100, 250);
            TextField t3 = AdminPage.addTextField(panel, 200, 250);
            
            JButton button = new JButton("Submit");
            button.setFont(new Font("Agency FB", Font.BOLD, 20));
            button.setBounds(200, 500, 150, 80);
            panel.add(button);
            
            int index = i;
            button.addActionListener(e -> {
                if (!t1.getText().isEmpty() && !t2.getText().isEmpty() && !t3.getText().isEmpty()) {
                    filefunction.ADD_DATA("suppliers.txt", t1.getText(), t2.getText(), t3.getText());
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
    
            JLabel label = new JLabel("New Hospital");
            label.setFont(new Font("Agency FB", Font.BOLD, 50));
            label.setBounds(190, 0, 600, 100);
            panel.add(label);
    
            AdminPage.addLabel(panel, "Name:", 100, 150);
            TextField t1 = AdminPage.addTextField(panel, 200, 150);
            
            AdminPage.addLabel(panel, "Address:", 100, 200);
            TextField t2 = AdminPage.addTextField(panel, 200, 200);
            
            AdminPage.addLabel(panel, "Phone Number:", 100, 250);
            TextField t3 = AdminPage.addTextField(panel, 200, 250);
            
            JButton button = new JButton("Submit");
            button.setFont(new Font("Agency FB", Font.BOLD, 20));
            button.setBounds(200, 500, 150, 80);
            panel.add(button);
    
            int index = i;
            button.addActionListener(e -> {
                if (!t1.getText().isEmpty() && !t2.getText().isEmpty() && !t3.getText().isEmpty()) {
                    ADD_DATA("hospitals.txt", t1.getText(), t2.getText(), t3.getText());
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
    
    
    public static void clearTextFields(TextField... textFields) {
        for (TextField textField : textFields) {
            textField.setText("");
        }
    } 

    public static void initialiseppePanel(CardLayout cardLayout,Container contentPane,String filename, Runnable onComplete){
        createFile(filename);
        String[] itemCode = {"HC","FS","MS","GL","GW","SC"};
        String[] itemName = {"Head Cover","Face Shield","Mask","Gloves","Gown","Shoe Covers"};  
        int[] submissionCount = {0};

        for (int i = 0; i < 6; i++) {
            JPanel panel = new JPanel(null);

            JLabel label = new JLabel("New PPE Item");
            label.setFont(new Font("Agency FB", Font.BOLD, 50));
            label.setBounds(190, 0, 600, 100);
            panel.add(label);

            AdminPage.addLabel(panel, "Item Code:", 100, 150);
            AdminPage.addLabel(panel, itemCode[i], 250, 150);

            AdminPage.addLabel(panel, "Item Name:", 100, 200);
            AdminPage.addLabel(panel, itemName[i], 250, 200);

            AdminPage.addLabel(panel, "Supplier Code:", 100, 250);
            Choice t3 = new Choice();
            List<String> allID = GET_ALL_ID("suppliers.txt");
            for (String id:allID){
                t3.add(id);
            }
            t3.setBounds(250, 250, 400, 30);
            panel.add(t3);

            AdminPage.addLabel(panel, "Quantity:         100", 100, 300);
            
            JButton button = new JButton("Submit");
            button.setFont(new Font("Agency FB", Font.BOLD, 20));
            button.setBounds(200, 500, 150, 80);
            panel.add(button);
    
            int index = i;
            button.addActionListener(e -> {
                String input1 = itemCode[index];
                String input2 = itemName[index];
                String input3 = t3.getSelectedItem();
                String input4 = "100";
                if (!input3.isEmpty()) {
                    ADD_DATA("ppe.txt", input1, input2, input3, input4);
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

    public static String generateNewID(String filename) throws IOException {
        String lastID = "00000";
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                String[] data = currentLine.split("<>");
                if (data.length > 0) {
                    String longLastID = data[0];
                    String[] idParts = longLastID.split("-");
                    if (idParts.length > 1) {
                        lastID = idParts[1];
                    }
                }
            }
        } catch (NoSuchElementException e) {
            return String.format("%05d", 1);
        }
        int newIDNum = Integer.parseInt(lastID) + 1;
        return String.format("%05d", newIDNum);
    }  

    public static String[] dataFormat(String filename) {
        String[] label = null;
        switch (filename) {
            case "users.txt" -> label = user;
            case "ppe.txt" -> label = ppe;
            case "suppliers.txt" -> label = supplier;
            case "hospitals.txt" -> label = hospital;
            default -> System.out.println("Invalid file type.");
        }
        return label;
    }
    
    public static void ADD_DATA(String filename, String... data) {
        File file = new File(filename);
        if (!file.exists()) {
            createFile(filename);
        }

        try (BufferedWriter myWriter = new BufferedWriter(new FileWriter(filename, true))) {
            String newID;
            List<String> lines = new ArrayList<>();

            switch (filename) {
                case "suppliers.txt" -> {
                    newID = "S-" + generateNewID(filename);
                    lines.add(newID);
                }
                case "hospitals.txt" -> {
                    newID = "H-" + generateNewID(filename);
                    lines.add(newID);
                }
                case "users.txt" -> {
                    newID = "Staff-" + generateNewID(filename);
                    lines.add(newID);
                }
            }

            for (String i : data) {
                if (i == null || i.trim().isEmpty()) {
                    System.err.println("Invalid data provided. Skipping empty data.");
                    continue;  // Skip invalid data
                }
                lines.add(i.trim());
            }

            // Write the assembled line to the file
            myWriter.write(String.join("<>", lines));
            myWriter.newLine();

        } catch (IOException e) {
            System.err.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }

    public static String SEARCH_DATA (String filename,String search){
        File file = new File(filename);
        try (BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line;
            while ((line = reader.readLine()) != null){
                String[] data = line.split("<>");
                if (search.equals(data[0])){
                    return line;
                }
            }
            System.out.println("Results not found. Please try again.");
            return null;
        } catch (IOException e){
            System.err.println("An error occurred while searching.");
            e.printStackTrace();
            return null;
        }
    }

    public static List<String> GET_ALL_ID(String filename){
        List<String> ids = new ArrayList<>();
        File file = new File(filename);
        try (BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line;
            while ((line = reader.readLine()) != null){
                String[] data = line.split("<>");
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

    public static boolean EDIT_DATA(String filename, String[] newData) {
        List<String> lines = new ArrayList<>();
        File file = new File(filename);
        boolean done = false;
    
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] existingData = line.split("<>");
                if (existingData[0].equals(newData[0])) {
                    line = String.join("<>", newData);
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

    public static void DELETE_DATA(String filename, String search, JPanel panelName) throws IOException {
        File inputFile = new File(filename);
        File tempFile = new File("temp.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            boolean deleted = false; //to validate results
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("<>");
                if (search.equals(data[0])) {
                    int response = JOptionPane.showConfirmDialog(panelName, "Are you sure you want to delete this?", "Confirmation Message", JOptionPane.YES_NO_OPTION);
                    if (response == JOptionPane.YES_OPTION){
                        deleted = true;
                        continue;
                    } else{
                        JOptionPane.showConfirmDialog(panelName, "Cancellation of deletion completed.", "Cancellation Message", JOptionPane.PLAIN_MESSAGE);
                    }
                }
                // line is rewritten if no results or if user doesn't confirm deletion
                writer.write(line + System.lineSeparator());
            }

            if (!deleted) {System.out.println("Results not found");}

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
}




