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
    // Declare any additional variables
    private static Scanner in;
    private static UserSystem userSystem;
    private static String userName;
    private static AdminSystem adminSystem;
    private static int uID;
    private static boolean loggedIn;
    /**
     * Main is the entry point to SweetRide
     * @param args
     */
    public static void main(String[] args) {
        // Create a UserSystem object 
        userSystem = new UserSystem();
        adminSystem = new AdminSystem();
        // Initiallize our scanner for user input
        in = new Scanner(System.in);
        // Start the command line interface with nobody logged in
        loggedIn = false;
        start();
    }

    /**
     * Start is the main entry point for the command line interface
     */
    private static void start() {
        boolean exit = false;
        while (!exit) {
            System.out.println("\nWelcome to Sweet Ride.");
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
    
    /**
     * userMenu is the main menu for a normal customer (non-admin)
     */
    private static void userMenu() {
        boolean exit = false;
        while (!exit) {
            System.out.println("\nWelcome " + userName);
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
            System.out.println("10: Search for Newer Vehicles by Vehicle Class");
            int option = getOptionIntFromInput(11);
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
                case 10:
                    searchVehicle(7);
                    break;
                default:
                    break;
            }
        }
        start();
    }
    
    private static void adminMenu(){
    	int menuOption;
    	while(loggedIn){
            System.out.println("\n\n Admin Main Menu:\n");
            System.out.println("1: Add New Vehicle into System");
            System.out.println("2: Remove Existing Vehicle from System");
            System.out.println("3: Update Vehicle Location");
            System.out.println("4: Create/Add a New Location");
            //System.out.println("5: Modify an Existing Reservation");
            System.out.println("6: Exit Admin System");
            System.out.print("Enter choice selection: ");
            menuOption = getOptionIntFromInput(7);
    		switch(menuOption){
    		case 1:
    			addNewVehicle();
    			break;
    		case 2:
    			removeExistingVehicle();
    			break;
    		case 3:
    			updateVehicleLocation();
    			break;
    		case 4:
    			addNewLocation();
    			break;
    		case 5:
    			break;
    		case 6:
    			loggedIn = false;
    			break;
    		default:
    			break;
    		}
    		
    	}
    }
    
    /**
     * login is the login handler for the customer
     */
    private static void login() {
        boolean tryAgain = true;
        try {
            while (!loggedIn && tryAgain) {
                System.out.println("Enter email");
                String loginEmail = in.nextLine();
                System.out.println("Enter password");
                String loginPassword = in.nextLine();
                ResultSet loginResult = userSystem.loginAccount(loginEmail, loginPassword);
                if (!loginResult.next()) {
                    System.out.println("Invalid Email or Password!");
                    System.out.println("Enter any key to try again or type 'quit' to start over.");
                    String exit = in.nextLine();
                    if (exit.toLowerCase().equals("quit")) {
                        tryAgain = false;
                    }
                } else {
                    uID = loginResult.getInt("c_id");
                    userName = loginResult.getString("first_name");
                    loggedIn = true;
                }
            }
        } catch (SQLException e) {
            System.out.println("Login Fail!\n");
            e.printStackTrace();
        }
        if (loggedIn) {
            userMenu();
        } else {
            start();
        }
    }
    
    private static void adminLogin() {
    	 boolean tryAgain = true;
         try {
             while (!loggedIn && tryAgain) {
                 System.out.print("Enter email : ");
                 String loginEmail = in.nextLine();
                 System.out.print("Enter password : ");
                 String loginPassword = in.nextLine();
                 ResultSet loginResult = adminSystem.loginAccount(loginEmail, loginPassword);
                 if (!loginResult.next()) {
                     System.out.println("Invalid Email or Password!");
                     System.out.println("Enter any key to try again or type 'quit' to start over.");
                     String exit = in.nextLine();
                     if (exit.toLowerCase().equals("quit")) {
                         tryAgain = false;
                     }
                 } else {
                     userName = "admin";
                     loggedIn = true;
                 }
             }
         } catch (SQLException e) {
             System.out.println("Login Fail!\n");
             e.printStackTrace();
         }
         if (loggedIn) {
             adminMenu();
         } else {
             start();
         }
    }
    
    private static void createAccount() {
        //TODO
    }
    
    private static void updateAccount() {
        //TODO
    }
    
    private static void deleteAccount() {
        //TODO
    }
    
    /**
     * searchVehicle is used for all reservation searches.
     * depending on your search criteria, this method shows you matching vehicles
     * that you are then able to reserve if available
     * @param searchOption
     */
    private static void searchVehicle(int searchOption) {
        boolean found;
        switch (searchOption) {
            case 0: // Reservations by vehicle make
                found = false;
                try {
                    while (!found) {
                        System.out.println("\nPlease enter the vehicle make");
                        String make = in.nextLine();
                        ResultSet makeResult = userSystem.searchByMake(make);
                        if (makeResult.next()) {
                            makeResult.beforeFirst();
                            makeReservation(makeResult);
                            found = true;
                        } else { 
                            System.out.println("There were no vehicles matching that criteria, please try again.");
                        }
                    }
                } catch (SQLException e) {
                    System.out.println("There was a problem with your search!\n");
                    e.printStackTrace();
                }
                break;
            case 1: // Reservations by vehicle make and model
                found = false;
                try {
                    while (!found) {
                        System.out.println("\nPlease enter the vehicle make");
                        String vMake = in.nextLine();
                        System.out.println("Please enter the vehicle model");
                        String model = in.nextLine();
                        ResultSet makeModelResult = userSystem.searchByMakeModel(vMake, model);
                        if (makeModelResult.next()) {
                            makeModelResult.beforeFirst();
                            makeReservation(makeModelResult);
                            found = true;
                        } else {
                            System.out.println("There were no vehicles matching that criteria, please try again.");
                        }
                    }
                } catch (SQLException e) {
                    System.out.println("There was a problem with your search!\n");
                    e.printStackTrace();
                }
                break;
            case 2: // Reservations by vehicle year
                found = false;
                try {
                    while (!found) {
                        System.out.println("\nPlease enter the vehicle Year");
                        int year = in.nextInt();
                        ResultSet yearResult = userSystem.searchByYear(year);
                        if (yearResult.next()) {
                            yearResult.beforeFirst();
                            makeReservation(yearResult);
                            found = true;
                        } else {
                            System.out.println("There were no vehicles matching that criteria, please try again.");
                        }
                    }
                } catch (SQLException e) {
                    System.out.println("There was a problem with your search!\n");
                    e.printStackTrace();
                }
                break;
            case 3: // Reservations by vehicle transmission type
                try {
                    System.out.println("\nPlease select the type of transmission you want");
                    userSystem.printTransOptions();
                    ResultSet transResult = null;
                    int option = getOptionIntFromInput(3);
                    switch (option) {
                        case 1:
                            transResult = userSystem.searchByTransmission("MANUAL");
                            break;
                        case 2:
                            transResult = userSystem.searchByTransmission("AUTOMATIC");
                            break;
                        default:
                            break;
                    }
                    if (transResult.next()) {
                        transResult.beforeFirst();
                        makeReservation(transResult);
                    } else {
                        System.out.println("There were no vehicles matching that criteria, please try again.");
                    }
                } catch (SQLException e) {
                    System.out.println("There was a problem with your search!\n");
                    e.printStackTrace();
                }
                break;
            case 4: // Reservations by vehicle class
                try {
                    System.out.println("\nPlease select the vehicle class you would like");
                    userSystem.printClassOptions();
                    ResultSet classResult = null;
                    int option = getOptionIntFromInput(6);
                    switch (option) {
                        case 1:
                            classResult = userSystem.searchByClass("COMPACT");
                            break;
                        case 2:
                            classResult = userSystem.searchByClass("SPORT");
                            break;
                        case 3:
                            classResult = userSystem.searchByClass("LUXURY");
                            break;
                        case 4:
                            classResult = userSystem.searchByClass("SUV");
                            break;
                        case 5:
                            classResult = userSystem.searchByClass("TRUCK");
                            break;
                        default:
                            break;
                    }
                    if (classResult.next()) {
                        classResult.beforeFirst();
                        makeReservation(classResult);
                    } else {
                        System.out.println("There were no vehicles matching that criteria, please try again.");
                    }
                } catch (SQLException e) {
                    System.out.println("There was a problem with your search!\n");
                    e.printStackTrace();
                }
                break;
            case 5: // Reservations available by zip code
                found = false;
                try {
                    while (!found) {
                        System.out.println("\nPlease enter the zip code where you would like to make a reservation");
                        int zip = in.nextInt();
                        ResultSet zipResult = userSystem.searchByZipCode(zip);
                        if (zipResult.next()) {
                            zipResult.beforeFirst();
                            makeReservation(zipResult);
                            found = true;
                        } else { 
                            System.out.println("There were no vehicles matching that criteria, please try again.");
                        }
                    }
                } catch (SQLException e) {
                    System.out.println("There was a problem with your search\n!");
                    e.printStackTrace();
                }
                break;
            case 6: // Reservations available by city and state
                found = false;
                try {
                    while (!found) {
                        System.out.println("\nPlease enter the CITY where you would like to make a reservation");
                        String city = in.nextLine();
                        System.out.println("Please enter the STATE (full name)");
                        String state = in.nextLine();
                        ResultSet cityStateResult = userSystem.searchByCityState(city, state);
                        if (cityStateResult.next()) {
                            cityStateResult.beforeFirst();
                            makeReservation(cityStateResult);
                            found = true;
                        } else { 
                            System.out.println("There were no vehicles matching that criteria, please try again.");
                        }
                    }
                } catch (SQLException e) {
                    System.out.println("There was a problem with your search!\n");
                    e.printStackTrace();
                }
                break;
            case 7: // Reservations for newer vehicles by vehicle class
                found = false;
                try {
                    while (!found) {
                        System.out.println("\nPlease enter the minimum vehicle YEAR you are interested in reserving");
                        int year = in.nextInt();
                        System.out.println("Please select the vehicle class you would like by the corresponding number");
                        userSystem.printClassOptions();
                        int classID = in.nextInt();
                        ResultSet yearClassResult = userSystem.searchNewerThanByClass(year, classID);
                        if (yearClassResult.next()) {
                            yearClassResult.beforeFirst();
                            makeReservation(yearClassResult);
                            found = true;
                        } else { 
                            System.out.println("There were no vehicles matching that criteria, please try again.");
                        }
                    }
                } catch (SQLException e) {
                    System.out.println("There was a problem with your search!\n");
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

    /**
     * makeReservation actually makes the reservation if the vehicle is available
     * the method also prints out reservation details on success 
     * @param rs
     * @return true if reservation was made
     */
    private static boolean makeReservation(ResultSet rs) {
        boolean reserved = false;
        boolean passed = false;
        int vID;
        try {
            // Get the vehicle choice from the user
            while (!passed) {
                System.out.println("\nPlease enter the ID number corresponding to the vehicle you would like to reserve:");
                System.out.println(String.format("%-10s %s", "ID: 0 ", "I do not want to reserve reserve a vehicle at this time"));
                rs.beforeFirst();
                userSystem.printVehicles(rs);
                vID = in.nextInt();
                if (vID == 0) {
                    userMenu();
                } else if (userSystem.checkAvailability(vID)) {
                    passed = false;
                    System.out.println("That vehicle is currently reserved, please try again\n");
                } else {
                    reserved = userSystem.reserveVehicleByID(uID, vID);
                    if (!reserved) {
                        System.out.println("Sorry, there was a problem with this reservation, please try again at another time.");
                    } else {
                        passed = true;
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("There was a problem with your reservation!\n");
            e.printStackTrace();
        }
        return reserved;
    }
    
    private static void updateVehicleLocation(){
    	
    	System.out.println("\n\nPlease enter the following information regarding the vehicle....");
		try{
			System.out.println("\tVehicle Id: ");
			int v_id = Integer.parseInt(in.nextLine());
			
			System.out.println("\tLocation Id: ");
			int l_id = Integer.parseInt(in.nextLine());
			
			try{
				adminSystem.transferVehicle(v_id, l_id);
			}catch(SQLException e){
				System.err.println("An Error has occured....");
			}
		}catch(NumberFormatException e){
			System.err.println("Invalid Input...");
		}
    }
    
    private static void addNewVehicle(){
		try{
			int year=0, transType, locationId, classType;
			String make, model;
			
			System.out.println("\n\nPlease enter the following information regarding the vehicle....");
			System.out.print("\tYear: ");
			try{
				year = Integer.parseInt(in.nextLine());
			}catch(Exception e){
				System.err.println("Invalid Input...");
			}
			
			System.out.print("\tMake: ");
			make = in.nextLine();
			
			System.out.println("\tModel: ");
			model = in.nextLine();
			
			System.out.println("\tTransmissions Option ");
				System.out.println("\t\t 1:Manual");
				System.out.println("\t\t 2:Automatic");
			System.out.print("Transmission:");
			transType = getOptionIntFromInput(3);
		
			System.out.println("\tClass Options");
				System.out.println("\t\t 1:COMPACT");
				System.out.println("\t\t 2:SPORT");
				System.out.println("\t\t 3:LUXURY");
				System.out.println("\t\t 4:SUV");
				System.out.println("\t\t 5:Truck");
			System.out.print("Class:");
			classType = getOptionIntFromInput(6);
			
			System.out.println("\tLocation Options");
			
			ResultSet locRs = adminSystem.getLocationList();
			int locationEntry = 0;
			while(locRs.next())
				System.out.println("\t\t"+ (++locationEntry) +": " + locRs.getString("location_name"));
			System.out.print("location: ");
			locationId = getOptionIntFromInput(locationEntry+1);
		
			adminSystem.insertNewVehicle(year, make, model, transType, locationId, classType);

		}catch(SQLException e){
				System.out.println("an error has occured.");
		}

		System.out.println("Vehicle Successfully Added To Inventory.");
		System.out.println("Total Number of vehicles in the system: " + adminSystem.vehicleCount());
		
    }
    
    private static void removeExistingVehicle(){
		try{
			ResultSet curVehicle = adminSystem.getCurVehicleList();
			System.out.println("Current vehicles in the system...");
			while(curVehicle.next())
				System.out.printf("%8d %8d %8s %8s\n", curVehicle.getInt("v_id"), curVehicle.getInt("year"), curVehicle.getString("Make"), curVehicle.getString("Model"));
			System.out.print("Enter Vehicle ID(v_id): ");
			try{
				int v_id = Integer.parseInt(in.nextLine());
				adminSystem.deleteVehicle(v_id);
			}catch(NumberFormatException e){
				System.err.println("Invalid input...");
			}	
		}catch(SQLException e){
				System.err.println("an error has occured.");
		}
		

		System.out.println("Vehicle Successfully Removed From Inventory.");
		System.out.println("Total Number of vehicles in the system: " + adminSystem.vehicleCount());
		
    }
    
    private static void addNewLocation(){
			int zipcode = 0;
			String locationName, street, city, state;
			
			System.out.println("\n\nPlease enter the following information regarding the location....");
			
			System.out.print("\tLocation Name: ");
			locationName = in.nextLine();
			
			System.out.print("\tStreet(Address): ");
			street = in.nextLine();
			
			System.out.print("\tCity: ");
			city = in.nextLine();
			
			System.out.print("\tState: ");
			state = in.nextLine();
			
			System.out.print("\tZip Code: ");
			try{
				zipcode = Integer.parseInt(in.nextLine());
			}catch(Exception e){
				System.err.println("Invalid Input...");
			}
			
			adminSystem.insertNewLocation(locationName, street, city, state, zipcode);

			System.out.println("New location has been added");
			System.out.println("Total Number of SweetRide location in the system: " + adminSystem.locationCount());
    }
    

    /**
     * helper function for the switch statements to ensure a valid menu input
     * @param lessThan
     * @return
     */
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
}
