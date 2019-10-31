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
	
	
	
	
/**====================== GET Methods ===========================*/
	
	
	/* --------- Courses ---------*/
	
	//1.1) get all courses
	//TODO : working, but make sure this doesn't fail (try catch)
	@GetMapping(value = {"/courses","/courses/"})
	public List<CourseDto> getAllCourses() {
		List<CourseDto> courseDtos = new ArrayList<>();
		for(Course c : service.getAllCourses()) {
			courseDtos.add(convertCourseToDto(c));
		}
		return courseDtos;
	}
	
	
	//1.2) working, but get course by ID
	//TODO: make sure this doesn't fail (try catch)
	@GetMapping(value = {"/courses/{courseID}","/courses/{courseID}/"})
	public CourseDto getCourseByID(@PathVariable("courseID") String courseID) 
	throws IllegalArgumentException{
		return convertCourseToDto(service.getCourse(courseID));
	}	
	
	/** --------- Tutorials ---------*/	
		
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
	
	
	//HELPER METHOD : Get tutor from set
	//Since tutors are returned as sets from our domain model, we need a way to extract the tutor
	private Tutor getTutorFromSet(Set<Tutor> tutorset) {
		Tutor tutor = null;
		if(tutorset.size() == 0) {
			return tutor; //tutor will be null
		}else if(tutorset.size() > 1) {
			for(java.util.Iterator<Tutor> iterate = tutorset.iterator(); iterate.hasNext();) {
				tutor = iterate.next();
				return tutor;
			}
		}else {
			tutor = tutorset.iterator().next();
			return tutor;
		}
		return tutor;//default
	}
	
	/** 2019 - 10 -26 : Sean Smith --> Doing my part from here down */
	
	//HELPER METHOD : Get student from set
	//Since tutors are returned as sets from our domain model, we need a way to extract the tutor
	private Student getStudentFromSet(Set<Student> studentset) {
		Student student = null;
		if(studentset.size() == 0) {
			return student; //tutor will be null
		}else if(studentset.size() > 1) {
			for(java.util.Iterator<Student> iterate = studentset.iterator(); iterate.hasNext();) {
				student = iterate.next();
				return student;
			}
		}else {
			student = studentset.iterator().next();
			return student;
		}
		return student;//default
	}

	
	//1.2) ------ get tutorial of tutor ------

	private Tutor convertTutorToDomainObject(TutorDto tutorDto) {
		List<Tutor> allTutors = service.getAllTutors();
		for(Tutor t : allTutors) {
			if(t.getUsername().equals(tutorDto.getUsername())) {
				return t;
			}
		}
		return null;
	}
	
	/* --------------- THIS SHOULD BE IN SERVICE, BUT IMPLEMENTED DIFFERENTLY---------*/
	private ArrayList<Tutorial> getAllTutorialsOfTutor(Tutor t){
		ArrayList<Tutorial> returnTutorial = new ArrayList<Tutorial>();
		List<Tutorial> allTutorials = service.getAllTutorials();
		for(Tutorial tutorial: allTutorials) {
			if(getTutorFromSet(tutorial.getTutor()).equals(t)) {
				returnTutorial.add(tutorial);
			}
		} return returnTutorial;
	}
	
	//is this even necessary?
	private List<TutorialDto> createTutorialDtosForTutor(Tutor tutor) {
		List<Tutorial> allTutorialsOfTutor = getAllTutorialsOfTutor(tutor);
		List<TutorialDto> tutorials = new ArrayList<>();
		for(Tutorial t: allTutorialsOfTutor) {
			TutorDto tutorToAdd = convertTutorToDto(getTutorFromSet(t.getTutor()));
			CourseDto courseToAdd = convertCourseToDto(t.getCourse());
			tutorials.add(convertTutorialToDto(tutorToAdd,courseToAdd));
		}
		return tutorials;
	}
	
	//TODO : CHECK THIS!!
	@GetMapping(value = {"/tutorials/tutor/{username}","tutorials/tutor/{username}/"})
	public List<TutorialDto> getTutorialsOfTutor(@PathVariable("username") TutorDto tutorDto){
		Tutor tutor = convertTutorToDomainObject(tutorDto);
		return createTutorialDtosForTutor(tutor);	
	}
	
	
	//1.3) get tutorials of a student
	@GetMapping(value = {"/tutorials/{studentUsername}" , "tutorials/{studentUsername}/"})
	public List<TutorialDto> getTutorialsOfStudent(@PathVariable("studentUsername") String studentUsername) throws IllegalArgumentException{
		List<TutorialDto> tutorialsOfStudent = new ArrayList<>();
		List<Tutorial> allTutorials = service.getAllTutorials();
		for(Tutorial t : allTutorials) { 
			//if(t.getId().get)
		}	
		return tutorialsOfStudent;
	}
	
	@GetMapping(value = {"/tutorials/{tutorialID}","/tutorials/{tutorialID}/"})
	public TutorialDto getTutorialById(@PathVariable("tutorialID") String tutorialID) throws IllegalArgumentException {
		TutorialDto tutorialDto = null;
		List<Tutorial> allTutorials = service.getAllTutorials();
		for(Tutorial t : allTutorials) {
			if(t.getId().trim().equals(tutorialID) || t.getId().trim()==tutorialID) {
				TutorDto tDto = convertTutorToDto(getTutorFromSet(t.getTutor()));
				CourseDto cDto = convertCourseToDto(t.getCourse());
				tutorialDto = convertTutorialToDto(tDto,cDto);
			}
		}
		return tutorialDto;
	} 
	
	//TODO : Make a convertTutorialToDto which takes a tutorial as argument.
	// Suggestion: convertTutorialToDto will take tutorial as argument
	// then you can access the tutors' fields (courses,tutor) through
	// the return value (tutorialDto)
	//TODO : Mark some of the issues as solved.
	/*
	@GetMapping(value = {"/tutorials/{tutorialID}","/tutorials/{tutorialID}/"})
	public TutorialDto getTutorialById(@PathVariable("tutorialID") String tutorialID) throws IllegalArgumentException {
		return convertTutorialToDto(service.getTutorial(tutorialID));
	}
	*/
	
	
	@GetMapping(value = {"/sessions","/sessions/"})
	public List<SessionDto> getAllSessions(){
		List<Session> allSessions = service.getAllSessions();
		List<SessionDto> allSessionsAsDto = new ArrayList<>();
		for(Session s : allSessions) {
			Student student = getStudentFromSet(s.getStudent());
			String studentUserName = convertStudentToDto(student).getUsername();
			allSessionsAsDto.add(convertSessionToDto(studentUserName,s));
		}
		return allSessionsAsDto;
	}
	
	@GetMapping(value = {"/students","/students/"})
	public List<StudentDto> getAllStudents(){
		List<Student> allStudents = service.getAllStudents();
		List<StudentDto> studentDtos = new ArrayList<>();
		for(Student s : allStudents) {
			studentDtos.add(convertStudentToDto(s));
		}
		return studentDtos;
	}

	
	@GetMapping(value = {"/students/{studentUsername}","/students/{studentUsername}/"})
	public StudentDto getStudentByUsername(@PathVariable("studentUsername") String studentUsername) throws IllegalArgumentException{
		List<StudentDto> allStudents = getAllStudents();
		StudentDto student = null;
		for(StudentDto s : allStudents) {
			if(s.getUsername().trim().equals(studentUsername) || s.getUsername().trim()==studentUsername) {
				student = s;
				return student;
			}
		}
		return student;
	}
	
	
	@GetMapping(value = {"/sessions/student/{studentUsername}","/sessions/student/{studentUsername}/"})
	public List<SessionDto> getAllSessionsOfStudent(@PathVariable("studentUsername") String studentUsername) throws IllegalArgumentException{
		List<SessionDto> allSessions = getAllSessions();
		List<SessionDto> allSessionsOfStudent = new ArrayList<>();
		StudentDto student = getStudentByUsername(studentUsername);
		for(SessionDto s: allSessions) {
			if(s.getRegisteredStudents().contains(student)) {
				allSessionsOfStudent.add(s);
			}
		}
		return allSessionsOfStudent;
	}
	
	
	@GetMapping(value = {"/sessions/{sessionID}","/sessions/{sessionID}/"})
	public SessionDto getSessionById(@PathVariable String sessionID) throws IllegalArgumentException {
		SessionDto sessionDto = null;
		List<Session> getAllSessions = service.getAllSessions();
		for(Session s: getAllSessions) {
			if(s.getSessionId().trim().equals(sessionID) || s.getSessionId().trim()==sessionID) {
				Student student = getStudentFromSet(s.getStudent());
				String studentUserName = convertStudentToDto(student).getUsername();
				sessionDto = convertSessionToDto(studentUserName,s);
				return sessionDto;
			}
		} return sessionDto;
	}
		
	@GetMapping(value = {"/tutors","/tutors/"})
	public List<TutorDto> getAllTutors(){
		List<Tutor> allTutors = service.getAllTutors();
		List<TutorDto> tutorDtos = new ArrayList<>();
		for(Tutor s : allTutors) {
			tutorDtos.add(convertTutorToDto(s));
		}
		return tutorDtos;
	}
	
	@GetMapping(value = {"/tutors/{tutorUsername}","/tutors/{tutorUsername}/"})
	public TutorDto getTutorByUsername(@PathVariable("tutorUsername") String tutorUsername) throws IllegalArgumentException {
		List<TutorDto> allTutors = getAllTutors();
		TutorDto tutor = null;
		for(TutorDto t : allTutors) {
			if(t.getUsername().trim().equals(tutorUsername) || t.getUsername().trim()==tutorUsername) {
				tutor = t;
				return t;
			}
		}
		return tutor;
	}
	
	
	//TODO:
	//1) Helper method --DONE
	/**2) GetTutorialByStudent*/
	//3) GetSessionByID --DONE
	//4) GetCourseByID --DONE
	//6) GetTutorByUsername --DONE

	//7) GetStudentByUsername --DONE
		//Also did getAllStudents
	//8) GetTutorialByID --> DONE
	//9) GetAllSessions --Done
		//Also added get sessions of student
	
	/** Sean Smith : Done Get methods for now */
	
	
	/* Delete request methods */
	
	//Delete method for Tutorial
	private void deleteTutorial(String ID)	{
		
		//TODO Create the method that gets a Tutorial instance saved in persistence using an ID function argument. Use a @DeleteRequest...?
		
	}
}
