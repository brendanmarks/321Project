package ca.mcgill.ecse321.tutoringsystem.dto;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

public class SessionDto {
	
	private String sessionId;
	private Time startTime;
	private Time endTime;
	private Date date;
	private static TutorialDto assignedTutorial;
	private static ArrayList<StudentDto> registeredStudents;
	
	/**
	 * Default constructor
	 */
	public SessionDto() {
	}
	
	/**
	 * Fake value session dto contructor
	 * @param sessionId
	 */
	public SessionDto(String sessionId) {
		this(sessionId, Date.valueOf("1971-01-01"), Time.valueOf("00:00:00"), Time.valueOf("23:59:59"), registeredStudents, assignedTutorial);
	}
	
	/**
	 * Session dto constructor
	 * @param sessionId
	 * @param date
	 * @param startTime
	 * @param endTime
	 * @param registeredStudents
	 * @param assignedTutorial
	 */
	public SessionDto(String sessionId, Date date, Time startTime, Time endTime, ArrayList<StudentDto> registeredStudents, TutorialDto assignedTutorial) {
		this.sessionId = sessionId;
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
		SessionDto.assignedTutorial = assignedTutorial;
		SessionDto.registeredStudents = registeredStudents;
	}
	
	/**
	 * Get session id
	 * @return session id
	 */
	public String getSessionId() {
		return sessionId;
	}
	
	/**
	 * Get session date
	 * @return date
	 */
	public Date getDate() {
		return date;
	}
	
	/**
	 * Get session start time
	 * @return start time
	 */
	public Time getStartTime() {
		return startTime;
	}
	
	/**
	 * Get session end time
	 * @return end time
	 */
	public Time getEndTime() {
		return endTime;
	}
	
	/**
	 * Get registered students of session
	 * @return registered students
	 */
	public ArrayList<StudentDto> getRegisteredStudents() {
		return registeredStudents;
	}
	
	/**
	 * Set registered students of session
	 * @param registeredStudents
	 */
	public void setRegisteredStudents(ArrayList<StudentDto> registeredStudents) {
		SessionDto.registeredStudents = registeredStudents;
	}
	
	/**
	 * Get assigned tutorial
	 * @return assigned tutorial
	 */
	public TutorialDto getAssignedTutorial() {
		return assignedTutorial;
	}
	
	/**
	 * Set assigned tutorial
	 * @param assignedTutorial
	 */
	public void setAssignedTutorial(TutorialDto assignedTutorial) {
		SessionDto.assignedTutorial = assignedTutorial;
	}
	

}

