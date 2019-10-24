package ca.mcgill.ecse321.tutoringsystem.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.mcgill.ecse321.tutoringsystem.dto.SessionDto;
import ca.mcgill.ecse321.tutoringsystem.dto.StudentDto;
import ca.mcgill.ecse321.tutoringsystem.model.Student;
import ca.mcgill.ecse321.tutoringsystem.service.*;

@CrossOrigin(origins = "*")
@RestController
public class TutoringSystemRestController {

	@Autowired
	TutoringSystemService service;
	
	@PostMapping(value = { 
		"/students/{name}/{email}/{username}/{password}",
		"/students/{name}/{email}/{username}/{password}/" 
	})
	public StudentDto createStudent(
		@PathVariable("name") String name, 
		@PathVariable("email") String email, 
		@PathVariable("username") String username, 
		@PathVariable("password") String password
		)	throws IllegalArgumentException {
		
		//create a person instance using the method in the service class
		Student student = service.createStudent(name, email, username, password);
		return convertToDto(student);	
	}
	
	private StudentDto convertToDto(Student s) {
		if (s == null) {
			throw new IllegalArgumentException("This student does not exist.");
		}
		
		ArrayList<SessionDto> sessionDtos = new ArrayList<>();
		
		StudentDto studentDto = new StudentDto(s.getName(), s.getEmail(), s.getUsername(), s.getPassword(), sessionDtos);
		return studentDto;
	}
	
}
