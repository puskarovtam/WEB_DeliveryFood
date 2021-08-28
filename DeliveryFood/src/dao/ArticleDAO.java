package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.Article;

public class ArticleDAO {

	private HashMap<Long, Article> articles;
	private String contextPath;

	public ArticleDAO(String contextPath) {
		super();
		this.contextPath = contextPath;
		this.articles = new HashMap<Long, Article>();
		loadArticles();
	}

	public HashMap<Long, Article> getArticles() {
		return articles;
	}

	public void setArticles(HashMap<Long, Article> articles) {
		this.articles = articles;
	}

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	public void loadArticles() {
		articles.clear();

		ObjectMapper objectMapper = new ObjectMapper();
		String line = "";

		ArrayList<Article> articleList = new ArrayList<Article>();
		String article = "";

		File articleFile = new File(this.contextPath + "/data/article.json");

		/* Ucitavanje artikala */
		try (BufferedReader br = new BufferedReader(new FileReader(articleFile))) {
			while ((line = br.readLine()) != null) {
				article += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			articleList = objectMapper.readValue(article, new TypeReference<ArrayList<Article>>() {
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (Article a : articleList) {
			this.articles.put(a.getId(), a);

		}
	}

	public void saveArticle(HashMap<Long, Article> articles) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
			objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
			objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);

			File articleFile = new File(this.contextPath + "/data/article.json");

			ArrayList<Article> articleList = new ArrayList<Article>();

			for (Article a : articles.values()) {
				articleList.add(a);
			}

			objectMapper.writeValue(articleFile, articleList);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Article findArticleById(Long articleId) {
		for (Article a : articles.values()) {
			if (a.getId().equals(articleId)) {
				return a;
			}
		}
		return null;
	}
}
