package services;

import java.util.ArrayList;

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

import beans.User;
import dao.UserDAO;

@Path("/user")
public class UserService {

	@Context
	ServletContext ctx;

	@PostConstruct
	public void init() {
		if (ctx.getAttribute("userDAO") == null) {
			String contextPath = ctx.getRealPath("");
			ctx.setAttribute("userDAO", new UserDAO(contextPath));
		}
	}

	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response register(User user, @Context HttpServletRequest request) {
		UserDAO users = (UserDAO) ctx.getAttribute("userDAO");
		boolean userExist = users.userExist(user.getUsername());

		if (userExist) {
			return Response.status(400).build();
		}

		users.getUsers().put(user.getUsername(), user);
		ctx.setAttribute("userDAO", users);
		users.registerUser();
		return Response.status(200).build();
	}

	@GET
	@Path("/signOut")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response signOut(@Context HttpServletRequest request) {
		request.getSession().invalidate();
		return Response.status(200).build();
	}

	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public User login(User user, @Context HttpServletRequest request) {
		UserDAO users = (UserDAO) ctx.getAttribute("userDAO");
		User u = users.loginUser(user.getUsername(), user.getPassword());

		if (u == null) {
			return null;
		}
		ctx.setAttribute("userDAO", users);
		request.getSession().setAttribute("loggedUser", u);
		return u;
	}

	@GET
	@Path("/currentUser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public User getLoggedUser(@Context HttpServletRequest request) {
		return (User) request.getSession().getAttribute("loggedUser");
	}

	@GET
	@Path("/findAll")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<User> getAllUsers() {
		ArrayList<User> all = new ArrayList<User>();
		UserDAO users = (UserDAO) ctx.getAttribute("userDAO");

		for (User user : users.getUsers().values()) {
			all.add(user);
		}

		return all;
	}

}
