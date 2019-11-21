package ca.mcgill.ecse321.tutoringsystem.service;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.tutoringsystem.dao.BillRepository;
import ca.mcgill.ecse321.tutoringsystem.dao.CourseRepository;
import ca.mcgill.ecse321.tutoringsystem.dao.ReviewRepository;
import ca.mcgill.ecse321.tutoringsystem.dao.SessionRepository;
import ca.mcgill.ecse321.tutoringsystem.dao.StudentRepository;
import ca.mcgill.ecse321.tutoringsystem.dao.TutorRepository;
import ca.mcgill.ecse321.tutoringsystem.dao.TutorialRepository;
import ca.mcgill.ecse321.tutoringsystem.model.Bill;
import ca.mcgill.ecse321.tutoringsystem.model.Course;
import ca.mcgill.ecse321.tutoringsystem.model.Review;
import ca.mcgill.ecse321.tutoringsystem.model.Session;
import ca.mcgill.ecse321.tutoringsystem.model.Student;
import ca.mcgill.ecse321.tutoringsystem.model.Tutor;
import ca.mcgill.ecse321.tutoringsystem.model.Tutorial;

@Service
public class TutoringSystemService {
	
	@Autowired
	StudentRepository studentRepository;
	
	@Autowired
	TutorRepository tutorRepository;
	
	@Autowired
	ReviewRepository reviewRepository;
	
	@Autowired
	CourseRepository courseRepository;
	
	@Autowired
	BillRepository billRepository;
	
	@Autowired
	SessionRepository sessionRepository;
	
	@Autowired
	TutorialRepository tutorialRepository;
	
	/**
	 * Create student method
	 * @param name
	 * @param email
	 * @param username
	 * @param password
	 * @return student
	 */
	@Transactional
	public Student createStudent(String name, String email, String username, String password) {
		
		// Input validation
	    String error = "";
	    if (name == null || name.trim().length() == 0) {
	        error = error + "Student name cannot be empty when creating a new Student.";
	    }
	    if (email == null || email.trim().length() == 0) {
	        error = error + "Student email cannot be empty when creating a new Student.";
	    }
	    if (username == null || username.trim().length() == 0) {
	        error = error + "Student username cannot be empty when creating a new Student.";
	    }
	    if (password == null || password.trim().length() == 0) {
	        error = error + "Student password cannot be empty when creating a new Student.";
	    }
	    error = error.trim();
	    if (error.length() > 0) {
	        throw new IllegalArgumentException(error);
	    }
		
	    Student student = studentRepository.findStudentByName(name);
	    if(student == null) {
			student = new Student();
			student.setName(name);
			student.setEmail(email);
			student.setUsername(username);
			student.setPassword(password);
			studentRepository.save(student);
	    }
		return student;
	}
	
	/**
	 * Get student method
	 * @param name
	 * @return student
	 */
	@Transactional
	public Student getStudent(String name) {
		
		if (name == null || name.trim().length() == 0) {
	        throw new IllegalArgumentException("Student name cannot be empty (getStudent).");
	    }
		Student student = studentRepository.findStudentByName(name);
		return student;
	}
	
	/**
	 * Get all students 
	 * @return list of students
	 */
	@Transactional
	public List<Student> getAllStudents() {
		return toList(studentRepository.findAll());
	}
	
	
	/**
	 * Create tutor 
	 * @param name
	 * @param email
	 * @param username
	 * @param password
	 * @param hourlyRate
	 * @return tutor
	 */
	@Transactional
	public Tutor createTutor(String name, String email, String username, String password, double hourlyRate) {
		// Input validation
	    String error = "";
	    if (name == null || name.trim().length() == 0) {
	        error = error + "Tutor name cannot be empty when creating a new Tutor.";
	    }
	    if (email == null || email.trim().length() == 0) {
	        error = error + "Tutor email cannot be empty when creating a new Tutor.";
	    }
	    if (username == null || username.trim().length() == 0) {
	        error = error + "Tutor username cannot be empty when creating a new Tutor.";
	    }
	    if (password == null || password.trim().length() == 0) {
	        error = error + "Tutor password cannot be empty when creating a new Tutor.";
	    }
	    error = error.trim();
	    if (error.length() > 0) {
	        throw new IllegalArgumentException(error);
	    }
	    if (hourlyRate <= 0) {
	        hourlyRate = 20;
	    }
		
		Tutor tutor = tutorRepository.findTutorByName(name);
		if(tutor == null) {
			tutor = new Tutor();
			tutor.setName(name);
			tutor.setEmail(email);
			tutor.setUsername(username);
			tutor.setPassword(password);
			tutor.setHourlyRate(hourlyRate);
			tutorRepository.save(tutor);
		}
		return tutor;
	}
	
