package ca.mcgill.ecse321.tutoringsystem.controller;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.mcgill.ecse321.tutoringsystem.dto.*;
import ca.mcgill.ecse321.tutoringsystem.model.*;
import ca.mcgill.ecse321.tutoringsystem.service.*;

@CrossOrigin(origins = "*")
@RestController
public class TutoringSystemRestController {

	@Autowired
	TutoringSystemService service;
	
	//??????What about our PersonDto Class???? Should we also include methods forthem in the controller???
	//I don't think it is necessary since we only have students or tutors in the persistence, never just a person
	//     -Dominic
	
	
	//Creating a new Student
	@PostMapping(value = { 
		"/students/{name}/{email}/{username}/{password}",
		"/students/{name}/{email}/{username}/{password}/" 
	})
	public StudentDto createStudent(
		@PathVariable("name") String name, 
		@PathVariable("email") String email, 
		@PathVariable("username") String username, 
		@PathVariable("password") String password
		)	throws IllegalArgumentException {
		
		//create a student instance in persistence using the method in the service class
		Student student = service.createStudent(name, email, username, password);
		return convertStudentToDto(student);	
	}
	
	
	//Creating a new Tutor
	@PostMapping(value = { 
		"/tutors/{name}/{email}/{username}/{password}/{rate}",
		"/tutors/{name}/{email}/{username}/{password}/{rate}/" 
	})
	public TutorDto createTutor(
		@PathVariable("name") String name, 
		@PathVariable("email") String email, 
		@PathVariable("username") String username, 
		@PathVariable("password") String password,
		@PathVariable("rate") Double rate
		)	throws IllegalArgumentException {
			
		//create a tutor instance in persistence using the method in the service class
		Tutor tutor = service.createTutor(name, email, username, password, rate);
		return convertTutorToDto(tutor);	
	}
	
	
	//Creating a new Course
	@PostMapping(value = { 
		"/courses/{courseId}/{courseName}",
		"/courses/{courseId}/{courseName}/" 
	})
	public CourseDto createCourse(
		@PathVariable("courseId") String courseId, 
		@PathVariable("courseName") String courseName 
		)	throws IllegalArgumentException {
				
		//create a course instance in persistence using the method in the service class
		Course course = service.createCourse(courseId, courseName);
		return convertCourseToDto(course);	
	}	
	
	
	
	//Create a new tutorial
	@PostMapping(value = { 
		"/tutorials/{tutorName}/{courseId}/{id}", 
		"/tutorials/{tutorName}/{courseId}/{id}/" 
	})
	public TutorialDto createTutorial(
		@PathVariable("tutorName") String tutorName, 
		@PathVariable("courseId") String courseId,
		
		@PathVariable("id") String id
		
		)	throws IllegalArgumentException {
		
		//retrieve the needed tutor instance from the database using the given Name parameter 
		//try {
			Tutor tutor = service.getTutor(tutorName);
		//}
		//catch(IllegalArgumentException e)	{
		//	System.out.println("The requested tutor does not exist in the system database or the name was written incorrectly.");
		//} 
		
		//retrieve the needed course instance from the database using the given Course ID parameter 
		//try {
			Course course = service.getCourse(courseId);
			
			////////String uniqueTutorialID = UUID.randomUUID().toString();
			Tutorial tutorial = service.createTutorial(/*uniqueTutorialID*/id, course);
		//}
		//catch(IllegalArgumentException e)	{
		//	System.out.println("The requested course does not exist in the system database or the course ID was written incorrectly.");
		//}
		
		Set<Tutor> tutors = new HashSet<>();
		tutors.add(tutor);
		//set the tutor of the tutorial instance that is saved in persistence
		tutorial.setTutor(tutors);
	
		TutorDto tutorDto = convertTutorToDto(tutor);
		CourseDto courseDto = convertCourseToDto(course);
			
		return convertTutorialToDto(tutorDto, courseDto);	
	}	

	
	
	//Create a session
	@PostMapping(value = { 
		"/sessions/{startTime}/{endTime}/{date}/{tutorialId}/{studentName}", 
		"/sessions/{startTime}/{endTime}/{date}/{tutorialId}/{studentName}/" 
	})
	public SessionDto createSession(
		@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm") LocalTime startTime,
		@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm") LocalTime endTime,
		@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
		@PathVariable("tutorialId") String tutorialId,
		@PathVariable("studentName") String studentName
		)	throws IllegalArgumentException {
		
		String uniqueSessionID = UUID.randomUUID().toString();
		
		Tutorial tutorial = service.getTutorial(tutorialId);
		
		Bill bill = service.createBill(false, 1/*uniqueSessionID*/);	//Make the Bill id a string parameter to be able to assign to it the same id as the session... -Dominic
		
		Session session = service.createSession(uniqueSessionID, Time.valueOf(startTime), Time.valueOf(endTime), date, bill, tutorial);
		return convertSessionToDto(studentName, session);
	}
	
	
	
	
	
	/* Methods that converts instances stored in persistence into DTOs */
	
	//Converts student DTOs
	private StudentDto convertStudentToDto(Student s) {
		if (s == null) {
			throw new IllegalArgumentException("This student does not exist.");
		}
		ArrayList<SessionDto> studentSessionDtos = new ArrayList<>();
		StudentDto studentDto = new StudentDto(s.getName(), s.getEmail(), s.getUsername(), s.getPassword(), studentSessionDtos);
		return studentDto;
	}
	//Converts tutor DTOs
	private TutorDto convertTutorToDto(Tutor t) {
		if (t == null) {
			throw new IllegalArgumentException("This tutor does not exist.");
		}
		ArrayList<SessionDto> tutorSessionDtos = new ArrayList<>();	
		TutorDto tutorDto = new TutorDto(t.getName(), t.getEmail(), t.getUsername(), t.getPassword(), tutorSessionDtos);
		return tutorDto;
	}
	//Converts course DTOs
	private CourseDto convertCourseToDto(Course c) {
		if (c == null) {
			throw new IllegalArgumentException("This course does not exist.");
		}	
		CourseDto courseDto = new CourseDto(c.getCourseId(), c.getCourseName());
		return courseDto;
	}
	//Converts tutorial DTOs
	private TutorialDto convertTutorialToDto(TutorDto t, CourseDto c) {
		if (c == null || t == null) {
			throw new IllegalArgumentException("This tutor or course does not exist.");
		}	
		TutorialDto tutorialDto = new TutorialDto(t, c);
		return tutorialDto;
	}
	//Converts session DTOs
	private SessionDto convertSessionToDto(String name, Session ss) {
		if (name == null || ss == null) {
			throw new IllegalArgumentException("This student or session does not exist.");
		}	
		
		ArrayList<StudentDto> sessionStudentDtos = new ArrayList<>();
		Student student = service.getStudent(name);
		StudentDto firstStudent = convertStudentToDto(student);
		sessionStudentDtos.add(firstStudent);
		
		
		SessionDto sessionDto = new SessionDto(ss.getSessionId(), ss.getDate(), ss.getStartTime(), ss.getEndTime(), sessionStudentDtos);
		return sessionDto;
	}
}
