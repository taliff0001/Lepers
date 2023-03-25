package leopardsCheckpointC;

import java.util.NoSuchElementException;

public class CustomerList {
    private Node first;  // list head
    private Node last;   // last element in list
    private Customer.ServiceType serviceType;
    private class Node {
        Customer value;
        Node next;

        Node(Customer val, Node n) {
            value = val;
            next = n;
        }

        Node(Customer val) {
            this(val, null);
        }
    }

    public CustomerList() {
        first = null;
        last = null;
    }
    public CustomerList(Customer.ServiceType serviceType) {
        first = null;
        last = null;
        this.setServiceType(serviceType);
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        int count = 0;
        Node p = first;
        while (p != null) {
            count++;
            p = p.next;
        }
        return count;
    }

    public void add(Customer e) {
        if (isEmpty()) {
            first = new Node(e);
            last = first;
        } else {
            last.next = new Node(e);
            last = last.next;
        }
    }

    public void addFirst(Customer e) {
        if (isEmpty()) {
            first = new Node(e);
            last = first;
        } else {
            first = new Node(e, first);
        }
    }

    public Customer removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Customer value;
        if (first == last) {
            value = first.value;
            first = null;
            last = null;
        } else {
            Node p = first;
            while (p.next != last) {
                p = p.next;
            }
            value = last.value;
            last = p;
            last.next = null;
        }
        return value;
    }

    public Customer frontPeak() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return last.value;
    }

    public Customer rearPeak() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return first.value;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node p = first;
        while (p != null) {
            sb.append(p.value);
            if (p != last) {
                sb.append(", ");
            }
            p = p.next;
        }
        return "[" + sb.toString() + "]";
    }
	public Customer.ServiceType getServiceType() {
		return serviceType;
	}
	public void setServiceType(Customer.ServiceType serviceType) {
		this.serviceType = serviceType;
	}
}