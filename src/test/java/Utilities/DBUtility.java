package Utilities;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DBUtility class provides reusable methods for database operations.
 * It uses JDBC to connect to MySQL database and execute queries.
 */
public class DBUtility {
    
    // Database connection objects
    private static Connection connection;
    private static Statement statement;

    /**
     * Establishes a connection to the MySQL database.
     * Uses the provided credentials to connect to the specified database.
     */
    public static void DBConnectionOpen() {
        // Replace these with your actual database credentials
        String url = "jdbc:mysql://your-database-url:port/database-name";
        String user = "your-username";
        String password = "your-password";
        
        try {
            // Establish database connection
            connection = DriverManager.getConnection(url, user, password);
            // Create statement object for executing queries
            statement = connection.createStatement();
        } catch (SQLException e) {
            System.err.println("Database connection failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Closes the database connection.
     * Should be called after all database operations are completed.
     */
    public static void DBConnectionClose() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing database connection: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Executes a SQL query and returns the results as a List of Lists.
     * Each inner List represents a row from the result set.
     * 
     * @param query The SQL query to execute
     * @return List of Lists containing the query results
     */
    public static List<List<String>> getListData(String query) {
        // Open database connection
        DBConnectionOpen();
        
        // Initialize return list
        List<List<String>> returnList = new ArrayList<>();

        try {
            // Execute the query
            ResultSet rs = statement.executeQuery(query);
            
            // Get metadata about the result set
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            // Process each row in the result set
            while (rs.next()) {
                List<String> rowList = new ArrayList<>();
                
                // Get each column's value in the current row
                for (int i = 1; i <= columnCount; i++) {
                    rowList.add(rs.getString(i));
                }
                
                returnList.add(rowList);
            }
        } catch (SQLException e) {
            System.err.println("Error executing query: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Always close the connection
            DBConnectionClose();
        }

        return returnList;
    }

    /**
     * Example usage of the DBUtility class.
     * Demonstrates how to retrieve and display data from the database.
     */
    public static void main(String[] args) {
        // Example query to get all actors
        List<List<String>> data = getListData("SELECT * FROM actor");

        // Display the results
        for (List<String> row : data) {
            System.out.println(row);
        }
    }
} 