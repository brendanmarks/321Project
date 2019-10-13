package ca.mcgill.ecse321.tutoringsystem.service;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

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
	
	//Services to create, get and get all students
	@Transactional
	public Student createStudent(String name, String email, String username, String password) {
		Student student = new Student();
		student.setName(name);
		student.setEmail(email);
		student.setUsername(username);
		student.setPassword(password);
		studentRepository.save(student);
		return student;
	}
	
	@Transactional
	public Student getStudent(String name) {
		Student student = studentRepository.findStudentByName(name);
		return student;
	}
	
	@Transactional
	public List<Student> getAllStudents() {
		return toList(studentRepository.findAll());
	}
	
	//Services to create, get and get all tutors
	@Transactional
	public Tutor createTutor(String name, String email, String username, String password, double hourlyRate) {
		Tutor tutor = new Tutor();
		
		tutor.setName(name);
		tutor.setEmail(email);
		tutor.setUsername(username);
		tutor.setPassword(password);
		tutor.setHourlyRate(hourlyRate);
		tutorRepository.save(tutor);
		return tutor;
	}
	
	@Transactional
	public Tutor getTutor(String name) {
		Tutor tutor = tutorRepository.findTutorByName(name);
		return tutor;
	}
	
	@Transactional
	public List<Tutor> getAllTutors() {
		return toList(tutorRepository.findAll());
	}
	
	//Services to create, get and get all reviews
	@Transactional
	public Review createReview(String reviewId, String comment, int rating) {
		Review review = new Review();
		review.setReviewId(reviewId);
		review.setComment(comment);
		review.setRating(rating);
		reviewRepository.save(review);
		return review;
	}
	
	@Transactional
	public Review getReview(String reviewId) {
		Review review = reviewRepository.findReviewByReviewId(reviewId);
		return review;
	}
	
	@Transactional
	public List<Review> getAllReviews() {
		return toList(reviewRepository.findAll());
	}
	
	//Services to create, get and get all courses
	@Transactional
	public Course createCourse(String courseId, String courseName) {
		Course course = new Course();
		course.setCourseId(courseId);
		course.setCourseName(courseName);
		courseRepository.save(course);
		return course;
	}
	
	@Transactional
	public Course getCourse(String courseId) {
		Course course = courseRepository.findCourseByCourseId(courseId);
		return course;
	}
	
	@Transactional
	public List<Course> getAllCourses() {
		return toList(courseRepository.findAll());
	}
	
	//Services to create, get and get all bills
	@Transactional
	public Bill createBill(boolean isPaid, int billId) {
		Bill bill = new Bill();
		bill.setBillId(billId);
		bill.setIsPaid(isPaid);
		billRepository.save(bill);
		return bill;
	}
	
	@Transactional
	public Bill getBill(int billId) {
		Bill bill = billRepository.findBillByBillId(billId);
		return bill;
	}
	
	@Transactional
	public List<Bill> getAllBills() {
		return toList(billRepository.findAll());
	}
	
	//Services to create, get and get all sessions
	@Transactional
	public Session createSession(String sessionId, Time startTime, Time endTime, Date date, Bill bill, Tutorial tutorial) {
		Session session = new Session();
		session.setSessionId(sessionId);
		session.setStartTime(startTime);
		session.setEndTime(endTime);
		session.setDate(date);
		session.setBill(bill);
		session.setTutorial(tutorial);
		sessionRepository.save(session);
		return session;	
	}
	
	@Transactional
	public Session getSession(String sessionId) {
		Session session = sessionRepository.findSessionBySessionId(sessionId);
		return session;
	}
	
	@Transactional
	public List<Session> getAllSessions() {
		return toList(sessionRepository.findAll());
	}
	
	//Services to create, get and get all sessions
	@Transactional
	public Tutorial createTutorial(String id, Course course) {
		Tutorial tutorial = new Tutorial();
		tutorial.setId(id);
		tutorial.setCourse(course);
		tutorialRepository.save(tutorial);
		return tutorial;
	}
	
	@Transactional
	public Tutorial getTutorial(String id) {
		Tutorial tutorial = tutorialRepository.findTutorialById(id);
		return tutorial;
	}
	
	@Transactional
	public List<Tutorial> getAllTutorials() {
		return toList(tutorialRepository.findAll());
	}
	
	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}


}
