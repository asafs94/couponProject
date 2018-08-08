package tools;

import javax.xml.bind.annotation.XmlRootElement;

import system.mainSystem.ClientType;

/**
 * A class that represents a User object.
 * a user object helps te client side scripts determine if a user is logged in or not,
 * what type of user, and also supplies the username and password.
 * @author asafs
 *
 */
@XmlRootElement
public class User {

	private long id;
	private String name;
	private String password;
	private ClientType type;
	
	public User() {
	}

	public User(long id, String name, String password, ClientType type) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.type = type;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ClientType getType() {
		return type;
	}

	public void setType(ClientType type) {
		this.type = type;
	}
	
	
	
	
}
