package view;

import controller.BeneficiaryController;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CategoryReportWindow extends JFrame {
    
    private JTable table;
    private DefaultTableModel tableModel;
    private BeneficiaryController controller;
    private JComboBox<String> cmbCategory;
    private JComboBox<String> cmbPurok; 

    public CategoryReportWindow() {
        controller = new BeneficiaryController();
        
        setTitle("BCLS - Category & Service Reports");
        setSize(1200, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());


        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        topPanel.setBorder(new EmptyBorder(10, 0, 10, 0));

        JLabel lblCat = new JLabel("Category:");
        lblCat.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        String[] categories = {
            "4Ps Members",
            "Person with Disability (PWD)",
            "Senior Citizen",
            "Solo Parent",
            "Homeless",
            "Extreme Poverty",
            "Calamity Victim",
            "Medical Assistance",
            "Food Packs",
            "Calamity Assistance",
            "Livelihood Assistance",
            "Burial Assistance"
        };
        cmbCategory = new JComboBox<>(categories);
        cmbCategory.setPreferredSize(new Dimension(220, 30));

        JLabel lblPurok = new JLabel("   Filter by Purok:");
        lblPurok.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        String[] puroks = {"All Puroks", "Sto. Nino", "Salazar", "Magsaysay", "Capitol", "Nazareno", "Hillside", "Magapo", "Don Luis Village", "Madang", "Lemente", "Calagundian", "Belsonda", "Riverside", "Chan Village", "Bilawan", "Tagawisan"};
        cmbPurok = new JComboBox<>(puroks);
        cmbPurok.setPreferredSize(new Dimension(150, 30));

        JButton btnGenerate = new JButton("Generate Report");
        btnGenerate.setBackground(new Color(52, 152, 219)); 
        btnGenerate.setForeground(Color.WHITE);
        btnGenerate.setPreferredSize(new Dimension(150, 30));

        topPanel.add(lblCat);
        topPanel.add(cmbCategory);
        topPanel.add(lblPurok);
        topPanel.add(cmbPurok);
        topPanel.add(btnGenerate);
        add(topPanel, BorderLayout.NORTH);

        String[] columns = {"ID", "Name", "Contact", "Address", "Birthday", "Income", "Status", "Service", "4Ps?", "Remarks"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        table.setRowHeight(25);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        
        JButton btnExport = new JButton("Export Report to CSV");
        btnExport.setBackground(new Color(46, 204, 113));
        btnExport.setForeground(Color.WHITE);
        btnExport.setPreferredSize(new Dimension(200, 35));

        JButton btnClose = new JButton("Close Window");
        btnClose.setPreferredSize(new Dimension(130, 35));
        
        bottomPanel.add(btnExport);
        bottomPanel.add(btnClose);
        add(bottomPanel, BorderLayout.SOUTH);

        btnGenerate.addActionListener(e -> {
            String selectedCat = (String) cmbCategory.getSelectedItem();
            String selectedPurok = (String) cmbPurok.getSelectedItem();
            
            controller.filterByCategoryAndPurok(tableModel, selectedCat, selectedPurok);
            
            if (tableModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "No records found for: " + selectedCat + " in " + selectedPurok);
            }
        });

        btnExport.addActionListener(e -> {
            if (tableModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "No data to export. Generate a report first.");
                return;
            }

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save Report");
            
            String selectedCat = ((String) cmbCategory.getSelectedItem()).replace(" ", "_");
            String selectedPurok = ((String) cmbPurok.getSelectedItem()).replace(" ", "_");
            
            fileChooser.setSelectedFile(new File("Report_" + selectedCat + "_" + selectedPurok + ".csv"));

            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                String path = fileChooser.getSelectedFile().getAbsolutePath();
                if (!path.endsWith(".csv")) path += ".csv";
                exportTableData(path); 
            }
        });

        btnClose.addActionListener(e -> dispose());
        setVisible(true);
    }

    private void exportTableData(String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
      
            for (int i = 0; i < tableModel.getColumnCount(); i++) {
                writer.append(tableModel.getColumnName(i));
                if (i < tableModel.getColumnCount() - 1) writer.append(",");
            }
            writer.append("\n");

            for (int i = 0; i < tableModel.getRowCount(); i++) {
                for (int j = 0; j < tableModel.getColumnCount(); j++) {
                    Object cellValue = tableModel.getValueAt(i, j);
                    String text = (cellValue == null) ? "" : cellValue.toString();
                    text = text.replace(",", " "); 
                    writer.append(text);
                    if (j < tableModel.getColumnCount() - 1) writer.append(",");
                }
                writer.append("\n");
            }
            JOptionPane.showMessageDialog(this, "Report Exported Successfully!\nSaved to: " + filePath);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error saving file: " + ex.getMessage());
        }
    }
}