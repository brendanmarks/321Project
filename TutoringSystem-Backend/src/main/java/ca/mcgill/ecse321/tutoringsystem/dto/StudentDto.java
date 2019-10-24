package ca.mcgill.ecse321.tutoringsystem.dto;

import java.util.ArrayList;

public class StudentDto {
	private String name;
	private String email;
	private String username;
	private String password;
	private static ArrayList<SessionDto> sessions;

	public StudentDto() {
	}
	
	//here we would want the student to get all his sessions

	public StudentDto(String name, String email, String username, String password, ArrayList<SessionDto> sessions) {
		this.name = name;
		this.email = email;
		this.username = username;
		this.password = password;
		StudentDto.sessions = sessions;
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
	
	public static ArrayList<SessionDto> getSessions() {
		return sessions;
	}

	public static void setSessions(ArrayList<SessionDto> sessions) {
		StudentDto.sessions = sessions;
	}


}
