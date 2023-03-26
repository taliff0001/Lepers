package leopardsCheckpointC;

import java.util.Scanner;

public class Simulator {

	public static void main(String[] args) {

		CustomerCreator customerCreator = getParameters();
		Scanner scan = new Scanner(System.in);
		System.out.println("Save a copy of the data to disk?(Type 1 for yes) ");
		int save = scan.nextInt();
		
		CustomerList A = new CustomerList(Customer.ServiceType.FULL);
		CustomerList B = new CustomerList(Customer.ServiceType.FULL);
		CustomerList C = new CustomerList(Customer.ServiceType.FULL);
		CustomerList D = new CustomerList(Customer.ServiceType.SELF);
		CustomerList E = new CustomerList(Customer.ServiceType.SELF);

		int time = 0;
		int custRemaining = CustomerCreator.getNumCust();
		boolean arrive = false;
		Customer cust = null;
		Customer cust1 = null;
		DataCollector collectData = new DataCollector();

		for (;;) {
			if (cust == null)
				if (custRemaining != 0)
					cust = CustomerCreator.next();
				else if (A.isEmpty() && B.isEmpty() && C.isEmpty() && D.isEmpty() && E.isEmpty()) {
					System.out.println("Finished at " + (time - 1));
					

					if(save==1)
						collectData.saveTable();
					System.exit(0);
				}

			if (!A.isEmpty() && A.frontPeak().getFinishTime() == time) {
				cust1 = A.removeLast();
				DataCollector.addCustomer(cust1);
				System.out.println(cust1.toString());
			}
			if (!B.isEmpty() && B.frontPeak().getFinishTime() == time) {
				cust1 = B.removeLast();
				DataCollector.addCustomer(cust1);
				System.out.println(cust1.toString());
			}
			if (!C.isEmpty() && C.frontPeak().getFinishTime() == time) {
				cust1 = C.removeLast();
				DataCollector.addCustomer(cust1);
				System.out.println(cust1.toString());
			}
			if (!D.isEmpty() && D.frontPeak().getFinishTime() == time) {
				cust1 = D.removeLast();
				DataCollector.addCustomer(cust1);
				System.out.println(cust1.toString());
			}
			if (!E.isEmpty() && E.frontPeak().getFinishTime() == time) {
				cust1 = E.removeLast();
				DataCollector.addCustomer(cust1);
				System.out.println(cust1.toString());
			}

			arrive = checkArrival(cust, time);
			if (arrive) {
				if (cust.getServiceType() == Customer.ServiceType.FULL) {
					if (A.isEmpty() || !B.isEmpty() && !C.isEmpty() && A.size() <= B.size() && A.size() <= C.size()) {
						cust.setServiceStartTime(time);
						cust.setLane("A", time);
						setFinish(cust, A);
						setWait(cust, A);
						A.addFirst(cust);
					} else if (B.isEmpty() || !C.isEmpty() && B.size() <= C.size()) {
						cust.setServiceStartTime(time);
						cust.setLane("B", time);
						setFinish(cust, B);
						setWait(cust, B);
						B.addFirst(cust);
					} else {
						cust.setServiceStartTime(time);
						cust.setLane("C", time);
						setFinish(cust, C);
						setWait(cust, C);
						C.addFirst(cust);

					}
				} else { //SELF checkout Lane
					if (D.isEmpty() || !E.isEmpty() && D.size() <= E.size()) {
						cust.setServiceStartTime(time);
						cust.setLane("D", time);
						setFinish(cust, D);
						setWait(cust, D);
						D.addFirst(cust);
					} else {
						cust.setServiceStartTime(time);
						cust.setLane("E", time);
						setFinish(cust, E);
						setWait(cust, E);
						E.addFirst(cust);
					}
				}
				cust = null;
				--custRemaining;
			}
			
			
			
			collectData.checkEmpty(A, B, C, D, E);
			// System.out.println("Time: " + time);
			++time;
		} // End main loop
	} // End main

	public static CustomerCreator getParameters() {

		Scanner scan = new Scanner(System.in);
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
		System.out.println("Enter the percentage slower for SELF checkout lanes (EX: 0.75): ");
		double selfPercentageSlower = scan.nextDouble();
		
		CustomerCreator cc = new CustomerCreator(minA, maxA, minS, maxS, numCust, selfPercentageSlower);
		DataCollector.addParameters(minA, maxA, minS, maxS, selfPercentageSlower);
		return cc;
	}

	public static void setFinish(Customer cust, CustomerList a) {

		int finishTime = 0;

		if (!a.isEmpty())
			finishTime = a.rearPeak().getFinishTime() + cust.getServiceTime();
		else
			finishTime = cust.getArrivalTime() + cust.getServiceTime();

		cust.setFinishTime(finishTime);

	}

	public static void setWait(Customer cust, CustomerList a) {

		int waitTime;

		if (a.isEmpty())
			waitTime = 0; // cust.getServiceTime();
		else
			waitTime = a.rearPeak().getFinishTime() - cust.getArrivalTime();

		cust.setWaitTime(waitTime);

	}

	public static boolean checkArrival(Customer c, int t) {
		if (c != null && c.getArrivalTime() == t) // When time to arrive set boolean to true
			return true;
		else
			return false;
	}
} // End class