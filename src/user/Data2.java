package user;

public class Data2 {
	private long id;
	private String username;
	private String password;
	private String name;
	private String email;
	private String phone;

    
	public Data2(String username, String name, String email, String phone) {
		this.username = username;
		this.name = name;
		this.email = email;
		this.phone = phone;
	}
	



	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}

