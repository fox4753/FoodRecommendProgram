package user;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Member {
	private long id;
	private String username;
	private String password;
	private String name;
	private String email;
	private String phone;
	private Timestamp createDate;
	
	public Member() {
	
	}
	
	public Member(long id, String username, String password, String name, String email, String phone) {	
		this.id = id;
		this.username = username;
		this.password = password;
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
