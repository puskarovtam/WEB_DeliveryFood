package beans;

import java.util.ArrayList;

public class DeliveryMan extends User {

	private static final long serialVersionUID = -383542320384946097L;
	
	private ArrayList<Order> ordersForDelivering = new ArrayList<Order>();

	public DeliveryMan() {
		super();
		this.setRole("DELIVERY MAN");
	}

	public ArrayList<Order> getOrdersForDelivering() {
		return ordersForDelivering;
	}

	public void setOrdersForDelivering(ArrayList<Order> ordersForDelivering) {
		this.ordersForDelivering = ordersForDelivering;
	}
	
	
	

}
