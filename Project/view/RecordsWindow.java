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
        setTitle("BCLS - Master Ledger");
        setSize(1200, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        setLayout(new BorderLayout());

        String[] columns = {"ID", "Name", "Contact", "Address", "Birthday", "Income", "Status", "Service", "4Ps?", "Remarks"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);

        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton btnRefresh = new JButton("Refresh");
        JButton btnEdit = new JButton("Edit Selected");
        JButton btnDelete = new JButton("Delete Selected");
        JButton btnExport = new JButton("Export CSV");
        JButton btnClose = new JButton("Close");
        
        buttonPanel.add(btnRefresh); buttonPanel.add(btnEdit); buttonPanel.add(btnDelete); buttonPanel.add(btnExport); buttonPanel.add(btnClose);
        add(buttonPanel, BorderLayout.SOUTH);

        controller.loadTableData(tableModel);

        btnEdit.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) return;

            int id = (int) tableModel.getValueAt(row, 0);
            String name = (String) tableModel.getValueAt(row, 1);
            String contact = (String) tableModel.getValueAt(row, 2);
            String address = (String) tableModel.getValueAt(row, 3);
            String dob = (String) tableModel.getValueAt(row, 4);
            double income = (double) tableModel.getValueAt(row, 5);
            String status = (String) tableModel.getValueAt(row, 6);
            String service = (String) tableModel.getValueAt(row, 7);
            String is4ps = (String) tableModel.getValueAt(row, 8);
            String remarks = (String) tableModel.getValueAt(row, 9);

            new EditDialog(this, id, name, contact, address, dob, income, status, service, is4ps, remarks, controller);
            controller.loadTableData(tableModel);
        });

        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if(row != -1) {
                int id = (int) tableModel.getValueAt(row, 0);
                if(JOptionPane.showConfirmDialog(this, "Delete?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    controller.deleteBeneficiary(id);
                    tableModel.removeRow(row);
                }
            }
        });

        btnExport.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save Ledger as CSV");
            fileChooser.setSelectedFile(new java.io.File("Barangay_Ledger.csv"));
            
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                String path = fileChooser.getSelectedFile().getAbsolutePath();
                if(!path.endsWith(".csv")) path += ".csv";

                if (controller.exportToCSV(path)) {
                    JOptionPane.showMessageDialog(this, "Export Successful!\nSaved to: " + path);
                } else {
                    JOptionPane.showMessageDialog(this, "Export Failed.");
                }
            }
        });

        btnRefresh.addActionListener(e -> controller.loadTableData(tableModel));
        btnClose.addActionListener(e -> dispose());
        setVisible(true);
    }
}