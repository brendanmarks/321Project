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
import ca.mcgill.ecse321.tutoringsystem.model.Student;
import ca.mcgill.ecse321.tutoringsystem.model.Tutor;
import ca.mcgill.ecse321.tutoringsystem.model.Tutorial;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
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
	}
	
	
	//This test creates a student and then reads its attributes from the database
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
	
	
	//This test creates a review and then reads its attributes from the database
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
	public void testCreateReviewrNull() {
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

	
}
