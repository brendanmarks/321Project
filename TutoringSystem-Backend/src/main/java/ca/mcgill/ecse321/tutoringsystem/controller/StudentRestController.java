package ca.mcgill.ecse321.tutoringsystem.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.tutoringsystem.dto.SessionDto;
import ca.mcgill.ecse321.tutoringsystem.dto.StudentDto;
import ca.mcgill.ecse321.tutoringsystem.model.Student;
import ca.mcgill.ecse321.tutoringsystem.service.TutoringSystemService;

@CrossOrigin(origins = "*")
@RestController
public class StudentRestController {
	@Autowired
	TutoringSystemService service;

	/**
	 * Create student endpoint
	 * @param name
	 * @param email
	 * @param username
	 * @param password
	 * @return student dto
	 * @throws IllegalArgumentException
	 */
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

	/**
	 * Convert student obj to student dto
	 * @param s
	 * @return student dto
	 */
	static StudentDto convertStudentToDto(Student s) {
		if (s == null) {
			throw new IllegalArgumentException("This student does not exist.");
		}
		ArrayList<SessionDto> studentSessionDtos = new ArrayList<>();
		StudentDto studentDto = new StudentDto(s.getName(), s.getEmail(), s.getUsername(), s.getPassword(), studentSessionDtos);
		return studentDto;
	}
	
	/**
	 * Get all students endpoint
	 * @return list of student dtos
	 */
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
	
	/**
	 * Get student by username
	 * @param studentUsername
	 * @return student dto
	 * @throws IllegalArgumentException
	 */
	@GetMapping(value = {"/students/{studentUsername}","/students/{studentUsername}/"})
	public StudentDto getStudentByUsername(@PathVariable("studentUsername") String studentUsername) throws IllegalArgumentException{
		return convertStudentToDto(service.getStudent(studentUsername));
	}		

}
