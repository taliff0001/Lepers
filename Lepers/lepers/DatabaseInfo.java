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
	public static void addInfo(int custID, String lane, int arrival, int serveTime, int finishTime, int waitTime) {
		String queryAdd = "INSERT INTO java_market(CustomerID, ServiceLane, "
				+ "ArrivalTime, WaitTime, ServiceTime, FinishTime) VALUES "
				+ "(" + custID + ",'" + lane + "'," + arrival + "," + waitTime + "," +serveTime+ "," + finishTime +")";
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
	
	
	
}
