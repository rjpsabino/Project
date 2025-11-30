package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MainMenu extends JFrame {

    public MainMenu() {
       
        setTitle("Barangay Constituents Ledger System (BCLS)"); // Updated Window Title
        setSize(850, 600); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        
        setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBorder(new EmptyBorder(40, 0, 20, 0));

     
        JLabel lblTitle = new JLabel("Barangay Constituents Ledger System (BCLS)");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 36)); 
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT); 
        
        JLabel lblTagline = new JLabel("Reliable records for reliable assistance.");
        lblTagline.setFont(new Font("Arial", Font.ITALIC, 20)); 
        lblTagline.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(lblTitle);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 10))); 
        headerPanel.add(lblTagline);

        add(headerPanel, BorderLayout.NORTH);

        JPanel centerWrapper = new JPanel(new GridBagLayout());

        JPanel buttonGrid = new JPanel();
        buttonGrid.setLayout(new GridLayout(2, 2, 20, 20)); 
        buttonGrid.setPreferredSize(new Dimension(600, 200));

        JButton btnRegister = createStyledButton("Register Constituent"); 
        JButton btnRecords = createStyledButton("Master Ledger");      
        JButton btnCensus = createStyledButton("Census by Purok");
        JButton btnExit = createStyledButton("Exit System");

        buttonGrid.add(btnRegister);
        buttonGrid.add(btnRecords);
        buttonGrid.add(btnCensus);
        buttonGrid.add(btnExit);

        centerWrapper.add(buttonGrid);
        add(centerWrapper, BorderLayout.CENTER);

        btnRegister.addActionListener(e -> new RegistrationForm());
        btnRecords.addActionListener(e -> new RecordsWindow());
        btnCensus.addActionListener(e -> new PurokCensusWindow());
        btnExit.addActionListener(e -> System.exit(0));

        setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.PLAIN, 16));
        btn.setFocusPainted(false);
        return btn;
    }
}