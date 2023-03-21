package leopardsCheckpoint2;

import java.util.Scanner;

/**
 * Simulator class simulates the famous JavaMarket. Customers get created along with
 * three lanes that the customers can use. Customers are designated to shortest line
 * of each lane.
 * @author Andrew, Tommy, Josh, Calvin
 *
 */
public class SimulatorV2 {

	/**
	 * The main method which executes the simulation.
	 * 
	 */
	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		System.out.println("Save simulation data to disk?(y/n) ");		
		String save = scan.nextLine();
		System.out.println("How many full-service lanes? ");
		int fullServ = scan.nextInt();
		System.out.println("How many self-service lanes? ");
		int selfServ = scan.nextInt();
		
		//Construct the customer generator based on the given parameters
		
		CustomerCreator customerCreator = getParameters();

		
		//Create the three service lanes
//		CustomerList A = new CustomerList();
//		CustomerList B = new CustomerList();
//		CustomerList C = new CustomerList();
		Lanes lanes = new Lanes(fullServ);
		SelfService selfService = new SelfService(selfServ);
		
		//Initialize the clock and enter the main loop
		
		int time=0;
		
		//Keeps track of when it is time for a customer to arrive and enter a line
		
		int custRemaining = CustomerCreator.getNumCust();
		
		boolean arrive = false;
		
		//Variables to store customers entering and leaving
		
		Customer cust = null; 
//		Customer cust1 = null;
		
		DataCollector collectData = new DataCollector();
		
		for(int i=0;i<500;++i) { 
					
			//If cust is null it means it's time to check the queue of entering customers for
			//the next customer in line.
			
			if(cust == null)
				
				//Only get the next customer if there is one
				
				if(custRemaining != 0) {
					cust = CustomerCreator.next();
					cust.flipCoinForServiceType();
				}
				//The loop is done when there are no customers in the main queue
				//or in any of the lanes. All data is stored in an html table at
				//a location chosen by the user
			
				else if (lanes.areEmpty() && selfService.isEmpty()) {
					System.out.println("Finished at " + (time-1));
					
					if(save.equals("y"))
						collectData.saveTable();
					
					System.exit(0);				
			}				
					
			lanes.checkDepartures(time);
			
//			if(!A.isEmpty() && A.frontPeek().getFinishTime()==time) {
//				cust1 = A.removeLast();
//				DataCollector.addCustomer(cust1);
//				System.out.println(cust1.getCustNum() + " finishes at " + time);
//			}
//			if(!B.isEmpty() && B.frontPeek().getFinishTime()==time) {
//				cust1 = B.removeLast();
//				DataCollector.addCustomer(cust1);
//				System.out.println(cust1.getCustNum() + " finishes at " + time);
//				
//			}
//			if(!C.isEmpty() && C.frontPeek().getFinishTime()==time) {
//				cust1 = C.removeLast();
//				DataCollector.addCustomer(cust1);
//				System.out.println(cust1.getCustNum() + " finishes at " + time);
//			}			
			
			
			//Put customer in the queue with the shortest line when it is their arrival time;
			//If lines are of equal length put in the first available line alphabetically.
			//Record the numbers.
			
			//Set shortest line to first line and save the number
			
			arrive = checkArrival(cust, time);
			if(arrive) { 	
				
				if(cust.getFullorSelf().equals("f"))
					lanes.addCustomer(cust, time);
				else
					selfService.addCustomer(cust, time);
				
				cust = null;
//			if (A.isEmpty() || !B.isEmpty() && !C.isEmpty() && A.size() <= B.size() && A.size() <= C.size()) {
//				cust.setServiceStartTime(time);
//				cust.setLane("A", time);
//				setFinish(cust, A);
//				setWait(cust, A);
//				A.addFirst(cust);
//				}
//			else if(B.isEmpty() || !C.isEmpty() && B.size() <= C.size()) {
//				cust.setServiceStartTime(time);
//				cust.setLane("B", time);
//				setFinish(cust, B);
//				setWait(cust, B);
//				B.addFirst(cust); 
//			}
//			else {
//				cust.setServiceStartTime(time);
//				cust.setLane("C", time);
//				setFinish(cust, C);
//				setWait(cust, C);
//				C.addFirst(cust);
//					
//				}

				//Every time a customer arrives and is placed in a service lane
				//the cust variable is reset to null to signal that the queue of
				//entering customers should be checked for new arrivals
				
//				cust = null;
				
				//Decrement the remaining customer variable every time a customer
				//is placed in a lane
				
				--custRemaining;
			}
			
			//Lanes are checked here and if unoccupied the time is recorded
			
			
			System.out.println("Time: " + time);
//			collectData.checkEmpty(A, B, C);        NEED TO ALTER THIS
			lanes.emptyCheck(collectData);
			++time;
			
		} //End main loop

	} //End main
	
	/**
	 * Sets the wait time of the customer for the end of loop.
	 * @return a CustomerCreator object initialized using the
	 * desired parameters
	 */
	
	@SuppressWarnings("resource")
	public static CustomerCreator getParameters() {
		
		Scanner scan = new Scanner(System.in);
		System.out.println("Load default values for testing?(y/n)");
		String d = scan.nextLine();
		if(d.equals("y"))
			return new CustomerCreator(1,3,3,9,200);		
		System.out.println("Min arrival time between customers: ");
		int minA = scan.nextInt();
		System.out.println("Max arrival time between customers: ");
		int maxA = scan.nextInt();
		System.out.println("Minimum service time: ");
		int minS = scan.nextInt();
		System.out.println("Maximum service time: ");
		int maxS = scan.nextInt();
		System.out.println("Number of customers: ");
		int numCust = scan.nextInt();
		scan.nextLine();
		
		System.out.println("\n\n-----------------------------------------------------------\n\n");
		
		CustomerCreator cc = new CustomerCreator(minA, maxA, minS, maxS, numCust);
		scan.close();
		return cc;
	}
	
	
	
	/**
	 * Sets the total/finish time for a specific customer of loop.
	 * @param cust customer you wish to set finishTime
	 * @param a the lane of which the customer is in.
	 */
	public static void setFinish(Customer cust, CustomerList a) {
		
		int finishTime = 0;
		
		if (!a.isEmpty())
			finishTime = a.rearPeek().getFinishTime() + cust.getServiceTime();
		else
			finishTime = cust.getArrivalTime() + cust.getServiceTime();
		
		cust.setFinishTime(finishTime);
		
	}
	
	/**
	 * Sets the wait time of the customer for the end of loop.
	 * @param cust customer we wish to set the wait time for
	 * @param a the lane of which the customer is currently in.
	 */
	public static void setWait(Customer cust, CustomerList a) {
		
		int waitTime;
		
		if(a.isEmpty())
			waitTime = 0; //cust.getServiceTime();
		else
			waitTime = a.rearPeek().getFinishTime() - cust.getArrivalTime();
		
		cust.setWaitTime(waitTime);
		
	}
	/**
	 * Check if its time for the customer to arrive, if it is, then allow the customer in.
	 * @param c customer that is being added
	 * @param t the current time in the simulation
	 * @return a true/false whether or not a customer has arrived.
	 */
	public static boolean checkArrival(Customer c, int t) {
		if (c != null && c.getArrivalTime()==t) //When time to arrive set boolean to true
			return true;
		else
			return false;
	}
	
} //End class










