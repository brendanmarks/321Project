package ca.mcgill.ecse321.tutoringsystem.dto;


public class TutorialDto {
	
	private TutorDto tutor;
	private CourseDto course;
	
	public TutorialDto() {
	}
	
	public TutorialDto(TutorDto tutor, CourseDto course) {
		this.tutor = tutor;
		this.course = course;
	}
	
	public TutorDto getTutor() {
		return tutor;
	}

	public void setTutor(TutorDto tutor) {
		this.tutor = tutor;
	}
	
	public CourseDto getCourse() {
		return course;
	}

	public void setCourse(CourseDto course) {
		this.course = course;
	}
	
}
