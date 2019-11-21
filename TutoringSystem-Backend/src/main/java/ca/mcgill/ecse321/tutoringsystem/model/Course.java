package ca.mcgill.ecse321.tutoringsystem.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Set;
import javax.persistence.OneToMany;

@Entity
public class Course{
	private String courseId;

	public void setCourseId(String value) {
		this.courseId = value;
	}
	@Id
	public String getCourseId() {
		return this.courseId;
	}
	private String courseName;

	public void setCourseName(String value) {
		this.courseName = value;
	}
	public String getCourseName() {
		return this.courseName;
	}
	private Set<Tutorial> tutorial;

	@OneToMany(mappedBy="course" )
	public Set<Tutorial> getTutorial() {
		return this.tutorial;
	}

	public void setTutorial(Set<Tutorial> tutorials) {
		this.tutorial = tutorials;
	}

}
