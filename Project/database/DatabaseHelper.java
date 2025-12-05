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
            if (e instanceof ClassNotFoundException) {
                System.out.println("MySQL JDBC Driver not found: " + e.getMessage());
                System.out.println("Add the MySQL Connector/J jar to the classpath, e.g.:\n  javac -cp .;mysql-connector-java-8.0.33.jar -d out <files>\n  java -cp out;mysql-connector-java-8.0.33.jar Main");
            } else {
                System.out.println("MySQL Connection Failed: " + e.getMessage());
            }
        }
        return conn;
    }

    public static void createNewTable() {
        Connection conn = connect();
        if (conn == null) {
            System.out.println("ERROR: No database connection available. Check MySQL availability.");
            return;
        }
        
        try (Statement stmt = conn.createStatement()) {
            String sql1 = "CREATE TABLE IF NOT EXISTS beneficiaries (\n"
                    + " id INT PRIMARY KEY AUTO_INCREMENT,\n"
                    + " name VARCHAR(255) NOT NULL,\n"
                    + " contact_number VARCHAR(20),\n"
                    + " address TEXT,\n"
                    + " birth_date VARCHAR(20),\n"
                    + " daily_income DOUBLE,\n"
                    + " status VARCHAR(50),\n"
                    + " services_availed TEXT,\n"
                    + " is_4ps VARCHAR(10),\n"
                    + " remarks TEXT\n"
                    + ");";
            stmt.execute(sql1);

            String sql2 = "CREATE TABLE IF NOT EXISTS users (\n"
                + " id INT PRIMARY KEY AUTO_INCREMENT,\n"
                + " username VARCHAR(50) UNIQUE NOT NULL,\n"
                + " password VARCHAR(50) NOT NULL,\n"
                + " email VARCHAR(255) UNIQUE,\n"
                + " role VARCHAR(50)\n"
                + ");";
            stmt.execute(sql2);

            try {
                String schema = "bcls_db"; 
                String checkSql = "SELECT COUNT(*) AS cnt FROM information_schema.COLUMNS WHERE TABLE_SCHEMA = ? AND TABLE_NAME = 'users' AND COLUMN_NAME = 'email'";
                try (java.sql.PreparedStatement ps = conn.prepareStatement(checkSql)) {
                    ps.setString(1, schema);
                    try (java.sql.ResultSet rs = ps.executeQuery()) {
                        if (rs.next() && rs.getInt("cnt") == 0) {
                            String alter = "ALTER TABLE users ADD COLUMN email VARCHAR(255) UNIQUE;";
                            stmt.execute(alter);
                            System.out.println("Added 'email' column to users table.");
                        }
                    }
                }
            } catch (SQLException ex) {
                System.out.println("Column-check/alter for users.email skipped: " + ex.getMessage());
            }

            System.out.println("Database Tables Verified.");
        } catch (SQLException e) {
            System.out.println("Table Creation Failed: " + e.getMessage());
        }
    }
}