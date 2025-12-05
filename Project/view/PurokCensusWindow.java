package view;

import controller.BeneficiaryController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PurokCensusWindow extends JFrame {
    
    private JTable table;
    private DefaultTableModel tableModel;
    private BeneficiaryController controller;
    private JComboBox<String> cmbPurok;

    public PurokCensusWindow() {
        controller = new BeneficiaryController();
        setTitle("BCLS - Census by Purok");
        setSize(1200, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Select Purok: "));
        String[] puroks = {"Sto. Nino", "Salazar", "Magsaysay", "Capitol", "Nazareno", "Hillside", "Magapo", "Don Luis Village", "Madang", "Lemente", "Calagundian", "Belsonda", "Riverside", "Chan Village", "Bilawan", "Tagawisan"};
        cmbPurok = new JComboBox<>(puroks);
        topPanel.add(cmbPurok);

        JButton btnFilter = new JButton("Show Residents");
        topPanel.add(btnFilter);
        add(topPanel, BorderLayout.NORTH);


        String[] columns = {"ID", "Name", "Contact", "Address", "Birthday", "Income", "Status", "Service", "4Ps?", "Remarks"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        JButton btnClose = new JButton("Close");
        bottomPanel.add(btnClose);
        add(bottomPanel, BorderLayout.SOUTH);

        btnFilter.addActionListener(e -> {
            String selected = (String) cmbPurok.getSelectedItem();
            controller.filterByPurok(tableModel, selected);
            if (tableModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "No residents found in " + selected);
            }
        });

        btnClose.addActionListener(e -> dispose());
        setVisible(true);
    }
}