import java.io.*;
import java.util.*;
import javax.swing.*;

public class file {
    private final DefaultListModel<String> supplierListModel;
    private JList<String> supplierList;

    public static final Scanner inputScanner = new Scanner(System.in);
    static String[] user = { "User ID ", "Name ", "Password ", "User Type " };
    static String[] ppe = { "Item Code ", "Item Name", "Supplier Code", "Quantity in stock " };
    static String[] supplier = { "Supplier Code ", "Name ", "Address ", "Phone Number " };
    static String[] hospital = { "Hospital Code ", "Name ", "Address ", "Phone Number " };

    public file() {
        supplierListModel = new DefaultListModel<>();
        supplierList = new JList<>(supplierListModel);
        loadSuppliers(); // Initial load of suppliers
    }

    static void createFile(String filename) {
        try {
            File newFile = new File(filename);
            if (newFile.createNewFile()) {
                System.out.println(newFile.getName() + " doesn't exist. A new one is created");
                if (filename.equals("ppe.txt")) {
                    List<String> lines = new ArrayList<>();
                    String[] itemCode = { "HC", "FS", "MS", "GL", "GW", "SC" };
                    String[] itemName = { "Head Cover", "Face Shield", "Mask", "Gloves", "Gown", "Shoe Covers" };
                    try (BufferedWriter myWriter = new BufferedWriter(new FileWriter(filename, true))) {
                        for (int i = 0; i < itemCode.length; i++) {
                            lines.add(itemCode[i] + "<>" + itemName[i] + "<>-------<>" + 100);
                            myWriter.write(lines.get(i));
                            myWriter.newLine();
                        }
                    } catch (IOException e) {
                        System.err.println("An error occurred while writing to the file.");
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
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
            String newID = "";
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
                lines.add(i.trim());
            }
            myWriter.write(String.join("<>", lines));
            myWriter.newLine();
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }

        List<String> suppliers = loadSuppliers(); // Reload suppliers from the file
        updateSupplierListUI(suppliers); // Update the JList with the new data
    }

    private static List<String> loadSuppliers() {
        List<String> suppliers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("suppliers.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                suppliers.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return suppliers;
    }

    private static void updateSupplierListUI(List<String> suppliers) {
        DefaultListModel<String> model = new DefaultListModel<>();
        for (String supplier : suppliers) {
            model.addElement(supplier);
        }
        // Assuming you have a JList to update:
        // supplierList.setModel(model);
    }

    public static String SEARCH_DATA(String filename, String search) {
        File file = new File(filename);
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("<>");
                if (search.equals(data[0])) {
                    return line;
                }
            }
            System.out.println("Results not found. Please try again.");
            return null;
        } catch (IOException e) {
            System.err.println("An error occurred while searching.");
            e.printStackTrace();
            return null;
        }
    }

    public static List<String> GET_ALL_ID(String filename) {
        List<String> ids = new ArrayList<>();
        File file = new File(filename);
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("<>");
                if (!data[0].isEmpty()) {
                    ids.add(data[0]);
                } else {
                    System.out.println("No IDs found.");
                }
            }
        } catch (IOException e) {
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
                lines.add(line); // add all lines into array list
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
            boolean deleted = false; // to validate results
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("<>");
                if (search.equals(data[0])) {
                    int response = JOptionPane.showConfirmDialog(panelName, "Are you sure you want to delete this?",
                            "Confirmation Message", JOptionPane.YES_NO_OPTION);
                    if (response == JOptionPane.YES_OPTION) {
                        deleted = true;
                        continue;
                    } else {
                        JOptionPane.showConfirmDialog(panelName, "Cancellation of deletion completed.",
                                "Cancellation Message", JOptionPane.PLAIN_MESSAGE);
                    }
                }
                // line is rewritten if no results or if user doesn't confirm deletion
                writer.write(line + System.lineSeparator());
            }

            if (!deleted) {
                System.out.println("Results not found");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Delete the original file and replace it with the temp file
        if (inputFile.delete()) {
            if (tempFile.renameTo(inputFile)) {
                System.out.println("Data deleted successfully.");
            }
        } else {
            System.out.println("Error. File isn't deleted.");
        }
    }

    public static void main(String[] args) throws IOException {
        // Ensure "ppe.txt" is created before any operations
        file.createFile("ppe.txt");

        // Example of adding data
        file.ADD_DATA("suppliers.txt", "Supplier Name", "Supplier Address", "Supplier Phone");
        // You can add more examples here to test other methods
    }

}
