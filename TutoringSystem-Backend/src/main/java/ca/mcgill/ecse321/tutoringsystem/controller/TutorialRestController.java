package ca.mcgill.ecse321.tutoringsystem.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.tutoringsystem.dto.CourseDto;
import ca.mcgill.ecse321.tutoringsystem.dto.TutorDto;
import ca.mcgill.ecse321.tutoringsystem.dto.TutorialDto;
import ca.mcgill.ecse321.tutoringsystem.model.Course;
import ca.mcgill.ecse321.tutoringsystem.model.Tutor;
import ca.mcgill.ecse321.tutoringsystem.model.Tutorial;
import ca.mcgill.ecse321.tutoringsystem.service.TutoringSystemService;

@CrossOrigin(origins = "*")
@RestController
public class TutorialRestController {
	@Autowired
	TutoringSystemService service;


	/**
	 * Create tutorial endpoint
	 * @param tutorialId
	 * @param tutorName
	 * @param courseId
	 * @return Tutorial Dto
	 * @throws IllegalArgumentException
	 */
	@PostMapping(value = { 
			"/tutorials/{tutorialId}", 
			"/tutorials/{tutorialId}/" 
	})
	public TutorialDto createTutorial(

			@PathVariable("tutorialId") String tutorialId,

			@RequestParam(name = "tutorName") String tutorName, 
			@RequestParam(name = "courseId") String courseId

			)	throws IllegalArgumentException {

		//retrieve the needed tutor instance from the database using the given Name parameter 

		Tutor tutor = service.getTutor(tutorName);


		//retrieve the needed course instance from the database using the given Course ID parameter 

		Course course = service.getCourse(courseId);

		Tutorial newTutorial = service.createTutorial(/*uniqueTutorialID*/tutorialId, course, tutor);

		//Check if tutor already has tutorials assigned to them and saved in database
		Set<Tutorial> tutorTutorials = tutor.getTutorial();

		//If the Tutorial field of the specified tutor instance hasn't been instantiated, we create a new empty Set<Tutorial>
		if(tutorTutorials.equals(null) || tutorTutorials == null || tutorTutorials.size() == 0)	{

			tutorTutorials = new HashSet<Tutorial>();

		}
		//If there already was an existing set of tutorials saved for this specified tutor instance, then we just add the new tutorial to the set
		tutorTutorials.add(newTutorial);

		//Assign the new set of tutorials to the tutor instance
		tutor.setTutorial(tutorTutorials);

		//Call the service method that will update the saved tutor and add the new tutorial
		tutor = service.updateTutor(tutor);		//method at line 151 of service class

		TutorDto tutorDto = TutorRestController.convertTutorToDto(tutor);
		CourseDto courseDto = CourseRestController.convertCourseToDto(course);

		return convertTutorialToDto(tutorDto, courseDto);	
	}	

	/**
	 * Converts tutorial obj to dto
	 * @param t
	 * @param c
	 * @return Tutorial Dto
	 */
	private TutorialDto convertTutorialToDto(TutorDto t, CourseDto c) {
		if (c == null || t == null) {
			throw new IllegalArgumentException("This tutor or course does not exist.");
		}	
		TutorialDto tutorialDto = new TutorialDto(t, c, null);
		return tutorialDto;
	}
	
	/**
	 * Get tutorials by course id and tutor name
	 * @param tutorName
	 * @param courseId
	 * @return list of tutorials
	 */
	@GetMapping(value = {"/tutorials/search","/tutorials/search/"})
	public List<TutorialDto> getTutorialsByCourseAndTutor(@RequestParam(name = "tutorName") String tutorName, @RequestParam(name = "courseId") String courseId) {
		List<TutorialDto> tutorialsDtos = new ArrayList<>();
		try {
			for(Tutorial t : service.getAllTutorials()) {
				if (t.getCourse().getCourseId().equals(courseId) && t.getTutor().iterator().next().getName().equals(tutorName)) {
					tutorialsDtos.add(new TutorialDto(new TutorDto(tutorName, null, null, null, null), new CourseDto(courseId, null), t.getId()));
				}
			}
			return tutorialsDtos;
		}catch(Exception e) {
			System.out.println("Error getting tutorials");
			return null;
		}
	}
	
