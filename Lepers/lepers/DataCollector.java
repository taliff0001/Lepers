package lepers;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.text.DecimalFormat;

/**
 * The DataCollector class handles the collection and recording of data
 * for the Java Market program
 */


public class DataCollector {
	
	/**
	 *  stores the string representation of the data which will be used
	 *  to generate an HTML file that stores data from the simulation
	 */
	
	private static StringBuffer HTMLString;
	
	/**
	 * keeps track of the total wait time of the customers
	 */
	
	private static int waitTimeTotal;
	
	/**
	 * the total number of customers in the simulation
	 */
	
	private static int numCust;
	
	/**
	 * An ArrayList used to sort the customers by number for the HTML file
	 */
	
	private static ArrayList<Customer> alc;
	
	/**
	 * the number of customers with a wait time under 5 minutes
	 */
	
	private static int satisfied;

	
	private int[] downtimeFull;
	
	private int[] downtimeSelf;
	
	
	/**
	 * no argument constructor that initializes the Customer ArrayList
	 */

	public DataCollector() {		
		alc = new ArrayList<>();
	}
	
	/**
	 * Records data that is stored in the customer objects
	 * @param c the customer whose data is being recorded
	 */
	
	public static void addCustomer(Customer c) {
		recordWaitTime(c);
		alc.add(c);
		
	}
	
	public void addDowntime(int[] f, int[] s) {
		downtimeFull = f;
		downtimeSelf = s;
	}
	
	
	/**
	 * Puts customer data in html format
	 * @param c the customer whose data is being recorded
	 */
	
	public static void logCustomer(Customer c) {
			
		String tableRow = "<tr><td>" + c.getCustNum() + "</td><td>" +c.getLane()  + "</td><td>" + c.getArrivalTime()
		+ "</td><td>" + c.getServiceTime() + "</td><td>" + c.getFinishTime() + "</td><td>" + c.getWaitTime() + "</td></tr>";
		
		HTMLString.append(tableRow);			
	}
	
	/**
	 * Generates the final version of the HTML documents to be saved
	 */
	
	public void saveTable() {
		
		Collections.sort(alc);
		
		HTMLString = new StringBuffer("<html><head><title>Leopards Data</title></head><body><h1>Leopards Data"
				+ "</h1><table><tr><td>Customer ID</td><td>Service Lane</td><td>Arrival Time</td><td>Service Time</td><td>Finish Time</td><td>Wait Time</td></tr>");
		
		for(Customer c: alc) 
			logCustomer(c);
			
		DecimalFormat df = new DecimalFormat("#.##");
		
		HTMLString.append("</table></body>");

		
		HTMLString.append("<h4>Minutes unoccupied for (Full Service): ");
		for(int i=0;i<downtimeFull.length;++i)
			HTMLString.append("Lane " + i + ": " + downtimeFull[i] + " minutes  |  ");
		
		HTMLString.append("<p>Average wait time was "
				+ df.format(avgWaitTime()) + " minutes. Total satisfied customers: " + satisfied + " (wait time < 5)"
						+ "  |  Unsatisfied: " + (numCust - satisfied) +  " (>= 5)</h4></html>");
		
		String html = new String(HTMLString);
		save(html);
	}
	
	/**
	 * Gets the save location and saves the HTML file to disk
	 * @param html the final representation of the data that is saved to disk
	 */
	
	public void save(String html) {
		
		System.out.println("\n-------- REMEMBER TO SAVE YOUR FILE WITH A .HTML EXTENSION --------\n"
				           + "---- AND THAT THE DIALOGUE BOX MAY APPEAR BEHIND OTHER WINDOWS ----");
		
		File HTMLTable = new File(InOut.getWriteLocation());
		PrintWriter p = null;
		
		try {
			p = new PrintWriter(HTMLTable);
			p.print(html);
		}
		
		catch(Exception e){
			System.err.print("Something went wrong! See ya later!!");
			System.exit(0);}
		
		finally { p.close(); 
	}		
	}
	
	
	/**
	 * Stores the sum of the customers' wait times
	 * @param c the current customer
	 */
	
	public static void recordWaitTime(Customer c) {
		
		if(c.getWaitTime() < 5)
			++satisfied;
		
		waitTimeTotal += c.getWaitTime();
		numCust++;
		
	}
	
	/**
	 * Calculates average customer wait time
	 */
	
	public static double avgWaitTime() {
		
		return waitTimeTotal / (double)numCust;
		
	}
	
	
	
	
	
}