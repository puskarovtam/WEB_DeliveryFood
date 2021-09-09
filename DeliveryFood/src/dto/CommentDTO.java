package dto;

public class CommentDTO {

	private String userCommenter;
	private int restaurantId;
	private String comment;
	private int rating;
	private boolean visible;

	public CommentDTO() {
		super();
	}

	public CommentDTO(String userCommenter, int restaurantId, String comment, int rating, boolean visible) {
		super();
		this.userCommenter = userCommenter;
		this.restaurantId = restaurantId;
		this.comment = comment;
		this.rating = rating;
		this.visible = visible;
	}

	public String getUserCommenter() {
		return userCommenter;
	}

	public void setUserCommenter(String userCommenter) {
		this.userCommenter = userCommenter;
	}

	public int getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

}
