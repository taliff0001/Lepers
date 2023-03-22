package lepers;

public class SelfService {

	private CustomerList queue;
	private Register[] registers;
	private int[] downtime;
	

	public SelfService(int numRegisters) {
		queue = new CustomerList();
		downtime = new int[numRegisters];
		registers = new Register[numRegisters];
		for (int i=0;i<registers.length;++i)
			registers[i] = new Register();
	}

	public boolean isEmpty() {
		boolean occupied = false;
		for (Register r : registers)
			if (r.isOccupied())
				occupied = true;
		if (queue.isEmpty() && occupied == false)
			return true;
		else
			return false;
	}

	public void addCustomer(Customer cust, int time) {
		// add percentage here..DUH
		queue.addFirst(cust);		
		for(int i=0;i<registers.length;++i)
			if(!registers[i].isOccupied() && !queue.isEmpty()) {
				Customer nextInLine = queue.removeLast();
				nextInLine.setServiceStartTime(time);
				nextInLine.setLane("SS " + i, time);
				nextInLine.setFinishTime(time + nextInLine.getServiceTime());
				nextInLine.setWaitTime(time - nextInLine.getArrivalTime());
				registers[i].setCust(nextInLine);
				registers[i].setOccupied(true);
			}

	}

	public void checkDepartures(int time) {
		Customer cust;
		for (int i = 0; i < registers.length; ++i) {
			if(registers[i].isOccupied())
				if (registers[i].getCust().getFinishTime() == time) {
					cust = registers[i].getCust();
					DataCollector.addCustomer(cust);
					System.out.println(cust.getCustNum() + " finishes at " + time);
					registers[i].setOccupied(false);
			}
		}
	}

	public void emptyCheck() {
		for(int i=0;i<registers.length;++i)
			if(!registers[i].isOccupied())
				++downtime[i];
	}
}












