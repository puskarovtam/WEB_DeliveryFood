package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.Restaurant;

public class RestaurantDAO {

	private HashMap<Long, Restaurant> restaurants;
	private String contextPath;

	public RestaurantDAO(String contextPath) {
		super();
		this.contextPath = contextPath;
		this.restaurants = new HashMap<Long, Restaurant>();
		loadRestaurants();
	}

	public HashMap<Long, Restaurant> getRestaurants() {
		return restaurants;
	}

	public void setRestaurants(HashMap<Long, Restaurant> restaurants) {
		this.restaurants = restaurants;
	}

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	public void loadRestaurants() {
		restaurants.clear();

		ObjectMapper objectMapper = new ObjectMapper();
		String line = "";

		ArrayList<Restaurant> restaurantList = new ArrayList<Restaurant>();
		String restaurant = "";

		File restaurantFile = new File(this.contextPath + "/data/restaurant.json");

		/* Ucitavanje restorana */
		try (BufferedReader br = new BufferedReader(new FileReader(restaurantFile))) {
			while ((line = br.readLine()) != null) {
				restaurant += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			restaurantList = objectMapper.readValue(restaurant, new TypeReference<ArrayList<Restaurant>>() {
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (Restaurant r : restaurantList) {
			this.restaurants.put(r.getId(), r);
		}
	}

	public void saveRestaurant(HashMap<Long, Restaurant> restaurants) {
		try {
			ObjectMapper objectMaper = new ObjectMapper();
			objectMaper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
			objectMaper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
			objectMaper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);

			File restaurantFile = new File(this.contextPath + "/data/restaurant.json");

			ArrayList<Restaurant> restaurantList = new ArrayList<Restaurant>();

			for (Restaurant r : restaurants.values()) {
				restaurantList.add(r);
			}

			objectMaper.writeValue(restaurantFile, restaurantList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Restaurant findRestaurantById(Long restaurantId) {
		for (Restaurant r : restaurants.values()) {
			if (r.getId().equals(restaurantId)) {
				return r;
			}
		}
		return null;
	}

}
