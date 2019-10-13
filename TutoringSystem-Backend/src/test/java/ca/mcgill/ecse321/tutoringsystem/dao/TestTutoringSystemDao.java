package ca.mcgill.ecse321.tutoringsystem.dao;

import static org.junit.Assert.fail;

import java.sql.Time;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//The TestTutoringSystemDao class performs tests on writing to database 
//through Entity Manager from Hibernate.  Refer to TestTutoringSystemService for 
//the formal tests for Sprint 1 using Spring framework’s inbuilt support for CRUD operations.
//Below are two tests

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestTutoringSystemDao {
	
	@Autowired
	private TutoringSystemRepository tutoringSystemRepository;
	
	@Test
	public void testCreateStudent() {
		
		String name = "Sami";
		String email = "sami@gmail.com";
		String username = "sami";
		String password = "samipassword";
		
		try {
			tutoringSystemRepository.createStudent(name, email, username, password);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		
	}
	
	@Test
	public void testCreateCourse() {
		String courseId = "ecse321winter";
		String name = "ecse321";
		
		try {
			tutoringSystemRepository.createCrouse(courseId, name);
			
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
	}

}
