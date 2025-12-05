package view;

import controller.BeneficiaryController;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class EditDialog extends JDialog {
    
    private final Font mainFont = new Font("Segoe UI", Font.PLAIN, 14);

    public EditDialog(JFrame parent, int id, String name, String contact, String address, String dob, double income, String status, String service, String is4ps, String remarks, BeneficiaryController controller) {
        super(parent, "BCLS - Edit & Validate", true);
        setSize(550, 650);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(new EmptyBorder(10, 20, 10, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 5, 3, 5); 
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        JTextField txtName = createTextField(name);
        JTextField txtContact = createTextField(contact);
        JTextField txtAddress = createTextField(address);
        JTextField txtDob = createTextField(dob);
        JTextField txtIncome = createTextField(String.valueOf(income));
        
        String[] statuses = {"Extreme Poverty", "Indigent", "Disaster Victim", "Person with Disability (PWD)", "Senior Citizen", "Solo Parent"};
        JComboBox<String> cmbStatus = createComboBox(statuses);
        cmbStatus.setSelectedItem(status);

        String[] serviceList = {"None", "Medical Assistance", "Calamity Assistance", "Livelihood Assistance", "Food Packs", "Burial Assistance"};
        JComboBox<String> cmbService = createComboBox(serviceList);
        cmbService.setSelectedItem(service);

        String[] options4ps = {"No", "Yes"};
        JComboBox<String> cmb4Ps = createComboBox(options4ps);
        cmb4Ps.setSelectedItem(is4ps);

        JTextField txtRemarks = createTextField(remarks);

        addLabel(formPanel, gbc, 0, "Name:");         addInput(formPanel, gbc, 0, txtName);
        addLabel(formPanel, gbc, 1, "Contact:");      addInput(formPanel, gbc, 1, txtContact);
        addLabel(formPanel, gbc, 2, "Address:");      addInput(formPanel, gbc, 2, txtAddress);
        addLabel(formPanel, gbc, 3, "Birthday:");     addInput(formPanel, gbc, 3, txtDob);
        addLabel(formPanel, gbc, 4, "Income (₱):");   addInput(formPanel, gbc, 4, txtIncome);
        addLabel(formPanel, gbc, 5, "Status:");       addInput(formPanel, gbc, 5, cmbStatus);
        addLabel(formPanel, gbc, 6, "Service:");      addInput(formPanel, gbc, 6, cmbService);
        addLabel(formPanel, gbc, 7, "Is 4Ps?");       addInput(formPanel, gbc, 7, cmb4Ps);
        addLabel(formPanel, gbc, 8, "Remarks:");      addInput(formPanel, gbc, 8, txtRemarks);

        add(new JScrollPane(formPanel), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnUpdate = new JButton("Update");
        JButton btnCancel = new JButton("Cancel");
        Dimension btnSize = new Dimension(100, 35);
        btnUpdate.setPreferredSize(btnSize);
        btnCancel.setPreferredSize(btnSize);
        
        buttonPanel.add(btnUpdate); buttonPanel.add(btnCancel);
        add(buttonPanel, BorderLayout.SOUTH);

        cmb4Ps.addActionListener(e -> {
            if(cmb4Ps.getSelectedItem().equals("Yes")) {
                txtRemarks.setText("NOT QUALIFIED (Existing 4Ps Member)");
                txtRemarks.setForeground(Color.RED);
            } else {
                txtRemarks.setText("Approved / Qualified");
                txtRemarks.setForeground(Color.BLACK);
            }
        });

        btnUpdate.addActionListener(e -> {
            try {
                boolean success = controller.updateBeneficiary(
                    id, txtName.getText(), txtContact.getText(), txtAddress.getText(), 
                    txtDob.getText(), Double.parseDouble(txtIncome.getText()), 
                    (String) cmbStatus.getSelectedItem(), 
                    (String) cmbService.getSelectedItem(),
                    (String) cmb4Ps.getSelectedItem(), 
                    txtRemarks.getText()              
                );
                if(success) { JOptionPane.showMessageDialog(this, "Updated!"); dispose(); }
                else { JOptionPane.showMessageDialog(this, "Failed."); }
            } catch(Exception ex) { JOptionPane.showMessageDialog(this, "Error inputs."); }
        });

        btnCancel.addActionListener(e -> dispose());
        setVisible(true);
    }

    private void addLabel(JPanel p, GridBagConstraints gbc, int row, String text) {
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0.3;
        JLabel lbl = new JLabel(text); lbl.setFont(mainFont);
        p.add(lbl, gbc);
    }
    private void addInput(JPanel p, GridBagConstraints gbc, int row, Component comp) {
        gbc.gridx = 1; gbc.gridy = row; gbc.weightx = 0.7;
        p.add(comp, gbc);
    }
    private JTextField createTextField(String text) {
        JTextField tf = new JTextField(text); tf.setPreferredSize(new Dimension(200, 30)); tf.setFont(mainFont);
        return tf;
    }
    private JComboBox<String> createComboBox(String[] items) {
        JComboBox<String> cb = new JComboBox<>(items); cb.setPreferredSize(new Dimension(200, 30)); cb.setFont(mainFont); cb.setBackground(Color.WHITE);
        return cb;
    }
}