package ca.mcgill.ecse321.tutoringsystem.dto;


public class StudentDto {
	private String name;
	private String email;
	private String username;
	private String password;
	
	public StudentDto() {
	}
	
	//here we would want the student to get all his sessions

	public StudentDto(String name, String email, String username, String password) {
		this.name = name;
		this.email = email;
		this.username = username;
		this.password = password;
	}
	
	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getUsername() {
		return username;
	}

	public String getEndTime() {
		return password;
	}

}
