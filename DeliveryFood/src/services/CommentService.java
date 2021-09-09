package services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

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

import beans.Comment;
import dao.CommentDAO;
import dto.CommentDTO;

@Path("/comment")
public class CommentService {

	@Context
	ServletContext ctx;

	public void init() {
		if (ctx.getAttribute("commentDAO") == null) {
			String contextPath = ctx.getRealPath("");
			ctx.setAttribute("commentDAO", new CommentDAO(contextPath));
		}
	}

	@GET
	@Path("/all")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Comment> getAllComments() {
		CommentDAO commentDAO = (CommentDAO) ctx.getAttribute("commentDAO");

		ArrayList<Comment> all = new ArrayList<Comment>();

		for (Comment comment : commentDAO.getComments().values()) {
			all.add(comment);
		}
		return all;
	}

	@GET
	@Path("/allVisible")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Comment> getAllVisibleComments() {
		CommentDAO commentDAO = (CommentDAO) ctx.getAttribute("commentDAO");

		ArrayList<Comment> all = new ArrayList<Comment>();

		for (Comment comment : commentDAO.getComments().values()) {
			if (comment.isVisible())
				all.add(comment);
		}
		return all;
	}

	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addArticle(CommentDTO newComment, @Context HttpServletRequest request) {
		CommentDAO commentDAO = (CommentDAO) ctx.getAttribute("commentDAO");

		HashMap<Long, Comment> commnets = commentDAO.getComments();
		Comment comment = new Comment();

		Long id = 0L;

		while (commnets.containsKey(id)) {
			id = ThreadLocalRandom.current().nextLong(0, 65000);
		}

		comment.setId(id);
		comment.setUserCommenter(newComment.getUserCommenter());
		comment.setRestaurantId(newComment.getRestaurantId());
		comment.setText(newComment.getComment());
		comment.setRating(newComment.getRating());
		comment.setVisible(newComment.isVisible());

		commnets.put(id, comment);
		commentDAO.setComments(commnets);
		commentDAO.saveComment(commnets);
		ctx.setAttribute("commentDAO", commentDAO);

		return Response.status(200).build();
	}

	@PUT
	@Path("/visible/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response visibleComment(@PathParam("id") Long id) {
		CommentDAO commentDAO = (CommentDAO) ctx.getAttribute("commentDAO");
		HashMap<Long, Comment> komentari = commentDAO.getComments();
		Comment komentar = commentDAO.findCommentById(id);

		komentari.remove(id);

		komentar.setVisible(true);
		komentari.put(id, komentar);
		commentDAO.setComments(komentari);
		commentDAO.saveComment(komentari);
		ctx.setAttribute("commentDAO", commentDAO);
		return Response.status(200).build();

	}
}
