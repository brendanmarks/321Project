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
import javassist.bytecode.Descriptor.Iterator;

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
		"/students/{name}",
		"/students/{name}/" 
	})
	public StudentDto createStudent(
		@PathVariable("name") String name, 
		@RequestParam(name = "email") String email, 
		@RequestParam(name = "username") String username, 
		@RequestParam(name = "password") String password
		)	throws IllegalArgumentException {
		
		//create a student instance in persistence using the method in the service class
		Student student = service.createStudent(name, email, username, password);
		return convertStudentToDto(student);	
	}
	
	//Retrieving a Tutor from database to view his details
	
	
	
	//Creating a new Tutor
	@PostMapping(value = { 
		"/tutors/{name}",
		"/tutors/{name}/" 
	})
	public TutorDto createTutor(
		@PathVariable("name") String name, 
		@RequestParam(name = "email") String email, 
		@RequestParam(name = "username") String username, 
		@RequestParam(name = "password") String password,
		@RequestParam(name = "rate") Double rate
		)	throws IllegalArgumentException {
			
		//create a tutor instance in persistence using the method in the service class
		Tutor tutor = service.createTutor(name, email, username, password, rate);
		return convertTutorToDto(tutor);	
	}
	
	
	//Creating a new Course
	@PostMapping(value = { 
		"/courses/{courseId}",
		"/courses/{courseId}/" 
	})
	public CourseDto createCourse(
		@PathVariable("courseId") String courseId, 
		@RequestParam(name = "courseName") String courseName 
		)	throws IllegalArgumentException {
				
		//create a course instance in persistence using the method in the service class
		Course course = service.createCourse(courseId, courseName);
		return convertCourseToDto(course);	
	}	
	
	
	
	//Create a new tutorial
	@PostMapping(value = { 
		"/tutorials/{id}", 
		"/tutorials/{id}/" 
	})
	public TutorialDto createTutorial(
		
		@PathVariable("id") String id,
		
		@RequestParam(name = "tutorName") String tutorName, 
		@RequestParam(name = "courseId") String courseId
		
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
			service.createTutorial(/*uniqueTutorialID*/id, course, tutor);
		//}
		//catch(IllegalArgumentException e)	{
		//	System.out.println("The requested course does not exist in the system database or the course ID was written incorrectly.");
		//}
		
		TutorDto tutorDto = convertTutorToDto(tutor);
		CourseDto courseDto = convertCourseToDto(course);
		
		return convertTutorialToDto(tutorDto, courseDto);	
	}	

	
	
	//Create a session
	@PostMapping(value = { 
		"/sessions/{sessionId}", 
		"/sessions/{sessionId}/" 
	})
	public SessionDto createSession(
		@PathVariable ("sessionId") String id,
		@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm") LocalTime startTime,
		@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm") LocalTime endTime,
		@RequestParam Date date,            /*(name = "date") @DateTimeFormat(pattern = "yyyy-MM-dd")*/ 
		@RequestParam(name = "tutorialId") String tutorialId,
		@RequestParam(name = "studentName") String studentName
		)	throws IllegalArgumentException {
		
		String uniqueSessionID = id; /* UUID.randomUUID().toString(); */
		
		Integer billId = Integer.parseInt(uniqueSessionID);
		
		Tutorial tutorial = service.getTutorial(tutorialId);
		
		Bill bill = service.createBill(false, billId);	//Make the Bill id a string parameter to be able to assign to it the same id as the session... -Dominic
		
		Session session = service.createSession(uniqueSessionID, Time.valueOf(startTime), Time.valueOf(endTime), date, bill, tutorial);
		return convertSessionToDto(studentName, session);
	}
	
	/* GET request methods */
	
	//Get all existing Tutorial instances saved in persistence and return a List<TutorialDto> of tutorial DTOs that can be viewed/used by a student.
	@GetMapping(value = { 
		"/tutorials",
		"/tutorials/" 
	})
	public List<TutorialDto> getAllTutorials() {
		
		List<TutorialDto> tutorialDtos = new ArrayList<>();
		for (Tutorial tutorial : service.getAllTutorials()) {
			
			Set<Tutor> tutors = tutorial.getTutor();
			if (tutors.size() == 0)	{
				
				//TODO check this delete method, make sure it doesn't bug and delete valid instances
				String emptyTutorialId = tutorial.getId();
				System.out.println("The tutorial instance with ID "+ emptyTutorialId + " saved in persistance does not have an assigned tutor. It will be deleted.");
				deleteTutorial(emptyTutorialId);
				continue;
			}
			
			Course course = tutorial.getCourse();
			CourseDto cDto = convertCourseToDto(course);
			
			if (tutors.size() > 1)	{
				
				for(java.util.Iterator<Tutor> iterate = tutors.iterator(); iterate.hasNext();) {
					Tutor t = iterate.next();
					TutorDto tDto = convertTutorToDto(t);
					tutorialDtos.add(convertTutorialToDto(tDto, cDto));
				} 	
			
			}	else if (tutors.size() == 1)	{
				
				Tutor t = tutors.iterator().next();
				TutorDto tDto = convertTutorToDto(t);

				tutorialDtos.add(convertTutorialToDto(tDto, cDto));
			}
		}	
		return tutorialDtos;
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
	
	
	
	/* Delete request methods */
	
	//Delete method for Tutorial
	private void deleteTutorial(String ID)	{
		
		//TODO Create the method that gets a Tutorial instance saved in persistence using an ID function argument. Use a @DeleteRequest...?
		
	}
}
