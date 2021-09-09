package dto;

public class ArticleDTO {

	private String name;
	private String type;
	private int price;
	private int amount;
	private String description;
	private String image;

	public ArticleDTO() {
		super();
	}

	public ArticleDTO(String name, String type, int price, int amount, String description, String image) {
		super();
		this.name = name;
		this.type = type;
		this.price = price;
		this.amount = amount;
		this.description = description;
		this.image = image;
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

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}
