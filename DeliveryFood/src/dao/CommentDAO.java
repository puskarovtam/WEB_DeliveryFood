package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.Comment;

public class CommentDAO {

	private HashMap<Long, Comment> comments;
	private String contextPath;

	public CommentDAO(String contextPath) {
		super();
		this.contextPath = contextPath;
		this.comments = new HashMap<Long, Comment>();
		loadComments();
	}

	public HashMap<Long, Comment> getComments() {
		return comments;
	}

	public void setComments(HashMap<Long, Comment> comments) {
		this.comments = comments;
	}

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	public void loadComments() {
		comments.clear();

		ObjectMapper objectMapper = new ObjectMapper();
		String line = "";

		ArrayList<Comment> commentList = new ArrayList<Comment>();
		String comment = "";

		File commentFile = new File(this.contextPath + "/data/comment.json");

		/* Ucitavanje komentara */
		try (BufferedReader br = new BufferedReader(new FileReader(commentFile))) {
			while ((line = br.readLine()) != null) {
				comment += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			commentList = objectMapper.readValue(comment, new TypeReference<ArrayList<Comment>>() {
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (Comment c : commentList) {
			this.comments.put(c.getId(), c);

		}
	}

	public void saveComment(HashMap<Long, Comment> comments) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
			objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
			objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);

			File commentFile = new File(this.contextPath + "/data/comment.json");

			ArrayList<Comment> commentList = new ArrayList<Comment>();

			for (Comment c : comments.values()) {
				commentList.add(c);
			}

			objectMapper.writeValue(commentFile, commentList);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Comment findCommentById(Long commentId) {
		for (Comment c : comments.values()) {
			if (c.getId().equals(commentId)) {
				return c;
			}
		}
		return null;
	}

}
