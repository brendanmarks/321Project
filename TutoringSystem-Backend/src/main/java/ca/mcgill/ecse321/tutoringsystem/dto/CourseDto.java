package ca.mcgill.ecse321.tutoringsystem.dto;

public class CourseDto {
	
	private String courseId;
	private String courseName;
	
	/**
	 * Default constructor
	 */
	public CourseDto() {
	}
	
	/**
	 * Course object constructor
	 * @param courseId
	 * @param courseName
	 */
	public CourseDto(String courseId, String courseName) {
		this.courseId = courseId;
		this.courseName = courseName;
	}
	
	/**
	 * Get course id
	 * @return course id
	 */
	public String getCourseId() {
		return courseId;
	}
	
	/**
	 * Get course name
	 * @return course name
	 */
	public String getCourseName() {
		return courseName;
	}

}
