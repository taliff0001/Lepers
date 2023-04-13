package lepers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DatabaseInfo {
	static Connection conn = null;
	static Statement stmt = null;
	private static Scanner scan = new Scanner(System.in);
	
	//creates connection between eclipse and php
	public static Connection createConnection() {
		
		String user = "DBUser";
		String pass = "DBUser";
		String name = "java market";
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/" + name;

		System.out.println(driver);
		System.out.println(url);

		try {
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(url, user, pass);
			System.out.println("Connection really is from : " + conn.getClass().getName());
			System.out.println("Connection successful!");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
		
	}
	
	//close connection once finished
	public static void closeConnection() {
		if (conn != null) {
			try {
				conn.close();
				conn = null;
				System.out.println("The connection has been succesfully closed.");
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	//check the connection between eclipse and php
	public static void checkConnection() {
		if (conn == null) {
			conn = createConnection();
		}
		if (stmt == null) {
			try {
				stmt = conn.createStatement();
			}catch(SQLException e) {
				System.out.println("Statement cannot be created.");
			}
		}
	}
	//add information to the database
	public static void addInfo(int custID, String lane, int arrival, int serveTime, int finishTime, int waitTime) {
		String queryAdd = "INSERT INTO leopards data(CustomerID, Service Lane, "
				+ "Arrival Time, Service Time, Finish Time, Wait Time) VALUES "
				+ "('" + custID + "'," + lane + "," + arrival + "'," + serveTime + "'," +finishTime+ "'," +waitTime +")";
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(queryAdd);

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL insert Exception");
		}
	}
	
	
	
	
}
