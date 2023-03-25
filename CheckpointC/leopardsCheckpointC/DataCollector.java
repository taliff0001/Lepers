package leopardsCheckpointC;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.text.DecimalFormat;

/**
 * The DataCollector class handles the collection and recording of data for the
 * Java Market program
 */

public class DataCollector {

	/**
	 * stores the string representation of the data which will be used to generate
	 * an HTML file that stores data from the simulation
	 */

	private static StringBuffer HTMLString;

	/**
	 * keeps track of the amount of time lane A is unoccupied
	 */

	private int downtimeA;

	/**
	 * keeps track of the amount of time lane B is unoccupied
	 */

	private int downtimeB;

	/**
	 * keeps track of the amount of time lane C is unoccupied
	 */

	private int downtimeC;

	/**
	 * keeps track of the total wait time of the customers
	 */
	private int downtimeD;
	private int downtimeE;
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

	private static int numFullCust;
	private static int numSelfCust;
	private static int fullWaitTimeTotal;
	private static int selfWaitTimeTotal;

	/**
	 * no argument constructor that initializes the Customer ArrayList
	 */

	public DataCollector() {
		alc = new ArrayList<>();
	}

	/**
	 * Records data that is stored in the customer objects
	 * 
	 * @param c the customer whose data is being recorded
	 */

	public static void addCustomer(Customer c) {
		if (c.getServiceType() == Customer.ServiceType.FULL)
			recordFullWaitTime(c);
		else {
			recordSelfWaitTime(c);
		}
		countCust(c);
		alc.add(c);

	}

	/**
	 * Puts customer data in html format
	 * 
	 * @param c the customer whose data is being recorded
	 */

	public static void logCustomer(Customer c) {

		String tableRow = "<tr><td>" + c.getCustNum() + "</td><td>" + c.getLane() + "</td><td>" + c.getArrivalTime()
				+ "</td><td>" + c.getServiceTime() + "</td><td>" + c.getFinishTime() + "</td><td>" + c.getWaitTime()
				+ "</td></tr>";

		HTMLString.append(tableRow);
	}

	/**
	 * Generates the final version of the HTML documents to be saved
	 */

	public void saveTable() {

		Collections.sort(alc);

		HTMLString = new StringBuffer(
				"<html><head><title>Leopards Data Checkpoint B</title></head><body><h1>Leopards Data Checkpoint B"
						+ "</h1><table><tr><td>Customer ID</td><td>Service Lane</td><td>Arrival Time</td><td>Service Time</td><td>Finish Time</td><td>Wait Time</td></tr>");

		for (Customer c : alc)
			logCustomer(c);

		DecimalFormat df = new DecimalFormat("#.##");

		HTMLString.append("</table></body>");
		HTMLString.append("<h4>Minutes unoccupied for: Lane A: " + (downtimeA - 1) + " minutes  | Lane B: "
				+ (downtimeB - 1) + " minutes  |  Lane C: " + (downtimeC - 1) + " minutes | Lane D: " + (downtimeD - 1)
				+ " minutes  |  Lane E: " + (downtimeE - 1) + " minutes <p> FULL Service Lane Average wait time was "
				+ df.format(fullAvgWaitTime()) + " minutes. SELF Service Lane Average wait time was "
				+ df.format(selfAvgWaitTime()) + " <p> Total satisfied customers: " + satisfied + " (wait time < 5)"
				+ "  |  Unsatisfied: " + (numCust - satisfied) + " (>= 5)</h4></html>");
		String html = new String(HTMLString);
		save(html);
	}

	/**
	 * Gets the save location and saves the HTML file to disk
	 * 
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

		catch (Exception e) {
			System.err.print("Something went wrong! See ya later!!");
			System.exit(0);
		}

		finally {
			p.close();
		}
	}

	/**
	 * Checks whether each lane is occupied and increments the downtime variable
	 * whenever they are not
	 * 
	 * @param a lane to be checked
	 * @param b lane to be checked
	 * @param c lane to be checked
	 * @param d lane to be checked
	 * @param e lane to be checked
	 */

	public void checkEmpty(CustomerList a, CustomerList b, CustomerList c, CustomerList d, CustomerList e) {

		if (a.isEmpty())
			++downtimeA;

		if (b.isEmpty())
			++downtimeB;

		if (c.isEmpty())
			++downtimeC;

		if (d.isEmpty())
			++downtimeD;

		if (e.isEmpty())
			++downtimeE;

	}

	/**
	 * Stores the sum of the customers' wait times
	 * 
	 * @param c the current customer
	 */

	public static void recordFullWaitTime(Customer c) {
		fullWaitTimeTotal += c.getWaitTime();
		numFullCust++;
	}
	public static void recordSelfWaitTime(Customer c) {
		selfWaitTimeTotal += c.getWaitTime();
		numSelfCust++;
	}

	public static void countCust(Customer c) {
		if (c.getWaitTime() < 5)
			++satisfied;
		numCust++;
	}

	/**
	 * Calculates average customer wait time
	 */

	public double fullAvgWaitTime() {
		return fullWaitTimeTotal / (double) numFullCust;
	}

	public double selfAvgWaitTime() {
		return selfWaitTimeTotal / (double) numSelfCust;

	}

}