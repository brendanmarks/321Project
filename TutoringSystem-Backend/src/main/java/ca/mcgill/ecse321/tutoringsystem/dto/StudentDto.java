package ca.mcgill.ecse321.tutoringsystem.dto;

import java.util.ArrayList;

public class StudentDto {
	
	private String name;
	private String email;
	private String username;
	private String password;
	private static ArrayList<SessionDto> sessions;
	
	/**
	 * Default constructor
	 */
	public StudentDto() {
	}
	
	/**
	 * Student dto constructor
	 * @param name
	 * @param email
	 * @param username
	 * @param password
	 * @param sessions
	 */
	public StudentDto(String name, String email, String username, String password, ArrayList<SessionDto> sessions) {
		this.name = name;
		this.email = email;
		this.username = username;
		this.password = password;
		StudentDto.sessions = sessions;
	}
	
	/**
	 * Get name
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Get email
	 * @return email
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Get username
	 * @return username
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * Get password
	 * @return password
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * Get student sessions
	 * @return sessions list
	 */
	public static ArrayList<SessionDto> getSessions() {
		return sessions;
	}
	
	/**
	 * Set student sessions
	 * @param sessions
	 */
	public static void setSessions(ArrayList<SessionDto> sessions) {
		StudentDto.sessions = sessions;
	}


}
