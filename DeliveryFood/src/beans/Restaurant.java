package beans;

import java.util.ArrayList;

public class Restaurant {

	private Long id;
	private String name;
	private String type;
	private Location location;
	private String logo;
	private boolean status = true;
	private boolean deleted;
	private ArrayList<Article> articles = new ArrayList<Article>();
	private ArrayList<Order> orders = new ArrayList<Order>();
	private String manager;
	private int review;

	public Restaurant() {
	}

	public Restaurant(Long id, String name, String type, Location location, String logo, boolean status,
			boolean deleted, ArrayList<Article> articles, ArrayList<Order> orders, String manager, int review) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.location = location;
		this.logo = logo;
		this.status = status;
		this.deleted = deleted;
		this.articles = articles;
		this.orders = orders;
		this.manager = manager;
		this.review = review;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public ArrayList<Article> getArticles() {
		return articles;
	}

	public void setArticles(ArrayList<Article> articles) {
		this.articles = articles;
	}

	public ArrayList<Order> getOrders() {
		return orders;
	}

	public void setOrders(ArrayList<Order> orders) {
		this.orders = orders;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public int getReview() {
		return review;
	}

	public void setReview(int review) {
		this.review = review;
	}

}
