package application;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

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

    private static Scanner in;
    private static UserSystem userSystem;
    private static String userName;
    private static int uID;
    private static boolean loggedIn;
    /**
     * Main is the entry point to SweetRide
     * @param args
     */
    public static void main(String[] args) {
        // Create a UserSystem object 
        userSystem = new UserSystem();
        in = new Scanner(System.in);
        start();


        //Testing calls for Tyler's queries
        tylersTest();
    }

    
    private static void start() {
        boolean exit = false;
        while (!exit) {
            System.out.println("Welcome to Sweet Ride.");
            System.out.println("Please enter the number corresponding to the action you would like to take:");
            System.out.println("0: Exit");
            System.out.println("1: Login");
            System.out.println("2: Admin Login");
            System.out.println("3: Create New Account");
            int option = getOptionIntFromInput(4);
            switch (option) {
                case 0:
                    exit = true;
                    break;
                case 1:
                    login();
                    break;
                case 2:
                    adminLogin();
                    break;
                case 3:
                    createAccount();
                    break;
                default:
                    break;
            }
        }
        System.exit(0);
    }
    
    private static void userMenu() {
        boolean exit = false;
        while (!exit) {
            System.out.println("Welcome " + userName);
            System.out.println("Main Menu:");
            System.out.println("0: Logout");
            System.out.println("1: Update Account");
            System.out.println("2: Delete Account");
            System.out.println("3: Search for Vehicle by Make");
            System.out.println("4: Search for Vehicle by Make and Model");
            System.out.println("5: Search for Vehicle by Year");
            System.out.println("6: Search for Vehicle by Transmission Type");
            System.out.println("7: Search for Vehicle by Vehicle Class");
            System.out.println("8: Search for Vehicle by Zip Code");
            System.out.println("9: Search for Vehicle by City and State");
            int option = getOptionIntFromInput(10);
            switch (option) {
                case 0:
                    loggedIn = false;
                    exit = true;
                    break;
                case 1:
                    updateAccount();
                    break;
                case 2:
                    deleteAccount();
                    break;
                case 3:
                    searchVehicle(0);
                    break;
                case 4:
                    searchVehicle(1);
                    break;
                case 5:
                    searchVehicle(2);
                    break;
                case 6:
                    searchVehicle(3);
                    break;
                case 7:
                    searchVehicle(4);
                    break;
                case 8:
                    searchVehicle(5);
                    break;
                case 9:
                    searchVehicle(6);
                    break;
                default:
                    break;
            }
        }
        //back to start()
    }
    
    private static void login() {
        System.out.println("Enter email");
        String loginEmail = in.nextLine();
        System.out.println("Enter password");
        String loginPassword = in.nextLine();
    }
    
    private static void adminLogin() {
        System.out.println("Enter administrator email");
        String adminEmail = in.nextLine();
        System.out.println("Enter administrator password");
        String adminPassword = in.nextLine();
    }
    
    private static void createAccount() {
        System.out.println("Enter email");
        String email = in.nextLine();
        System.out.println("Enter password");
        String password = in.nextLine();
    }
    
    private static void updateAccount() {
        //TODO
    }
    
    private static void deleteAccount() {
        //TODO
    }
    
    private static void searchVehicle(int searchOption) {
        switch (searchOption) {
            case 0:
                // Get and print results for a search by vehicle make
                System.out.println("Please enter the vehicle make");
                String make = in.nextLine();
                try {
                    ResultSet makeResult = userSystem.searchByMake(make);
                    userSystem.printVehicles(makeResult);
                } catch (SQLException e) {
                    System.out.println("There was a problem with your search!");
                    e.printStackTrace();
                }
                break;
            case 1:
             // Get and print results for a search by vehicle make and model
                System.out.println("Please enter the vehicle make");
                String vehicleMake = in.nextLine();
                System.out.println("Please enter the vehicle model");
                String model = in.nextLine();
                try {
                    ResultSet makeModelResult = userSystem.searchByMakeModel(vehicleMake, model);
                    userSystem.printVehicles(makeModelResult);
                } catch (SQLException e) {
                    System.out.println("There was a problem with your search!");
                    e.printStackTrace();
                }
                break;
            case 2:
                
                break;
            case 3:
                
                break;
            case 4:
                ;
                break;
            case 5:
                
                break;
            case 6:
                ;
                break;
            default:
                break;
        }
        // Get the vehicle choice from the user
        boolean exit = false;
        while (!exit) {
            System.out.println("Please enter the number corresponding to the vehicle you would like to reserve:");

            int option = getOptionIntFromInput(4);
            switch (option) {
                case 0:
                    exit = true;
                    break;
                default:
                    break;
            }
        }
    }
    
    private static boolean makeReservation() {
        boolean reserved = false;
        //TODO
        return reserved;
    }
    
    private static int getOptionIntFromInput(int lessThan) {
        int option = 0;
        boolean valid = false;
        while (!valid) {
            String input = in.nextLine();
            if (lessThan == 1) {
                valid = true;
                option = 0;
            } 
            else {
                try {
                    option = Integer.parseInt(input);
                    if (option >= lessThan)
                        throw new Exception();
                    valid = true;
                } catch (Exception e) {
                    System.out.println("Invalid Input. try again");
                }
            }            
        }
        return option;
    } 
    /**
     * tylersTest() is a test function that will be deleted when project is complete
     * please use the code contained in this method however you need/want.
     * Note: all calls to these methods need to handle SQLException with try/catch or whatever you want...
     */
    private static void tylersTest() {
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