	/**
	 * Get all tutorials
	 * @return list of tutorials
	 */
	@GetMapping(value = { 
			"/tutorials",
			"/tutorials/" 
	})
	public List<TutorialDto> getAllTutorials() {
		try {
			List<TutorialDto> tutorialDtos = new ArrayList<>();
			for (Tutorial tutorial : service.getAllTutorials()) {

				Set<Tutor> tutors = tutorial.getTutor();

				if (tutors.size() == 0)	{

					String emptyTutorialId = tutorial.getId();
					System.out.println("The tutorial instance with ID "+ emptyTutorialId + " saved in persistance does not have any assigned tutor(s).");
					continue;
				}

				Course course = tutorial.getCourse();
				CourseDto cDto = CourseRestController.convertCourseToDto(course);

				if (tutors.size() > 1)	{

					for(java.util.Iterator<Tutor> iterate = tutors.iterator(); iterate.hasNext();) {
						Tutor t = iterate.next();
						TutorDto tDto = TutorRestController.convertTutorToDto(t);
						tutorialDtos.add(convertTutorialToDto(tDto, cDto));
					} 	

				}	else if (tutors.size() == 1)	{

					Tutor t = tutors.iterator().next();
					TutorDto tDto = TutorRestController.convertTutorToDto(t);

					tutorialDtos.add(convertTutorialToDto(tDto, cDto));
				}
			}	
			return tutorialDtos;			
		}catch(Exception e) {
			System.out.println("Could not get all tutorials");
			return null;
		}
	}
	
	/**
	 * Get tutor from set
	 * @param tutorset
	 * @return tutor
	 */
	private Tutor getTutorFromSet(Set<Tutor> tutorset) {
		Tutor tutor = null;
		if(tutorset.size() == 0) {
			return tutor; //tutor will be null
		}else if(tutorset.size() > 1) {
			for(java.util.Iterator<Tutor> iterate = tutorset.iterator(); iterate.hasNext();) {
				tutor = iterate.next();
				return tutor;
			}
		}else {
			tutor = tutorset.iterator().next();
			return tutor;
		}
		return tutor;//default
	}
	
	/**
	 * Get tutorials of a specific tutor
	 * @param tutorUserName
	 * @return list of tutorials
	 */
	@GetMapping(value = {"/tutorials/tutor/{tutorUserName}","tutorials/tutor/{tutorUserName}/"})
	public List<TutorialDto> getTutorialsOfTutor(@PathVariable("tutorUserName") String tutorUserName){
		try {
			List<TutorialDto> allTutorialsOfTutor  = new ArrayList<>();
			List<Tutorial> allTutorials = service.getAllTutorials();
			Tutor tutor = service.getTutor(tutorUserName);
			TutorDto tutorDto = TutorRestController.convertTutorToDto(tutor);
			//TODO: use tutorDto instead for below???
			for(Tutorial t : allTutorials) {
				if(getTutorFromSet(t.getTutor()).equals(tutor)) {
					CourseDto courseDto = CourseRestController.convertCourseToDto(t.getCourse());
					allTutorialsOfTutor.add(convertTutorialToDto(tutorDto,courseDto));
				}
			}
			return allTutorialsOfTutor;
		}catch(Exception e) {
			System.out.println("Could not return all tutorials of "+tutorUserName);
			return null;
		}
	}
	
	/**
	 * Get tutorial by id
	 * @param tutorialID
	 * @return tutorial dto 
	 * @throws IllegalArgumentException
	 */
	@GetMapping(value = {"/tutorials/{tutorialID}","/tutorials/{tutorialID}/"})
	public TutorialDto getTutorialById(@PathVariable("tutorialID") String tutorialID) throws IllegalArgumentException {
		try {
			TutorialDto tutorialDto = null;
			Tutorial tutorial = service.getTutorial(tutorialID);
			CourseDto courseDto = CourseRestController.convertCourseToDto(tutorial.getCourse());
			Tutor tutor = getTutorFromSet(tutorial.getTutor());
			TutorDto tutorDto = TutorRestController.convertTutorToDto(tutor);
			tutorialDto = convertTutorialToDto(tutorDto,courseDto);
			return tutorialDto;
		}catch(Exception e) {
			System.out.println("TutorialID : "+tutorialID+" not found");
			return null;
		}
	}



}
