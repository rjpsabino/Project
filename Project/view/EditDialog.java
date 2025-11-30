package view;

import controller.BeneficiaryController;
import javax.swing.*;
import java.awt.*;

public class EditDialog extends JDialog {
  
    public EditDialog(JFrame parent, int id, String currentName, String currentContact, String currentAddress, String currentDob, double currentIncome, String currentStatus, BeneficiaryController controller) {
        super(parent, "BCLS - Edit Record", true);
        setSize(450, 550);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(7, 2, 10, 10));

        JTextField txtName = new JTextField(currentName);
        JTextField txtContact = new JTextField(currentContact);
        JTextField txtAddress = new JTextField(currentAddress);
        JTextField txtDob = new JTextField(currentDob);         
        JTextField txtIncome = new JTextField(String.valueOf(currentIncome));
        
        String[] statuses = {"Extreme Poverty", "Unemployed", "Single Parent", "Disaster Victim"};
        JComboBox<String> cmbStatus = new JComboBox<>(statuses);
        cmbStatus.setSelectedItem(currentStatus);

        add(new JLabel("  Full Name:"));      add(txtName);
        add(new JLabel("  Contact Number:")); add(txtContact);
        add(new JLabel("  Address:"));        add(txtAddress);
        add(new JLabel("  Date of Birth:"));  add(txtDob);
        add(new JLabel("  Daily Income (₱):")); add(txtIncome);
        add(new JLabel("  Status:"));         add(cmbStatus);

        JButton btnUpdate = new JButton("Update Record");
        JButton btnCancel = new JButton("Cancel");
        add(btnUpdate); add(btnCancel);

        btnUpdate.addActionListener(e -> {
            try {
                boolean success = controller.updateBeneficiary(
                    id, 
                    txtName.getText(), 
                    txtContact.getText(), 
                    txtAddress.getText(), 
                    txtDob.getText(), 
                    Double.parseDouble(txtIncome.getText()), 
                    (String) cmbStatus.getSelectedItem()
                );
                
                if (success) {
                    JOptionPane.showMessageDialog(this, "Record Updated Successfully!");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Update Failed.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Income must be a valid number.");
            }
        });

        btnCancel.addActionListener(e -> dispose());
        setVisible(true);
    }
}