package view;

import controller.BeneficiaryController;
import javax.swing.*;
import java.awt.*;

public class RegistrationForm extends JFrame {
    
    private BeneficiaryController controller = new BeneficiaryController();
    private JTextField txtName, txtContact, txtBarangay, txtCity, txtDob, txtIncome;
    private JComboBox<String> cmbPurok, cmbStatus;
    private JButton btnSave, btnClear, btnViewRecords;

    public RegistrationForm() {
        setTitle("Benificiary Registration - BCLS");
        setSize(500, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        setLayout(new GridLayout(11, 2, 10, 10)); 

        add(new JLabel("  Full Name :"));
        txtName = new JTextField();
        add(txtName);

        add(new JLabel("  Contact :"));
        txtContact = new JTextField("+63");
        add(txtContact);
        
        add(new JLabel("  Purok:"));
        String[] purokList = {
            "Don Luis Village", "Magapo", "Lemente", "Riverside", "Sto. Niño", 
            "Magsaysay", "Capitol", "Bilawan", "Belsonda", "Hillside"
        };
        cmbPurok = new JComboBox<>(purokList);
        add(cmbPurok);

        add(new JLabel("  Barangay:"));
        txtBarangay = new JTextField("Central"); 
        add(txtBarangay);

        add(new JLabel("  City / Municipality:"));
        txtCity = new JTextField("Mati City"); 
        add(txtCity);

        add(new JLabel("  Date of Birth (MM-dd-yyyy):"));
        JPanel dobPanel = new JPanel(new BorderLayout());
        txtDob = new JTextField();
        txtDob.setEditable(false);
        JButton btnCalendar = new JButton("Select");
        dobPanel.add(txtDob, BorderLayout.CENTER);
        dobPanel.add(btnCalendar, BorderLayout.EAST);
        add(dobPanel);

        add(new JLabel("  Daily Income (₱):"));
        txtIncome = new JTextField();
        add(txtIncome);

        add(new JLabel(" Status:"));
        String[] statuses = {"Extreme Poverty", "Unemployed", "Single Parent","Senior Citizen" , "Calamity Victim"};
        cmbStatus = new JComboBox<>(statuses);
        add(cmbStatus);

        btnSave = new JButton("Save Record");
        btnClear = new JButton("Clear");
        btnViewRecords = new JButton("View Records");
        
        add(btnSave);
        add(btnClear);
        add(btnViewRecords);
        add(new JLabel("")); 

        btnCalendar.addActionListener(e -> {
            DatePicker picker = new DatePicker(this);
            String result = picker.setPickedDate();
            if(!result.isEmpty()) {
                txtDob.setText(result);
            }
        });

        btnSave.addActionListener(e -> saveData());
        btnClear.addActionListener(e -> clearFields());
        btnViewRecords.addActionListener(e -> new RecordsWindow());
        
        setVisible(true);
    }

    private void saveData() {
        String name = txtName.getText().trim();
        String contact = txtContact.getText().trim();
        String dob = txtDob.getText().trim();
        String status = (String) cmbStatus.getSelectedItem();
        String incomeText = txtIncome.getText().trim();

        String purok = (String) cmbPurok.getSelectedItem();
        String barangay = txtBarangay.getText().trim();
        String city = txtCity.getText().trim();
        
        String fullAddress = purok + ", " + barangay + ", " + city;

        if (!name.matches("^[a-zA-Z]+(\\s+[a-zA-Z]+)+$")) {
            JOptionPane.showMessageDialog(this, "Name must contain First Name and Last Name.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!contact.matches("^\\+63\\d{10}$")) {
            JOptionPane.showMessageDialog(this, "Contact must be: +63 followed by 10 digits.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (barangay.isEmpty() || city.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Address fields cannot be empty.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (dob.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a Date of Birth.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double income = 0;
        try {
            income = Double.parseDouble(incomeText);
            if (income < 0) {
                 JOptionPane.showMessageDialog(this, "Income cannot be negative.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                 return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Income must be a valid number.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        boolean success = controller.registerBeneficiary(name, contact, fullAddress, dob, income, status);

        if (success) {
            JOptionPane.showMessageDialog(this, "Saved Successfully!");
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to save.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void clearFields() {
        txtName.setText("");
        txtContact.setText("+63");
        cmbPurok.setSelectedIndex(0);
        txtBarangay.setText("Central"); 
        txtCity.setText("Mati City"); 
        txtDob.setText("");
        txtIncome.setText("");
        cmbStatus.setSelectedIndex(0);
    }
}