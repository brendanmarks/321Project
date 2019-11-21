package ca.mcgill.ecse321.tutoringsystem.dto;


public class TutorialDto {
	
	private TutorDto tutor;
	private CourseDto course;
	private String id;
	
	/**
	 * Default constructor
	 */
	public TutorialDto() {
	}
	
	/**
	 * Tutorial dto constructor
	 * @param tutor
	 * @param course
	 * @param id
	 */
	public TutorialDto(TutorDto tutor, CourseDto course, String id) {
		this.tutor = tutor;
		this.course = course;
		this.setId(id);
	}
	
	/**
	 * Get tutor
	 * @return tutor dto
	 */
	public TutorDto getTutor() {
		return tutor;
	}
	
	/**
	 * Set tutor of tutorial
	 * @param tutor
	 */
	public void setTutor(TutorDto tutor) {
		this.tutor = tutor;
	}
	
	/**
	 * Get course
	 * @return course dto
	 */
	public CourseDto getCourse() {
		return course;
	}
	
	/**
	 * Set course of tutorial
	 * @param course
	 */
	public void setCourse(CourseDto course) {
		this.course = course;
	}
	
	/**
	 * Get id
	 * @return id
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Set id
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}
	
}
