package dto;

import java.util.ArrayList;

import beans.Location;

public class RestaurantDTO {

	private String name;
	private String type;
	private Location location;
	private String logo;
	private ArrayList<Long> articles;
	private String manager;

	public RestaurantDTO() {
		super();
	}

	public RestaurantDTO(String name, String type, Location location, String logo, ArrayList<Long> articles,
			String manager) {
		super();
		this.name = name;
		this.type = type;
		this.location = location;
		this.logo = logo;
		this.articles = articles;
		this.manager = manager;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public ArrayList<Long> getArticles() {
		return articles;
	}

	public void setArticles(ArrayList<Long> articles) {
		this.articles = articles;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

}
