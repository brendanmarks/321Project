package ca.mcgill.ecse321.tutoringsystem.dto;

import java.util.ArrayList;

public class TutorDto {
	
	private String name;
	private String email;
	private String username;
	private String password;
	private Double hourlyRate;
	private static ArrayList<SessionDto> sessions;
	
	/**
	 * Default constructor
	 */
	public TutorDto() {
	}
	
	/**
	 * Tutor dto constructor
	 * @param name
	 * @param email
	 * @param username
	 * @param password
	 * @param sessions
	 */
//	public TutorDto(String name, String email, String username, String password, ArrayList<SessionDto> sessions) {
//		this.name = name;
//		this.email = email;
//		this.username = username;
//		this.password = password;
//		TutorDto.sessions = sessions;
//	}
	
	public TutorDto(String name, String email, String username, String password, ArrayList<SessionDto> sessions, double hourlyRate) {
		this.name = name;
		this.email = email;
		this.username = username;
		this.password = password;
		this.hourlyRate = hourlyRate;
		TutorDto.sessions = sessions;
	}
	
	/**
	 * Get name
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	public double getHourlyRate() {
		return hourlyRate;
	}
	
	public void setHourlyRate(double rate) {
		this.hourlyRate = rate;
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
	 * Get sessions of tutor
	 * @return sessions list
	 */
	public ArrayList<SessionDto> getSessions() {
		return sessions;
	}
	
	/**
	 * Set sessions of tutor
	 * @param sessions 
	 */
	public void setSessions(ArrayList<SessionDto> sessions) {
		TutorDto.sessions = sessions;
	}
}
