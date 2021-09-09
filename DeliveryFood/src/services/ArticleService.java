package services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

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

import beans.Article;
import beans.User;
import dao.ArticleDAO;
import dto.ArticleDTO;

@Path("/article")
public class ArticleService {

	@Context
	ServletContext ctx;

	public void init() {
		if (ctx.getAttribute("articleDAO") == null) {
			String contextPath = ctx.getRealPath("");
			ctx.setAttribute("articleDAO", new ArticleDAO(contextPath));
		}
	}

	@GET
	@Path("/all")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Article> getAllArticle() {
		ArticleDAO articleDAO = (ArticleDAO) ctx.getAttribute("articleDAO");

		ArrayList<Article> all = new ArrayList<Article>();

		for (Article article : articleDAO.getArticles().values()) {
			all.add(article);
		}
		return all;
	}

	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addArticle(ArticleDTO newArticle, @Context HttpServletRequest request) {
		ArticleDAO articleDAO = (ArticleDAO) ctx.getAttribute("articleDAO");

		HashMap<Long, Article> articles = articleDAO.getArticles();
		Article article = new Article();

		Long id = 0L;

		while (articles.containsKey(id)) {
			id = ThreadLocalRandom.current().nextLong(0, 65000);
		}

		article.setId(id);
		article.setName(newArticle.getName());
		article.setType(newArticle.getType());
		article.setPrice(newArticle.getPrice());
		article.setAmount(newArticle.getAmount());
		article.setDescription(newArticle.getDescription());
		article.setImage(newArticle.getImage());
		article.setDeleted(false);

		articles.put(id, article);
		articleDAO.setArticles(articles);
		articleDAO.saveArticle(articles);
		ctx.setAttribute("articleDAO", articleDAO);

		return Response.status(200).build();
	}

	@PUT
	@Path("/edit/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response editArticle(@PathParam("id") Long id, ArticleDTO editArticle, @Context HttpServletRequest request) {
		User loggedIn = (User) request.getSession().getAttribute("loggedIn");

		if (loggedIn.getRole().equals("BUYER") || loggedIn.getRole().equals("DELIVERY MAN")
				|| loggedIn.getRole().equals("ADMIN")) {
			return Response.status(403).entity("Nemate dozvolu da menjate artikl").build();
		}

		ArticleDAO articleDAO = (ArticleDAO) ctx.getAttribute("articleDAO");
		HashMap<Long, Article> artikli = articleDAO.getArticles();
		Article artikl = artikli.get(id);

		if (artikl != null) {
			artikl.setName(editArticle.getName());
			artikl.setType(editArticle.getType());
			artikl.setPrice(editArticle.getPrice());
			artikl.setAmount(editArticle.getAmount());
			artikl.setDescription(editArticle.getDescription());
			artikl.setImage(editArticle.getImage());
			artikli.put(id, artikl);
			articleDAO.setArticles(artikli);
			articleDAO.saveArticle(artikli);
			ctx.setAttribute("articleDAO", articleDAO);
			return Response.status(200).build();
		} else {
			return Response.status(400).entity("Artikl nije pronađen.").build();
		}
	}

	@DELETE
	@Path("/delete/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteArticle(@PathParam("id") Long id) {
		ArticleDAO articleDAO = (ArticleDAO) ctx.getAttribute("articleDAO");
		HashMap<Long, Article> artikli = articleDAO.getArticles();

		Article artikl = articleDAO.findArticleById(id);
		if (artikl != null) {
			artikl.setDeleted(true);
			artikli.put(id, artikl);
			articleDAO.setArticles(artikli);
			articleDAO.saveArticle(artikli);
			ctx.setAttribute("articleDAO", articleDAO);
			return Response.status(200).build();
		} else {
			return Response.status(400).build();
		}
	}

}
