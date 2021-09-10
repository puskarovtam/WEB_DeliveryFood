package dto;

import java.util.ArrayList;

public class OrderDTO {

	private ArrayList<Long> articles;
	private Long restaurantID;
	private String buyerUsername;
	private String date;
	private int price;
	private String status;

	public OrderDTO() {
		super();
	}

	public OrderDTO(ArrayList<Long> articles, Long restaurantID, String buyerUsername, String date, int price,
			String status) {
		super();
		this.articles = articles;
		this.restaurantID = restaurantID;
		this.buyerUsername = buyerUsername;
		this.date = date;
		this.price = price;
		this.status = status;
	}

	public ArrayList<Long> getArticles() {
		return articles;
	}

	public void setArticles(ArrayList<Long> articles) {
		this.articles = articles;
	}

	public Long getRestaurantID() {
		return restaurantID;
	}

	public void setRestaurantID(Long restaurantID) {
		this.restaurantID = restaurantID;
	}

	public String getBuyerUsername() {
		return buyerUsername;
	}

	public void setBuyerUsername(String buyerUsername) {
		this.buyerUsername = buyerUsername;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
