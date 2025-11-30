package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseHelper {
    
    private static final String URL = "jdbc:mysql://localhost:3306/bcls_db";
    private static final String USER = "root"; 
    private static final String PASSWORD = "SYFamily4ever"; 

    public static Connection connect() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); 
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Connection Failed: " + e.getMessage());
        }
        return conn;
    }

    public static void createNewTable() {
       
        String sql = "CREATE TABLE IF NOT EXISTS beneficiaries (\n"
                + " id INT PRIMARY KEY AUTO_INCREMENT,\n"
                + " name VARCHAR(255) NOT NULL,\n"
                + " contact_number VARCHAR(20),\n"
                + " address TEXT,\n"
                + " birth_date VARCHAR(20),\n"
                + " daily_income DOUBLE,\n"
                + " status VARCHAR(50)\n"
                + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
             
            if (conn != null) {
                stmt.execute(sql);
                System.out.println("MySQL Table 'beneficiaries' check/creation successful.");
            }
            
        } catch (SQLException e) {
            System.out.println("Table Creation Failed: " + e.getMessage());
        }
    }
}