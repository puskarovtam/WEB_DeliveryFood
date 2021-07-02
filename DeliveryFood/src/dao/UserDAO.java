package dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.databind.ObjectMapper;

import beans.User;

public class UserDAO {

	private HashMap<Integer, User> users;
	private String contextPath;

	public UserDAO() {
		users = new HashMap<Integer, User>();
		loadUsers();
	}

	public UserDAO(String contextPath) {
		users = new HashMap<Integer, User>();
		this.contextPath = contextPath;
		loadUsers();
	}

	public HashMap<Integer, User> getUsers() {
		return users;
	}

	public void setUsers(HashMap<Integer, User> users) {
		this.users = users;
	}

	public void loadUsers() {
		users.clear();
		
		ObjectMapper objectMapper = new ObjectMapper();
		String line;
		
	}

}
