package ca.mcgill.ecse321.tutoringsystem.dao;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.tutoringsystem.model.Person;
import ca.mcgill.ecse321.tutoringsystem.model.Student;
import ca.mcgill.ecse321.tutoringsystem.model.Tutor;
import ca.mcgill.ecse321.tutoringsystem.model.Session;
import ca.mcgill.ecse321.tutoringsystem.model.Course;
import ca.mcgill.ecse321.tutoringsystem.model.Tutorial;
import ca.mcgill.ecse321.tutoringsystem.model.Bill;
import ca.mcgill.ecse321.tutoringsystem.model.Review;



@Repository
public class TutoringSystemRepository {
	
	@Autowired
	EntityManager entityManager;

	@Transactional
	public Student createStudent(String name, String email, String username, String password) {
		Student p = new Student();
		p.setName(name);
		p.setEmail(email);
		p.setUsername(email);
		p.setPassword(username);
		entityManager.persist(p);
		return p;
	}
	@Transactional
	public Student getStudent(String name) {
	Student s = entityManager.find(Student.class, name);	
	return s;
	}
	
	@Transactional
	public Tutor createTutor(String name, String email, String username, String password, int hourlyRate, Time avalability) {
		Tutor p = new Tutor();
		p.setName(name);
		p.setPassword(password);
		p.setUsername(email);
		p.setPassword(username);
		p.setHourlyRate(hourlyRate);
		entityManager.persist(p);
		return p;
	}
	@Transactional
	public Course createCrouse(String courseId, String name) {
		Course p = new Course();
		p.setCourseId(courseId);
		p.setCourseName(name);
		entityManager.persist(p);
		return p;
	}
	@Transactional
	public Tutorial createTutorial(String id, String name) {
		Tutorial p = new Tutorial();
		p.setId(id);
		entityManager.persist(p);
		return p;
	}
	@Transactional
	public Session createSession(Time startTime, Time endTime, Date date, String sessionid) {
		Session p = new Session();
		p.setDate(date);
		p.setStartTime(startTime);
		p.setEndTime(endTime);
		p.setSessionId(sessionid);
		entityManager.persist(p);
		return p;
	}
	@Transactional
	public Bill createbill(boolean ispaid, int billid) {
		Bill p = new Bill();
		p.setBillId(billid);
		p.setIsPaid(ispaid);
		entityManager.persist(p);
		return p;
	}
	@Transactional
	public Review createReview(String comment, String ReviewId, int rating) {
		Review p = new Review();
		p.setComment(comment);
		p.setRating(rating);
		p.setReviewId(ReviewId);
		entityManager.persist(p);
		return p;
	}
	
	
	
	
}