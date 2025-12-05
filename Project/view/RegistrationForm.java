package view;

import controller.BeneficiaryController;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class RegistrationForm extends JFrame {
    
    private BeneficiaryController controller = new BeneficiaryController();
    private JTextField txtName, txtContact, txtBarangay, txtCity, txtDob, txtIncome, txtRemarks;
    private JComboBox<String> cmbPurok, cmbStatus, cmbServices, cmb4Ps;
    private JButton btnSave, btnClear, btnViewRecords, btnClose; 

    private final Font mainFont = new Font("Segoe UI", Font.PLAIN, 14);

    public RegistrationForm() {
        setTitle("BCLS - New Constituent Entry");
        
        setSize(650, 650); 
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        
        JLabel lblTitle = new JLabel("Constituent Registration", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setBorder(new EmptyBorder(10, 0, 5, 0)); 
        add(lblTitle, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(new EmptyBorder(5, 20, 5, 20)); 
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.insets = new Insets(3, 5, 3, 5); 
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHWEST; 

        addLabel(formPanel, gbc, 0, "Full Name:");
        txtName = createTextField();
        addInput(formPanel, gbc, 0, txtName);

        addLabel(formPanel, gbc, 1, "Contact:");
        txtContact = createTextField(); txtContact.setText("+63");
        addInput(formPanel, gbc, 1, txtContact);

        addLabel(formPanel, gbc, 2, "Purok:");
        String[] puroks = { "Sto. Nino", "Salazar", "Magsaysay", "Capitol", "Nazareno", "Hillside", "Magapo", "Don Luis Village", "Madang", "Lemente", "Calagundian", "Belsonda", "Riverside", "Chan Village", "Bilawan", "Tagawisan"};
        cmbPurok = createComboBox(puroks);
        addInput(formPanel, gbc, 2, cmbPurok);

        addLabel(formPanel, gbc, 3, "Barangay:");
        txtBarangay = createTextField(); txtBarangay.setText("Central");
        addInput(formPanel, gbc, 3, txtBarangay);

        addLabel(formPanel, gbc, 4, "City:");
        txtCity = createTextField(); txtCity.setText("Mati City");
        addInput(formPanel, gbc, 4, txtCity);

        addLabel(formPanel, gbc, 5, "Birthday:");
        JPanel dobPanel = new JPanel(new BorderLayout());
        txtDob = createTextField(); txtDob.setEditable(false);
        JButton btnCal = new JButton("Select");
        btnCal.setPreferredSize(new Dimension(80, 28)); 
        dobPanel.add(txtDob, BorderLayout.CENTER); dobPanel.add(btnCal, BorderLayout.EAST);
        addInput(formPanel, gbc, 5, dobPanel);

        addLabel(formPanel, gbc, 6, "Income (₱):");
        txtIncome = createTextField();
        addInput(formPanel, gbc, 6, txtIncome);

        addLabel(formPanel, gbc, 7, "Is 4Ps Member?");
        String[] options4ps = {"No", "Yes"};
        cmb4Ps = createComboBox(options4ps);
        addInput(formPanel, gbc, 7, cmb4Ps);

        addLabel(formPanel, gbc, 8, "Status:");
        String[] statuses = {"Extreme Poverty", "Indigent", "Disaster Victim", "Person with Disability (PWD)", "Senior Citizen", "Solo Parent"};
        cmbStatus = createComboBox(statuses);
        addInput(formPanel, gbc, 8, cmbStatus);

        addLabel(formPanel, gbc, 9, "Service:");
        String[] serviceList = {"None", "Medical Assistance", "Calamity Assistance", "Livelihood Assistance", "Food Packs", "Burial Assistance"};
        cmbServices = createComboBox(serviceList);
        addInput(formPanel, gbc, 9, cmbServices);

        addLabel(formPanel, gbc, 10, "Remarks:");
        txtRemarks = createTextField();
        txtRemarks.setText("Pending Validation...");
        txtRemarks.setBackground(new Color(245, 245, 245));
        addInput(formPanel, gbc, 10, txtRemarks);

        gbc.gridy = 11;
        gbc.weighty = 1.0; 
        formPanel.add(new JLabel(), gbc);


        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBorder(new EmptyBorder(5, 0, 10, 0));

        btnSave = new JButton("Save");
        btnClear = new JButton("Clear");
        btnViewRecords = new JButton("View Ledger");
        btnClose = new JButton("Close"); 

        Dimension btnSize = new Dimension(110, 35);
        btnSave.setPreferredSize(btnSize);
        btnClear.setPreferredSize(btnSize);
        btnViewRecords.setPreferredSize(btnSize);
        btnClose.setPreferredSize(btnSize);

        buttonPanel.add(btnSave);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnViewRecords);
        buttonPanel.add(btnClose);

        add(buttonPanel, BorderLayout.SOUTH);

    
        cmb4Ps.addActionListener(e -> {
            if(cmb4Ps.getSelectedItem().equals("Yes")) {
                txtRemarks.setText("NOT QUALIFIED (Existing 4Ps Member)");
                txtRemarks.setForeground(Color.RED);
                cmbServices.setSelectedItem("None");
                cmbServices.setEnabled(false);
            } else {
                txtRemarks.setText("Approved / Qualified");
                txtRemarks.setForeground(new Color(0, 100, 0));
                cmbServices.setEnabled(true);
            }
        });

        cmbStatus.addActionListener(e -> {
            if (!cmbServices.isEnabled()) return;
            String status = (String) cmbStatus.getSelectedItem();
            if (status.equals("Calamity Victim")) cmbServices.setSelectedItem("Calamity Assistance");
            else if (status.equals("Homeless") || status.equals("Extreme Poverty")) cmbServices.setSelectedItem("Food Packs");
            else if (status.equals("Senior Citizen") || status.equals("Person with Disability (PWD)")) cmbServices.setSelectedItem("Medical Assistance");
            else if (status.equals("Solo Parent")) cmbServices.setSelectedItem("Livelihood Assistance");
            else cmbServices.setSelectedItem("None");
        });

        btnCal.addActionListener(e -> {
            DatePicker picker = new DatePicker(this);
            String result = picker.setPickedDate();
            if(!result.isEmpty()) txtDob.setText(result);
        });

        btnSave.addActionListener(e -> saveData());
        btnClear.addActionListener(e -> clearFields());
        btnViewRecords.addActionListener(e -> new RecordsWindow());
        btnClose.addActionListener(e -> dispose()); 
        
        setVisible(true);
    }

    private void addLabel(JPanel p, GridBagConstraints gbc, int row, String text) {
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0.3; gbc.weighty = 0;
        JLabel lbl = new JLabel(text); lbl.setFont(mainFont);
        p.add(lbl, gbc);
    }
    private void addInput(JPanel p, GridBagConstraints gbc, int row, Component comp) {
        gbc.gridx = 1; gbc.gridy = row; gbc.weightx = 0.7; gbc.weighty = 0;
        p.add(comp, gbc);
    }
    private JTextField createTextField() {
        JTextField tf = new JTextField(); 
        tf.setPreferredSize(new Dimension(200, 28)); 
        tf.setFont(mainFont);
        return tf;
    }
    private JComboBox<String> createComboBox(String[] items) {
        JComboBox<String> cb = new JComboBox<>(items); 
        cb.setPreferredSize(new Dimension(200, 28)); 
        cb.setFont(mainFont); 
        cb.setBackground(Color.WHITE);
        return cb;
    }

    private void saveData() {
   
        String fullName = txtName.getText().trim();
        String contact = txtContact.getText().trim();
        String dob = txtDob.getText().trim();
        String incomeText = txtIncome.getText().trim();

        if (fullName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Full Name cannot be empty.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] nameParts = fullName.split("\\s+");
        if (nameParts.length < 2) {
            JOptionPane.showMessageDialog(this, "Full Name must consist of First Name and Last Name.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!fullName.matches("^[a-zA-Z\\s]+$")) {
            JOptionPane.showMessageDialog(this, "Full Name must contain only letters and spaces. Numbers and special characters are not allowed.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (contact.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Contact number cannot be empty.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!contact.matches("^\\+63\\d{10}$")) {
            JOptionPane.showMessageDialog(this, "Contact must be in Philippine format: +63 followed by 10 digits (e.g., +63 9123456789).", "Validation Error", JOptionPane.ERROR_MESSAGE);
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
                JOptionPane.showMessageDialog(this, "Daily Income cannot be negative.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Daily Income must be a valid number.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // All validations passed, proceed with save
        try {
            String fullAddress = cmbPurok.getSelectedItem() + ", " + txtBarangay.getText() + ", " + txtCity.getText();
            
            boolean success = controller.registerBeneficiary(
                fullName, contact, fullAddress, dob, 
                income, (String)cmbStatus.getSelectedItem(), (String)cmbServices.getSelectedItem(),
                (String)cmb4Ps.getSelectedItem(), txtRemarks.getText()
            );

            if(success) {
                JOptionPane.showMessageDialog(this, "Saved Successfully!");
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to save.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error during save: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        txtName.setText(""); txtContact.setText("+63"); txtDob.setText(""); txtIncome.setText("");
        cmbServices.setSelectedIndex(0); cmb4Ps.setSelectedIndex(0);
        txtRemarks.setText("Pending Validation...");
        cmbServices.setEnabled(true);
    }
}