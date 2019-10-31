package ca.mcgill.ecse321.tutoringsystem.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.ParentRunner;
import org.junit.runners.model.FrameworkMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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

import org.mockito.invocation.InvocationOnMock;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TutoringSystemServiceMockTest {
	
	@Mock
	private StudentRepository studentDao;
	@Mock
	private TutorRepository tutorDao;
	@Mock
	private ReviewRepository reviewDao;
	@Mock
	private CourseRepository courseDao;
	@Mock
	private BillRepository billDao;
	@Mock
	private TutorialRepository tutorialDao;
	@Mock
	private SessionRepository sessionDao;
		
	@InjectMocks
	private TutoringSystemService service;
		
	//Student
	private static final String STUDENT_KEY_1 = "Brad";
	private static final String STUDENT_KEY_2 = "Sami";
	private static final String NONEXISTING_STUDENT_KEY = "NotAStudent";
	private static final String STUDENT_EMAIL = "brad@gmail.com";
	private static final String STUDENT_USERNAME = "brad1";
	private static final String STUDENT_PASSWORD = "bradpassword";
	
	//Tutor
	private static final String TUTOR_KEY_1 = "Brendan";
	private static final String TUTOR_KEY_2 = "Sean";
	private static final String NONEXISTING_TUTOR_KEY = "NotATutor";
	private static final String TUTOR_EMAIL= "brendan@gmail.com";
	private static final String TUTOR_USERNAME = "brendan1";
	private static final String TUTOR_PASSWORD = "brendanpassword";
	private static final Double TUTOR_HOURLY_RATE = 20.0;
	
	//Review 
	private static final String REVIEW_KEY_1 = "1234";
	private static final String REVIEW_KEY_2 = "5678";
	private static final String NONEXISTING_REVIEW_KEY = "NotaReview";
	private static final String REVIEW_COMMENT = "Great Experience";
	private static final Integer REVIEW_RATING = 5;
	
	//Course
	private static final String COURSE_KEY_1 = "0001";
	private static final String COURSE_KEY_2 = "0002";
	private static final String NONEXISTING_COURSE_KEY = "NotaCourse";
	private static final String COURSE_NAME = "MATH240";
	
	//Bill
	private static final int BILL_KEY_1 = 1111;
	private static final Integer BILL_KEY_2 = 2222;
	private static final Integer NONEXISTING_BILL_KEY = 5555;
	private static final Boolean BILL_ISPAID = true;
	
	//Tutorial
	private static final String TUTORIAL_KEY_1 = "11111";
	private static final String TUTORIAL_KEY_2 = "22222";
	private static final String NONEXISTING_TUTORIAL_KEY = "55555";
	
	//Session
	private static final String SESSION_KEY_1 = "11";
	private static final String SESSION_KEY_2 = "22";
	private static final String NONEXISTING_SESSION_KEY = "55";
	
	
	@Before
	public void setMockOutput() {
		when(studentDao.findStudentByName(anyString())).thenAnswer( (InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(STUDENT_KEY_1)) {
				Student student = new Student();
				student.setName(STUDENT_KEY_1);
				student.setEmail(STUDENT_EMAIL);
				student.setPassword(STUDENT_USERNAME);
				student.setUsername(STUDENT_PASSWORD);
				return student;
			} else {
				return null;
			}
		});
		
		when(studentDao.findAll()).thenAnswer( (InvocationOnMock invocation) -> {
				List<Student> students = new ArrayList<Student>();
				Student student1 = new Student();
				Student student2 = new Student();
				student1.setName(STUDENT_KEY_1);
				student2.setName(STUDENT_KEY_2);
				students.add(student1);
				students.add(student2);
				return students;
		});
		
		when(tutorDao.findTutorByName(anyString())).thenAnswer( (InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(TUTOR_KEY_1)) {
				Tutor tutor = new Tutor();
				tutor.setName(TUTOR_KEY_1);
				tutor.setEmail(TUTOR_EMAIL);
				tutor.setPassword(TUTOR_USERNAME);
				tutor.setUsername(TUTOR_PASSWORD);
				tutor.setHourlyRate(TUTOR_HOURLY_RATE);
				return tutor;
			} else {
				return null;
			}
		});
		
		when(tutorDao.findAll()).thenAnswer( (InvocationOnMock invocation) -> {
				List<Tutor> tutors = new ArrayList<Tutor>();
				Tutor tutor1 = new Tutor();
				Tutor tutor2 = new Tutor();
				tutor1.setName(TUTOR_KEY_1);
				tutor2.setName(TUTOR_KEY_2);
				tutors.add(tutor1);
				tutors.add(tutor2);
				return tutors;
		});
		
		when(reviewDao.findReviewByReviewId(anyString())).thenAnswer( (InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(REVIEW_KEY_1)) {
				Review review = new Review();
				review.setReviewId(REVIEW_KEY_1);
				review.setComment(REVIEW_COMMENT);
				review.setRating(REVIEW_RATING);
				return review;
			} else {
				return null;
			}
		});
		
		when(reviewDao.findAll()).thenAnswer( (InvocationOnMock invocation) -> {
				List<Review> reviews = new ArrayList<Review>();
				Review review1 = new Review();
				Review review2 = new Review();
				review1.setReviewId(REVIEW_KEY_1);
				review2.setReviewId(REVIEW_KEY_2);
				reviews.add(review1);
				reviews.add(review2);
				return reviews;
		});
		
		when(courseDao.findCourseByCourseId(anyString())).thenAnswer( (InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(COURSE_KEY_1)) {
				Course course = new Course();
				course.setCourseId(COURSE_KEY_1);
				course.setCourseName(COURSE_NAME);
				return course;
			} else {
				return null;
			}
		});
		
		when(courseDao.findAll()).thenAnswer( (InvocationOnMock invocation) -> {
				List<Course> courses = new ArrayList<Course>();
				Course course1 = new Course();
				Course course2 = new Course();
				course1.setCourseId(COURSE_KEY_1);
				course2.setCourseId(COURSE_KEY_2);
				courses.add(course1);
				courses.add(course2);
				return courses;
		});
		
		when(billDao.findBillByBillId(anyInt())).thenAnswer( (InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(BILL_KEY_1)) {
				Bill bill = new Bill();
				bill.setBillId(BILL_KEY_1);
				bill.setIsPaid(BILL_ISPAID);
				return bill;
			} else {
				return null;
			}
		});
		
		when(billDao.findAll()).thenAnswer( (InvocationOnMock invocation) -> {
				List<Bill> bills = new ArrayList<Bill>();
				Bill bill1 = new Bill();
				Bill bill2 = new Bill();
				bill1.setBillId(BILL_KEY_1);
				bill2.setBillId(BILL_KEY_2);
				bills.add(bill1);
				bills.add(bill2);
				return bills;
		});
		
		when(tutorialDao.findTutorialById(anyString())).thenAnswer( (InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(TUTORIAL_KEY_1)) {
				Tutorial tutorial = new Tutorial();
				Course course = new Course();
				tutorial.setId(TUTORIAL_KEY_1);
				tutorial.setCourse(course);
				return tutorial;
			} else {
				return null;
			}
		});
		
		when(tutorialDao.findAll()).thenAnswer( (InvocationOnMock invocation) -> {
			List<Tutorial> tutorials = new ArrayList<Tutorial>();
			Tutorial tutorial1 = new Tutorial();
			Tutorial tutorial2 = new Tutorial();
			tutorial1.setId(TUTORIAL_KEY_1);
			tutorial2.setId(TUTORIAL_KEY_2);
			tutorials.add(tutorial2);
			tutorials.add(tutorial2);
			return tutorials;
		});
		
		when(sessionDao.findSessionBySessionId(anyString())).thenAnswer( (InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(SESSION_KEY_1)) {
				Session session = new Session();
				Tutorial tutorial = new Tutorial();
				Bill bill = new Bill();
				session.setSessionId(SESSION_KEY_1);
				session.setTutorial(tutorial);
				session.setBill(bill);
				return session;
			} else {
				return null;
			}
		});
		
		when(sessionDao.findAll()).thenAnswer( (InvocationOnMock invocation) -> {
			List<Session> sessions = new ArrayList<Session>();
			Session session1 = new Session();
			Session session2 = new Session();
			session1.setSessionId(SESSION_KEY_1);
			session2.setSessionId(SESSION_KEY_2);
			sessions.add(session1);
			sessions.add(session2);
			return sessions;
		});
	}
	
	
	/**====================== Student Tests  ===========================*/
	@Test
	public void testCreateStudent() {
		String name = "John";
		String email = "john@gmail.com";
		String username = "john1";
		String password = "johnpassword";
		Student student = new Student();
		
		try {
			student = service.createStudent(name, email, username, password);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		assertEquals(name, student.getName());
		
	}

	@Test
	public void testCreateStudentNull() {
		String name = null;
		String email = "";
		String username = null;
		String password = null;
		String error = null;
		Student student = new Student();
		try {
			student = service.createStudent(name, email, username, password);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		// check error
		assertEquals("Student name cannot be empty when creating a new Student."
				+ "Student email cannot be empty when creating a new Student."
				+ "Student username cannot be empty when creating a new Student."
				+ "Student password cannot be empty when creating a new Student.", error);
	}
	
		
	@Test
	public void testGetStudentByNameExistingName() {
		Student student = service.getStudent(STUDENT_KEY_1);
		assertEquals(STUDENT_KEY_1, student.getName());
	}
	
	@Test
	public void testGetStudentByNameNonExistingName() {
		Student student = service.getStudent(NONEXISTING_STUDENT_KEY);
		assertNull(student);
	}
	
	@Test
	public void testGetAllStudents() {
		List<Student> students = service.getAllStudents();
		assertEquals(2, students.size());
	}
	
	
	/**====================== Tutor Tests  ===========================*/
	@Test
	public void testCreateTutor() {
		String name = "John";
		String email = "john@gmail.com";
		String username = "john1";
		String password = "johnpassword";
		Double hourlyRate = 25.00;
		Tutor tutor = new Tutor();
		
		try {
			tutor = service.createTutor(name, email, username, password, hourlyRate);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		assertEquals(name, tutor.getName());
		
	}

	@Test
	public void testCreateTutorNull() {
		String name = null;
		String email = "";
		String username = null;
		String password = null;
		Double hourlyRate = 0.00;
		String error = null;
		Tutor tutor = new Tutor();
		try {
			tutor = service.createTutor(name, email, username, password, hourlyRate);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		// check error
		assertEquals("Tutor name cannot be empty when creating a new Tutor."
				+ "Tutor email cannot be empty when creating a new Tutor."
				+ "Tutor username cannot be empty when creating a new Tutor."
				+ "Tutor password cannot be empty when creating a new Tutor.", error);
	}
	
	@Test
	public void testGetTutorByNameExistingName() {
		Tutor tutor = service.getTutor(TUTOR_KEY_1);
		assertEquals(TUTOR_KEY_1, tutor.getName());
	}
	
	@Test
	public void testGetTutorByNameNonExistingName() {
		Tutor tutor = service.getTutor(NONEXISTING_TUTOR_KEY);
		assertNull(tutor);
	}
	
	@Test
	public void testGetAllTutors() {
		List<Tutor> tutors = service.getAllTutors();
		assertEquals(2, tutors.size());
	}
	
	/**====================== Review Tests  ===========================*/

	@Test
	public void testCreateReview() {
		
		String reviewId = "r1";
		String comment = "It was a great experience.";
		int rating = 5;
		Review review = new Review();
		
		try {
			review = service.createReview(reviewId, comment, rating);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		assertEquals(rating, review.getRating());
		
	}
	
	@Test
	public void testCreateReviewNull() {
		String reviewId = null;
		String comment = null;
		int rating = -5;
		String error = null;
		Review review = new Review();
		
		try {
			review = service.createReview(reviewId, comment, rating);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}

		
		// check error
		assertEquals("Review ReviewId cannot be empty when creating a new Review."
				+ "Review comment cannot be empty when creating a new Review."
				+ "Review rating must be from 0 to 10.", error);
	}
	
	@Test
	public void testGetReviewByReviewIdExistingReviewId() {
		Review review = service.getReview(REVIEW_KEY_1);
		assertEquals(REVIEW_KEY_1, review.getReviewId());
	}
	
	@Test
	public void testGetReviewByReviewIdNonExistingReviewId() {
		Review review = service.getReview(NONEXISTING_REVIEW_KEY);
		assertNull(review);
	}
	
	@Test
	public void testGetAllReviews() {
		List<Review> reviews = service.getAllReviews();
		assertEquals(2, reviews.size());
	}
	
	/**====================== Course Tests  ===========================*/
	@Test
	public void testCreateCourse() {
		String courseId = "0010";
		String courseName = "ECSE321";
		Course course = new Course();
		
		try {
			course = service.createCourse(courseId, courseName);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		
		assertEquals(courseId, course.getCourseId());
		
	}
	
	@Test
	public void testCreateCourseNull() {
		String courseId = null;
		String courseName = "";
		Course course = new Course();
		String error = null;

		
		try {
			course = service.createCourse(courseId, courseName);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}

		
		// check error
		assertEquals("Course courseId cannot be empty when creating a new Course."
				+ "Course name cannot be empty when creating a new Course.", error);
	}
	
	@Test
	public void testGetCourseByCourseIdExistingCourseId() {
		Course course = service.getCourse(COURSE_KEY_1);
		assertEquals(COURSE_KEY_1, course.getCourseId());
	}
	
	@Test
	public void testGetCourseByCourseIdNonExistingCourseId() {
		Course course = service.getCourse(NONEXISTING_COURSE_KEY);
		assertNull(course);
	}
	
	@Test
	public void testGetAllCourses() {
		List<Course> courses = service.getAllCourses();
		assertEquals(2, courses.size());
	}

	/**====================== Bill Tests  ===========================*/
	@Test
	public void testCreateBill() {
		
		boolean isPaid = false;
		int billId = 9999;
		Bill bill = new Bill();
		
		try {
			bill = service.createBill(isPaid, billId);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		
		assertEquals(billId, bill.getBillId());
		
	}
	
	
	@Test
	public void testGetBillByBillIdExistingBillId() {
		Bill bill = service.getBill(BILL_KEY_1);
		assertEquals(BILL_KEY_1, bill.getBillId());
	}
	
	@Test
	public void testGetBillByBillIdNonExistingBillId() {
		Bill bill = service.getBill(NONEXISTING_BILL_KEY);
		assertNull(bill);
	}
	
	@Test
	public void testGetAllBills() {
		List<Bill> bills = service.getAllBills();
		assertEquals(2, bills.size());
	}
	/**====================== Tutorial Tests  ===========================*/
	@Test
	public void testCreateTutorial() {
		
		String tutorialId = "t1";
		
		Course course = new Course();
		String courseId = "ECSE321";
		String courseName = "Intro to Software Engineering";
		course.setCourseId(courseId);
		course.setCourseName(courseName);
		
		Tutorial tutorial = new Tutorial();
		Tutor tutor = new Tutor();
		
		try {
			tutorial = service.createTutorial(tutorialId, course, tutor);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		assertEquals(tutorialId, tutorial.getId());
		
	}
	@Test
	public void testCreateTutorialNull() {
		String tutorialId = null;
		Course course = null;	
		Tutorial tutorial = new Tutorial();
		Tutor tutor = new Tutor();
		
		String error = null;

		
		try {
			tutorial = service.createTutorial(tutorialId, course, tutor);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}

		
		// check error
		assertEquals("Tutorial Id cannot be empty when creating a new Tutorial."
				+ "Tutorial's course cannot be empty when creating a new Tutorial.", error);
	}
	@Test
	public void testGetTutorialByIdExistingId() {
		Tutorial tutorial = service.getTutorial(TUTORIAL_KEY_1);
		assertEquals(TUTORIAL_KEY_1, tutorial.getId());
	}
	
	@Test
	public void testGetTutorialByIdNonExistingId() {
		Tutorial tutorial = service.getTutorial(NONEXISTING_TUTORIAL_KEY);
		assertNull(tutorial);
	}
	
	@Test
	public void testGetAllTutorials() {
		List<Tutorial> tutorials = service.getAllTutorials();
		assertEquals(2, tutorials.size());
	}
	
	/**====================== Session Tests  ===========================*/
	@Test
	public void testCreateSession() {
		Session session = new Session();
		Tutorial tutorial = new Tutorial();
		Bill bill = new Bill();
		Time startTime = Time.valueOf("10:30:00");
		Time endTime = Time.valueOf("11:30:00");
		Date date = Date.valueOf("2020-01-10");
		String sessionId = "session1";
		
		try {
			session = service.createSession(sessionId, startTime, endTime, date, bill, tutorial);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		assertEquals(sessionId, session.getSessionId());
		
	}
	
	@Test
	public void testCreateSessionNull() {
		Session session = new Session();
		Tutorial tutorial = null;
		Bill bill = null;
		Time startTime = null;
		Time endTime = null;
		Date date = null;
		String sessionId = "";
		String error = null;
		try {
			session = service.createSession(sessionId, startTime, endTime, date, bill, tutorial);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
		assertEquals("Session sessionId cannot be empty when creating a new Session."
				+ "Session's bill cannot be empty when creating a new Session." 
				+ "Session's tutorial cannot be empty when creating a new Session."
				+ "Session's startTime cannot be empty when creating a new Session." 
				+ "Session's endTime cannot be empty when creating a new Session."
				+ "Session's date cannot be empty when creating a new Session.", error);
		
	}
	
	
	@Test
	public void testGetSessionBySessionIdExistingSessionId() {
		Session session = service.getSession(SESSION_KEY_1);
		assertEquals(SESSION_KEY_1, session.getSessionId());
	}
	
	@Test
	public void testGetSessionBySessionIdNonExistingSessionId() {
		Session session = service.getSession(NONEXISTING_SESSION_KEY);
		assertNull(session);
	}
	
	@Test
	public void testGetAllSessions() {
		List<Session> sessions = service.getAllSessions();
		assertEquals(2, sessions.size());
	}
	
}
