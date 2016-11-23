package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.util.Pair;

/**
 * COPYRIGHT (C) 2016 SweetRide. All Rights Reserved. UserSystem is responsible for handling all functions that are accessible to the standard user (non-admin) Solves CS157A Project
 * 
 * @authors Tyler Jones, Jonathan Chen ,Vinay Patel
 */
public class UserSystem {
	private static Connection conn = null;
	private static Statement statement = null;

	/**
	 * Constructor for UserSystem
	 */
	public UserSystem() {
		// Register JDBC Driver for this class and establish a connection to the
		// database
		try {
			Class.forName(LoginSystem.JDBC_DRIVER);
			conn = DriverManager.getConnection(LoginSystem.DB_URL, LoginSystem.USER, LoginSystem.PASS);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}

	/**
	 * Allows new users to register for new account
	 * 
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param pwd
	 * @return ResultSet after Inserting the user into database
	 * @throws SQLException
	 */
	protected ResultSet userRegistration(String firstName, String lastName, String email, String pwd) throws SQLException {
		ResultSet rs = null;
		statement = conn.createStatement();
		rs = statement.executeQuery(String.format("INSERT INTO Customer (first_name, last_name, email, pwd)" + "VALUES ('%s','%s','%s','%s')", firstName, lastName, email, pwd));
		return rs;
	}

	/**
	 * Allows user to update details such as firstname, lastname, email and password given a valid customer ID is provided.
	 * 
	 * @param customerID
	 * @param args
	 * @return ResultSet after update query execution
	 * @throws SQLException
	 */
	protected ResultSet editAccountDetails(int customerID, Pair<String, String>... args) throws SQLException {
		ResultSet rs = null;
		statement = conn.createStatement();
		String updateBuilder = "";
		for (Pair<String, String> arg : args) {
			updateBuilder += arg.getKey() + String.format("='%s', ", arg.getValue());
		}
		updateBuilder = updateBuilder.substring(0, updateBuilder.length() - 2); // Remove the last 2 char i.e. extra ", "
		rs = statement.executeQuery(String.format("UPDATE customer SET %s WHERE c_id='%d'", updateBuilder, customerID));
		return rs;
	}

	/**
	 * Deletes the account details of a user given the customer ID
	 * 
	 * @param customerID
	 * @return ResultSet after delete query execution
	 * @throws SQLException
	 */
	protected ResultSet deleteAccount(int customerID) throws SQLException {
		ResultSet rs = null;
		statement = conn.createStatement();
		rs = statement.executeQuery(String.format("DELETE FROM customer WHERE c_id='%d'", customerID));
		return rs;
	}

	/**
	 * Finds User matching the email and password typed
	 * 
	 * @param email
	 * @param pwd
	 * @return Account details for the loggedin user
	 * @throws SQLException
	 */
	protected ResultSet loginAccount(String email, String pwd) throws SQLException {
		ResultSet rs = null;
		statement = conn.createStatement();
		rs = statement.executeQuery(String.format("SELECT * FROM customer WHERE email='%s' AND pwd='%s'", email, pwd));
		return rs;
	}

	/**
	 * SearchByMake queries the SWEET_RIDE database for vehicle information based on the make of the vehicle
	 * 
	 * @param make
	 * @return Vehicle year, make, model, and reservation status
	 * @throws SQLException
	 */
	protected ResultSet searchByMake(String make) throws SQLException {
		ResultSet rs = null;
		statement = conn.createStatement();
		rs = statement.executeQuery("SELECT * FROM vehicle WHERE make = '" + make + "'");
		return rs;
	}

	/**
	 * SearchByMakeModel queries the SWEET_RIDE database for vehicle information based on the make and model of the vehicle
	 * 
	 * @param make
	 *            and model
	 * @return Vehicle year, make, model, and reservation status
	 * @throws SQLException
	 */
	protected ResultSet searchByMakeModel(String make, String model) throws SQLException {
		ResultSet rs = null;
		statement = conn.createStatement();
		rs = statement.executeQuery("SELECT * " + "FROM vehicle " + "WHERE make = '" + make + "' AND model = '" + model + "'");
		return rs;
	}

	/**
	 * SearchByYear queries the SWEET_RIDE database for vehicle information based on the year of the vehicle
	 * 
	 * @param year
	 * @return Vehicle year, make, model, and reservation status
	 * @throws SQLException
	 */
	protected ResultSet searchByYear(int year) throws SQLException {
		ResultSet rs = null;
		statement = conn.createStatement();
		rs = statement.executeQuery("SELECT * FROM vehicle WHERE year = " + year);
		return rs;
	}

	/**
	 * SearchByTransmission queries the SWEET_RIDE database for vehicle information based on the vehicle transmission type
	 * 
	 * @param transmission
	 *            type
	 * @return Vehicle year, make, model, and reservation status
	 * @throws SQLException
	 */
	protected ResultSet searchByTransmission(String trans) throws SQLException {
		ResultSet rs = null;
		statement = conn.createStatement();
		rs = statement.executeQuery("SELECT year, make, model, reserved " + "FROM vehicle join vehicle_transmission ON(vehicle.v_id = vehicle_transmission.v_id) "
				+ "WHERE trans_id IN(SELECT trans_id FROM transmission WHERE trans_type = '" + trans + "')");
		return rs;
	}

	/**
	 * SearchByClass queries the SWEET_RIDE database for vehicle information based on the vehicle class
	 * 
	 * @param vehicle
	 *            class
	 * @return Vehicle year, make, model, and reservation status
	 * @throws SQLException
	 */
	protected ResultSet searchByClass(String vClass) throws SQLException {
		ResultSet rs = null;
		statement = conn.createStatement();
		rs = statement.executeQuery("SELECT year, make, model, reserved " + "FROM vehicle join vehicle_class ON(vehicle.v_id = vehicle_class.v_id) " + "WHERE class_id IN(SELECT class_id FROM class WHERE class_type = '"
				+ vClass + "')");
		return rs;
	}

	/**
	 * SearchByZipCode queries the SWEET_RIDE database for vehicle information based on the zip code of where the vehicle is located
	 * 
	 * @param zipCode
	 * @return Vehicle year, make, model, and reservation status
	 * @throws SQLException
	 */
	protected ResultSet searchByZipCode(int zipCode) throws SQLException {
		ResultSet rs = null;
		statement = conn.createStatement();
		rs = statement.executeQuery("SELECT year, make, model, reserved " + "FROM vehicle join vehicle_location ON(vehicle.v_id = vehicle_location.v_id) " + "WHERE l_id IN(SELECT l_id FROM location WHERE zip = '"
				+ zipCode + "')");
		return rs;
	}

	/**
	 * SearchByZipCityState queries the SWEET_RIDE database for vehicle information based on the city and state of where the vehicle is located
	 * 
	 * @param city
	 *            and state
	 * @return Vehicle year, make, model, and reservation status
	 * @throws SQLException
	 */
	protected ResultSet searchByCityState(String city, String state) throws SQLException {
		ResultSet rs = null;
		statement = conn.createStatement();
		rs = statement.executeQuery("SELECT year, make, model, reserved " + "FROM vehicle join vehicle_location ON(vehicle.v_id = vehicle_location.v_id) " + "WHERE l_id IN(SELECT l_id FROM location WHERE city = '"
				+ city + "' AND state = '" + state + "')");
		return rs;
	}

	/**
	 * printVehicles prints vehicle information matching the schema of the vehicle relation in the SWEET_RIDE database
	 * 
	 * @param rs
	 * @throws SQLException
	 */
	protected void printVehicles(ResultSet rs) throws SQLException {
		String available;
		int index = 1;
		while (rs.next()) {
			int year = rs.getInt("year");
			String make = rs.getString("make");
			String model = rs.getString("model");
			boolean reserved = rs.getBoolean("reserved");
			if (reserved) {
				available = "No";
			} else {
				available = "Yes";
			}
			System.out.println(String.format("%-5s %-20s %-20s %-20s %s", index + ":", "Year: " + year, "Make: " + make, "Model: " + model, "Available: " + available));
			index++;
		}
		System.out.println();
	}
	
	protected void printTransOptions() throws SQLException {
	    ResultSet rs = null;
        statement = conn.createStatement();
        rs = statement.executeQuery("SELECT * FROM transmission");
        while (rs.next()) {
            int opt = rs.getInt("trans_id");
            String trans = rs.getString("trans_type");
            System.out.println(opt + ": " + trans);
        }
	}
	
   protected void printClassOptions() throws SQLException {
        ResultSet rs = null;
        statement = conn.createStatement();
        rs = statement.executeQuery("SELECT * FROM class");
        while (rs.next()) {
            int opt = rs.getInt("class_id");
            String vClass = rs.getString("class_type");
            System.out.println(opt + ": " + vClass);
        }
    }
}
