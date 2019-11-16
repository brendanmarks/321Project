package ca.mcgill.ecse321.tutoringsystem.controller;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	
	
	/**====================== POST Request Methods ===========================*/

/*	In order, this section contains the following methods:
 * 
 * 1.1)
 * 
 */
	
	
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
		try	{
			Student student = service.createStudent(name, email, username, password);
			return convertStudentToDto(student);
		} catch(Exception e) {
			System.out.println("Error ocured when creating a new student with the name: "+name+".");
			return null;
		}	
	}
	
	
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
		try {
			Tutor tutor = service.createTutor(name, email, username, password, rate);
			return convertTutorToDto(tutor);	
		} catch(Exception e) {
			System.out.println("Error ocured when creating a new tutor with the name: "+name+".");
			return null;
		}	
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
		"/tutorials/{tutorialId}", 
		"/tutorials/{tutorialId}/" 
	})
	public TutorialDto createTutorial(
		
		@PathVariable("tutorialId") String tutorialId,
		
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
			
			Tutorial newTutorial = service.createTutorial(/*uniqueTutorialID*/tutorialId, course, tutor);
			
			//Check if tutor already has tutorials assigned to them and saved in database
			Set<Tutorial> tutorTutorials = tutor.getTutorial();
			
			//If the Tutorial field of the specified tutor instance hasn't been instantiated, we create a new empty Set<Tutorial>
			if(tutorTutorials.equals(null) || tutorTutorials == null || tutorTutorials.size() == 0)	{
				
				tutorTutorials = new HashSet<Tutorial>();
				
			}
			//If there already was an existing set of tutorials saved for this specified tutor instance, then we just add the new tutorial to the set
			tutorTutorials.add(newTutorial);
			
			//Assign the new set of tutorials to the tutor instance
			tutor.setTutorial(tutorTutorials);
			
			//Call the service method that will update the saved tutor and add the new tutorial
			tutor = service.updateTutor(tutor);		//method at line 151 of service class
			
		//}
		//catch(IllegalArgumentException e)	{
		//	System.out.println("The requested course does not exist in the system database or the course ID was written incorrectly.");
		//}
		
		TutorDto tutorDto = convertTutorToDto(tutor);
		CourseDto courseDto = convertCourseToDto(course);
		
		return convertTutorialToDto(tutorDto, courseDto);	
	}	

	
	//Create a new review
	@PostMapping(value = { 
		"/reviews/{sessionId}/{reviewId}", 
		"/reviews/{sessionId}/{reviewId}/" 
	})
	public ReviewDto createReview(
			
		@PathVariable("sessionId") String sessionId, 		
		@PathVariable("reviewId") String reviewId,
		
		@RequestParam(name = "comment") String comment, 		//For now the comment must be one string... Must change the method to accept multi word...or use a text file as param after creating the review to then call setComment on the review and [ass thet text file ....   -Dom
		@RequestParam(name = "rating") int rating,				
		@RequestParam(name = "studentName") String name			//The name of the student that will be posting the review 
			
		)	throws IllegalArgumentException {
		
			//First we save to persistence the new review
			Review review = service.createReview(reviewId, comment, rating);
			
			//Fetch the session that has just been completed
			Session session = service.getSession(sessionId);
			
			//Check if session already has reviews saved
			Set<Review> reviews = session.getReview();
			
			//If the Review field of the specified session instance hasn't been instantiated, we create a new empty Set<Review>
			if(reviews.equals(null) || reviews == null || reviews.size() == 0)	{
				
				reviews = new HashSet<Review>();
				
			}
			//If there already was an existing set of reviews saved for this specified session instance, then we just add the new created review to the set
			reviews.add(review);
			
			//Assign the new set of reviews to the session instance
			session.setReview(reviews);
			
			//Call the service method that will update the saved session and add the new reviews
			session = service.updateSession(session);		//method at line 232 of service class
			
			return convertReviewToDto(review);	
		}
	
	
	//Create a session
	@PostMapping(value = { 
		"/sessions/{sessionId}", 
		"/sessions/{sessionId}/" 
	})
	public SessionDto createSession(
		@PathVariable ("sessionId") String sessionId,
		@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm") LocalTime startTime,
		@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm") LocalTime endTime,
		@RequestParam Date date,         
		@RequestParam(name = "tutorialId") String tutorialId,
		@RequestParam(name = "studentName") String studentName
		)	throws IllegalArgumentException {
			
		Integer billId = Integer.parseInt(sessionId);
		
		Tutorial tutorial = service.getTutorial(tutorialId);
		
		Student firstStudent = service.getStudent(studentName);
		
		Bill bill = service.createBill(false, billId);
		
		Session session = service.createSession(sessionId, Time.valueOf(startTime), Time.valueOf(endTime), date, bill, tutorial, firstStudent);
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
		Set<Student> students = ss.getStudent();
		
		ArrayList<StudentDto> sessionStudentDtos = new ArrayList<>();
		
		if(students.size() > 0)	{
			
			for(java.util.Iterator<Student> iterate = students.iterator(); iterate.hasNext();) {
				Student s = iterate.next();
				StudentDto sDto = convertStudentToDto(s);
				sessionStudentDtos.add(sDto);
			}
		}	else if(students.size() == 0 || students == null)	{
			
			Student student = service.getStudent(name);
			StudentDto firstStudent = convertStudentToDto(student);
			sessionStudentDtos.add(firstStudent);
		}
		
		SessionDto sessionDto = new SessionDto(ss.getSessionId(), ss.getDate(), ss.getStartTime(), ss.getEndTime(), sessionStudentDtos);
		return sessionDto;
	}

	//Converts review DTOs
	private ReviewDto convertReviewToDto(Review r) {
		if (r == null) {
			throw new IllegalArgumentException("This review does not exist.");
		}		
		ReviewDto reviewDto = new ReviewDto(r.getReviewId(), r.getComment(), r.getRating());
		return reviewDto;
	}
	
	
	
	
