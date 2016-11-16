package application;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *COPYRIGHT (C) 2016 SweetRide. All Rights Reserved.
 * SweetRide is a vehicle rental application demonstrating our
 * teams knowledge of MySQL. The LoginSystem is the main entry 
 * point to the application and hadles all user and admin login functions
 * Solves CS157A Project
 * @authors Tyler Jones, Jonathan Chen ,Vinay Patel
 */
public class LoginSystem {
    // JDBC driver name and database URL
    protected static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    protected static final String DB_URL = "jdbc:mysql://localhost/SWEET_RIDE";
    //  Database credentials
    protected static final String USER = "root";
    protected static final String PASS = "";

    /**
     * Main is the entry point to SweetRide
     * @param args
     */
    public static void main(String[] args) {
        // TODO


        //Testing calls for Tyler's queries
        tylersTest();
    }

    /**
     * tylersTest() is a test function that will be deleted when project is complete
     * please use the code contained in this method however you need/want.
     * Note: all calls to these methods need to handle SQLException with try/catch or whatever you want...
     */
    private static void tylersTest() {
        // Create a UserSystem object for method calls. May want to move later 
        //    for use throughout the LoginSystem class
        UserSystem userSystem = new UserSystem();
        // Testing and printing all queries
        try {
            // Get and print results for a search by vehicle make
            ResultSet makeResult = userSystem.searchByMake("Toyota");
            System.out.println("Results for search by make:");
            userSystem.printVehicles(makeResult);
            
            // Get and print results for a search by vehicle make and model
            ResultSet makeModelResult = userSystem.searchByMakeModel("Toyota", "Supra");
            System.out.println("Results for search by make and model:");
            userSystem.printVehicles(makeModelResult);
            
            // Get and print results for a search by vehicle year
            ResultSet yearResult = userSystem.searchByYear(2016);
            System.out.println("Results for search by year:");
            userSystem.printVehicles(yearResult);
            
            // Get and print results for a search by transmission type
            ResultSet transResult = userSystem.searchByTransmission("MANUAL");
            System.out.println("Results for search by transmission:");
            userSystem.printVehicles(transResult);
            
            // Get and print results for a search by vehicle class
            ResultSet classResult = userSystem.searchByClass("LUXURY");
            System.out.println("Results for search by class:");
            userSystem.printVehicles(classResult);
            
            // Get and print results for a search by zip code
            ResultSet zipCodeResult = userSystem.searchByZipCode(95113);
            System.out.println("Results for search by zip code:");
            userSystem.printVehicles(zipCodeResult);
            
            // Get and print results for a search by city and state
            ResultSet cityStateResult = userSystem.searchByCityState("Los Angeles", "California");
            System.out.println("Results for search by city and state:");
            userSystem.printVehicles(cityStateResult);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
