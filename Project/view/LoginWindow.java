package view;

import controller.LoginController;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginWindow extends JFrame {
    
    private LoginController controller = new LoginController();
    private JTextField txtUser;
    private JPasswordField txtPass;
    private JCheckBox chkShowPass; 

    public LoginWindow() {
        setTitle("BCLS - Security Access");
        setSize(450, 600); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel header = new JPanel();
        header.setBackground(new Color(44, 62, 80)); 
        header.setBorder(new EmptyBorder(20, 0, 20, 0));
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));

        JLabel lblLogo = new JLabel();
        try {
            ImageIcon originalIcon = new ImageIcon("res/logo.png"); 
            Image scaledImg = originalIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            lblLogo.setIcon(new ImageIcon(scaledImg));
        } catch (Exception e) {}
        lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblTitle = new JLabel("Official Login");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        header.add(lblLogo);
        header.add(Box.createRigidArea(new Dimension(0, 10)));
        header.add(lblTitle);
        add(header, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(236, 240, 241)); 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        txtUser = new JTextField();
        txtUser.setPreferredSize(new Dimension(300, 60));
        txtUser.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Username"));
        txtUser.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridy = 0; formPanel.add(txtUser, gbc);

        txtPass = new JPasswordField();
        txtPass.setPreferredSize(new Dimension(300, 60));
        txtPass.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Password"));
        txtPass.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        txtPass.setEchoChar('•'); 
        gbc.gridy = 1; formPanel.add(txtPass, gbc);

        JPanel optionsPanel = new JPanel(new BorderLayout());
        optionsPanel.setBackground(new Color(236, 240, 241));
        
        chkShowPass = new JCheckBox("Show Password");
        chkShowPass.setBackground(new Color(236, 240, 241));
        chkShowPass.setFocusPainted(false);
        
        JLabel lblForgot = new JLabel("Forgot Password?");
        lblForgot.setForeground(new Color(52, 152, 219)); 
        lblForgot.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 
        lblForgot.setFont(new Font("Segoe UI", Font.BOLD, 12));

        optionsPanel.add(chkShowPass, BorderLayout.WEST);
        optionsPanel.add(lblForgot, BorderLayout.EAST);
        
        gbc.gridy = 2; 
        gbc.insets = new Insets(0, 10, 10, 10); 
        formPanel.add(optionsPanel, gbc);

        add(formPanel, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        btnPanel.setBackground(new Color(236, 240, 241));
        
        JButton btnLogin = new JButton("Login");
        JButton btnRegister = new JButton("Create Account");

        btnLogin.setBackground(new Color(46, 204, 113)); 
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setPreferredSize(new Dimension(120, 40));

        btnRegister.setBackground(new Color(52, 152, 219));
        btnRegister.setForeground(Color.WHITE);
        btnRegister.setPreferredSize(new Dimension(150, 40));

        btnPanel.add(btnLogin); btnPanel.add(btnRegister);
        add(btnPanel, BorderLayout.SOUTH);


        chkShowPass.addActionListener(e -> {
            if (chkShowPass.isSelected()) {
                txtPass.setEchoChar((char) 0);
            } else {
                txtPass.setEchoChar('•'); 
            }
        });

        lblForgot.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                showForgotPasswordDialog();
            }
        });

        btnLogin.addActionListener(e -> {
            if (controller.login(txtUser.getText(), new String(txtPass.getPassword()))) {
                JOptionPane.showMessageDialog(this, "Welcome!");
                new MainMenu(); dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnRegister.addActionListener(e -> showRegisterDialog());

        setVisible(true);
    }

    private void showForgotPasswordDialog() {
        JDialog d = new JDialog(this, "Reset Password", true);
        d.setSize(400, 250);
        d.setLocationRelativeTo(this);
        d.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;

        JTextField txtResetUser = new JTextField();
        txtResetUser.setPreferredSize(new Dimension(250, 50));
        txtResetUser.setBorder(BorderFactory.createTitledBorder("Enter Username"));

        JPasswordField txtResetPass = new JPasswordField();
        txtResetPass.setPreferredSize(new Dimension(250, 50));
        txtResetPass.setBorder(BorderFactory.createTitledBorder("Enter New Password"));

        JButton btnReset = new JButton("Reset Password");
        btnReset.setBackground(new Color(231, 76, 60));
        btnReset.setForeground(Color.WHITE);
        btnReset.setPreferredSize(new Dimension(250, 40));

        gbc.gridy = 0; d.add(txtResetUser, gbc);
        gbc.gridy = 1; d.add(txtResetPass, gbc);
        gbc.gridy = 2; d.add(btnReset, gbc);

        btnReset.addActionListener(e -> {
            String u = txtResetUser.getText();
            String p = new String(txtResetPass.getPassword());

            if(u.isEmpty() || p.isEmpty()) {
                JOptionPane.showMessageDialog(d, "Please fill all fields.");
                return;
            }

            if(controller.resetPassword(u, p)) {
                JOptionPane.showMessageDialog(d, "Password Reset Successfully! You can now login.");
                d.dispose();
            } else {
                JOptionPane.showMessageDialog(d, "Username not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        d.setVisible(true);
    }

    private void showRegisterDialog() {
        JDialog d = new JDialog(this, "Register New Official", true);
        d.setSize(400, 420);
        d.setLocationRelativeTo(this);
        d.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;

        JTextField newUse = new JTextField();
        newUse.setPreferredSize(new Dimension(250, 50));
        newUse.setBorder(BorderFactory.createTitledBorder("New Username"));
        
        JPasswordField newPass = new JPasswordField();
        newPass.setPreferredSize(new Dimension(250, 50));
        newPass.setBorder(BorderFactory.createTitledBorder("New Password"));

        JTextField newEmail = new JTextField();
        newEmail.setPreferredSize(new Dimension(250, 50));
        newEmail.setBorder(BorderFactory.createTitledBorder("Email"));

        String[] roles = {"Barangay Secretary", "Barangay Treasurer", "Clerk", "Admin"};
        JComboBox<String> cmbRoles = new JComboBox<>(roles);
        cmbRoles.setPreferredSize(new Dimension(250, 50));
        cmbRoles.setBorder(BorderFactory.createTitledBorder("Role"));

        JButton btnSave = new JButton("Sign Up");
        btnSave.setBackground(new Color(52, 152, 219));
        btnSave.setForeground(Color.WHITE);
        btnSave.setPreferredSize(new Dimension(250, 40));

        gbc.gridy = 0; d.add(newUse, gbc);
        gbc.gridy = 1; d.add(newPass, gbc);
        gbc.gridy = 2; d.add(newEmail, gbc);
        gbc.gridy = 3; d.add(cmbRoles, gbc);
        gbc.gridy = 4; d.add(btnSave, gbc);

        btnSave.addActionListener(e -> {
            String username = newUse.getText().trim();
            String password = new String(newPass.getPassword());
            String email = newEmail.getText().trim();
            String role = (String) cmbRoles.getSelectedItem();

            if(username.isEmpty()) {
                JOptionPane.showMessageDialog(d, "Username cannot be empty.");
                return;
            }

            if(username.contains(" ")) {
                JOptionPane.showMessageDialog(d, "Username must not contain spaces.");
                return;
            }

            if(password.isEmpty()) {
                JOptionPane.showMessageDialog(d, "Password cannot be empty.");
                return;
            }

            if(email.isEmpty()) {
                JOptionPane.showMessageDialog(d, "Email cannot be empty.");
                return;
            }

            if(!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                JOptionPane.showMessageDialog(d, "Please enter a valid email address.");
                return;
            }

            if(controller.registerUser(username, password, email, role)) {
                JOptionPane.showMessageDialog(d, "Account Created!");
                d.dispose();
            } else {
                JOptionPane.showMessageDialog(d, "Failed to create account. Username or email may already exist.");
            }
        });
        d.setVisible(true);
    }
}