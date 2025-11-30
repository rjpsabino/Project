package controller;

import database.DatabaseHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

public class BeneficiaryController {

   
    public boolean registerBeneficiary(String name, String contact, String address, String dob, double income, String status) {
        String sql = "INSERT INTO beneficiaries(name, contact_number, address, birth_date, daily_income, vulnerability_status) VALUES(?,?,?,?,?,?)";

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setString(2, contact);
            pstmt.setString(3, address);
            pstmt.setString(4, dob);
            pstmt.setDouble(5, income);
            pstmt.setString(6, status);

            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
            return false;
        }
    }
    public void filterByPurok(DefaultTableModel tableModel, String selectedPurok) {
        tableModel.setRowCount(0);
        
        String sql = "SELECT * FROM beneficiaries WHERE address LIKE ?";

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, selectedPurok + "%"); 
            
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getInt("id"));
                row.add(rs.getString("name"));
                row.add(rs.getString("contact_number"));
                row.add(rs.getString("address"));
                row.add(rs.getString("birth_date"));
                row.add(rs.getDouble("daily_income"));
                row.add(rs.getString("status"));
                tableModel.addRow(row);
            }

        } catch (SQLException e) {
            System.out.println("Error filtering data: " + e.getMessage());
        }
    }

    public void loadTableData(DefaultTableModel tableModel) {
        tableModel.setRowCount(0);
        String sql = "SELECT * FROM beneficiaries";

        try (Connection conn = DatabaseHelper.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getInt("id"));
                row.add(rs.getString("name"));
                row.add(rs.getString("contact_number"));
                row.add(rs.getString("address"));   
                row.add(rs.getString("birth_date"));
                row.add(rs.getDouble("daily_income"));
                row.add(rs.getString("vulnerability_status"));
                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }

    public boolean deleteBeneficiary(int id) {
        String sql = "DELETE FROM beneficiaries WHERE id = ?";

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Record deleted successfully.");
            return true;

        } catch (SQLException e) {
            System.out.println("Error deleting record: " + e.getMessage());
            return false;
        }
    }

    public boolean updateBeneficiary(int id, String name, String contact, String address, String dob, double income, String status) {
        String sql = "UPDATE beneficiaries SET name = ?, contact_number = ?, address = ?, birth_date = ?, daily_income = ?, vulnerability_status = ? WHERE id = ?";

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setString(2, contact);
            pstmt.setString(3, address); 
            pstmt.setString(4, dob);     
            pstmt.setDouble(5, income);
            pstmt.setString(6, status);
            pstmt.setInt(7, id);

            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error updating record: " + e.getMessage());
            return false;
        }
    }

    public boolean exportToCSV(String filePath) {
        String sql = "SELECT * FROM beneficiaries";
        
        try (Connection conn = DatabaseHelper.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql);
             java.io.FileWriter writer = new java.io.FileWriter(filePath)) {

            writer.append("ID,Name,Contact,Address,Birthday,Daily Income,Status\n");

            while (rs.next()) {
                writer.append(String.valueOf(rs.getInt("id"))).append(",");
                writer.append(rs.getString("name")).append(",");
                writer.append(rs.getString("contact_number")).append(",");
                writer.append(rs.getString("address")).append(",");
                writer.append(rs.getString("birth_date")).append(",");
                writer.append(String.valueOf(rs.getDouble("daily_income"))).append(",");
                writer.append(rs.getString("vulnerability_status")).append("\n");
            }
            return true;

        } catch (Exception e) {
            System.out.println("Export failed: " + e.getMessage());
            return false;
        }
    }
}