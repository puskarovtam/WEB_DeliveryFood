package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.Order;

public class OrderDAO {

	private HashMap<Long, Order> orders;
	private String contextPath;

	public OrderDAO(String contextPath) {
		super();
		this.contextPath = contextPath;
		this.orders = new HashMap<Long, Order>();
		loadOrders();
	}

	public HashMap<Long, Order> getOrders() {
		return orders;
	}

	public void setOrders(HashMap<Long, Order> orders) {
		this.orders = orders;
	}

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	public void loadOrders() {
		orders.clear();

		ObjectMapper objectMapper = new ObjectMapper();
		String line = "";

		ArrayList<Order> orderList = new ArrayList<Order>();
		String order = "";

		File orderFile = new File(this.contextPath + "/data/order.json");

		/* Ucitavanje restorana */
		try (BufferedReader br = new BufferedReader(new FileReader(orderFile))) {
			while ((line = br.readLine()) != null) {
				order += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			orderList = objectMapper.readValue(order, new TypeReference<ArrayList<Order>>() {
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (Order o : orderList) {
			this.orders.put(o.getId(), o);
		}
	}

	public void saveOrder(HashMap<Long, Order> orders) {
		try {
			ObjectMapper objectMaper = new ObjectMapper();
			objectMaper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
			objectMaper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
			objectMaper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);

			File orderFile = new File(this.contextPath + "/data/order.json");

			ArrayList<Order> orderList = new ArrayList<Order>();

			for (Order o : orders.values()) {
				orderList.add(o);
			}

			objectMaper.writeValue(orderFile, orderList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Order findOrderById(Long orderId) {
		for (Order o : orders.values()) {
			if (o.getId().equals(orderId)) {
				return o;
			}
		}
		return null;
	}
}