/**====================== GET Methods ===========================*/
	
	
	/* --------- Courses ---------*/
	
	//1.1) get all courses
	@GetMapping(value = {"/courses","/courses/"})
	public List<CourseDto> getAllCourses() {
		List<CourseDto> courseDtos = new ArrayList<>();
		try {
			for(Course c : service.getAllCourses()) {
				courseDtos.add(convertCourseToDto(c));
			}
			return courseDtos;
		}catch(Exception e) {
			System.out.println("Error getting all courses");
			return null;
		}
	}
	
	
	//1.2) working, but get course by ID
	//TODO: make sure this doesn't fail (try catch)
	@GetMapping(value = {"/courses/{courseID}","/courses/{courseID}/"})
	public CourseDto getCourseByID(@PathVariable("courseID") String courseID) 
	throws IllegalArgumentException{
		try{
			return convertCourseToDto(service.getCourse(courseID));
		}catch(Exception e) {
			System.out.println("Could not get course "+courseID);
			return null;
		}
	}	
	
	/** --------- Tutorials ---------*/	
		
	//Get all existing Tutorial instances saved in persistence and return a List<TutorialDto> of tutorial DTOs that can be viewed/used by a student.
	@GetMapping(value = { 
		"/tutorials",
		"/tutorials/" 
	})
	public List<TutorialDto> getAllTutorials() {
		try {
			List<TutorialDto> tutorialDtos = new ArrayList<>();
			for (Tutorial tutorial : service.getAllTutorials()) {
				
				Set<Tutor> tutors = tutorial.getTutor();
				
				if (tutors.size() == 0)	{
					
					String emptyTutorialId = tutorial.getId();
					System.out.println("The tutorial instance with ID "+ emptyTutorialId + " saved in persistance does not have any assigned tutor(s).");
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
		}catch(Exception e) {
			System.out.println("Could not get all tutorials");
			return null;
		}
	}
	
	
	/*
	 * Get a review saved in the system database using the unique reviewId specified in the GET requestURL parameter.
	 * We also retrieve the session instance associated to the requested review using the unique sessionId passed in the GET requestURL parameter.
	 * 
	 * Returns a reviewDto that contains a sessionDto of its associated session.
	 */
	@GetMapping(value = { 
		"/reviews/{reviewId}",
		"/reviews/{reviewId}/" 
	})
	public ReviewDto getReviewById(@PathVariable("reviewId") String reviewId)	
		throws IllegalArgumentException {
			
		//Get the instances that are required from database
		Review review = service.getReview(reviewId);

		//Create a review DTO. It needs a sessionDto as a parameter	
		ReviewDto reviewDto = convertReviewToDto(review);
		
		return reviewDto;
	}
		
		
	/*
	 * Get all reviews saved in the system database that are associated to a unique Tutor instance saved in the system database.
	 * The name of the concerned Tutor needs to be specified in the GET requestURL parameter.
	 * 
	 * Returns a List<ReviewDto> of reviewDtos that are related to the given tutor.
	 */
	@GetMapping(value = { 
			"/reviews",
			"/reviews/" 
		})
	public List<ReviewDto> getReviewsOfTutor(@RequestParam(name = "tutorName") String tutorName)
		throws IllegalArgumentException {
		
		//Instantiate to null the returned variable
		List<ReviewDto> tutorReviewsDtos = new ArrayList<ReviewDto>();
		
		//Retrieve from database the saved instance
		Tutor tutor = service.getTutor(tutorName);
		
		//Exit this function if the tutor was not fund in the database
		if(tutor == null || tutor.equals(null))	{
			System.out.println("Error: The specified Tutor was not found in the system database. The tutor '"+tutorName+"' does not exist or the specified name ("+tutorName+") might be invalid/contains a spelling error.");
			return tutorReviewsDtos;
		}
		
		//Get all tutorials offered by the specified tutor
		Set<Tutorial> tutorTutorials = tutor.getTutorial();
		
		//The specified tutor does not have any assigned tutorials
		if (tutorTutorials == null || tutorTutorials.equals(null) || tutorTutorials.size() == 0)	{
			System.out.println("The specified tutor ("+tutorName+") does not have any assigned tutorials yet. Therefore, he does not have any reviews to their name at the moment.");
			return tutorReviewsDtos;
		}
		
		
		//The specified tutor only has one assigned Tutorial, thus we do not need to iterate in a for loop
		if (tutorTutorials.size() == 1)	{
			
			
			//We retrieve the only tutorial in the set 
			java.util.Iterator<Tutorial> iterateT = tutorTutorials.iterator();
			Tutorial tutorial = iterateT.next();
			Set<Session> sessions = tutorial.getSession();
			
			
			//The tutor has one tutorial, but no assigned sessions
			if (sessions == null || sessions.equals(null) || sessions.size() == 0)	{
				System.out.println("One tutorial instance was found for the specified tutor ("+tutorName+").However, they have not yet been assigned any sessions yet. Therefore, he does not have any reviews to their name at the moment.");
				return tutorReviewsDtos;
			}
			
			
			//The tutor has one tutorial, with one assigned session
			if (sessions.size() == 1)	{
			
				
				java.util.Iterator<Session> iterateS = sessions.iterator();
				Session session = iterateS.next();
				Set<Review> reviews = session.getReview();
				
				
				//The tutor has one tutorial, with one assigned session, but no reviews have been created
				if (reviews == null || reviews.equals(null) || reviews.size() == 0)	{
				
					
					System.out.println("One session instance for the "+tutorial.getCourse()+" tutorial was found for the specified tutor ("+tutorName+").However, this tutor did not receive any reviews at the moment.");
					return tutorReviewsDtos;
					
					
				//The tutor has one tutorial, with one assigned session and one or multiple review instance(s) have been found	
				} else	{
					for(java.util.Iterator<Review> iterateR = reviews.iterator(); iterateR.hasNext();) {
						Review review = iterateR.next();
						ReviewDto rDto = convertReviewToDto(review);
						tutorReviewsDtos.add(rDto);	
					} 
					return tutorReviewsDtos;
				}
				
				
			//The tutor has one tutorial, with many assigned session instances
			} else	{
				//We iterate through the multiple session instances of the tutorial
				for(java.util.Iterator<Session> iterateS = sessions.iterator(); iterateS.hasNext();) {
					Session session = iterateS.next();
					
					Set<Review> reviews = session.getReview();
					
					//Some sessions might not have any reviews related to them. In this case, we skip to the next loop iteration (continue)
					if (reviews == null || reviews.equals(null) || reviews.size() == 0)	{
						continue;
					
					
					//One or multiple reviews were found. We iterate through the Set of reviews, convert them to Dtos and add them to the List<ReviewDto>
					} else	{
						for(java.util.Iterator<Review> iterateR = reviews.iterator(); iterateR.hasNext();) {
							Review review = iterateR.next();
							ReviewDto rDto = convertReviewToDto(review);
							tutorReviewsDtos.add(rDto);	
						} 
					}
				} 
				return tutorReviewsDtos;
			}	
				
		//The specified tutor has multiple assigned Tutorials
		} else {
			//We iterate through the multiple tutorials assigned to the specified tutor
			for(java.util.Iterator<Tutorial> iterateT = tutorTutorials.iterator(); iterateT.hasNext();) {
				Tutorial tutorial = iterateT.next();

				/* We repeat the same steps in this for loop as the previous steps (i.e. when the tutor had only one tutorial), but we skip */
			
				Set<Session> sessions = tutorial.getSession();
			
				//Some tutorial assigned to the specified tutor might not have any session instance. In this case, we skip this loop iteration (continue)
				if (sessions == null || sessions.equals(null) || sessions.size() == 0)	{
					continue;
				}
				
				//One or multiple session instances were found
				else {
					
					//We iterate through all the multiple session instances
					for(java.util.Iterator<Session> iterateS = sessions.iterator(); iterateS.hasNext();) {
						Session session = iterateS.next();
						
						Set<Review> reviews = session.getReview();
						
						//Some session instances might not have any reviews related to them. In this case, we skip this loop iteration (continue)
						if (reviews == null || reviews.equals(null) || reviews.size() == 0)	{
							continue;
						}
						
						//One or multiple reviews were found. We iterate through the Set of reviews, convert them to Dtos and add them to the List<ReviewDto>
						else	{
							//We iterate through the Set of reviews, convert them to Dtos and add them to the List<ReviewDto>
							for(java.util.Iterator<Review> iterateR = reviews.iterator(); iterateR.hasNext();) {
								Review review = iterateR.next();
								ReviewDto rDto = convertReviewToDto(review);
								tutorReviewsDtos.add(rDto);	
							} 
						}
					}

				}
			}
			if(tutorReviewsDtos.size() == 0)	{
				System.out.println("After completion of this request, although the specified tutor has many assigned tutorials with many session instances, no review instances were found in the system database. Either none exist, or some unknow error occured during the execution of this request.");
			}
		}
		return tutorReviewsDtos;		
	}
	
	@GetMapping(value = { 
			"/reviews/",
			"/reviews//" 
		})
		public List<ReviewDto> getAllReviews()	
			throws IllegalArgumentException {
				
			//Get the instances that are required from database
			List<Review> reviews = service.getAllReviews();
			List<ReviewDto> reviewsDtos = new ArrayList<ReviewDto>();
			for (int i = 0; i < reviews.size(); i++) {
				reviewsDtos.add(convertReviewToDto(reviews.get(i)));
			}
			return reviewsDtos;
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
	

	//HELPER METHOD : Get student from set
	//Since students are returned as sets from our domain model, we need a way to extract the student
	private Student getStudentFromSet(Set<Student> studentset) {
		Student student = null;
		if(studentset.size() == 0) {
			return student; //student will be null
		} else if(studentset.size() > 1) {
			for(java.util.Iterator<Student> iterate = studentset.iterator(); iterate.hasNext();) {
				student = iterate.next();
			}
		} else {
			student = studentset.iterator().next();
		}
		return student;//default
	}
	
	@GetMapping(value = {"/tutorials/tutor/{tutorUserName}","tutorials/tutor/{tutorUserName}/"})
	public List<TutorialDto> getTutorialsOfTutor(@PathVariable("tutorUserName") String tutorUserName){
		try {
			List<TutorialDto> allTutorialsOfTutor  = new ArrayList<>();
			List<Tutorial> allTutorials = service.getAllTutorials();
			Tutor tutor = service.getTutor(tutorUserName);
			TutorDto tutorDto = convertTutorToDto(tutor);
			//TODO: use tutorDto instead for below???
			for(Tutorial t : allTutorials) {
				if(getTutorFromSet(t.getTutor()).equals(tutor)) {
					CourseDto courseDto = convertCourseToDto(t.getCourse());
					allTutorialsOfTutor.add(convertTutorialToDto(tutorDto,courseDto));
				}
			}
			return allTutorialsOfTutor;
		}catch(Exception e) {
			System.out.println("Could not return all tutorials of "+tutorUserName);
			return null;
		}
	}
	
	//1.2) ------ get tutorial of tutor ------
	/*
	private Tutor convertTutorToDomainObject(TutorDto tutorDto) {
		List<Tutor> allTutors = service.getAllTutors();
		for(Tutor t : allTutors) {
			if(t.getUsername().equals(tutorDto.getUsername())) {
				return t;
			}
		}
		return null;
	}*/
	
	/* --------------- THIS SHOULD BE IN SERVICE, BUT IMPLEMENTED DIFFERENTLY---------*/
	/*
	private ArrayList<Tutorial> getAllTutorialsOfTutor(Tutor t){
		ArrayList<Tutorial> returnTutorial = new ArrayList<Tutorial>();
		List<Tutorial> allTutorials = service.getAllTutorials();
		for(Tutorial tutorial: allTutorials) {
			if(getTutorFromSet(tutorial.getTutor()).equals(t)) {
				returnTutorial.add(tutorial);
			}
		} return returnTutorial;
	}*/
	
	//is this even necessary?
	/*
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
	}*/
	
	
	//1.3) get tutorials of a student
	@GetMapping(value = {"/tutorials/student/{studentUsername}" , "tutorials/{studentUsername}/"})
	public List<TutorialDto> getTutorialsOfStudent(@PathVariable("studentUsername") String studentUsername) throws IllegalArgumentException{
		List<TutorialDto> tutorialsOfStudent = new ArrayList<>();
		List<Tutorial> allTutorials = service.getAllTutorials();
		StudentDto studentDto = convertStudentToDto(service.getStudent(studentUsername));
		ArrayList<SessionDto> sessions = studentDto.getSessions();
		// ????
		for(Tutorial t : allTutorials) {
			
		}
		
		return tutorialsOfStudent;
	}
	
	@GetMapping(value = {"/tutorials/{tutorialID}","/tutorials/{tutorialID}/"})
	public TutorialDto getTutorialById(@PathVariable("tutorialID") String tutorialID) throws IllegalArgumentException {
		try {
			TutorialDto tutorialDto = null;
			Tutorial tutorial = service.getTutorial(tutorialID);
			CourseDto courseDto = convertCourseToDto(tutorial.getCourse());
			Tutor tutor = getTutorFromSet(tutorial.getTutor());
			TutorDto tutorDto = convertTutorToDto(tutor);
			tutorialDto = convertTutorialToDto(tutorDto,courseDto);
			return tutorialDto;
		}catch(Exception e) {
			System.out.println("TutorialID : "+tutorialID+" not found");
			return null;
		}
	}
	
	/*	
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
	*/
	
	
	@GetMapping(value = {"/sessions","/sessions/"})
	public List<SessionDto> getAllSessions(){
		try {
			List<Session> allSessions = service.getAllSessions();
			
			List<SessionDto> allSessionsAsDto = new ArrayList<>();
			for(Session s : allSessions) {
				allSessionsAsDto.add(new SessionDto(s.getSessionId()));
			}
			return allSessionsAsDto;
		}catch(Exception e) {
			System.out.println("Could not get all sessions");
			return null;
		}
	}
	
	@GetMapping(value = {"/sessions/student/{studentUsername}","/sessions/student/{studentUsername}/"})
	public List<SessionDto> getAllSessionsOfStudent(@PathVariable("studentUsername") String studentUsername) throws IllegalArgumentException{
		try {
			List<Session> allSessions = service.getAllSessions();
			List<SessionDto> allSessionsOfStudent = new ArrayList<>();
			for(Session s: allSessions) {
				Set<Student> sessionStudents = s.getStudent();
				for (Student student: sessionStudents) {
					if(student.getName().equals(studentUsername)) {
						allSessionsOfStudent.add(new SessionDto(s.getSessionId()));
					}
				}
			}
			return allSessionsOfStudent;	
		}catch(Exception e) {
			System.out.println("Could not get all sessions of "+studentUsername);
			return null;
		}
	}
	
	@GetMapping(value = {"/students","/students/"})
	public List<StudentDto> getAllStudents(){
		try {
			List<Student> allStudents = service.getAllStudents();
			List<StudentDto> studentDtos = new ArrayList<>();
			for(Student s : allStudents) {
				studentDtos.add(convertStudentToDto(s));
			}
			return studentDtos;
		}catch(Exception e) {
			System.out.println("Could not return all students");
			return null;
		}
	}

	
	@GetMapping(value = {"/students/{studentUsername}","/students/{studentUsername}/"})
	public StudentDto getStudentByUsername(@PathVariable("studentUsername") String studentUsername) throws IllegalArgumentException{
		return convertStudentToDto(service.getStudent(studentUsername));
	}
	
	@GetMapping(value = {"/students/{studentUsername}/{password}","/students/{studentUsername}/{password}/"})
	public int login(@PathVariable("studentUsername") String studentUsername, @PathVariable("password") String password) throws IllegalArgumentException{
		Student student = service.getStudent(studentUsername);
		if (student == null) {
			return 401;
		}
		return (password.equals(student.getPassword()) ? 200 : 401);
	}
	
	
	@GetMapping(value = {"/sessions/{sessionID}","/sessions/{sessionID}/"})
	public SessionDto getSessionById(@PathVariable String sessionID) throws IllegalArgumentException {
		try {
			Session session = service.getSession(sessionID);
			Student student = getStudentFromSet(session.getStudent());
			StudentDto studentDto = convertStudentToDto(student);
			SessionDto sessionDto = convertSessionToDto(studentDto.getUsername(),session);
			return sessionDto;
		}catch(Exception e){
			System.out.println("Could not get session "+sessionID);
			return null;
		}
	}
		
	@GetMapping(value = {"/tutors","/tutors/"})
	public List<TutorDto> getAllTutors(){
		try {
			List<Tutor> allTutors = service.getAllTutors();
			List<TutorDto> tutorDtos = new ArrayList<>();
			for(Tutor s : allTutors) {
				tutorDtos.add(convertTutorToDto(s));
			}
			return tutorDtos;			
		}catch(Exception e) {
			System.out.println("Could not get all tutors");
			return null;
		}
	}
	
	@GetMapping(value = {"/tutors/{tutorUsername}","/tutors/{tutorUsername}/"})
	public TutorDto getTutorByUsername(@PathVariable("tutorUsername") String tutorUsername) throws IllegalArgumentException {
		try {
			return convertTutorToDto(service.getTutor(tutorUsername));
		}catch(Exception e) {
			System.out.println("Could not get tutor "+tutorUsername);
			return null;
		}
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
	
}
