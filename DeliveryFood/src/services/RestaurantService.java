package services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.Article;
import beans.Location;
import beans.Restaurant;
import dao.ArticleDAO;
import dao.RestaurantDAO;
import dto.RestaurantDTO;

@Path("/restaurant")
public class RestaurantService {

	@Context
	ServletContext ctx;

	@PostConstruct
	public void init() {
		if (ctx.getAttribute("restaurantDAO") == null) {
			String contextPath = ctx.getRealPath("");
			ctx.setAttribute("restaurantDAO", new RestaurantDAO(contextPath));
		}
	}

	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public ArrayList<Restaurant> getAllRestaurants() {
		ArrayList<Restaurant> all = new ArrayList<Restaurant>();
		RestaurantDAO restaurantDAO = (RestaurantDAO) ctx.getAttribute("restaurantDAO");

		for (Restaurant res : restaurantDAO.getRestaurants().values()) {
			if (!res.isDeleted()) {
				all.add(res);
			}
		}
		return all;
	}

	@POST
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addRestaurant(RestaurantDTO newRestaurant, @Context HttpServletRequest request) {
		RestaurantDAO restaurantDAO = (RestaurantDAO) ctx.getAttribute("restaurantDAO");
		ArticleDAO articleDAO = (ArticleDAO) ctx.getAttribute("articleDAO");

		HashMap<Long, Restaurant> restaurants = restaurantDAO.getRestaurants();
		HashMap<Long, Article> articles = articleDAO.getArticles();

		Restaurant restaurant = new Restaurant();

		Long id = 0L;

		while (restaurants.containsKey(id)) {
			id = ThreadLocalRandom.current().nextLong(0, 65000);
		}

		restaurant.setId(id);
		restaurant.setName(newRestaurant.getName());
		restaurant.setType(newRestaurant.getType());
		restaurant.setLogo(newRestaurant.getLogo());
		restaurant.setManager(newRestaurant.getManager());

		Location location = new Location(newRestaurant.getLocation().getAddress(),
				newRestaurant.getLocation().getCity(), newRestaurant.getLocation().getPostalCode(),
				newRestaurant.getLocation().getLatitude(), newRestaurant.getLocation().getLongitude());

		restaurant.setLocation(location);

		ArrayList<Article> articless = new ArrayList<Article>();
		for (Article a : articles.values()) {
			for (Long aId : newRestaurant.getArticles()) {
				if (a.getId().equals(aId)) {
					articless.add(a);
				}
			}
		}

		restaurant.setArticles(articless);

		restaurants.put(id, restaurant);
		restaurantDAO.setRestaurants(restaurants);
		restaurantDAO.saveRestaurant(restaurants);
		ctx.setAttribute("restaurantDAO", restaurantDAO);

		return Response.status(200).build();
	}

}
