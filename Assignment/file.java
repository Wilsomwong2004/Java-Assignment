import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;
import javax.swing.*;

public class file{
    public static final Scanner inputScanner = new Scanner(System.in);
    static String[] user = {"User ID ","Name ","Password ","User Type "};
    static String[] ppe = {"Item Code ","Item Name","Supplier Code","Quantity in stock "};
    static String[] supplier = {"Supplier Code ","Name ","Address ","Phone Number "};
    static String[] hospital = {"Hospital Code ","Name ","Address ","Phone Number "};

    static void checkMissingFiles(JPanel PanelName, CardLayout cardLayout, Container contentPane){
        String[] allfiles = {"suppliers.txt","hospitals.txt","ppe.txt"};
        for (String eachfile : allfiles){
            File testfile = new File(eachfile);
            if (!testfile.exists()){
                createFile(eachfile);
                switch (eachfile) {
                    case "suppliers.txt" -> {
                        for (int i = 0; i<= 2; i++){
                            JOptionPane.showMessageDialog(PanelName, "Number of suppliers left:" + (3-i), "Message", JOptionPane.INFORMATION_MESSAGE);
                            cardLayout.show(contentPane,"Create Supplier");
                        }
                    }
                    case "hospitals.txt" -> {
                        for (int i = 0; i<= 2; i++){
                            JOptionPane.showMessageDialog(PanelName, "Number of hospital left:" + (3-i), "Message", JOptionPane.INFORMATION_MESSAGE);
                            cardLayout.show(contentPane,"Create Hospital");
                        }
                    }
                    case "ppe.txt" -> {
                        JPanel editpage= new JPanel(null);
                        List<String> lines = new ArrayList<>(); 
                        String[] itemCode = {"HC","FS","MS","GL","GW","SC"};
                        String[] itemName = {"Head Cover","Face Shield","Mask","Gloves","Gown","Shoe Covers"};  
                        for (int i=0; i< ppe.length; i++){
                            JLabel displaydata1 = new JLabel(ppe[i] + ":");
                            displaydata1.setBounds(50, 250 + i*70, 150, 50);
                            displaydata1.setFont(new Font("Agency FB", Font.BOLD, 30));
                            editpage.add(displaydata1);
                            if (i == 2){
                                TextField displaydata2 = new TextField();
                                displaydata2.setBounds(300, 250 + i*70, 300, 40);
                                displaydata2.setFont(new Font("Agency FB", Font.BOLD, 25));
                                editpage.add(displaydata2);
                            } else if (i == 0){
                                JLabel displaydata2 = new JLabel(item);
                                displaydata2.setBounds(300, 250 + i*70, 300, 40);
                                displaydata2.setFont(new Font("Agency FB", Font.BOLD, 30));
                                editpage.add(displaydata1);
                            }
                            try (BufferedWriter myWriter = new BufferedWriter(new FileWriter(filename,true))) {
                                for (int i=0; i <itemCode.length; i++) {
                                    lines.add(itemCode[i]+"<>"+itemName[i]+"<>-------<>"+100);
                                    myWriter.write(lines.get(i));
                                    myWriter.newLine();
                                }
                            } catch (IOException e) {
                                System.err.println("An error occurred while writing to the file.");
                                e.printStackTrace();
                        }

                        JButton savebutton = new JButton("Submit");
                        savebutton.setFont(new Font("Agency FB", Font.BOLD, 30));
                        savebutton.setBounds(230, 650, 140, 40);
                        editpage.add(savebutton);
                        savebutton.addActionListener(e -> {
                            String[] newdata = new String[data.length];
                            newdata[0] = data[0];
                            for (int i = 1; i<data.length; i++){
                                newdata[i] = allTextField[i].getText();
                                if (allTextField[i]!= null){
                                    newdata[i]= allTextField[i].getText();
                            }
                            boolean actiondone = file.EDIT_DATA(filename, newdata);
                            if (actiondone){
                                JOptionPane.showMessageDialog(PanelName, "Data updated successfully", "Search Result", JOptionPane.INFORMATION_MESSAGE);
                                
                                // Clear all TextField contents
                                for (TextField textField : allTextField) {
                                    if (textField != null) {
                                        textField.setText("");
                                    }
                                }

                                cardLayout.show(AdminPage.this.getContentPane(),previousPanel);
                                }
                            }
                        });
                        add(editpage, "Edit Panel");
                        cardLayout.show(AdminPage.this.getContentPane(),"Edit Panel");
                    }
                    }
                    default -> {
                    }
                }
            }
        }
        cardLayout.show(contentPane,"Main Panel");
    }

    static String createFile(String filename){
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

    static String generateNewID(String filename) throws IOException {
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
    

    static void ADD_DATA(String filename, String... data) {
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
                default -> {
                    System.err.println("Unknown filename type.");
                    return;
                }
            }

            // Add the provided data, ensuring it is trimmed
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
                    ids.add(data[0]);
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

    static void DELETE_DATA(String filename, String search, JPanel panelName) throws IOException {
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


