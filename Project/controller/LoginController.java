package controller;

import database.DatabaseHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    public boolean login(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) { return false; }
    }

    public boolean registerUser(String username, String password, String email, String role) {
    
        if (username == null || username.trim().isEmpty()) return false;
        if (username.contains(" ")) return false;
        if (password == null || password.isEmpty()) return false;
        if (email == null || email.trim().isEmpty()) return false;

        if (checkUserExists(username)) {
            System.out.println("Registration failed: username already exists: " + username);
            return false;
        }

        if (checkEmailExists(email)) {
            System.out.println("Registration failed: email already exists: " + email);
            return false;
        }

        String sql = "INSERT INTO users(username, password, email, role) VALUES(?,?,?,?)";
        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, email);
            pstmt.setString(4, role);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
        
            System.out.println("SQL Error during registerUser: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

 
    public boolean resetPassword(String username, String newPassword) {
       
        if(!checkUserExists(username)) return false;

        String sql = "UPDATE users SET password = ? WHERE username = ?";
        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newPassword);
            pstmt.setString(2, username);
            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) { return false; }
    }

    private boolean checkUserExists(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) { return false; }
    }

    private boolean checkEmailExists(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Error checking email existence: " + e.getMessage());
            return false;
        }
    }
}