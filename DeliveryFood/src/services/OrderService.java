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
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.Article;
import beans.Order;
import beans.Restaurant;
import beans.User;
import dao.ArticleDAO;
import dao.OrderDAO;
import dao.RestaurantDAO;
import dao.UserDAO;
import dto.OrderDTO;

@Path("/order")
public class OrderService {

	@Context
	ServletContext ctx;

	@PostConstruct
	public void init() {
		if (ctx.getAttribute("orderDAO") == null) {
			String contextPath = ctx.getRealPath("");
			ctx.setAttribute("orderDAO", new OrderDAO(contextPath));
		}
	}

	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public ArrayList<Order> getAllOrders() {
		ArrayList<Order> all = new ArrayList<Order>();
		OrderDAO orderDAO = (OrderDAO) ctx.getAttribute("orderDAO");

		for (Order order : orderDAO.getOrders().values()) {
			all.add(order);
		}
		return all;
	}

	@POST
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addOrder(OrderDTO newOrder, @Context HttpServletRequest request) {
		RestaurantDAO restaurantDAO = (RestaurantDAO) ctx.getAttribute("restaurantDAO");
		ArticleDAO articleDAO = (ArticleDAO) ctx.getAttribute("articleDAO");
		OrderDAO orderDAO = (OrderDAO) ctx.getAttribute("orderDAO");

		UserDAO userDAO = (UserDAO) ctx.getAttribute("userDAO");
		User user = userDAO.findUserByUsername(newOrder.getBuyerUsername());

		if (!user.getRole().equals("BUYER")) {
			return Response.status(403).build();
		}

		HashMap<Long, Restaurant> restaurants = restaurantDAO.getRestaurants();
		HashMap<Long, Article> articles = articleDAO.getArticles();
		HashMap<Long, Order> orders = orderDAO.getOrders();

		Restaurant restaurant = new Restaurant();
		Order order = new Order();

		ArrayList<Order> restOrders = new ArrayList<Order>();

		Long id = 0L;

		while (orders.containsKey(id)) {
			id = ThreadLocalRandom.current().nextLong(0, 65000);
		}

		order.setId(id);
		order.setRestaurantId(newOrder.getRestaurantID());
		order.setDate(newOrder.getDate());
		order.setPrice(newOrder.getPrice());
		order.setBuyerUsername(newOrder.getBuyerUsername());
		order.setStatus(newOrder.getStatus());

		ArrayList<Article> artikli = new ArrayList<Article>();
		for (Article a : articles.values()) {
			for (Long aID : newOrder.getArticles()) {
				if (a.getId().equals(aID)) {
					artikli.add(a);
				}
			}
		}

		order.setArticles(artikli);

		orders.put(id, order);
		orderDAO.setOrders(orders);
		orderDAO.saveOrder(orders);

		restaurant = restaurantDAO.findRestaurantById(order.getRestaurantId());
		restaurants.remove(order.getRestaurantId());
		restOrders = restaurant.getOrders();
		restOrders.add(order);
		restaurant.setOrders(restOrders);
		restaurants.put(order.getRestaurantId(), restaurant);
		restaurantDAO.setRestaurants(restaurants);
		restaurantDAO.saveRestaurant(restaurants);

		ctx.setAttribute("orderDAO", orderDAO);

		return Response.status(200).build();
	}

	@PUT
	@Path("/{orderId}/{newStatus}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response changeStatus(@PathParam("orderId") Long orderId, @PathParam("newStatus") String newStatus) {
		OrderDAO orderDAO = (OrderDAO) ctx.getAttribute("orderDAO");
		RestaurantDAO restaurantDAO = (RestaurantDAO) ctx.getAttribute("restaurantDAO");

		Order order = orderDAO.findOrderById(orderId);
		Restaurant restaurant = new Restaurant();

		HashMap<Long, Restaurant> restaurants = restaurantDAO.getRestaurants();
		HashMap<Long, Order> orders = orderDAO.getOrders();

		ArrayList<Order> restOrders = new ArrayList<Order>();

		try {

			orders.remove(orderId);
			order.setStatus(newStatus);
			orders.put(orderId, order);
			orderDAO.setOrders(orders);
			orderDAO.saveOrder(orders);
			ctx.setAttribute("orderDAO", orderDAO);

			restaurant = restaurantDAO.findRestaurantById(order.getRestaurantId());
			restOrders = restaurant.getOrders();
			for (Order o : restOrders) {
				if (o.getId() == order.getId()) {
					o.setStatus(newStatus);
				}
			}
			restaurant.setOrders(restOrders);
			restaurants.put(order.getRestaurantId(), restaurant);
			restaurantDAO.setRestaurants(restaurants);
			restaurantDAO.saveRestaurant(restaurants);

			return Response.status(200).build();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return Response.status(400).build();
	}

}
