package ca.mcgill.ecse321.tutoringsystem.dto;

public class TutorDto {
	
	private String name;
	private String email;
	private String username;
	private String password;
	
	public TutorDto() {
	}

	public TutorDto(String name, String email, String username, String password) {
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
