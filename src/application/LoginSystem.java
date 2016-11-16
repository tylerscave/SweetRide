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
     * please use the code contained in this method however you need
     */
    private static void tylersTest() {
        UserSystem userSystem = new UserSystem();
        try {
            
            //ResultSet transResult = userSystem.searchByTransmission("MANUAL");
            //System.out.println("Results for search by transmission:");
            //userSystem.printVehicles(transResult);
            
            //ResultSet classResult = userSystem.searchByClass("LUXURY");
            //System.out.println("Results for search by class:");
            //userSystem.printVehicles(classResult);
            
            //ResultSet zipCodeResult = userSystem.searchByZipCode(95113);
            //System.out.println("Results for search by zip code:");
            //userSystem.printVehicles(zipCodeResult);
            
            //ResultSet cityStateResult = userSystem.searchByCityState("Los Angeles", "California");
            //System.out.println("Results for search by city and state:");
            //userSystem.printVehicles(cityStateResult);
            
            //ResultSet makeResult = userSystem.searchByMake("Toyota");
            //System.out.println("Results for search by make:");
            //userSystem.printVehicles(makeResult);
            
            //ResultSet makeModelResult = userSystem.searchByMakeModel("Toyota", "Supra");
            //System.out.println("Results for search by make and model:");
            //userSystem.printVehicles(makeModelResult);
            
            ResultSet yearResult = userSystem.searchByYear(2016);
            System.out.println("Results for search by year:");
            userSystem.printVehicles(yearResult);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
