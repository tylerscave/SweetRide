package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *COPYRIGHT (C) 2016 SweetRide. All Rights Reserved.
 * UserSystem is responsible for handling all functions
 * that are accessible to the standard user (non-admin)
 * Solves CS157A Project
 * @authors Tyler Jones, Jonathan Chen ,Vinay Patel
 */
public class UserSystem {
    private static Connection conn = null;
    private static Statement statement = null;

    /**
     * Constructor for UserSystem
     */
    public UserSystem () {
        //Register JDBC Driver for this class and establish a connection to the database
        try {
        Class.forName(LoginSystem.JDBC_DRIVER);
        conn = DriverManager.getConnection(LoginSystem.DB_URL, LoginSystem.USER, LoginSystem.PASS);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }




    protected ResultSet searchByTransmission(String trans) throws SQLException {
        ResultSet rs = null;
        
        return rs;
    }
    
    protected ResultSet searchByClass(String vClass) throws SQLException {
        ResultSet rs = null;
        
        return rs;
    }
    
    protected ResultSet searchByZipCode(int zipCode) throws SQLException {
        ResultSet rs = null;
        
        return rs;
    }
    
    protected ResultSet searchByCityState(String city, String state) throws SQLException {
        ResultSet rs = null;
        
        return rs;
    }
    
    protected ResultSet searchByMake(String make) throws SQLException {
        ResultSet rs = null;
        
        return rs;
    }
    
    protected ResultSet searchByMakeModel(String make, String model) throws SQLException {
        ResultSet rs = null;
        
        return rs;
    }
    
    protected ResultSet searchByYear(int year) throws SQLException {
        ResultSet rs = null;
        statement = conn.createStatement();
        rs = statement.executeQuery("SELECT * FROM vehicle WHERE year = " + year);
        return rs;
    }
    
    protected void printVehicles(ResultSet rs) throws SQLException {
        String available;
        while(rs.next()) {
            int year = rs.getInt("year"); 
            String make = rs.getString("make"); 
            String model = rs.getString("model");
            boolean reserved = rs.getBoolean("reserved");
            if (reserved) {
                available = "No";
            } else {
                available = "Yes";
            }
            System.out.println("Year: " + year + " | Make: " + make + " | Model: " + model + " | Available: " + available); 
        }
    }
}
