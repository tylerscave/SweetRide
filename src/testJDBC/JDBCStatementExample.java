package testJDBC;
//STEP 1. Import required packages
import java.sql.*;

public class JDBCStatementExample 
{
 // JDBC driver name and database URL
 static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
 static final String DB_URL = "jdbc:mysql://localhost/";

 //  Database credentials
 static final String USER = "root";
 static final String PASS = "";
 private static Connection conn = null;
 private static Statement statement = null;
 
 public static void main(String[] args) throws SQLException
 {
 try
 {

    Class.forName(JDBC_DRIVER); //Register JDBC Driver
    createDatabase(); 
    createTable();
    loadDataIntoTable();
    manipulateData();
    batchUpdate();
 }
 catch(SQLException se){se.printStackTrace(); }
 catch(Exception e){ e.printStackTrace(); }
 finally
 {
    try{ if(statement!=null) statement.close(); }
    catch(SQLException se2){ }// nothing we can do
   
    try{ if(conn!=null) conn.close(); }
    catch(SQLException se){ se.printStackTrace(); }
 }
 System.out.println("Goodbye!");
}//end main

private static void createDatabase() throws SQLException
{  

    // Open a connection
    System.out.println("Connecting to database...");
    conn = DriverManager.getConnection(DB_URL, USER, PASS);

String queryDrop = "DROP DATABASE IF EXISTS CS";
  Statement stmtDrop = conn.createStatement();
  stmtDrop.execute(queryDrop);


    // Create a database named CS
    System.out.println("Creating database...");
    statement = conn.createStatement();
    
    String sql = "CREATE DATABASE CS";
    statement.executeUpdate(sql);
    System.out.println("Database created successfully...");
    
    conn.close();
}
private static void createTable() throws SQLException
{
    // Open a connection and select the database named CS
    
    System.out.println("Connecting to database...");
    conn = DriverManager.getConnection(DB_URL+"CS", USER, PASS);
    statement = conn.createStatement();
    
  String queryDrop = "DROP TABLE IF EXISTS STUDENTS";
  Statement stmtDrop = conn.createStatement();
  stmtDrop.execute(queryDrop);


    String createTableSQL = "CREATE TABLE STUDENTS("
              + "id INTEGER NOT NULL, "
              + "name VARCHAR(20), "
              + "age INTEGER, " 
              + "PRIMARY KEY (ID))";
    statement.execute(createTableSQL); 
    System.out.println("Table called STUDENTS created successfully...");

}
private static void loadDataIntoTable() throws SQLException
{
    System.out.println("Load data from a file students.txt");
    String loadDataSQL = "LOAD DATA LOCAL INFILE 'students.txt' INTO TABLE STUDENTS";
    statement.execute(loadDataSQL); 
}
private static void manipulateData() throws SQLException
{     ResultSet rs = null;

    statement.executeUpdate("INSERT INTO STUDENTS " + "VALUES (789, 'Megan Poulin', 19)");
    statement.executeUpdate("INSERT INTO STUDENTS " + "VALUES (712, 'Kenneth Martinez', 33)");
    System.out.println("After updating Ronald's last name with Lee") ;
    statement.executeUpdate("UPDATE STUDENTS SET name = 'Ronald Lee' WHERE id = 123"); 

    rs = statement.executeQuery("SELECT * FROM STUDENTS"); // The result goes to ResultSet
    printResultSetfromStudents(rs);

    rs = statement.executeQuery("SELECT * FROM STUDENTS WHERE age < 20"); // The result goes to ResultSet
    System.out.println("Students who are younger than 20") ;
    printResultSetfromStudents(rs);
    
    statement.executeUpdate("DELETE FROM STUDENTS WHERE age <20");
    rs = statement.executeQuery("SELECT * FROM STUDENTS"); // The result goes to ResultSet

    System.out.println("After deleting students who are younger than 20") ;
    printResultSetfromStudents(rs);
    
    System.out.println("Ids of students who are older than 30") ;
    rs = statement.executeQuery("SELECT id FROM STUDENTS WHERE age > 30"); 
    //printResultSetfromStudents(rs); // incorrect. it looks for all columns. 

    rs = statement.executeQuery("SELECT * FROM STUDENTS WHERE name LIKE '%ar%'");      
    System.out.println("After finding names including 'ar' in them") ;
    printResultSetfromStudents(rs); 

    System.out.println("Student list sorted by name") ;
    rs = statement.executeQuery("SELECT * FROM STUDENTS ORDER BY name ASC");  
    printResultSetfromStudents(rs);

}
private static void printResultSetfromStudents(ResultSet rs) throws SQLException
{
 while(rs.next())
 {
    int id = rs.getInt("id"); 
    String name = rs.getString("name"); 
    int age = rs.getInt("age");
    System.out.println("ID:" + id + " Name:" + name + " Age:" + age); 
 }
}

private static void batchUpdate() throws SQLException
{
 conn.setAutoCommit(false);
 String sql1 = "INSERT INTO STUDENTS " + "VALUES (495, 'Robert Cliff', 22)";
 String sql2 = "INSERT INTO STUDENTS " + "VALUES (333, 'Toni Smith', 27)";
 String sql3 = "INSERT INTO STUDENTS " + "VALUES (555, 'Robert E.Laskey', 25)";

 statement = conn.createStatement();
 statement.addBatch(sql1);
 statement.addBatch(sql2);
 statement.addBatch(sql3);

 statement.executeBatch();

 System.out.println("Student list") ;
 ResultSet rs = statement.executeQuery("SELECT * FROM STUDENTS");  
 printResultSetfromStudents(rs);

 conn.commit();
}

}//end JDBCExample
