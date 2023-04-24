package lepers;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DatabaseInfo {
	static int runID = 0;
	static Connection conn = null;
	static Statement stmt = null;
	static CallableStatement callStmt = null;
	private static Scanner scan = new Scanner(System.in);
	
	//creates connection between eclipse and php
	public static Connection createConnection() {
		
		String user = "DBUser";
		String pass = "DBUser";
		String name = "leopards";
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
	//close connection of database
	public static void closeConnection() {
		if (conn != null) {
			try {
				conn.close();
				conn = null;
				// stmt.close();
				System.out.println("The connection was successfully closed");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	//check connection of database
	public static void checkConnect() {
		if (conn == null) {
			conn = createConnection();
		}
		if (stmt == null) {
			try {
				stmt = conn.createStatement();
			} catch (SQLException e) {
				System.out.println("Cannot create the statement");
			}

		}
	}
	//add information to the database
	public static void addInfo(int runID, int custID, String lane, int arrival, int serveTime, int finishTime, int waitTime) {
		
		String queryAdd = "INSERT INTO java_market(RunID, CustomerID, ServiceLane, "
				+ "ArrivalTime, WaitTime, ServiceTime, FinishTime) VALUES "
				+ "(" +runID + "," + custID + ",'" + lane + "'," + arrival + "," + waitTime + "," +serveTime+ "," + finishTime +")";
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(queryAdd);

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL insert Exception");
		}
	}
	
	public static void selfServeTable(int lane, int unoccupied) {
		double avgWait = DataCollector.avgWaitTimeSelf();
		int satisfied = DataCollector.getSatisfiedSelf();
		int unsatisfied = DataCollector.getNumCustSelf() - satisfied;
		
		String queryAdd = "INSERT INTO self_service_data(serviceLane, unoccupiedTime, avgWaitTime, satisfiedCust, "
				+ "unsatisfiedCust) VALUES "
				+ "('" + lane + "',"+ unoccupied + "," + avgWait + "," + satisfied + "," + unsatisfied+")";
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(queryAdd);

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL insert Exception");
		}
	}
	
	public static void fullServeTable(int lane, int unoccupied) {
		double avgWait = DataCollector.avgWaitTimeFull();
		int satisfied = DataCollector.getSatisfiedFull();
		int unsatisfied = DataCollector.getNumCustFull() - satisfied;
		
		String queryAdd = "INSERT INTO full_service_data(serviceLane, unoccupiedTime, avgWaitTime, satisfiedCust, "
				+ "unsatisfiedCust) VALUES "
				+ "('" + lane + "',"+ unoccupied + "," + avgWait + "," + satisfied + "," + unsatisfied+")";
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(queryAdd);

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL insert Exception");
		}
	}
	
	public static void callProcedures(Lanes full, SelfService self) {
		System.out.println("Would you like to run a procedure? (y / n)");
		String desc = scan.nextLine();
		if (desc.equalsIgnoreCase("y")) {
			menu();
			System.out.println("Please enter a choice: ");
			int choice = scan.nextInt();
			scan.nextLine();
			
			if (choice == 1) {
				System.out.println("Do you want Self-Service or Full-Service");
				System.out.println("Type SS or FS: ");
				String serviceChoice = scan.nextLine();
				if (serviceChoice.equalsIgnoreCase("SS")){
					System.out.println("Which lane do you want to see? ");
					int laneNum = scan.nextInt();
					
					if (laneNum <= self.getRegisters().length) {
						
					}
				}
			}
			else if (choice == 2) {
				
			}
			else if (choice == 3) {
				
			}
			
			
		}
	}
	
	public static void menu() {
		System.out.println("1. Show the average statistics of a desired lane: ");
		System.out.println("2. Show the average statistics of every lane:");
		System.out.println("3. Show the statistics of a customer given their ID: ");
	}
	
	public static int queryRunID() {
		checkConnect();
		String stored = "SELECT MAX(RunID) FROM java_market ";
		
		try {
			
			callStmt =  conn.prepareCall(stored);
			ResultSet rs = callStmt.executeQuery();
			while (rs.next()) {
				runID = rs.getInt("RunID");
			}
			return runID;
		}
		catch(SQLException e) {
			System.out.println("SQL Exception");
			e.printStackTrace();
		}
		return runID;
		
		
	}
	
	
	
}
