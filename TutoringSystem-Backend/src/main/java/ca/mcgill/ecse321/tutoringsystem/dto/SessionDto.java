package ca.mcgill.ecse321.tutoringsystem.dto;

import java.sql.Date;
import java.sql.Time;

public class SessionDto {
	
	private String sessionId;
	private Time startTime;
	private Time endTime;
	private Date date;
	
	public SessionDto() {
	}
	
	public SessionDto(String sessionId) {
		this(sessionId, Date.valueOf("1971-01-01"), Time.valueOf("00:00:00"), Time.valueOf("23:59:59"));
	}
	
	public SessionDto(String sessionId, Date date, Time startTime, Time endTime) {
		this.sessionId = sessionId;
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
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

}

