package ca.mcgill.ecse321.tutoringsystem.dto;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

public class SessionDto {
	
	private String sessionId;
	private Time startTime;
	private Time endTime;
	private Date date;
	private TutorDto assignedTutor;
	private static ArrayList<StudentDto> registeredStudents;
	

	public SessionDto() {
	}
	
	public SessionDto(String sessionId) {
		this(sessionId, Date.valueOf("1971-01-01"), Time.valueOf("00:00:00"), Time.valueOf("23:59:59"), registeredStudents);
	}
	
	public SessionDto(String sessionId, Date date, Time startTime, Time endTime, ArrayList<StudentDto> registeredStudents) {
		this.sessionId = sessionId;
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
		SessionDto.registeredStudents = registeredStudents;
	}
	
	public String getSessionId() {
		return sessionId;
	}

	public Date getDate() {
		return date;
	}

	public Time getStartTime() {
		return startTime;
	}

	public Time getEndTime() {
		return endTime;
	}
	
	public ArrayList<StudentDto> getRegisteredStudents() {
		return registeredStudents;
	}

	public void setRegisteredStudents(ArrayList<StudentDto> registeredStudents) {
		SessionDto.registeredStudents = registeredStudents;
	}
	
	public TutorDto getAssignedTutor() {
		return assignedTutor;
	}

	public void setAssignedTutor(TutorDto assignedTutor) {
		this.assignedTutor = assignedTutor;
	}
	


}

