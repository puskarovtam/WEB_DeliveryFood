package services;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.Article;
import dao.ArticleDAO;

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

}
