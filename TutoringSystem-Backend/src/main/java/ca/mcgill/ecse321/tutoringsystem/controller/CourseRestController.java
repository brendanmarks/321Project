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

import ca.mcgill.ecse321.tutoringsystem.dto.CourseDto;
import ca.mcgill.ecse321.tutoringsystem.model.Course;
import ca.mcgill.ecse321.tutoringsystem.service.TutoringSystemService;

@CrossOrigin(origins = "*")
@RestController
public class CourseRestController {
	@Autowired
	TutoringSystemService service;

	/**
	 * Create course
	 * @param courseId
	 * @param courseName
	 * @return course dto
	 * @throws IllegalArgumentException
	 */
	@PostMapping(value = { 
			"/courses/{courseId}",
			"/courses/{courseId}/" 
	})
	public CourseDto createCourse(
			@PathVariable("courseId") String courseId, 
			@RequestParam(name = "courseName") String courseName 
			)	throws IllegalArgumentException {

		//create a course instance in persistence using the method in the service class
		Course course = service.createCourse(courseId, courseName);
		return convertCourseToDto(course);	
	}

	/**
	 * Converts course obj to course dto
	 * @param c
	 * @return course dto
	 */
	static CourseDto convertCourseToDto(Course c) {
		if (c == null) {
			throw new IllegalArgumentException("This course does not exist.");
		}	
		CourseDto courseDto = new CourseDto(c.getCourseId(), c.getCourseName());
		return courseDto;
	}
	
	/**
	 * Get all courses endpoint
	 * @return list of course dtos
	 */
	@GetMapping(value = {"/courses","/courses/"})
	public List<CourseDto> getAllCourses() {
		List<CourseDto> courseDtos = new ArrayList<>();
		try {
			for(Course c : service.getAllCourses()) {
				courseDtos.add(convertCourseToDto(c));
			}
			return courseDtos;
		}catch(Exception e) {
			System.out.println("Error getting all courses");
			return null;
		}
	}
	
	/**
	 * Get course by id endpoint
	 * @param courseID
	 * @return course dto
	 * @throws IllegalArgumentException
	 */
	@GetMapping(value = {"/courses/{courseID}","/courses/{courseID}/"})
	public CourseDto getCourseByID(@PathVariable("courseID") String courseID) 
			throws IllegalArgumentException{
		try{
			return convertCourseToDto(service.getCourse(courseID));
		}catch(Exception e) {
			System.out.println("Could not get course "+courseID);
			return null;
		}
	}

}
