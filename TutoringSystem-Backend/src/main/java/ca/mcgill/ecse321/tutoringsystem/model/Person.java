package ca.mcgill.ecse321.tutoringsystem.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public abstract class Person{
	private String email;

	public void setEmail(String value) {
		this.email = value;
	}
	public String getEmail() {
		return this.email;
	}
	private String username;

	public void setUsername(String value) {
		this.username = value;
	}
	public String getUsername() {
		return this.username;
	}
	private String password;

	public void setPassword(String value) {
		this.password = value;
	}
	public String getPassword() {
		return this.password;
	}
	private String name;

	public void setName(String value) {
		this.name = value;
	}
	@Id
	public String getName() {
		return this.name;
	}
}
