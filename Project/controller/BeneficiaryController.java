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

    public boolean registerBeneficiary(String name, String contact, String address, String dob, double income, String status, String services, String is4Ps, String remarks) {
        String sql = "INSERT INTO beneficiaries(name, contact_number, address, birth_date, daily_income, vulnerability_status, services_availed, is_4ps, remarks) VALUES(?,?,?,?,?,?,?,?,?)";
        try (Connection conn = DatabaseHelper.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name); pstmt.setString(2, contact); pstmt.setString(3, address);
            pstmt.setString(4, dob); pstmt.setDouble(5, income); pstmt.setString(6, status);
            pstmt.setString(7, services); pstmt.setString(8, is4Ps); pstmt.setString(9, remarks);
            pstmt.executeUpdate(); return true;
        } catch (SQLException e) { return false; }
    }


    public void loadTableData(DefaultTableModel tableModel) {
        tableModel.setRowCount(0);
        String sql = "SELECT * FROM beneficiaries";
        try (Connection conn = DatabaseHelper.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            fillTable(rs, tableModel);
        } catch (SQLException e) { System.out.println("Load Error: " + e.getMessage()); }
    }


    public boolean updateBeneficiary(int id, String name, String contact, String address, String dob, double income, String status, String services, String is4Ps, String remarks) {
        String sql = "UPDATE beneficiaries SET name = ?, contact_number = ?, address = ?, birth_date = ?, daily_income = ?, vulnerability_status = ?, services_availed = ?, is_4ps = ?, remarks = ? WHERE id = ?";
        try (Connection conn = DatabaseHelper.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name); pstmt.setString(2, contact); pstmt.setString(3, address);
            pstmt.setString(4, dob); pstmt.setDouble(5, income); pstmt.setString(6, status);
            pstmt.setString(7, services); pstmt.setString(8, is4Ps); pstmt.setString(9, remarks);
            pstmt.setInt(10, id); pstmt.executeUpdate(); return true;
        } catch (SQLException e) { return false; }
    }


    public boolean deleteBeneficiary(int id) {
        String sql = "DELETE FROM beneficiaries WHERE id = ?";
        try (Connection conn = DatabaseHelper.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id); pstmt.executeUpdate(); return true;
        } catch (SQLException e) { return false; }
    }

    public boolean exportToCSV(String filePath) {
        String sql = "SELECT * FROM beneficiaries";
        try (Connection conn = DatabaseHelper.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql); java.io.FileWriter writer = new java.io.FileWriter(filePath)) {
            writer.append("ID,Name,Contact,Address,Birthday,Income,Status,Services,4Ps Member,Remarks\n");
            while (rs.next()) {
                writer.append(String.valueOf(rs.getInt("id"))).append(",");
                writer.append(safeString(rs.getString("name"))).append(",");
                writer.append(safeString(rs.getString("contact_number"))).append(",");
                writer.append(safeString(rs.getString("address")).replace(",", " ")).append(",");
                writer.append(safeString(rs.getString("birth_date"))).append(",");
                writer.append(String.valueOf(rs.getDouble("daily_income"))).append(",");
                writer.append(safeString(rs.getString("vulnerability_status"))).append(",");
                writer.append(safeString(rs.getString("services_availed")).replace(",", ";")).append(",");
                writer.append(safeString(rs.getString("is_4ps"))).append(",");
                writer.append(safeString(rs.getString("remarks")).replace(",", " ")).append("\n");
            } return true;
        } catch (Exception e) { return false; }
    }
    
    public void filterByPurok(DefaultTableModel tableModel, String selectedPurok) {
        tableModel.setRowCount(0);
        String sql = "SELECT * FROM beneficiaries WHERE address LIKE ?";
        try (Connection conn = DatabaseHelper.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, selectedPurok + "%");
            ResultSet rs = pstmt.executeQuery();
            fillTable(rs, tableModel);
        } catch (SQLException e) {}
    }

    public void filterByCategoryAndPurok(DefaultTableModel tableModel, String category, String purok) {
        tableModel.setRowCount(0);
        
        String sql = "";
        String paramCategory = "";
        String paramPurok = purok.equals("All Puroks") ? "%" : purok + "%"; 

        switch (category) {
            case "4Ps Members":
                sql = "SELECT * FROM beneficiaries WHERE is_4ps = 'Yes' AND address LIKE ?";
                paramCategory = null; 
                break;
            
            case "Person with Disability (PWD)":
            case "Senior Citizen":
            case "Solo Parent":
            case "Indigent":
            case "Disaster Victim":
            case "Extreme Poverty":
                sql = "SELECT * FROM beneficiaries WHERE vulnerability_status = ? AND address LIKE ?";
                paramCategory = category;
                break;

            case "Medical Assistance":
            case "Food Packs":
            case "Calamity Assistance":
            case "Livelihood Assistance":
            case "Burial Assistance":
                sql = "SELECT * FROM beneficiaries WHERE services_availed LIKE ? AND address LIKE ?";
                paramCategory = "%" + category.split(" ")[0] + "%"; 
                break;
        }

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            if (paramCategory != null) {
               
                pstmt.setString(1, paramCategory);
                pstmt.setString(2, paramPurok);
            } else {
     
                pstmt.setString(1, paramPurok);
            }
            
            ResultSet rs = pstmt.executeQuery();
            fillTable(rs, tableModel);

        } catch (SQLException e) {
            System.out.println("Filter Error: " + e.getMessage());
        }
    }

    private void fillTable(ResultSet rs, DefaultTableModel tableModel) throws SQLException {
        while (rs.next()) {
            Vector<Object> row = new Vector<>();
            row.add(rs.getInt("id"));
            row.add(rs.getString("name"));
            row.add(rs.getString("contact_number"));
            row.add(rs.getString("address"));
            row.add(rs.getString("birth_date"));
            row.add(rs.getDouble("daily_income"));
            row.add(rs.getString("vulnerability_status"));
            row.add(rs.getString("services_availed"));
            row.add(rs.getString("is_4ps"));
            row.add(rs.getString("remarks"));
            tableModel.addRow(row);
        }
    }
    
    private String safeString(String input) { return (input == null) ? "" : input; }
}