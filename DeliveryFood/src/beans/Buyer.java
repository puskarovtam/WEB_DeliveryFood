package beans;

import java.util.ArrayList;

public class Buyer extends User {

	private static final long serialVersionUID = 1355009881595320948L;

	private ArrayList<Order> orders = new ArrayList<Order>();

	public Buyer() {
		super();
		this.setRole("BUYER");
	}

	public ArrayList<Order> getOrders() {
		return orders;
	}

	public void setOrders(ArrayList<Order> orders) {
		this.orders = orders;
	}

}
