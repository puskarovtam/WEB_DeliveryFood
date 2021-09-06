package services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataParam;

import beans.Article;
import beans.Location;
import beans.Restaurant;
import beans.User;
import dao.ArticleDAO;
import dao.RestaurantDAO;
import dto.RestaurantDTO;
import dto.RestaurantSearchDTO;

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

	@GET
	@Path("/allOpen")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public ArrayList<Restaurant> getAllOpenRestaurants() {
		ArrayList<Restaurant> allOpen = new ArrayList<Restaurant>();
		RestaurantDAO restaurantDAO = (RestaurantDAO) ctx.getAttribute("restaurantDAO");

		for (Restaurant r : restaurantDAO.getRestaurants().values()) {
			if (!r.isDeleted()) {
				if (r.isStatus()) {
					allOpen.add(r);
				}
			}
		}
		return allOpen;
	}

	@GET
	@Path("/one/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getOneRestaurants(@PathParam("id") Long id) {
		Restaurant restaurant = null;
		RestaurantDAO restaurantDAO = (RestaurantDAO) ctx.getAttribute("restaurantDAO");
		for (Restaurant r : restaurantDAO.getRestaurants().values()) {
			if (r.getId().equals(id)) {
				if (!r.isDeleted()) {
					restaurant = r;
				}
			}
		}
		return Response.status(200).entity(restaurant).build();
	}

	@GET
	@Path("/{manager}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getRestaurantByManager(@PathParam("manager") String manager) {
		Restaurant restaurant = null;
		RestaurantDAO restaurantDAO = (RestaurantDAO) ctx.getAttribute("restaurantDAO");
		for (Restaurant r : restaurantDAO.getRestaurants().values()) {
			if (r.getManager().equals(manager)) {
				restaurant = r;
			}
		}
		return Response.status(200).entity(restaurant).build();
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

	@PUT
	@Path("/edit/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response editRestaurant(@PathParam("id") Long id, RestaurantDTO editRestaurant,
			@Context HttpServletRequest request) {
		User loggedIn = (User) request.getSession().getAttribute("loggedIn");

		if (loggedIn.getRole().equals("BUYER") || loggedIn.getRole().equals("DELIVERY MAN")
				|| loggedIn.getRole().equals("MANAGER")) {
			return Response.status(403).entity("Nemate dozvolu da menjate restoran").build();
		}

		RestaurantDAO restaurantDAO = (RestaurantDAO) ctx.getAttribute("restaurantDAO");
		HashMap<Long, Restaurant> restorani = restaurantDAO.getRestaurants();
		Restaurant restoran = restorani.get(id);

		if (restoran != null) {
			restoran.setName(editRestaurant.getName());
			restoran.setType(editRestaurant.getType());
			Location lokacija = new Location(editRestaurant.getLocation().getAddress(),
					editRestaurant.getLocation().getCity(), editRestaurant.getLocation().getPostalCode(),
					editRestaurant.getLocation().getLatitude(), editRestaurant.getLocation().getLongitude());
			restoran.setLocation(lokacija);
			restoran.setLogo(editRestaurant.getLogo());
			restorani.put(id, restoran);
			restaurantDAO.setRestaurants(restorani);
			restaurantDAO.saveRestaurant(restorani);
			ctx.setAttribute("restaurantDAO", restaurantDAO);
			return Response.status(200).build();
		} else {
			return Response.status(400).entity("Restoran nije pronađen.").build();
		}

	}

	@DELETE
	@Path("/delete/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteRestaurant(@PathParam("id") Long id) {
		RestaurantDAO restaurantDAO = (RestaurantDAO) ctx.getAttribute("restaurantDAO");
		HashMap<Long, Restaurant> restorani = restaurantDAO.getRestaurants();

		Restaurant restoran = restaurantDAO.findRestaurantById(id);
		if (restoran != null) {
			restoran.setDeleted(true);
			restorani.put(id, restoran);
			restaurantDAO.setRestaurants(restorani);
			restaurantDAO.saveRestaurant(restorani);
			ctx.setAttribute("restaurantDAO", restaurantDAO);
			return Response.status(200).build();
		} else {
			return Response.status(400).build();
		}
	}

	@POST
	@Path("/uploadLogo")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadLogo(@FormDataParam("fileToUpload") InputStream uploadedInputStream,
			@FormDataParam("name") String name) {
		String fileLocation = ctx.getRealPath("images/" + name);
		try {
			OutputStream out = new FileOutputStream(new File(fileLocation));
			int read = 0;
			byte[] bytes = new byte[1024];

			out = new FileOutputStream(new File(fileLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Response.status(200).build();
	}

	@POST
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response search(RestaurantSearchDTO restaurantSearchDTO, @Context HttpServletRequest request) {
		User loggedIn = (User) request.getSession().getAttribute("loggedIn");
		RestaurantDAO restaurantDAO = (RestaurantDAO) ctx.getAttribute("restaurantDAO");
		HashMap<Long, Restaurant> restaurants = new HashMap<Long, Restaurant>();

		if (loggedIn == null || loggedIn.getRole().equals("BUYER")) {
			for (Restaurant r : restaurantDAO.getRestaurants().values()) {
				if (!r.isDeleted()) {
					restaurants.put(r.getId(), r);
				}
			}
		} else if (loggedIn.getRole().equals("ADMIN")) {
			restaurants = restaurantDAO.getRestaurants();
		} else if (loggedIn.getRole().equals("MANAGER")) {
			for (Restaurant r : restaurantDAO.getRestaurants().values()) {
				if (r.getManager().equals(loggedIn.getUsername())) {
					restaurants.put(r.getId(), r);
				}
			}
		}

		// Search by name
		ArrayList<Restaurant> byName = new ArrayList<>();
		if (!restaurantSearchDTO.getName().equals("")) {
			for (Restaurant r : restaurants.values()) {
				if (r.getName().equals(restaurantSearchDTO.getName())) {
					byName.add(r);
				}
			}
		}

		// Search by type
		ArrayList<Restaurant> byType = new ArrayList<Restaurant>();
		if (!restaurantSearchDTO.getType().equals("")) {
			for (Restaurant r : restaurants.values()) {
				if (r.getType().equals(restaurantSearchDTO.getType())) {
					byType.add(r);
				}
			}
		} else {
			byType = byName;
		}

		// Search by location
		ArrayList<Restaurant> byLocation = new ArrayList<>();
		if (!(restaurantSearchDTO.getCity().equals(""))) {
			for (Restaurant r : restaurants.values()) {
				if (r.getLocation().getCity().equals(restaurantSearchDTO.getCity())) {
					byLocation.add(r);
				}
			}
		} else {
			byLocation = byType;
		}

		// Search by review
		ArrayList<Restaurant> byReview = new ArrayList<Restaurant>();
		if (!restaurantSearchDTO.getMinReview().equals("") && !restaurantSearchDTO.getMaxReview().equals("")) {
			Integer minReview = Integer.parseInt(restaurantSearchDTO.getMinReview());
			Integer maxReview = Integer.parseInt(restaurantSearchDTO.getMaxReview());
			for (Restaurant r : byLocation) {
				if (r.getReview() >= minReview && r.getReview() <= maxReview) {
					byReview.add(r);
				}
			}
		} else {
			byReview = byLocation;

		}

		return Response.status(200).entity(byReview).build();
	}

}
