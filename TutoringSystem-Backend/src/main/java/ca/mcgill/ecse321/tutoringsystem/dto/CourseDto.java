package ca.mcgill.ecse321.tutoringsystem.dto;

public class CourseDto {
	
	private String courseId;
	private String courseName;
	
	public CourseDto() {
	}

	public CourseDto(String courseId, String courseName) {
		this.courseId = courseId;
		this.courseName = courseName;
	}
	
	public String getCourseId() {
		return courseId;
	}

	public String getCourseName() {
		return courseName;
	}

}
