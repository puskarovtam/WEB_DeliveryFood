package beans;

import java.util.ArrayList;

public class Order {

	private Long id;
	private ArrayList<Article> articles = new ArrayList<Article>();
	private Long restaurantId;
	private String date;
	private int price;
	private String buyerUsername;
	private String status;

	public Order() {
		super();
	}

	public Order(Long id, ArrayList<Article> articles, Long restaurantId, String date, int price, String buyerUsername,
			String status) {
		super();
		this.id = id;
		this.articles = articles;
		this.restaurantId = restaurantId;
		this.date = date;
		this.price = price;
		this.buyerUsername = buyerUsername;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ArrayList<Article> getArticles() {
		return articles;
	}

	public void setArticles(ArrayList<Article> articles) {
		this.articles = articles;
	}

	public Long getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getBuyerUsername() {
		return buyerUsername;
	}

	public void setBuyerUsername(String buyerUsername) {
		this.buyerUsername = buyerUsername;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
