package lepers;

public class SelfService extends Lanes{
	
	CustomerList queue;
	
	public SelfService(int numLanes) {
			super(numLanes);
			queue = new CustomerList();
	}
	public boolean isEmpty() {
		if(this.areEmpty() && queue.isEmpty())
			return true;
		else
			return false;
	}
	@Override
	public void addCustomer(Customer cust, int time) {
		//queue.add(cust);
	}
}
