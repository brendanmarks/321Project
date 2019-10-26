package ca.mcgill.ecse321.tutoringsystem.dto;

import java.util.ArrayList;

public class TutorDto {
	
	private String name;
	private String email;
	private String username;
	private String password;
	private static ArrayList<SessionDto> sessions;

	public TutorDto() {
	}

	public TutorDto(String name, String email, String username, String password, ArrayList<SessionDto> sessions) {
		this.name = name;
		this.email = email;
		this.username = username;
		this.password = password;
		TutorDto.sessions = sessions;
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

	public String getPassword() {
		return password;
	}
	
	public ArrayList<SessionDto> getSessions() {
		return sessions;
	}

	public void setSessions(ArrayList<SessionDto> sessions) {
		TutorDto.sessions = sessions;
	}
}
