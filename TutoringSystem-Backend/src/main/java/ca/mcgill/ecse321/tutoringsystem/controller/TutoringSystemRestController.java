package ca.mcgill.ecse321.tutoringsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import ca.mcgill.ecse321.tutoringsystem.model.*;
import ca.mcgill.ecse321.tutoringsystem.service.*;

@CrossOrigin(origins = "*")
@RestController
public class TutoringSystemRestController {

	@Autowired
	TutoringSystemService service;

	/**
	 * Login method: Verifies credentials
	 * @param studentUsername
	 * @param password
	 * @return status code 200 success or 401 unauthorized
	 * @throws IllegalArgumentException
	 */
	@GetMapping(value = {"/students/{studentUsername}/{password}","/students/{studentUsername}/{password}/"})
	public int login(@PathVariable("studentUsername") String studentUsername, @PathVariable("password") String password) throws IllegalArgumentException{
		Student student = service.getStudent(studentUsername);
		if (student == null) {
			return 401;
		}
		return (password.equals(student.getPassword()) ? 200 : 401);
	}
		
}
