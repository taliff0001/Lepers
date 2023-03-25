package leopardsCheckpointC;

public class Customer implements Comparable<Customer> {

	private int custNum;

	private static int customerNumber = 0;

	private int arrivalTime;

	private int serviceTime;

	private int finishTime;

	private int waitTime;

	private int serviceStartTime;

	private String lane;
	
	public enum ServiceType {FULL, SELF};
	
	private ServiceType serviceType;

	public Customer() {}

	public Customer(int a, int s, ServiceType serviceType) {
		this.arrivalTime = a;
		this.serviceTime = s;
		this.serviceType = serviceType;
		customerNumber++;
		this.custNum = customerNumber;
	}

	public ServiceType getServiceType() {
		return serviceType;
	}

	public void setServiceType(ServiceType serviceType) {
		this.serviceType = serviceType;
	}

	public String toString() {
		String s = "Customer number " + custNum + " Arrival time: " + arrivalTime + " Entered Service Lane: " + lane + " at " + serviceStartTime + "   |   Service time: " + serviceTime + " Finish time: " + finishTime +
				" Wait time: " + waitTime;
		return s;
	}
	
	public int compareTo(Customer c){
		if(this.custNum < c.custNum)
			return -1;
		else if(this.custNum > c.custNum)
			return 1;
		else
			return 0;
	}

	public int getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public int getServiceTime() {
//		if (serviceType == serviceType.SELF) {
//			return (int)(serviceType * (1 + percentageSlower /100.0));
//		}
		return serviceTime;
	}

	public void setServiceTime(int serviceTime) {
		this.serviceTime = serviceTime;
	}

	public int getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(int finishTime) {
		this.finishTime = finishTime;
	}

	public int getWaitTime() {
		return waitTime;
	}

	public void setWaitTime(int waitTime) {
		this.waitTime = waitTime;
	}

	public String getLane() {
		return lane;
	}

	public void setLane(String lane, int time) {
		this.lane = lane;
		System.out.println("Customer " + custNum + " enters lane " + lane + " at " + time);
	}

	public int getServiceStartTime() {
		return serviceStartTime;
	}

	public void setServiceStartTime(int serviceStartTime) {
		this.serviceStartTime = serviceStartTime;
	}

	public int getCustNum() {
		return custNum;
	}

	public void setCustNum(int custNum) {
		this.custNum = custNum;
	}
}