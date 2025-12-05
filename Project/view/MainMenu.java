package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MainMenu extends JFrame {

    public MainMenu() {
        setTitle("Barangay Constituents Ledger System (BCLS)");
        setSize(850, 700); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBorder(new EmptyBorder(30, 0, 30, 0)); 
        JLabel lblLogo = new JLabel();
        try {
            ImageIcon originalIcon = new ImageIcon("res/logo.png");
            Image scaledImg = originalIcon.getImage().getScaledInstance(130, 130, Image.SCALE_SMOOTH);
            lblLogo.setIcon(new ImageIcon(scaledImg));
        } catch (Exception e) {
           
        }
        lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
    
        JLabel lblTitle = new JLabel("Barangay Constituents Ledger System (BCLS)");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 32));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblTagline = new JLabel("Empowering barangay service through precise data.");
        lblTagline.setFont(new Font("Arial", Font.ITALIC, 16));
        lblTagline.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(lblLogo);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 20))); 
        headerPanel.add(lblTitle);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 10))); 
        headerPanel.add(lblTagline);
        add(headerPanel, BorderLayout.NORTH);

        JPanel mainButtonContainer = new JPanel(new GridBagLayout());
        mainButtonContainer.setBorder(new EmptyBorder(20, 50, 50, 50)); 

        JPanel gridPanel = new JPanel(new GridLayout(2, 2, 20, 20)); 
        
        JButton btnRegister = createStyledButton("Register Constituent");
        JButton btnRecords = createStyledButton("Master Ledger");
        JButton btnCensus = createStyledButton("Census by Purok");
        JButton btnReports = createStyledButton("Category Reports");

        gridPanel.add(btnRegister);
        gridPanel.add(btnRecords);
        gridPanel.add(btnCensus);
        gridPanel.add(btnReports);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; 
        gbc.gridy = 0;
        
        mainButtonContainer.add(gridPanel, gbc);

 
        JButton btnExit = createStyledButton("Exit System");
   
        btnExit.setPreferredSize(new Dimension(0, 60)); 
        
        gbc.gridy = 1; 
        gbc.insets = new Insets(20, 0, 0, 0); 
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        mainButtonContainer.add(btnExit, gbc);

        add(mainButtonContainer, BorderLayout.CENTER);

        btnRegister.addActionListener(e -> new RegistrationForm());
        btnRecords.addActionListener(e -> new RecordsWindow());
        btnCensus.addActionListener(e -> new PurokCensusWindow());
        btnReports.addActionListener(e -> new CategoryReportWindow());
        btnExit.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to exit the system?",
                "Confirm Exit", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.PLAIN, 18)); 
        btn.setFocusPainted(false);
        
        btn.setPreferredSize(new Dimension(250, 80)); 
        return btn;
    }
}