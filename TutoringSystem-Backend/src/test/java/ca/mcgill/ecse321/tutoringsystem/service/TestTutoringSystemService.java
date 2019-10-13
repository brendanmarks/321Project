package ca.mcgill.ecse321.tutoringsystem.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ca.mcgill.ecse321.tutoringsystem.dao.BillRepository;
import ca.mcgill.ecse321.tutoringsystem.dao.CourseRepository;
import ca.mcgill.ecse321.tutoringsystem.dao.ReviewRepository;
import ca.mcgill.ecse321.tutoringsystem.dao.SessionRepository;
import ca.mcgill.ecse321.tutoringsystem.dao.StudentRepository;
import ca.mcgill.ecse321.tutoringsystem.dao.TutorRepository;
import ca.mcgill.ecse321.tutoringsystem.model.Bill;
import ca.mcgill.ecse321.tutoringsystem.model.Course;
import ca.mcgill.ecse321.tutoringsystem.model.Review;
import ca.mcgill.ecse321.tutoringsystem.model.Student;
import ca.mcgill.ecse321.tutoringsystem.model.Tutor;
import ca.mcgill.ecse321.tutoringsystem.model.Tutorial;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestTutoringSystemService {
	
	@Autowired
	private TutoringSystemService service;
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private TutorRepository tutorRepository;
	
	@Autowired
	private ReviewRepository reviewRepository;
	
	@Autowired
	private CourseRepository courseRepository;
	
	@Autowired
	private BillRepository billRepository;
	
	@Autowired
	private SessionRepository sessionRepository;
	
	
	@Before
	public void setupDatabase() {
		// Then we can clear the other tables
		studentRepository.deleteAll();
		tutorRepository.deleteAll();
		reviewRepository.deleteAll();
		courseRepository.deleteAll();
		billRepository.deleteAll();
		sessionRepository.deleteAll();
	}
	
	//@After
	public void clearDatabase() {
		// Then we can clear the other tables
		studentRepository.deleteAll();
		tutorRepository.deleteAll();
		reviewRepository.deleteAll();
		courseRepository.deleteAll();
		billRepository.deleteAll();
		sessionRepository.deleteAll();
	}
	@Test
	public void testCreateAndGetStudent() {
		assertEquals(0, service.getAllStudents().size());
		String name = "John";
		String email = "john@gmail.com";
		String username = "john1";
		String password = "johnpassword";

		try {
			service.createStudent(name, email, username, password);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		

		List<Student> allStudents = service.getAllStudents();
		
		assertEquals(1, allStudents.size());
		assertEquals("John", allStudents.get(0).getName());
		assertEquals("john@gmail.com", allStudents.get(0).getEmail());
		assertEquals("john1", allStudents.get(0).getUsername());
		assertEquals("johnpassword", allStudents.get(0).getPassword());
		assertEquals("John", service.getStudent(name).getName());
		
	}
	
	@Test
	public void testCreateAndGetTutor() {
		assertEquals(0, service.getAllTutors().size());
		
		String name = "Daniel";
		String email = "daniel@gmail.com";
		String username = "Daniel1";
		String password = "danielpassword";
		double hourlyRate = 20;

		try {
			service.createTutor(name, email, username, password, hourlyRate);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		

		List<Tutor> allTutors = service.getAllTutors();
		
		assertEquals(1, allTutors.size());
		assertEquals("Daniel", allTutors.get(0).getName());
		assertEquals("daniel@gmail.com", allTutors.get(0).getEmail());
		assertEquals("Daniel1", allTutors.get(0).getUsername());
		assertEquals("danielpassword", allTutors.get(0).getPassword());
		assertEquals(20, (int)(allTutors.get(0).getHourlyRate()));
		assertEquals("Daniel", service.getTutor(name).getName());
	}
	@Test
	public void testCreateAndGetReview() {
		assertEquals(0, service.getAllReviews().size());
		
		String reviewId = "r1";
		String comment = "It was a great experience.";
		int rating = 5;
		
		try {
			service.createReview(reviewId, comment, rating);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		
		List<Review> allReviews = service.getAllReviews();
		
		assertEquals(1, allReviews.size());
		assertEquals("r1", allReviews.get(0).getReviewId());
		assertEquals("It was a great experience.", allReviews.get(0).getComment());
		assertEquals(5, allReviews.get(0).getRating());
		assertEquals(5, service.getReview(reviewId).getRating());
		
	}
	
	@Test
	public void testCreateAndGetCourse() {
		assertEquals(0, service.getAllCourses().size());
		
		String courseId = "MATH240";
		String courseName = "Discrete Structures";
		
		try {
			service.createCourse(courseId, courseName);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		

		List<Course> allCourses = service.getAllCourses();
		
		assertEquals(1, allCourses.size());
		assertEquals("Discrete Structures", allCourses.get(0).getCourseName());
		assertEquals("MATH240", allCourses.get(0).getCourseId());
		assertEquals("MATH240", service.getCourse(courseId).getCourseId());
		
		
	}
	
	@Test
	public void testCreateAndGetBill() {
		assertEquals(0, service.getAllBills().size());
		
		boolean isPaid = false;
		int billId = 101;
		
		try {
			service.createBill(isPaid, billId);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		
		List<Bill> allBills = service.getAllBills();
		
		assertEquals(1, allBills.size());
		assertEquals(101, allBills.get(0).getBillId());
		assertEquals(false, service.getBill(101).isIsPaid());
		
		
	}

	/*@Test
	public void testCreateAndGetSession() {
		assertEquals(0, service.getAllCourses().size());
		
		String sessionId = "s1";
		Time startTime = Time.valueOf("10:30:00");
		Time endTime = Time.valueOf("11:30:00");
		Date date = Date.valueOf("2020-01-10");
		
		List<Bill> allBills = null;
		
		//Checking if a bill is already made in the database (by testCreateAndGetBill)
		try {
			allBills.get(0);
		} catch(NullPointerException e) {
			fail();
		}
		Bill bill = allBills.get(0);
		
		Tutorial tutorial = new Tutorial();
		
		try {
			service.createSession(sessionId, startTime, endTime, date, bill, tutorial);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		

	}*/
	
	

}
