package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.Administrator;
import beans.Buyer;
import beans.DeliveryMan;
import beans.Manager;
import beans.User;

public class UserDAO {

	private HashMap<String, User> users;
	private String contextPath;

	public UserDAO() {
		users = new HashMap<String, User>();
		loadUsers();
	}

	public UserDAO(String contextPath) {
		users = new HashMap<String, User>();
		this.contextPath = contextPath;
		loadUsers();
	}

	public HashMap<String, User> getUsers() {
		return users;
	}

	public void setUsers(HashMap<String, User> users) {
		this.users = users;
	}

	public void loadUsers() {
		users.clear();

		ObjectMapper objectMapper = new ObjectMapper();
		String line;

		ArrayList<Administrator> admins = new ArrayList<Administrator>();
		ArrayList<Manager> managers = new ArrayList<Manager>();
		ArrayList<DeliveryMan> deliveryMen = new ArrayList<DeliveryMan>();
		ArrayList<Buyer> buyers = new ArrayList<Buyer>();

		String admin = "";
		String manager = "";
		String deliveryMan = "";
		String buyer = "";

		File adminFile = new File(this.contextPath + "/data/admin.json");
		File managerFile = new File(this.contextPath + "/data/manager.json");
		File deliveryManFile = new File(this.contextPath + "/data/deliveryMan.json");
		File buyerFile = new File(this.contextPath + "/data/buyer.json");

		/* Ucitavanje administratora */
		try (BufferedReader br = new BufferedReader(new FileReader(adminFile))) {
			while ((line = br.readLine()) != null) {
				admin += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			admins = objectMapper.readValue(admin, new TypeReference<ArrayList<Administrator>>() {
			});
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (Administrator a : admins) {
			this.users.put(a.getUsername(), a);
		}

		line = "";

		/* Ucitavanje menadzera */
		try (BufferedReader br = new BufferedReader(new FileReader(managerFile))) {
			while ((line = br.readLine()) != null) {
				manager += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			managers = objectMapper.readValue(manager, new TypeReference<ArrayList<Manager>>() {
			});
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (Manager m : managers) {
			this.users.put(m.getUsername(), m);
		}

		line = "";

		/* Ucitavanje dostavljaca */
		try (BufferedReader br = new BufferedReader(new FileReader(deliveryManFile))) {
			while ((line = br.readLine()) != null) {
				deliveryMan += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			deliveryMen = objectMapper.readValue(deliveryMan, new TypeReference<ArrayList<DeliveryMan>>() {
			});
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (DeliveryMan dm : deliveryMen) {
			this.users.put(dm.getUsername(), dm);
		}

		line = "";

		/* Ucitavanje kupca */
		try (BufferedReader br = new BufferedReader(new FileReader(buyerFile))) {
			while ((line = br.readLine()) != null) {
				buyer += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			buyers = objectMapper.readValue(buyer, new TypeReference<ArrayList<Buyer>>() {
			});
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (Buyer b : buyers) {
			this.users.put(b.getUsername(), b);
		}

		line = "";
	}

	public void saveUsers() {
		ObjectMapper objectMapper = new ObjectMapper();

		ArrayList<Administrator> admins = new ArrayList<Administrator>();
		ArrayList<Manager> managers = new ArrayList<Manager>();
		ArrayList<DeliveryMan> deliveryMen = new ArrayList<DeliveryMan>();
		ArrayList<Buyer> buyers = new ArrayList<Buyer>();

		for (User user : this.users.values()) {
			if (user.getRole().equalsIgnoreCase("ADMIN")) {
				Administrator admin = new Administrator();
				admin.setUsername(user.getUsername());
				admin.setPassword(user.getPassword());
				admin.setName(user.getName());
				admin.setSurname(user.getSurname());
				admin.setGender(user.getGender());
				admin.setDateOfBirth(user.getDateOfBirth());
				admin.setRole(user.getRole());
				admin.setBlocked(user.isBlocked());
				admins.add(admin);
			} else if (user.getRole().equalsIgnoreCase("MANAGER")) {
				Manager manager = new Manager();
				manager.setUsername(user.getUsername());
				manager.setPassword(user.getPassword());
				manager.setName(user.getName());
				manager.setSurname(user.getSurname());
				manager.setGender(user.getGender());
				manager.setDateOfBirth(user.getDateOfBirth());
				manager.setRole(user.getRole());
				manager.setBlocked(user.isBlocked());
				managers.add(manager);
			} else if (user.getRole().equalsIgnoreCase("DELIVERY_MAN")) {
				DeliveryMan deliveryMan = new DeliveryMan();
				deliveryMan.setUsername(user.getUsername());
				deliveryMan.setPassword(user.getPassword());
				deliveryMan.setName(user.getName());
				deliveryMan.setSurname(user.getSurname());
				deliveryMan.setGender(user.getGender());
				deliveryMan.setDateOfBirth(user.getDateOfBirth());
				deliveryMan.setRole(user.getRole());
				deliveryMan.setBlocked(user.isBlocked());
				deliveryMen.add(deliveryMan);
			} else if (user.getRole().equalsIgnoreCase("BUYER")) {
				Buyer buyer = new Buyer();
				buyer.setUsername(user.getUsername());
				buyer.setPassword(user.getPassword());
				buyer.setName(user.getName());
				buyer.setSurname(user.getSurname());
				buyer.setGender(user.getGender());
				buyer.setDateOfBirth(user.getDateOfBirth());
				buyer.setRole(user.getRole());
				buyer.setBlocked(user.isBlocked());
				buyers.add(buyer);
			}
		}

		File fileAdmins = new File(this.contextPath + "/data/admin.json");
		try {
			objectMapper.writerWithDefaultPrettyPrinter().writeValue(fileAdmins, admins);
		} catch (IOException e) {
			e.printStackTrace();
		}

		File fileManagers = new File(this.contextPath + "/data/manager.json");
		try {
			objectMapper.writerWithDefaultPrettyPrinter().writeValue(fileManagers, managers);
		} catch (IOException e) {
			e.printStackTrace();
		}

		File fileDeliveryMen = new File(this.contextPath + "/data/deliveryMan.json");
		try {
			objectMapper.writerWithDefaultPrettyPrinter().writeValue(fileDeliveryMen, deliveryMen);
		} catch (IOException e) {
			e.printStackTrace();
		}

		File fileBuyers = new File(this.contextPath + "/data/buyer.json");
		try {
			objectMapper.writerWithDefaultPrettyPrinter().writeValue(fileBuyers, buyers);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean userExist(String username) {
		for (User user : this.users.values()) {
			if (user.getUsername().equals(username))
				return true;
		}
		return false;
	}

	public User findUserByUsername(String username) {
		for (User user : users.values()) {
			if (user.getUsername().equals(username)) {
				return user;
			}
		}
		return null;
	}

	public User loginUser(String username, String password) {
		for (User user : this.users.values()) {
			if (user.getUsername().equals(username)) {
				if (user.getPassword().equals(password)) {
					return user;
				}
			}
		}
		return null;
	}

	public void registerUser() {
		ObjectMapper objectMapper = new ObjectMapper();
		ArrayList<User> buyers = new ArrayList<User>();

		for (User user : this.users.values()) {
			if (user.getRole().equals("BUYER")) {
				buyers.add(user);
			}
		}

		File buyerJSON = new File(this.contextPath + "/data/buyer.json");
		try {
			objectMapper.writerWithDefaultPrettyPrinter().writeValue(buyerJSON, buyers);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