	/**
	 * Get tutor
	 * @param name
	 * @return tutor
	 */
	@Transactional
	public Tutor getTutor(String name) {
		Tutor tutor = tutorRepository.findTutorByName(name);
		return tutor;
	}
	
	/**
	 * Get all tutors
	 * @return list of tutors
	 */
	@Transactional
	public List<Tutor> getAllTutors() {
		return toList(tutorRepository.findAll());
	}
	
	/**
	 * Updates tutor in persistence
	 * @param tutor
	 * @return tutor
	 */
	@Transactional
	public Tutor updateTutor(Tutor tutor)	{
		//Very general method that is called to save new info into the persistence 
		tutorRepository.save(tutor);
		return tutor;
	}
	
	
	/**
	 * Create review
	 * @param reviewId
	 * @param comment
	 * @param rating
	 * @return review
	 */
	@Transactional
	public Review createReview(String reviewId, String comment, int rating) {
		// Input validation
	    String error = "";
	    if (reviewId == null || reviewId.trim().length() == 0) {
	        error = error + "Review ReviewId cannot be empty when creating a new Review.";
	    }
	    if (comment == null || comment.trim().length() == 0) {
	        error = error + "Review comment cannot be empty when creating a new Review.";
	    }
	    if (rating < 0 || rating > 10) {
	        error = error + "Review rating must be from 0 to 10.";
	    }
	
	    error = error.trim();
	    if (error.length() > 0) {
	        throw new IllegalArgumentException(error);
	    }
		
	    Review review = reviewRepository.findReviewByReviewId(reviewId);
		if(review == null) {
		    review = new Review();
			review.setReviewId(reviewId);
			review.setComment(comment);
			review.setRating(rating);
			reviewRepository.save(review);
		}
		return review;
	}
	
	/**
	 * Get review
	 * @param reviewId
	 * @return review
	 */
	@Transactional
	public Review getReview(String reviewId) {
		Review review = reviewRepository.findReviewByReviewId(reviewId);
		return review;
	}
	
	/**
	 * Get all reviews
	 * @return list of reviews
	 */
	@Transactional
	public List<Review> getAllReviews() {
		return toList(reviewRepository.findAll());
	}
	
	/**
	 * Create course
	 * @param courseId
	 * @param courseName
	 * @return course 
	 */
	@Transactional
	public Course createCourse(String courseId, String courseName) {
		// Input validation
	    String error = "";
	    if (courseId == null || courseId.trim().length() == 0) {
	        error = error + "Course courseId cannot be empty when creating a new Course.";
	    }
	    if (courseName == null || courseName.trim().length() == 0) {
	        error = error + "Course name cannot be empty when creating a new Course.";
	    }
	
	    error = error.trim();
	    if (error.length() > 0) {
	        throw new IllegalArgumentException(error);
	    }
		
	    Course course = courseRepository.findCourseByCourseId(courseId);
		if(course == null) {
			course = new Course();
			course.setCourseId(courseId);
			course.setCourseName(courseName);
			courseRepository.save(course);
		}
		return course;
	}
	
	/**
	 * Get course
	 * @param courseId
	 * @return course
	 */
	@Transactional
	public Course getCourse(String courseId) {
		Course course = courseRepository.findCourseByCourseId(courseId);
		return course;
	}
	
	/**
	 * Get all courses
	 * @return list of courses
	 */
	@Transactional
	public List<Course> getAllCourses() {
		return toList(courseRepository.findAll());
	}
	
	/**
	 * Create bill 
	 * @param isPaid
	 * @param billId
	 * @return bill
	 */
	@Transactional
	public Bill createBill(boolean isPaid, int billId) {
		
	    Bill bill = billRepository.findBillByBillId(billId);
		if(bill == null) {
			bill = new Bill();
			bill.setBillId(billId);
			bill.setIsPaid(isPaid);
			billRepository.save(bill);
		}
		return bill;
	}
	
	/**
	 * Get bill
	 * @param billId
	 * @return bill
	 */
	@Transactional
	public Bill getBill(int billId) {
		Bill bill = billRepository.findBillByBillId(billId);
		return bill;
	}
	
	/**
	 * Get all bills
	 * @return list of bills
	 */
	@Transactional
	public List<Bill> getAllBills() {
		return toList(billRepository.findAll());
	}
	
