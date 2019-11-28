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
import ca.mcgill.ecse321.tutoringsystem.dto.TutorDto;
import ca.mcgill.ecse321.tutoringsystem.model.Tutor;
import ca.mcgill.ecse321.tutoringsystem.model.Tutorial;
import ca.mcgill.ecse321.tutoringsystem.service.TutoringSystemService;

@CrossOrigin(origins = "*")
@RestController
public class TutorRestController {
	@Autowired
	TutoringSystemService service;
	
	/**
	 * Create tutor endpoint
	 * @param name
	 * @param email
	 * @param username
	 * @param password
	 * @param rate
	 * @return tutor dto
	 * @throws IllegalArgumentException
	 */
	@PostMapping(value = { 
			"/tutors/{name}",
			"/tutors/{name}/" 
	})
	public TutorDto createTutor(
			@PathVariable("name") String name, 
			@RequestParam(name = "email") String email, 
			@RequestParam(name = "username") String username, 
			@RequestParam(name = "password") String password,
			@RequestParam(name = "rate") Double rate
			)	throws IllegalArgumentException {

		//create a tutor instance in persistence using the method in the service class
		try {
			Tutor tutor = service.createTutor(name, email, username, password, rate);
			return convertTutorToDto(tutor);	
		} catch(Exception e) {
			System.out.println("Error ocured when creating a new tutor with the name: "+name+".");
			return null;
		}	
	}

	/**
	 * Converts tutor obj to dto
	 * @param t
	 * @return tutor dto
	 */
	static TutorDto convertTutorToDto(Tutor t) {
		if (t == null) {
			throw new IllegalArgumentException("This tutor does not exist.");
		}
		ArrayList<SessionDto> tutorSessionDtos = new ArrayList<>();	
		TutorDto tutorDto = new TutorDto(t.getName(), t.getEmail(), t.getUsername(), t.getPassword(), tutorSessionDtos, t.getHourlyRate());
		return tutorDto;
	}
	
	/**
	 * Get tutors of a course by id
	 * @param courseId
	 * @return list of tutors
	 */
	@GetMapping(value = {"/tutors/course","/tutors/course/"})
	public List<TutorDto> getTutorsByCourse(@RequestParam(name = "courseId") String courseId) {
		List<TutorDto> tutorsDtos = new ArrayList<>();
		try {
			for (Tutorial tut : service.getAllTutorials()) {
				if (tut.getCourse().getCourseId().equals(courseId)) {
					for(Tutor t : tut.getTutor()) {
						tutorsDtos.add(new TutorDto(t.getName(), t.getEmail(), t.getUsername(), t.getPassword(), null, t.getHourlyRate()));
					}
				}
			}
			return tutorsDtos;
		}catch(Exception e) {
			System.out.println("Error getting tutors ");
			return null;
		}
	}
	
	/**
	 * Get all tutors
	 * @return list of all tutors
	 */
	@GetMapping(value = {"/tutors","/tutors/"})
	public List<TutorDto> getAllTutors(){
		try {
			List<Tutor> allTutors = service.getAllTutors();
			List<TutorDto> tutorDtos = new ArrayList<>();
			for(Tutor s : allTutors) {
				tutorDtos.add(convertTutorToDto(s));
			}
			return tutorDtos;			
		}catch(Exception e) {
			System.out.println("Could not get all tutors");
			return null;
		}
	}
	
	/**
	 * Get tutor by username
	 * @param tutorUsername
	 * @return tutor dto
	 * @throws IllegalArgumentException
	 */
	@GetMapping(value = {"/tutors/{tutorUsername}","/tutors/{tutorUsername}/"})
	public TutorDto getTutorByUsername(@PathVariable("tutorUsername") String tutorUsername) throws IllegalArgumentException {
		try {
			return convertTutorToDto(service.getTutor(tutorUsername));
		}catch(Exception e) {
			System.out.println("Could not get tutor "+tutorUsername);
			return null;
		}
	}

}
