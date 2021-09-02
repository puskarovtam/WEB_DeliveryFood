package dto;

public class RestaurantSearchDTO {

	private String name;
	private String type;
	private String city;
	private String minReview;
	private String maxReview;

	public RestaurantSearchDTO() {
		super();
	}

	public RestaurantSearchDTO(String name, String type, String city, String minReview, String maxReview) {
		super();
		this.name = name;
		this.type = type;
		this.city = city;
		this.minReview = minReview;
		this.maxReview = maxReview;
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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getMinReview() {
		return minReview;
	}

	public void setMinReview(String minReview) {
		this.minReview = minReview;
	}

	public String getMaxReview() {
		return maxReview;
	}

	public void setMaxReview(String maxReview) {
		this.maxReview = maxReview;
	}

}