	/**
	 * Create session
	 * @param sessionId
	 * @param startTime
	 * @param endTime
	 * @param date
	 * @param bill
	 * @param tutorial
	 * @param student
	 * @return session
	 */
	@Transactional
	public Session createSession(String sessionId, Time startTime, Time endTime, Date date, Bill bill, Tutorial tutorial, Student student) {
		// Input validation
	    String error = "";
	    if (sessionId == null || sessionId.trim().length() == 0) {
	        error = error + "Session sessionId cannot be empty when creating a new Session.";
	    }
	    if (bill == null) {
	        error = error + "Session's bill cannot be empty when creating a new Session.";
	    }
	    if (tutorial == null) {
	        error = error + "Session's tutorial cannot be empty when creating a new Session.";
	    }
	    if (startTime == null) {
	        error = error + "Session's startTime cannot be empty when creating a new Session.";
	    }
	    if (endTime == null) {
	        error = error + "Session's endTime cannot be empty when creating a new Session.";
	    }
	    if (date == null) {
	        error = error + "Session's date cannot be empty when creating a new Session.";
	    }
	    if (student == null) {
	        error = error + "Session's student cannot be empty when creating a new Session.";
	    }
	
	    error = error.trim();
	    if (error.length() > 0) {
	        throw new IllegalArgumentException(error);
	    }
		
	    Session session = sessionRepository.findSessionBySessionId(sessionId);
		if(session == null) {
			session = new Session();
			session.setSessionId(sessionId);
			session.setStartTime(startTime);
			session.setEndTime(endTime);
			session.setDate(date);
			session.setBill(bill);
			session.setTutorial(tutorial);

			Set<Student> registeredStudents = new HashSet<>();
			registeredStudents.add(student);
			session.setStudent(registeredStudents);
			
			sessionRepository.save(session);
		}
		return session;	
	}
	
	/**
	 * Get session
	 * @param sessionId
	 * @return session
	 */
	@Transactional
	public Session getSession(String sessionId) {
		Session session = sessionRepository.findSessionBySessionId(sessionId);
		return session;
	}
	
	/**
	 * Delete session
	 * @param id
	 * @return status code (200 success, 500 error)
	 */
	@Transactional
	public int deleteSession(String id) {
		Optional<Session> session = sessionRepository.findById(id);
		if (session.isPresent()) {
			sessionRepository.deleteById(id);
			return 200;
		}
		return 500;
	}
	
	
	/**
	 * Update session in persistence
	 * @param session
	 * @return session
	 */
	@Transactional
	public Session updateSession(Session session)	{
		//Very general method that is called to save new info into the persistence 
		sessionRepository.save(session);
		return session;
	}
	
	/**
	 * Get all sessions
	 * @return list of sessions
	 */
	@Transactional
	public List<Session> getAllSessions() {
		return toList(sessionRepository.findAll());
	}
	
	/**
	 * Create tutorial
	 * @param id
	 * @param course
	 * @param tutor
	 * @return tutorial
	 */
	@Transactional
	public Tutorial createTutorial(String id, Course course, Tutor tutor) {
		// Input validation
	    String error = "";
	    if (id == null || id.trim().length() == 0) {
	        error = error + "Tutorial Id cannot be empty when creating a new Tutorial.";
	    }
	    if (course == null) {
	        error = error + "Tutorial's course cannot be empty when creating a new Tutorial.";
	    }
	
	    error = error.trim();
	    if (error.length() > 0) {
	        throw new IllegalArgumentException(error);
	    }
		
	    Tutorial tutorial = tutorialRepository.findTutorialById(id);
		if(tutorial == null) {
			
			tutorial = new Tutorial();
			tutorial.setId(id);
			tutorial.setCourse(course);
			
			Set<Tutor> tutors = new HashSet<>();
			tutors.add(tutor);
			tutorial.setTutor(tutors);
			
			tutorialRepository.save(tutorial);
		}
		return tutorial;
	}
	
	/**
	 * Get tutorial
	 * @param id
	 * @return tutorial
	 */
	@Transactional
	public Tutorial getTutorial(String id) {
		Tutorial tutorial = tutorialRepository.findTutorialById(id);
		return tutorial;
	}
	
	/**
	 * Get all tutorials
	 * @return list of all tutorials
	 */
	@Transactional
	public List<Tutorial> getAllTutorials() {
		return toList(tutorialRepository.findAll());
	}
	
	/**
	 * Add tutor to tutorial
	 * @param tutor
	 * @param tutorialId
	 */
	@Transactional
	public void addTutorToTutorial(Tutor tutor, String tutorialId)	{
		
		Tutorial tutorial = getTutorial(tutorialId);
		
		Set<Tutor> tutors = tutorial.getTutor();
		tutors.add(tutor);
		
		tutorial.setTutor(tutors);
	}
	
	/**
	 * Convert to list
	 * @param <T>
	 * @param iterable
	 * @return list
	 */
	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}


}
