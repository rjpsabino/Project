package view;

import controller.BeneficiaryController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class RecordsWindow extends JFrame {
    
    private JTable table;
    private DefaultTableModel tableModel;
    private BeneficiaryController controller;

    public RecordsWindow() {
        controller = new BeneficiaryController();
        
        setTitle("Beneficiary Records - BCLS");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        setLayout(new BorderLayout());

        String[] columns = {"ID", "Name", "Contact", "Address", "Birthday", "Income (₱)", "Status"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        
        JButton btnRefresh = new JButton("Refresh Data");
        JButton btnEdit = new JButton("Edit Selected");   
        JButton btnDelete = new JButton("Delete Selected"); 
        JButton btnClose = new JButton("Close");
        
        buttonPanel.add(btnRefresh);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClose);
        
        add(buttonPanel, BorderLayout.SOUTH);

        controller.loadTableData(tableModel);

        btnEdit.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a row to edit.");
                return;
            }

            try {
               
                int id = (int) tableModel.getValueAt(selectedRow, 0);
                String name = (String) tableModel.getValueAt(selectedRow, 1);
                String contact = (String) tableModel.getValueAt(selectedRow, 2);
                String address = (String) tableModel.getValueAt(selectedRow, 3); 
                String dob = (String) tableModel.getValueAt(selectedRow, 4);     
                double income = (double) tableModel.getValueAt(selectedRow, 5);  
                String status = (String) tableModel.getValueAt(selectedRow, 6);  
    
                new EditDialog(this, id, name, contact, address, dob, income, status, controller);
    
                controller.loadTableData(tableModel);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        btnDelete.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a row to delete.");
                return;
            }

            int id = (int) tableModel.getValueAt(selectedRow, 0);

            int confirm = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to delete ID: " + id + "?", 
                "Confirm Delete", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                if (controller.deleteBeneficiary(id)) {
                    tableModel.removeRow(selectedRow);
                    JOptionPane.showMessageDialog(this, "Record Deleted.");
                } else {
                    JOptionPane.showMessageDialog(this, "Error deleting record.");
                }
            }
        });

        btnRefresh.addActionListener(e -> {
            controller.loadTableData(tableModel);
        });

        btnClose.addActionListener(e -> dispose());

        setVisible(true);
    }
}