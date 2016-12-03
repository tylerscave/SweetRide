package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.GregorianCalendar;

import com.mysql.jdbc.PreparedStatement;

import javafx.util.Pair;
/**
 *COPYRIGHT (C) 2016 SweetRide. All Rights Reserved.
 * AdminSystem is responsible for handling all functions
 * that are accessible to the Administrator
 * Solves CS157A Project
 * @authors Tyler Jones, Jonathan Chen ,Vinay Patel
 */
public class AdminSystem {
	private static Connection conn = null;
	private static Statement statement = null;
	
	
	/**
	 * Constructor for UserSystem
	 */
	public AdminSystem() {
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
	 * Admin Login
	 * 
	 * @param email
	 * @param pwd
	 * @return Account details for the loggedin user
	 * @throws SQLException
	 */
	protected ResultSet loginAccount(String email, String pwd) throws SQLException {
		ResultSet rs = null;
		statement = conn.createStatement();
		System.out.println(email + "<>" + pwd);
		rs = statement.executeQuery(String.format("SELECT * FROM admin WHERE email='%s' AND pwd='%s'", email, pwd));
		return rs;
	}
	
	
	
	/**
	 * Admin will be able to add new rental cars into system
	 * @param year
	 * @param make
	 * @param model
	 * @param transmission
	 * @param locationId
	 * @param vehicle_class
	 * @return 1 if insert successful, 0 if insert fails
	 * @throws SQLException
	 */
	protected int insertNewVehicle(int year, String make, String model, int transType, int locationId, int classType) throws SQLException {
		ResultSet rs = null;
		statement = conn.createStatement();
		
		//inserting Car into the vehicle table
		String insertSQL = "INSERT INTO VEHICLE"
				+ "(YEAR, MAKE, MODEL) VALUES"
				+ "(?,?,?)";
		PreparedStatement preparedStatement = (PreparedStatement) conn.prepareStatement(insertSQL);
		preparedStatement.setInt(1, year);
		preparedStatement.setString(2, make);
		preparedStatement.setString(3, model);
		// execute insert SQL stetement
		
		if(preparedStatement.executeUpdate() == 1){
			//insert is ok, and get the v_id to be used else where
			rs = statement.executeQuery("SELECT MAX(v_id) FROM VEHICLE");
			if(rs.next()) {
				int vId = rs.getInt(1);
			
			//insert into veh_trans table
			insertSQL = "INSERT INTO VEHICLE_TRANSMISSION"
					+ "(V_ID, TRANS_ID) VALUEs"
					+ "(?,?)";
			PreparedStatement vehicleTransStatement = (PreparedStatement) conn.prepareStatement(insertSQL);
			vehicleTransStatement.setInt(1, vId);
			vehicleTransStatement.setInt(2, transType);
			vehicleTransStatement.executeUpdate();
			
			//insert into veh_location table
			insertSQL = "INSERT INTO VEHICLE_LOCATION"
					+ "(V_ID, L_ID) VALUEs"
					+ "(?,?)";
			PreparedStatement vehicleLocationStatement = (PreparedStatement) conn.prepareStatement(insertSQL);
			vehicleLocationStatement.setInt(1, vId);
			vehicleLocationStatement.setInt(2, locationId);
			vehicleLocationStatement.executeUpdate();
			
			//insert into veh_location table
			insertSQL = "INSERT INTO VEHICLE_CLASS"
					+ "(V_ID, CLASS_ID) VALUEs"
					+ "(?,?)";
			PreparedStatement vehicleClassStatement = (PreparedStatement) conn.prepareStatement(insertSQL);
			vehicleClassStatement.setInt(1, vId);
			vehicleClassStatement.setInt(2, locationId);
			vehicleClassStatement.executeUpdate();
			}
			
		}else{
			// bad insert
		}
		
		return 1;
	}
	
	/**
	 * Admin will be able to remove old rental cars from system
	 * @param year
	 * @param make
	 * @param model
	 * @param location
	 * @param transmission
	 * @return 1 if insert successful, 0 if insert fails
	 * @throws SQLException
	 */
	protected int deleteVehicle(int v_id) throws SQLException {
		ResultSet rs = null;
		String deleteSQL;

		deleteSQL= "DELETE FROM vehicle_transmission"
				+ " WHERE v_id=?";
		PreparedStatement preparedStatement = (PreparedStatement) conn.prepareStatement(deleteSQL);
		preparedStatement.setInt(1, v_id);
		preparedStatement.executeUpdate();
		
		
		deleteSQL= "DELETE FROM vehicle_class"
				+ " WHERE v_id=?";
		preparedStatement = (PreparedStatement) conn.prepareStatement(deleteSQL);
		preparedStatement.setInt(1, v_id);
		preparedStatement.executeUpdate();
		
		deleteSQL= "DELETE FROM vehicle_location"
				+ " WHERE v_id=?";
		preparedStatement = (PreparedStatement) conn.prepareStatement(deleteSQL);
		preparedStatement.setInt(1, v_id);
		preparedStatement.executeUpdate();
		
		deleteSQL= "DELETE FROM vehicle"
				+ " WHERE v_id=?";
		preparedStatement = (PreparedStatement) conn.prepareStatement(deleteSQL);
		preparedStatement.setInt(1, v_id);
		preparedStatement.executeUpdate();
		// execute insert SQL stetement
		return preparedStatement.executeUpdate();
	}
	
	/**
	 * Admin will be able to tranfer cars from one location to another
	 * @param car(v_id)
	 * @param newLocation(l_id)
	 * @throws SQLException
	 */
	protected int transferVehicle(int v_id, int l_id) throws SQLException {
		ResultSet rs = null;
		String insertSQL = "UPDATE vehicle_location"
				+ " SET l_id=?"
				+ " WHERE v_id=?";
		PreparedStatement preparedStatement = (PreparedStatement) conn.prepareStatement(insertSQL);
		preparedStatement.setInt(1, l_id);
		preparedStatement.setInt(2, v_id);
		// execute insert SQL stetement
		return preparedStatement.executeUpdate();
	}
	
	/**
	 * Adding a new location to location list
	 * @return
	 * @throws SQLException
	 */
	protected int insertNewLocation(String location_name, String street, String city, String state, int zip){
		try {
		ResultSet rs = null;
		statement = conn.createStatement();
		//inserting Car into the vehicle table
		String insertSQL = "INSERT INTO location"
				+ "(location_name, street, city, state, zip) VALUES"
				+ "(?,?,?,?,?)";
		PreparedStatement preparedStatement = (PreparedStatement) conn.prepareStatement(insertSQL);
		preparedStatement.setString(1, location_name);
		preparedStatement.setString(2, street);
		preparedStatement.setString(3, city);
		preparedStatement.setString(4, state);
		preparedStatement.setInt(5, zip);
		
			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	protected ResultSet getLocationList() throws SQLException{
		return statement.executeQuery("SELECT location_name FROM location;");
	}
	
	protected ResultSet getCurVehicleList() throws SQLException{
		return statement.executeQuery("SELECT * FROM vehicle WHERE reserved=0");
	}
	
	protected int locationCount(){
		try{
			ResultSet rs = statement.executeQuery("SELECT COUNT(*) from location");
			if(rs.next()){
				return rs.getInt("count(*)");
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return -1;
	}
	
	protected int vehicleCount(){
		try{
			ResultSet rs = statement.executeQuery("SELECT COUNT(*) from vehicle");
			if(rs.next()){
				return rs.getInt("count(*)");
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return -1;
	}
	 
	 
	
	
}
