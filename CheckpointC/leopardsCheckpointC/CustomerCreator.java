package leopardsCheckpointC;

import java.util.Random;

public class CustomerCreator {

	static int minArrivalTime;

	static int maxArrivalTime;

	static int minServiceTime;

	static int maxServiceTime;

	static int previousArrivalTime;
	
	static int numCust;
	
	static double percentageSlower;
	
	static boolean firstCust;

	public CustomerCreator() {
		
	}
	
	public CustomerCreator(int minAT, int maxAT, int minST, int maxST, int n, double ps) {
		minArrivalTime = minAT;
		maxArrivalTime = maxAT;
		minServiceTime = minST;
		maxServiceTime = maxST;
		numCust = n;
		percentageSlower = ps;
		previousArrivalTime = 0;
		firstCust=true;
	}
	
		public static Customer next() {
			Random rand = new Random();
			
	        int arrivalTime = rand.nextInt(maxArrivalTime - minArrivalTime + 1) + minArrivalTime;
	        int serviceTime = rand.nextInt(maxServiceTime - minServiceTime + 1) + minServiceTime;
	        
			arrivalTime += previousArrivalTime;
			previousArrivalTime = arrivalTime;
			Customer.ServiceType serviceType = (Math.random()<0.5) ? Customer.ServiceType.FULL : Customer.ServiceType.SELF;
			if (serviceType == Customer.ServiceType.SELF) {
			    // modify service time based on percentage slower for self-checkout lanes
			    serviceTime = (int) (serviceTime * (1.0 + percentageSlower));
			}
			Customer cust = new Customer(arrivalTime, serviceTime, serviceType);
			if(firstCust==true) {
				cust.setArrivalTime(0);
				previousArrivalTime=0;
				firstCust=false;
			}
			return cust;
			
		}
	
	public static int getNumCust() {
		return numCust;
	}
}
