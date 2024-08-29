import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.List;
import javax.swing.*;

public final class AdminPage extends JFrame {

    private final CardLayout cardLayout;
    private final JPanel mainPanel;

    public AdminPage() {
        cardLayout = new CardLayout();
        setLayout(cardLayout);

        mainPanel = mainPanel();
        add(mainPanel, "Main Panel");

        add(HospitalPanel(), "Hospital Panel");
        add(createHospitalPanel(), "Create Hospital");
        add(searchHospitalPanel(), "Search Hospital");

        add(SupplierPanel(), "Supplier Panel");
        add(createSupplierPanel(), "Create Supplier");
        add(searchSupplierPanel(), "Search Supplier");

        add(UserPanel(), "User Panel");
        add(createUserPanel(), "Create User");
        add(searchUserPanel(), "Search User");

        add(ppePanel(), "ppe Panel");
        add(createppePanel(), "Create Item");
        add(searchppePanel(), "Search Item");

        // Configure the frame
        setTitle("Admin Page");
        setSize(700, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        // Handle window closing
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }

    // Reusable code
    public JLabel createtitle(JPanel PanelName, String titleName) {
        JLabel label = new JLabel(titleName);
        label.setFont(new Font("Agency FB", Font.BOLD, 50));
        label.setBounds(190, 0, 600, 100);
        PanelName.add(label);
        return label;
    }

    public void backbutton(JPanel PanelName, String nextPanelName) {
        JButton button = new JButton("Back");
        button.setFont(new Font("Agency FB", Font.BOLD, 20));
        button.setBounds(10, 50, 80, 30);
        button.addActionListener(e -> {
            cardLayout.show(AdminPage.this.getContentPane(), nextPanelName);
        });
        PanelName.add(button);
    }

    public void searchpanel(JPanel PanelName, String filename, String title, String previousPanel) {
        createtitle(PanelName, title);
        backbutton(PanelName, previousPanel);

        JLabel searchbar = new JLabel("Enter the ID:");
        searchbar.setBounds(100, 150, 80, 30);
        searchbar.setFont(new Font("Agency FB", Font.BOLD, 20));
        PanelName.add(searchbar);

        JLabel line = new JLabel(
                "_________________________________________________________________________________________________________________________________________");
        line.setBounds(50, 200, 600, 50);
        line.setFont(new Font("Agency FB", Font.BOLD, 10));
        PanelName.add(line);

        TextField t1 = new TextField(30);
        t1.setBounds(180, 150, 200, 30);
        PanelName.add(t1);

        JButton button = new JButton("Search");
        button.setFont(new Font("Agency FB", Font.BOLD, 20));
        button.setBounds(450, 150, 100, 30);
        PanelName.add(button);

        JButton delbutton = new JButton("Delete");
        delbutton.setFont(new Font("Agency FB", Font.BOLD, 20));
        delbutton.setBounds(500, 700, 80, 30);

        JButton editbutton = new JButton("Edit");
        editbutton.setFont(new Font("Agency FB", Font.BOLD, 20));
        editbutton.setBounds(400, 700, 80, 30);

        button.addActionListener(e -> {
            String search = t1.getText();
            if (!search.isEmpty()) {
                String searchresult = file.SEARCH_DATA(filename, search);
                String[] data1 = file.dataFormat(filename);
                if (searchresult != null) {
                    String[] data2 = searchresult.split("<>");
                    for (int i = 0; i < data2.length; i++) {
                        JLabel displaydata1 = new JLabel(data1[i] + ":");
                        displaydata1.setBounds(100, 250 + i * 50, 150, 50);
                        displaydata1.setFont(new Font("Agency FB", Font.BOLD, 30));
                        PanelName.add(displaydata1);
                        JLabel displaydata2 = new JLabel(data2[i]);
                        displaydata2.setBounds(300, 250 + i * 50, 300, 50);
                        displaydata2.setFont(new Font("Agency FB", Font.BOLD, 30));
                        PanelName.add(displaydata2);
                    }
                    PanelName.add(delbutton);
                    PanelName.add(editbutton);
                    delbutton.addActionListener(e2 -> {
                        try {
                            file.DELETE_DATA(filename, search, PanelName);
                        } catch (IOException e1) {
                            System.out.println("Error in deleting data.");
                            e1.printStackTrace();
                        }
                        cardLayout.show(AdminPage.this.getContentPane(), previousPanel);
                    });

                    editbutton.addActionListener(e3 -> {
                        editpanel(data1, data2, filename, PanelName, previousPanel);
                    });
                } else {
                    JOptionPane.showMessageDialog(PanelName, "No results found.", "Search Result",
                            JOptionPane.WARNING_MESSAGE);
                }
                PanelName.revalidate();
                PanelName.repaint();
            }
        });
    }

    public void editpanel(String[] dataname, String[] data, String filename, JPanel PanelName, String previousPanel) {

        JPanel editpage = new JPanel(null);

        createtitle(editpage, "EDIT PAGE");
        backbutton(PanelName, previousPanel);

        TextField[] allTextField = new TextField[data.length];

        for (int i = 0; i < data.length; i++) {
            JLabel displaydata1 = new JLabel(dataname[i] + ":");
            displaydata1.setBounds(50, 250 + i * 70, 150, 50);
            displaydata1.setFont(new Font("Agency FB", Font.BOLD, 30));
            editpage.add(displaydata1);
            if (i == 0) {
                JLabel displaydata2 = new JLabel(data[i]);
                displaydata2.setBounds(300, 250 + i * 70, 150, 50);
                displaydata2.setFont(new Font("Agency FB", Font.BOLD, 30));
                editpage.add(displaydata2);
            } else {
                TextField displaydata2 = new TextField(data[i]);
                displaydata2.setBounds(300, 250 + i * 70, 300, 40);
                displaydata2.setFont(new Font("Agency FB", Font.BOLD, 25));
                editpage.add(displaydata2);
                allTextField[i] = displaydata2;
            }
        }

        JButton savebutton = new JButton("Save");
        savebutton.setFont(new Font("Agency FB", Font.BOLD, 30));
        savebutton.setBounds(230, 650, 140, 40);
        editpage.add(savebutton);
        savebutton.addActionListener(e -> {
            String[] newdata = new String[data.length];
            newdata[0] = data[0];
            for (int i = 1; i < data.length; i++) {
                newdata[i] = allTextField[i].getText();
            }
            boolean actiondone = file.EDIT_DATA(filename, newdata);
            if (actiondone) {
                JOptionPane.showMessageDialog(PanelName, "Data updated successfully", "Search Result",
                        JOptionPane.INFORMATION_MESSAGE);
                cardLayout.show(AdminPage.this.getContentPane(), previousPanel);
            }
        });
        add(editpage, "Edit Panel");
        cardLayout.show(AdminPage.this.getContentPane(), "Edit Panel");
    }

    // Starts here
    private JPanel mainPanel() {
        JPanel main = new JPanel(null);

        JLabel title = new JLabel("Welcome to the admin page. Choose any file you like");
        title.setFont(new Font("Agency FB", Font.BOLD, 30));
        title.setBounds(50, 0, 600, 100);
        main.add(title);

        for (int i = 0; i < 4; i++) {
            JButton button = new JButton(new String[] { "HOSPITAL", "SUPPLIER", "USER", "PPE ITEMS" }[i]);
            button.setFont(new Font("Agency FB", Font.BOLD, 20));
            button.setBounds(250, 100 + i * 100, 150, 80);
            button.addActionListener(e -> {
                switch (e.getActionCommand()) {
                    case "HOSPITAL" -> cardLayout.show(AdminPage.this.getContentPane(), "Hospital Panel");
                    case "SUPPLIER" -> cardLayout.show(AdminPage.this.getContentPane(), "Supplier Panel");
                    case "USER" -> cardLayout.show(AdminPage.this.getContentPane(), "User Panel");
                    case "PPE ITEMS" -> cardLayout.show(AdminPage.this.getContentPane(), "ppe Panel");
                }
            });
            main.add(button);
        }
        return main;
    }

    private JPanel HospitalPanel() {
        JPanel hospitalPanel = new JPanel(null);

        createtitle(hospitalPanel, "Hospital Menu");
        backbutton(hospitalPanel, "Main Panel");

        for (int i = 0; i < 2; i++) {
            JButton button = new JButton(new String[] { "CREATE HOSPITAL", "SEARCH HOSPITAL" }[i]);
            button.setFont(new Font("Agency FB", Font.BOLD, 20));
            button.setBounds(250, 100 + i * 100, 150, 80);
            button.addActionListener(e -> {
                switch (e.getActionCommand()) {
                    case "CREATE HOSPITAL" -> cardLayout.show(AdminPage.this.getContentPane(), "Create Hospital");
                    case "SEARCH HOSPITAL" -> cardLayout.show(AdminPage.this.getContentPane(), "Search Hospital");
                }
            });
            hospitalPanel.add(button);
        }
        return hospitalPanel;
    }

    private JPanel createHospitalPanel() {
        JPanel createhospital = new JPanel(null);

        createtitle(createhospital, "New Hospital Form");
        backbutton(createhospital, "Hospital Panel");

        JLabel hname = new JLabel("Name:");
        hname.setBounds(100, 150, 80, 30);
        createhospital.add(hname);

        TextField t1 = new TextField(null, 30);
        t1.setBounds(180, 150, 400, 30);
        createhospital.add(t1);

        Label haddress = new Label("Address:");
        haddress.setBounds(100, 200, 80, 30);
        createhospital.add(haddress);

        TextField t2 = new TextField(null, 30);
        t2.setBounds(180, 200, 400, 30);
        createhospital.add(t2);

        Label hphonenum = new Label("Phone Number:");
        hphonenum.setBounds(100, 250, 80, 30);
        createhospital.add(hphonenum);

        TextField t3 = new TextField(null, 30);
        t3.setBounds(180, 250, 400, 30);
        createhospital.add(t3);

        JButton button = new JButton("Submit");
        button.setFont(new Font("Agency FB", Font.BOLD, 20));
        button.setBounds(200, 500, 150, 80);
        createhospital.add(button);
        button.addActionListener(e -> {
            String input1 = t1.getText();
            String input2 = t2.getText();
            String input3 = t3.getText();
            if (!input1.isEmpty() && !input2.isEmpty() && !input3.isEmpty()) {
                file.ADD_DATA("hospitals.txt", input1, input2, input3);
                JOptionPane.showMessageDialog(createhospital, "Hospital data submitted", "Message",
                        JOptionPane.INFORMATION_MESSAGE);
                cardLayout.show(AdminPage.this.getContentPane(), "Main Panel");
            } else {
                JOptionPane.showMessageDialog(createhospital, "Please ensure that all details are written.",
                        "Input Error Message", JOptionPane.WARNING_MESSAGE);
            }
        });
        return createhospital;
    }

    private JPanel searchHospitalPanel() {
        JPanel searchhospital = new JPanel(null);
        searchpanel(searchhospital, "hospitals.txt", "Hospital Search", "Hospital Panel");
        return searchhospital;
    }

    private JPanel SupplierPanel() {
        JPanel SupplierPanel = new JPanel(null);

        createtitle(SupplierPanel, "Supplier Menu");
        backbutton(SupplierPanel, "Main Panel");

        for (int i = 0; i < 2; i++) {
            JButton button = new JButton(new String[] { "CREATE SUPPLIER", "SEARCH SUPPLIER" }[i]);
            button.setFont(new Font("Agency FB", Font.BOLD, 20));
            button.setBounds(250, 100 + i * 100, 150, 80);
            button.addActionListener(e -> {
                switch (e.getActionCommand()) {
                    case "CREATE SUPPLIER" -> cardLayout.show(AdminPage.this.getContentPane(), "Create Supplier");
                    case "SEARCH SUPPLIER" -> cardLayout.show(AdminPage.this.getContentPane(), "Search Supplier");
                }
            });
            SupplierPanel.add(button);
        }
        return SupplierPanel;
    }

    private JPanel createSupplierPanel() {
        JPanel createsupplier = new JPanel(null);

        createtitle(createsupplier, "New Supplier Form");
        backbutton(createsupplier, "Supplier Panel");

        JLabel sname = new JLabel("Name:");
        sname.setBounds(100, 150, 80, 30);
        createsupplier.add(sname);

        TextField t1 = new TextField(null, 30);
        t1.setBounds(200, 150, 400, 30);
        createsupplier.add(t1);

        Label saddress = new Label("Address:");
        saddress.setBounds(100, 200, 80, 30);
        createsupplier.add(saddress);

        TextField t2 = new TextField(null, 30);
        t2.setBounds(200, 200, 400, 30);
        createsupplier.add(t2);

        Label sphonenum = new Label("Phone Number:");
        sphonenum.setBounds(100, 250, 80, 30);
        createsupplier.add(sphonenum);

        TextField t3 = new TextField(null, 30);
        t3.setBounds(200, 250, 400, 30);
        createsupplier.add(t2);

        JButton button = new JButton("Submit");
        button.setFont(new Font("Agency FB", Font.BOLD, 20));
        button.setBounds(200, 500, 150, 80);
        createsupplier.add(button);
        button.addActionListener(e -> {
            String input1 = t1.getText();
            String input2 = t2.getText();
            String input3 = t3.getText();
            if (!input1.isEmpty() && !input2.isEmpty()) {
                file.ADD_DATA("suppliers.txt", input1, input2, input3);
                JOptionPane.showMessageDialog(createsupplier, "Supplier data submitted", "Message",
                        JOptionPane.INFORMATION_MESSAGE);
                cardLayout.show(AdminPage.this.getContentPane(), "Main Panel");
            } else {
                JOptionPane.showMessageDialog(createsupplier, "Please ensure that all details are written.",
                        "Input Error Message", JOptionPane.WARNING_MESSAGE);
            }
        });
        return createsupplier;
    }

    private JPanel searchSupplierPanel() {
        JPanel searchsupplier = new JPanel(null);
        searchpanel(searchsupplier, "suppliers.txt", "Supplier Search", "Supplier Panel");
        return searchsupplier;
    }

    private JPanel UserPanel() {
        JPanel UserPanel = new JPanel(null);

        createtitle(UserPanel, "User Menu");
        backbutton(UserPanel, "Main Panel");

        for (int i = 0; i < 2; i++) {
            JButton button = new JButton(new String[] { "CREATE USER", "SEARCH USER" }[i]);
            button.setFont(new Font("Agency FB", Font.BOLD, 20));
            button.setBounds(250, 100 + i * 100, 150, 80);
            button.addActionListener(e -> {
                switch (e.getActionCommand()) {
                    case "CREATE USER" -> cardLayout.show(AdminPage.this.getContentPane(), "Create User");
                    case "SEARCH USER" -> cardLayout.show(AdminPage.this.getContentPane(), "Search User");
                }
            });
            UserPanel.add(button);
        }
        return UserPanel;
    }

    private JPanel createUserPanel() {
        JPanel createUser = new JPanel(null);

        createtitle(createUser, "New User Form");
        backbutton(createUser, "User Panel");

        JLabel uname = new JLabel("Name:");
        uname.setBounds(100, 150, 80, 30);
        createUser.add(uname);

        TextField t1 = new TextField(null, 30);
        t1.setBounds(200, 150, 400, 30);
        createUser.add(t1);

        Label upassword = new Label("Password:");
        upassword.setBounds(100, 200, 80, 30);
        createUser.add(upassword);

        TextField t2 = new TextField(null, 30);
        t2.setBounds(200, 200, 400, 30);
        createUser.add(t2);

        Label uusertype = new Label("User Type:");
        uusertype.setBounds(100, 250, 80, 30);
        createUser.add(uusertype);

        Choice t3 = new Choice();
        t3.setBounds(200, 250, 400, 30);
        t3.add("Admin");
        t3.add("Staff");
        createUser.add(t3);

        JButton button = new JButton("Submit");
        button.setFont(new Font("Agency FB", Font.BOLD, 20));
        button.setBounds(200, 500, 150, 80);
        createUser.add(button);
        button.addActionListener(e -> {
            String input1 = t1.getText();
            String input2 = t2.getText();
            String input3 = t3.getSelectedItem();
            if (!input1.isEmpty() && !input2.isEmpty() && !input3.isEmpty()) {
                file.ADD_DATA("users.txt", input1, input2, input3);
                JOptionPane.showMessageDialog(createUser, "User data submitted", "Message",
                        JOptionPane.INFORMATION_MESSAGE);
                cardLayout.show(AdminPage.this.getContentPane(), "Main Panel");
            } else {
                JOptionPane.showMessageDialog(createUser, "Please ensure that all details are written.",
                        "Input Error Message", JOptionPane.WARNING_MESSAGE);
            }
        });
        return createUser;
    }

    private JPanel searchUserPanel() {
        JPanel searchuser = new JPanel(null);
        searchpanel(searchuser, "users.txt", "User Search", "User Panel");
        return searchuser;
    }

    private JPanel ppePanel() {
        JPanel ppePanel = new JPanel(null);

        createtitle(ppePanel, "PPE Item Menu");
        backbutton(ppePanel, "Main Panel");

        for (int i = 0; i < 2; i++) {
            JButton button = new JButton(new String[] { "CREATE ITEM", "SEARCH ITEM" }[i]);
            button.setFont(new Font("Agency FB", Font.BOLD, 20));
            button.setBounds(250, 100 + i * 100, 150, 80);
            button.addActionListener(e -> {
                switch (e.getActionCommand()) {
                    case "CREATE ITEM" -> cardLayout.show(AdminPage.this.getContentPane(), "Create Item");
                    case "SEARCH ITEM" -> cardLayout.show(AdminPage.this.getContentPane(), "Search Item");
                }
            });
            ppePanel.add(button);
        }
        return ppePanel;
    }

    private JPanel createppePanel() {
        JPanel createppe = new JPanel(null);

        createtitle(createppe, "New PPE Item Form");
        backbutton(createppe, "ppe Panel");

        JLabel pcode = new JLabel("Item Code:");
        pcode.setBounds(100, 150, 80, 30);
        createppe.add(pcode);

        TextField t1 = new TextField(null, 30);
        t1.setBounds(200, 150, 400, 30);
        createppe.add(t1);

        Label pname = new Label("Item Name:");
        pname.setBounds(100, 200, 80, 30);
        createppe.add(pname);

        TextField t2 = new TextField(null, 30);
        t2.setBounds(200, 200, 400, 30);
        createppe.add(t2);

        Label scode = new Label("Supplier Code:");
        scode.setBounds(100, 250, 80, 30);
        createppe.add(scode);

        Choice t3 = new Choice();
        List<String> allID = file.GET_ALL_ID("suppliers.txt");
        t3.setBounds(200, 250, 400, 30);
        for (String id : allID) {
            t3.add(id);
        }
        createppe.add(t3);

        Label pquantity = new Label("Quantity:    100");
        pquantity.setBounds(100, 300, 80, 30);
        createppe.add(pquantity);

        JButton button = new JButton("Submit");
        button.setFont(new Font("Agency FB", Font.BOLD, 20));
        button.setBounds(200, 500, 150, 80);
        createppe.add(button);
        button.addActionListener(e -> {
            String input1 = t1.getText();
            String input2 = t2.getText();
            String input3 = t3.getSelectedItem();
            String input4 = "100";
            if (!input1.isEmpty() && !input2.isEmpty() && !input3.isEmpty()) {
                file.ADD_DATA("ppe.txt", input1, input2, input3, input4);
                JOptionPane.showMessageDialog(createppe, "PPE item data submitted", "Message",
                        JOptionPane.INFORMATION_MESSAGE);
                cardLayout.show(AdminPage.this.getContentPane(), "Main Panel");
            } else {
                JOptionPane.showMessageDialog(createppe, "Please ensure that all details are written.",
                        "Input Error Message", JOptionPane.WARNING_MESSAGE);
            }
        });
        return createppe;
    }

    private JPanel searchppePanel() {
        JPanel searchppe = new JPanel(null);
        searchpanel(searchppe, "ppe.txt", "Ppe Item Search", "ppe Panel");
        return searchppe;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminPage());
    }
}
