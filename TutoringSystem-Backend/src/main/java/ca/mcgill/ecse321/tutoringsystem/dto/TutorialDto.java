package ca.mcgill.ecse321.tutoringsystem.dto;


public class TutorialDto {
	
	private TutorDto tutor;
	private CourseDto course;
	private String id;
	
	public TutorialDto() {
	}
	
	public TutorialDto(TutorDto tutor, CourseDto course, String id) {
		this.tutor = tutor;
		this.course = course;
		this.setId(id);
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
